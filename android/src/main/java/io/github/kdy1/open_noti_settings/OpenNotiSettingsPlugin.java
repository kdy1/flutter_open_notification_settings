package io.github.kdy1.open_noti_settings;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** OpenNotiSettingsPlugin */
public class OpenNotiSettingsPlugin implements MethodCallHandler {
    private final Activity activity;

    private OpenNotiSettingsPlugin(Activity activity) {
        this.activity = activity;
    }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "open_noti_settings");
    channel.setMethodCallHandler(new OpenNotiSettingsPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("open")) {
        openSettings(call, result);
    } else if (call.method.equals("configureChannel")) {
        configureChannel(call, result);
    } else {
        result.notImplemented();
    }
  }

    private void openSettings(MethodCall call, Result result) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

        intent.putExtra("app_package", activity.getPackageName());
        intent.putExtra("app_uid", activity.getApplicationInfo().uid);

        intent.putExtra("android.provider.extra.APP_PACKAGE", activity.getPackageName());
        activity.startActivity(intent);
        result.success(null);
    }

    private void configureChannel(MethodCall call, Result result) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

          String id = call.argument("channelId");
          if (id == null) throw new RuntimeException("channelId is nul");

          CharSequence name = call.argument("channelName");
          if (name == null) throw new RuntimeException("name is nul");

          String description = call.argument("channelDescription");
          if (description == null) throw new RuntimeException("description is nul");

          Integer importance = call.argument("importance");
          if (importance == null) {
              importance = NotificationManager.IMPORTANCE_DEFAULT;
          }
          NotificationChannel channel = new NotificationChannel(id, name, importance);
          channel.setDescription(description);
//          channel.setGroup((String) call.argument("groupKey"));
//          // TODO
////          channel.setLightColor();
//          if (Boolean.valueOf(true).equals(call.argument("playSound"))) {
//              AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build();
//              Uri uri = retrieveSoundResourceUri(activity, (String) call.argument("sound"));
//              channel.setSound(uri, audioAttributes);
//          } else {
////              channel.setSound(null,null);
//          }
//
//          channel.enableVibration(Boolean.valueOf(true).equals(call.argument("enableVibration")));
//
//          if(call.hasArgument("vibrationPattern")){
//              channel.setVibrationPattern((long[]) call.argument("vibrationPattern"));
//          }
//
//          boolean enableLights = Boolean.valueOf(true).equals(call.argument("enableLights"));
//          channel.enableLights(enableLights);
//          if (enableLights && call.hasArgument("ledColorAlpha") && call.hasArgument("ledColorRed")
//                  && call.hasArgument("ledColorGreen") && call.hasArgument("ledColorBlue")) {
//              channel.setLightColor(Color.argb(
//                  (int) call.argument("ledColorAlpha"),
//                  (int) call.argument("ledColorRed"),
//                  (int) call.argument("ledColorGreen"),
//                  (int) call.argument("ledColorBlue")
//              ));
//          }
//          channel.setShowBadge(Boolean.valueOf(true).equals(call.argument("channelShowBadge")));
          // Register the channel with the system; you can't change the importance
          // or other notification behaviors after this
          NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
          notificationManager.createNotificationChannel(channel);
        }
      result.success(null);
    }

    private static Uri retrieveSoundResourceUri(Context context, String sound) {
        Uri uri;
        if (sound==null || sound.equals("")) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        } else {

            int soundResourceId = context.getResources().getIdentifier(sound, "raw", context.getPackageName());
            return Uri.parse("android.resource://" + context.getPackageName() + "/" + soundResourceId);
        }
        return uri;
    }
}
