import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter_startapp/flutter_startapp.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  AdEventListener _listener = Event();

  @override
  void initState() {
    super.initState();
    Startapp.init(appId: '207038995', defaultAd: true);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
            child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            MaterialButton(
              onPressed: () {
                Interstitial(listener: _listener)..load();
              },
              color: Colors.lightGreen,
              child: Text('Show Interstitial Ad'),
            ),
            MaterialButton(
              onPressed: () {
                Rewarded(listener: _listener)..load();
              },
              color: Colors.lightGreen,
              child: Text('Show Rewarded Ad'),
            )
          ],
        )),
      ),
    );
  }
}

class Event implements AdEventListener {
  @override
  void adClicked() {
    // TODO: implement adClicked
  }

  @override
  void adDisplayed() {
    // TODO: implement adDisplayed
  }

  @override
  void adHidden() {
    // TODO: implement adHidden
  }

  @override
  void adNotDisplayed() {
    // TODO: implement adNotDisplayed
  }

  @override
  void onFailedToReceiveAd() {
    // TODO: implement onFailedToReceiveAd
  }

  @override
  void onReceiveAd() {
    Startapp.showAd();
  }

  @override
  void onVideoCompleted() {
    print('Hola Mundo');
  }
}
