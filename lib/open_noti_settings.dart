import 'dart:async';

import 'package:flutter/services.dart';

class NotificationSetting {
  static const _channel = MethodChannel('open_noti_settings');

  static Future<void> open() async {
    final String version = await _channel.invokeMethod('open');
    return version;
  }

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
