import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:open_noti_settings/open_noti_settings.dart';

void main() {
  const MethodChannel channel = MethodChannel('open_noti_settings');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await OpenNotiSettings.platformVersion, '42');
  });
}
