import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
//import 'package:flutter_startapp/flutter_startapp.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_startapp');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    //expect(await St.platformVersion, '42');
  });
}
