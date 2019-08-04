import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

abstract class AdEventListener {
  void adHidden();

  void adDisplayed();

  void adClicked();

  void adNotDisplayed();

  void onReceiveAd();

  void onFailedToReceiveAd();

  void onVideoCompleted();
}

abstract class MobileAd {
  MobileAd({@required this.listener, @required this.method});

  AdEventListener listener;
  String method;

  Future<bool> load() {
    return _invokeBooleanMethod(method);
  }

  Future<bool> show() {
    return _invokeBooleanMethod('ShowAd');
  }
}

class Startapp {
  static const MethodChannel _channel = const MethodChannel('flutter_startapp');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}

Future<bool> _invokeBooleanMethod(String method, [dynamic arguments]) async {
  final bool result = await Startapp._channel.invokeMethod<bool>(
    method,
    arguments,
  );
  return result;
}
