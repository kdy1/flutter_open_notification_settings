import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class NotificationSetting {
  static const _channel = MethodChannel('open_noti_settings');

  static Future<void> open() async {
    await _channel.invokeMethod('open');
  }

  /// Configures channel on android. This is no-op on ios.
  static Future<void> configureChannel(
    NotificationDetails notificationDetails,
  ) async {
    if (Platform.isIOS) {
      return;
    }

    var serializedPlatformSpecifics =
        _retrievePlatformSpecificNotificationDetails(notificationDetails);
    await _channel.invokeMethod(
        'configureChannel', serializedPlatformSpecifics);
  }

  static Map<String, dynamic> _retrievePlatformSpecificNotificationDetails(
      NotificationDetails notificationDetails) {
    Map<String, dynamic> serializedPlatformSpecifics;
    serializedPlatformSpecifics = {
      'channelId': notificationDetails?.android?.channelId,
      'channelName': notificationDetails?.android?.channelName,
      'channelDescription': notificationDetails?.android?.channelDescription,
      'importance': notificationDetails?.android?.importance?.value,
      'groupKey': notificationDetails?.android?.groupKey,
      'playSound': notificationDetails?.android?.playSound,
      'sound': notificationDetails?.android?.sound?.toString(),
      'enableVibration': notificationDetails?.android?.enableVibration,
      'vibrationPattern': notificationDetails?.android?.vibrationPattern,
      'enableLights': notificationDetails?.android?.enableLights,
      'ledColorRed': notificationDetails?.android?.ledColor?.red,
      'ledColorGreen': notificationDetails?.android?.ledColor?.green,
      'ledColorBlue': notificationDetails?.android?.ledColor?.blue,
      'ledColorAlpha': notificationDetails?.android?.ledColor?.alpha,
      'channelShowBadge': notificationDetails?.android?.channelShowBadge,
    };
    return serializedPlatformSpecifics;
  }
}
