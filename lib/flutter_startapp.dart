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
    Startapp.listener = listener;
    return _invokeBooleanMethod(method);
  }
}

class Interstitial extends MobileAd {
  Interstitial({@required AdEventListener listener})
      : super(listener: listener, method: "LoadInterstitial");
}

class Rewarded extends MobileAd {
  Rewarded({@required AdEventListener listener})
      : super(listener: listener, method: "LoadRewarded");
}

class Startapp {
  static const MethodChannel _channel = const MethodChannel('flutter_startapp');
  static AdEventListener listener;

  static Future<dynamic> init(
      {@required String appId, @required bool defaultAd}) async {
    _channel.setMethodCallHandler(_handleMethod);
    return await _invokeBooleanMethod(
        'Init', {'AppId': appId, 'Default': defaultAd});
  }

  static Future<dynamic> onBackPressed() async =>
      await _invokeBooleanMethod('OnBackPressed', null);

  static Future<dynamic> autoInterstitialTime(int seconds) async =>
      await _invokeBooleanMethod('AutoInterstitialTime', {'Seconds': seconds});

  static Future<dynamic> autoInterstitialActivities(int activities) async =>
      await _invokeBooleanMethod(
          'AutoInterstitialActivities', {'Activities': activities});

  static Future<dynamic> showAd() async => await _invokeBooleanMethod('ShowAd');

  static Future<dynamic> disableSplash() async =>
      await _invokeBooleanMethod('DisableSplash');

  static Future<dynamic> autoInterstitial(bool enable) async =>
      await _invokeBooleanMethod('AutoInterstitial', {'Enable': enable});

  static Future<dynamic> _handleMethod(MethodCall call) {
    if (call.method == "adHidden") {
      if (listener != null && listener.adHidden != null) listener.adHidden();
    } else if (call.method == "adDisplayed") {
      if (listener != null && listener.adDisplayed != null)
        listener.adDisplayed();
    } else if (call.method == "adClicked") {
      if (listener != null && listener.adClicked != null) listener.adClicked();
    } else if (call.method == "adNotDisplayed") {
      if (listener != null && listener.adNotDisplayed != null)
        listener.adNotDisplayed();
    } else if (call.method == "onReceiveAd") {
      if (listener != null && listener.onReceiveAd != null)
        listener.onReceiveAd();
    } else if (call.method == "onFailedToReceiveAd") {
      if (listener != null && listener.onFailedToReceiveAd != null)
        listener.onFailedToReceiveAd();
    } else if (call.method == "onVideoCompleted") {
      if (listener != null && listener.onVideoCompleted != null)
        listener.onVideoCompleted();
    }
    return Future<dynamic>.value(null);
  }
}

Future<bool> _invokeBooleanMethod(String method, [dynamic arguments]) async {
  final bool result = await Startapp._channel.invokeMethod<bool>(
    method,
    arguments,
  );
  return result;
}
