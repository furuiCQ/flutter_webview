import 'dart:async';
import 'webview_controller.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
typedef void WebViewCallback(WebViewController controller);

class FlutterWebView extends StatefulWidget {

  const FlutterWebView({
    Key key,
    @required this.callback,
  }) : super(key: key);

  final WebViewCallback callback;


  @override
  State createState() => new _FlutterWebViewState();
}

class _FlutterWebViewState extends State<FlutterWebView> {
  @override
  Widget build(BuildContext context) {
    if (defaultTargetPlatform == TargetPlatform.android) {

      return AndroidView(
        viewType: 'flutter_webview',
        onPlatformViewCreated: onPlatformCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      return UiKitView(
        viewType: 'flutter_webview',
        onPlatformViewCreated: onPlatformCreated,
        creationParamsCodec: const StandardMessageCodec(),
      );
    }

    return new Text(
        '$defaultTargetPlatform is not yet supported by this plugin');
  }

  Future<void> onPlatformCreated(id)  async {
    if (widget.callback == null) {
        return;
    }
    widget.callback(new WebViewController.init(id));
  }
}