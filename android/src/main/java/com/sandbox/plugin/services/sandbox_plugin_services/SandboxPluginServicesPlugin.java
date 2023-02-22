package com.sandbox.plugin.services.sandbox_plugin_services;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Reader;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** SandboxPluginServicesPlugin */
public class SandboxPluginServicesPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private final static String TAG = "PLUGIN";
  private Reader mReader;

  private final Handler handler = new Handler(Looper.getMainLooper());
  private int btn1;
  private int btn2;

  public int getBtn1(){
    return btn1;
  }

  private void setBtn1(int val){
    this.btn1 = val;
  }

  private int getBtn2(){
    return btn2;
  }

  private void setBtn2(int val){
    this.btn2 = val;
  }


  Handler gpioHandler = new UpdateLevelHander();
  GPIO gpio = new GPIO(gpioHandler);


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "sandbox_plugin_services");
    channel.setMethodCallHandler(this);

    initIO();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {


    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if ("getBtn1".equals(call.method)) {
      Log.d(TAG, "onMethodCall: Get Btn1");
      result.success(getBtn1());
    } else if ("getBtn2".equals(call.method)) {
      Log.d(TAG, "onMethodCall: Get Btn2");
    result.success(getBtn2());
  } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }


  private void initIO(){
    try {
      gpio.InitDefaultState();
      Log.d("DBG", "initIO: ");


    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public class UpdateLevelHander extends Handler {

    @Override
    public void handleMessage(Message msg) {

      Integer val;

      switch(msg.what) {
        case 0:
          if(msg.arg1 == 1) {
            setBtn1(1);
            onBtn1Changed("1");
//            Log.d(TAG, "onCreate: GPIO23 "+getBtn1());
          }else {
            setBtn1(0);
            onBtn1Changed("0");
//            Log.d(TAG, "onCreate: GPIO23 "+getBtn1());
          }
          break;
        case 1:
          if(msg.arg1 == 1) {
            setBtn2(msg.arg1);
            onBtn2Changed("1");
//            Log.d(TAG, "onCreate: GPIO24 "+getBtn2());
          }else {
            setBtn2(0);
            onBtn2Changed("0");
//            Log.d(TAG, "onCreate: GPIO24 "+getBtn2());
          }
          break;
      }
    }

  }

  void onBtn1Changed(String val) {
    handler.post(() -> channel.invokeMethod("getBtn1", val));
  }

  void onBtn2Changed(String val) {
    handler.post(() -> channel.invokeMethod("getBtn2", val));
  }


}
