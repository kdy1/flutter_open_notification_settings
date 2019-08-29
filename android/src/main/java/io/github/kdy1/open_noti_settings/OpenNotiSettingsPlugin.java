package io.github.kdy1.open_noti_settings;

import android.app.Activity;
import android.content.Intent;

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
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

        intent.putExtra("app_package", activity.getPackageName());
        intent.putExtra("app_uid", activity.getApplicationInfo().uid);

        intent.putExtra("android.provider.extra.APP_PACKAGE", activity.getPackageName());
        activity.startActivity(intent);
        result.success("");
    } else {
        result.notImplemented();
    }
  }
}
