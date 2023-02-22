package com.sandbox.plugin.services.sandbox_plugin_services;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GPIO {
    private final static String TAG = "GPIO";
    private final static boolean DEBUG = true;

    private final static int GPIO23_INDEX  = 0;
    private final static int GPIO24_INDEX = 1;

    private final static int GPIO23_PORT  = 23;
    private final static int GPIO24_PORT  = 24;

    private Context mContext;
    private Process mProcess;
    private Handler mUpdateLevelHander;
    public ReadLevelThread  mReadLevelThread;
    private int LOOP_TIME = 200;

    public GPIO(Handler handler){
        this.mUpdateLevelHander = handler;
    }

    private final static int[] ALL_GPIO_PORTS = {
            GPIO23_PORT,
            GPIO24_PORT
    };


    //DEFAULT_DIRECTION : 0--Input  1---Output
    private String DIRECTION_INPUT = "in";
    private String DIRECTION_OUTPUT = "out";
    private String DEFAULT_DIRECTION = DIRECTION_INPUT;

    //DEFAULT_LEVEL :  0---low  1---high
    private int LEVEL_LOW = 0;
    private int LEVEL_HIGH = 1;
    private int DEFAULT_LEVEL = LEVEL_HIGH;

    private List<GPIO_Attr> mList = new ArrayList<GPIO_Attr>();

    private void exportAllGPIO() {
        for (int port : ALL_GPIO_PORTS) {
            exportGPIO(port);
        }
    }




    public void InitDefaultState() throws IOException {
        mProcess = Runtime.getRuntime().exec("su");

        exportAllGPIO();
        for (int port : ALL_GPIO_PORTS) {
            setDirection(port,DEFAULT_DIRECTION);
            setLevel(DEFAULT_LEVEL,port);
        }

        for(int i=0;i<ALL_GPIO_PORTS.length;i++){
            GPIO_Attr mGPIO_Attr = new GPIO_Attr(ALL_GPIO_PORTS[i], DEFAULT_DIRECTION);
            mList.add(mGPIO_Attr);
        }

//        mUpdateLevelHander = new UpdateLevelHander();
        mReadLevelThread   = new ReadLevelThread();
        mReadLevelThread.setStop(false);
        mReadLevelThread.start();

    }

    //set value of the output
    private boolean setLevel(int value,int port){
        String command = String.format("echo %d > /sys/class/gpio/gpio%s/value", value,port);
        try {
            String[] test = new String[] {"su", "-c", command};
            Runtime.getRuntime().exec(test);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // set direction
    public boolean setDirection(int port,String direction){
        String command = String.format("echo %s > /sys/class/gpio/gpio%s/direction", direction,port);
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    //export gpio
    public boolean exportGPIO(int port){
        String command = String.format("echo %d > /sys/class/gpio/export", port);
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // unexport gpio
    public boolean unexportGPIO(int port){
        String command = String.format("echo %d > /sys/class/gpio/unexport", port);
        try {
            Runtime.getRuntime().exec(new String[] {"su", "-c", command});
            return true;
        } catch (IOException e) {
            return false;
        }
    }



    private int getLevel(int port) {

        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("cat " + "/sys/class/gpio/gpio" + port + "/value");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line ;
            while (null != (line = br.readLine())) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(TAG,e.getMessage());
        }
        return -1;

    }

    class GPIO_Attr {
        int port;
        String direction;


        public GPIO_Attr(int port, String direction){
            setport(port);
            setdirection(direction);
        }
        private void setport(int port){
            this.port = port;
        }
        private int getport(){
            return this.port;
        }
        private void setdirection(String direction){
            this.direction = direction;
        }
        private String getdirection(){
            return this.direction;
        }

    }

    public class ReadLevelThread extends Thread {

        private int port;
        private boolean stop = false;
        private List<GPIO_Attr> list = new ArrayList<GPIO_Attr>();


        @Override
        public void run() {
            // TODO Auto-generated method stub
//            Log.d(TAG, "run: ReadLevelThread");
            while(true){
                if (!stop){
                    for (int i=0;i<mList.size();i++){
                        if (mList.get(i).direction == DIRECTION_INPUT) {
                            Message msg = new Message();
                            int level = getLevel(mList.get(i).port);
                            msg.what = i;
                            msg.arg1 = level;
//                            Log.d(TAG, "run: ReadLevelThread"+level);
                            mUpdateLevelHander.sendMessage(msg);
                        }
                    }
                    try {
                        Thread.sleep(LOOP_TIME);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        public void setStop(boolean stop) {
            this.stop = stop;
        }

    }


}
