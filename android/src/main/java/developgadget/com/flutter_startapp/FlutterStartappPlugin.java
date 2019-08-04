package developgadget.com.flutter_startapp;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterStartappPlugin */
public class FlutterStartappPlugin implements MethodCallHandler {

  private final Registrar registrar;
  private final MethodChannel channel;
  private final StartAppAd startAppAd;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_startapp");
    channel.setMethodCallHandler(new FlutterStartappPlugin(registrar, channel));
  }

  private FlutterStartappPlugin(Registrar registrar, MethodChannel channel){
    this.registrar = registrar;
    this.channel = channel;
    this.startAppAd = new StartAppAd(registrar.context());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    switch (call.method) {
      case "initialize":
        callInitialize(call, result);
        break;
      case "loadInterstitial":
       // callLoadInterstitial(call, result);
        break;
      case "showAd":
       // callShowAd(call, result);
        break;
      case "loadRewarded":
        //callLoadRewarded(call, result);
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

  private void callOnBackPressed(MethodCall call, Result result){
    StartAppAd.onBackPressed(this.registrar.context());
    result.success(Boolean.TRUE);
  }


}
