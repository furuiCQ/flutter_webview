import 'dart:async';
import 'package:flutter/services.dart';

class WebViewController {
  MethodChannel _channel;
  EventChannel _LoadStart;
  EventChannel _LoadFinsih;

  WebViewController.init(int id){
    _channel = new MethodChannel('flutter_webview_$id');
    _LoadStart = EventChannel('flutter_webview_loadStart_$id');
    _LoadFinsih = EventChannel('flutter_webview_loadFinish_$id');
  }
  Future<void> loadUrl(String url) async {
    assert(url != null);
    return _channel.invokeMethod('loadUrl', url);
  }

  Future<void> loadData(String data) async {
    assert(data != null);
    return _channel.invokeMethod('loadData', data);
  }

  Future<void> evalJs(String code) async {
  assert(code != null);
  return _channel.invokeMethod('evalJs', code);
  }

  Stream<String> get onLoadStart{
    var url = _LoadStart
        .receiveBroadcastStream()
        .map<String>(
    (element) => element);
    return url;
  }
  Stream<String> get onLoadFinsih {
    var url = _LoadFinsih
        .receiveBroadcastStream()
        .map<String>(
    (element) => element);
    return url;
  }

}