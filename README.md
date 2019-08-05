# flutter_startapp

**Note: Currently only Android platform is supported.**

**Note: AndroidX is required.**

**Note: only show interstitial and video rewarded ads.**

## Getting Started

1. Initialization

Call `Startapp.init();` during app initialization.

```dart
 Startapp.init(appId: 'your_app_id', defaultAd: true or false);
```

### 2. Request Interstitial Ad and Rewarded Video Ad

```dart
 Interstitial(listener: _listener)..load();
```

**On Class Listener implements AdEventListener**

```dart
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
  // TODO: implement onVideoCompleted
  }
}
```

## Events

| Event              | Description                                                                        |
|--------------------|------------------------------------------------------------------------------------|
| onReceiveAd    | Called in response to an ad request when the request has been successfully filled. |
| onFailedToReceiveAd | Called in response to an ad request when the request failed to fill.               |
| adDisplayed           | Called when the interstitial ad opens.                                             |
| adNotDisplayed         | Called when an ad not open          |
| adClicked         | Called when an user click to ad            |
| adHidden         | Called when an ad is close        |
| onVideoCompleted           | Called when the rewarded video ends successfully.                                  |


## Future Work
Implement for iOS platform, Banner Ads, NativeAds,

## [More Info]("https://support.startapp.com/hc/en-us/articles/115007225767-Integrate-our-SDK")

