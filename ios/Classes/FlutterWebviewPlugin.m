#import "FlutterWebviewPlugin.h"
#import "FlutterWebView.h"

@implementation FlutterWebviewPlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
   FlutterNativeWebFactory* webViewFactory =
        [[FlutterNativeWebFactory alloc] initWithMessenger:registrar.messenger];
    [registrar registerViewFactory:webViewFactory withId:@"flutter_webview"];
}
@end
