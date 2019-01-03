package com.markfrain.flutterwebview;

import android.content.Context;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

/**
 * Created by diy on 2019/1/3.
 */

public class FlutterWebViewFactory extends PlatformViewFactory {

    private final PluginRegistry.Registrar mPluginRegistrar;

    public FlutterWebViewFactory(PluginRegistry.Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        mPluginRegistrar = registrar;
    }

    @Override
    public PlatformView create(Context context, int i, Object o) {
        return new FlutterWebView(context, mPluginRegistrar, i);
    }

}
