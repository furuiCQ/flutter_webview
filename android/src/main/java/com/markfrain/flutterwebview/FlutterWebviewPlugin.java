package com.markfrain.flutterwebview;

import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterWebviewPlugin */
public class FlutterWebviewPlugin {
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    registrar
            .platformViewRegistry()
            .registerViewFactory(
                    "flutter_webview", new FlutterWebViewFactory(registrar));
  }
}
