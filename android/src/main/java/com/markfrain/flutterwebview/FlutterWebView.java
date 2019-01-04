package com.markfrain.flutterwebview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.plugin.platform.PlatformView;

import static io.flutter.plugin.common.MethodChannel.MethodCallHandler;

/**
 * Created by diy on 2019/1/3.
 */
class FlutterWebView implements PlatformView, MethodCallHandler {

    Context context;
    Registrar registrar;
    WebView webView;
    String url = "";
    MethodChannel channel;
    EventChannel.EventSink onPageFinishEvent;
    EventChannel.EventSink onPageStartEvent;


    @SuppressLint("SetJavaScriptEnabled")
    FlutterWebView(Context context, Registrar registrar, int id) {
        this.context = context;
        this.registrar = registrar;
        this.url = url;
        webView = getWebView(registrar);

        channel = new MethodChannel(registrar.messenger(), "flutter_webview_channel_" + id);
        final EventChannel onLoadStartEvenetChannel = new EventChannel(registrar.messenger(), "flutter_webview_loadStart_" + id);
        final EventChannel onLoadFinishEvenetChannel = new EventChannel(registrar.messenger(), "flutter_webview_loadFinish_" + id);
        onLoadStartEvenetChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {
                onPageStartEvent = eventSink;
            }

            @Override
            public void onCancel(Object o) {

            }
        });
        onLoadFinishEvenetChannel.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object o, EventChannel.EventSink eventSink) {
                onPageFinishEvent = eventSink;
            }

            @Override
            public void onCancel(Object o) {

            }
        });
        channel.setMethodCallHandler(this);
    }

    @Override
    public View getView() {
        return webView;
    }

    @Override
    public void dispose() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private WebView getWebView(Registrar registrar) {
        WebView webView = new WebView(registrar.context());
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        return webView;
    }


    private class CustomWebViewClient extends WebViewClient {
        @SuppressWarnings("deprecated")
        @Override
        public boolean shouldOverrideUrlLoading(WebView wv, String url) {
            if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp")) {
                return false;
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                registrar.activity().startActivity(intent);
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (onPageStartEvent != null) {
                onPageStartEvent.success(url);
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (onPageFinishEvent != null) {
                onPageFinishEvent.success(url);
            }
            super.onPageFinished(view, url);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        switch (call.method) {
            case "loadUrl":
                String url = call.arguments.toString();
                webView.loadUrl(url);
                break;
            case "loadData":
                String html = call.arguments.toString();
                webView.loadData(html, "text/html", "utf-8");
                break;
            case "evalJs":
                String code = call.arguments.toString();
                webView.evaluateJavascript(code,, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        result.success(value);
                    }
                });
                break;
            default:
                result.notImplemented();
        }

    }

}
