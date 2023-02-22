import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:sandbox_plugin_services/sandbox_plugin_services.dart';

const channelName = "sandbox_plugin_services";
const methodChannel = MethodChannel(channelName);
void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _btn1 = '1';
  String _btn2 = '1';
  final _sandboxPluginServicesPlugin = SandboxPluginServices();

  @override
  void initState() {
    super.initState();
    methodChannel.setMethodCallHandler(onBtnCall);
  }



  Future<void> onBtnCall(MethodCall call) async {
    // print("method "+call.method);
    switch(call.method){
      case "getBtn1":
        setState(() {
          _btn1 = call.arguments.toString();
        });
      break;
      case "getBtn2":
        setState(() {
          _btn2 = call.arguments.toString();
        });
        break;
    }

  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Sandbox plugin services'),
        ),
        body:  Container(
          margin: EdgeInsets.all(30),

          width: double.infinity,
          height: 100,
          child: Row(
            children: [
              Expanded(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    // UEC607 & Digital communication text
                    Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: <Widget>[
                            Text(
                              'IO Event',
                              style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey
                              ),
                            ),
                          ],
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: <Widget>[
                            Text(
                              'Button IO 1',
                              style: TextStyle(
                                  fontSize: 20,
                                  color: Colors.black
                              ),
                            ),
                          ],
                        )
                      ],
                    ),

                    Expanded(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: <Widget>[
                          Text(
                            'Value: Btn1:$_btn1',
                            style: TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                                color: Colors.black
                            ),
                          ),
                        ],
                      ),
                    )
                  ],
                ),
              ),

              // Card
              Container(
                width: 90,

                color:  _btn1=="1" ? Colors.lightBlue.withOpacity(.8):Colors.red.withOpacity(.8),

              ),
              Expanded(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    // UEC607 & Digital communication text
                    Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: <Widget>[
                            Text(
                              'IO Event',
                              style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.bold,
                                  color: Colors.grey
                              ),
                            ),
                          ],
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: <Widget>[
                            Text(
                              'Button IO 1',
                              style: TextStyle(
                                  fontSize: 20,
                                  color: Colors.black
                              ),
                            ),
                          ],
                        )
                      ],
                    ),

                    Expanded(
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: <Widget>[
                          Text(
                            'Value:Btn2:$_btn2',
                            style: TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                                color: Colors.black
                            ),
                          ),
                        ],
                      ),
                    )
                  ],
                ),
              ),

              // Card
              Container(
                width: 90,

                color:  _btn2=="1" ? Colors.lightBlue.withOpacity(.8):Colors.red.withOpacity(.8),

              )
            ],
          ),

        ),
      ),
    );
  }
}
