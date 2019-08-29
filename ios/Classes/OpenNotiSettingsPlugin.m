#import "OpenNotiSettingsPlugin.h"
#import <open_noti_settings/open_noti_settings-Swift.h>

@implementation OpenNotiSettingsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftOpenNotiSettingsPlugin registerWithRegistrar:registrar];
}
@end
