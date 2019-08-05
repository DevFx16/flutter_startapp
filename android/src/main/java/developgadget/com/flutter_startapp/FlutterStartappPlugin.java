package developgadget.com.flutter_startapp;

import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterStartappPlugin
 */
public class FlutterStartappPlugin implements MethodCallHandler {

    private final Registrar registrar;
    private final MethodChannel channel;
    private final StartAppAd startAppAd;
    private final AdEventListener adEventListener;
    private final AdDisplayListener adDisplayListener;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_startapp");
        channel.setMethodCallHandler(new FlutterStartappPlugin(registrar, channel));
    }

    private FlutterStartappPlugin(Registrar registrar, MethodChannel channel) {
        this.registrar = registrar;
        this.channel = channel;
        this.startAppAd = new StartAppAd(registrar.context());
        StartAppSDK.setUserConsent(this.registrar.context(),
                "pas",
                System.currentTimeMillis(),
                false);
        this.adDisplayListener = new AdDisplayListener() {
            @Override
            public void adHidden(Ad ad) {
                FlutterStartappPlugin.this.channel.invokeMethod("adHidden", null);
                Log.d("adHidden", "Hidden");
            }

            @Override
            public void adDisplayed(Ad ad) {
                FlutterStartappPlugin.this.channel.invokeMethod("adDisplayed", null);
                Log.d("adDisplayed", "complete");
            }

            @Override
            public void adClicked(Ad ad) {
                FlutterStartappPlugin.this.channel.invokeMethod("adClicked", null);
                Log.d("adClicked", "Click");
            }

            @Override
            public void adNotDisplayed(Ad ad) {
                FlutterStartappPlugin.this.channel.invokeMethod("adNotDisplayed", null);
                Log.d("adNotDisplayed", ad.getErrorMessage());
            }
        };
        this.adEventListener = new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                FlutterStartappPlugin.this.channel.invokeMethod("onReceiveAd", null);
		Log.d("onFailedToReceiveAd", "Ad ready");
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                FlutterStartappPlugin.this.channel.invokeMethod("onFailedToReceiveAd", null);
                Log.d("onFailedToReceiveAd", ad.getErrorMessage());
            }
        };
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        switch (call.method) {
            case "Init":
                callInitialize(call, result);
                break;
            case "OnBackPressed":
                callOnBackPressed(call, result);
                break;
            case "AutoInterstitialTime":
                callAutoInterstitialTime(call, result);
                break;
            case "DisableSplash":
                callDisableSplash(call, result);
                break;
            case "AutoInterstitial":
                callAutoIterstitial(call, result);
                break;
            case "LoadInterstitial":
                callLoadInterstitial(call, result);
                break;
            case "LoadRewarded":
                callLoadRewarded(call, result);
                break;
            case "AutoInterstitialActivities":
                callAutoInterstitialActivities(call, result);
                break;
            case "ShowAd":
                callShowAd(call, result);
                break;
            default:
                result.notImplemented();
                break;
        }
        return;
    }

    private void callInitialize(MethodCall call, Result result) {
        StartAppSDK.init(this.registrar.activity(), call.argument("AppId").toString(), (Boolean) call.argument("Default"));
        result.success(Boolean.TRUE);
    }

    private void callShowAd(MethodCall call, Result result) {
        startAppAd.showAd(FlutterStartappPlugin.this.adDisplayListener);
        result.success(Boolean.TRUE);
    }

    private void callOnBackPressed(MethodCall call, Result result) {
        StartAppAd.onBackPressed(this.registrar.context());
        result.success(Boolean.TRUE);
    }

    private void callAutoInterstitialTime(MethodCall call, Result result) {
        StartAppAd.setAutoInterstitialPreferences(new AutoInterstitialPreferences().setSecondsBetweenAds((int) call.argument("Seconds")));
        result.success(Boolean.TRUE);
    }

    private void callAutoInterstitialActivities(MethodCall call, Result result) {
        StartAppAd.setAutoInterstitialPreferences(new AutoInterstitialPreferences().setActivitiesBetweenAds((int) call.argument("Activities")));
        result.success(Boolean.TRUE);
    }

    private void callDisableSplash(MethodCall call, Result result) {
        StartAppAd.disableSplash();
        result.success(Boolean.TRUE);
    }

    private void callAutoIterstitial(MethodCall call, Result result) {
        if ((Boolean) call.argument("Enable")) StartAppAd.enableAutoInterstitial();
        else StartAppAd.disableAutoInterstitial();
        result.success(Boolean.TRUE);
    }

    private void callLoadInterstitial(MethodCall call, Result result) {
        startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC, this.adEventListener);
        result.success(Boolean.TRUE);
    }

    private void callLoadRewarded(MethodCall call, Result result) {
        startAppAd.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                FlutterStartappPlugin.this.channel.invokeMethod("onVideoCompleted", null);
                Log.d("onVideoCompleted", "Complete");
            }
        });
        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, this.adEventListener);
        result.success(Boolean.TRUE);
    }

}
