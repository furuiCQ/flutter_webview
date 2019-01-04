import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_webview/flutter_webview.dart';
import 'package:flutter_webview/webview_controller.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }
  WebViewController webViewController;
  void callBack(webViewController){
    this.webViewController = webViewController;
    this.webViewController.loadUrl('http://www.baidu.com');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: FlutterWebView(callback:callBack),
        ),
      ),
    );
  }
}
