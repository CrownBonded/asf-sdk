package com.asf.appcoins.sdk.ads;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.asf.appcoins.sdk.ads.poa.PoAManager;
import com.asf.appcoins.sdk.ads.poa.PoAServiceConnector;

import static com.asf.appcoins.sdk.ads.poa.MessageListener.MSG_REGISTER_CAMPAIGN;
import static com.asf.appcoins.sdk.ads.poa.MessageListener.MSG_SEND_PROOF;

/**
 * Created by Joao Raimundo on 01-03-2018.
 */

final class AdvertisementSdkImpl implements AdvertisementSdk {

  private final PoAServiceConnector poaConnector;

  private Context context;

  AdvertisementSdkImpl(PoAServiceConnector poaConnector) {
    this.poaConnector = poaConnector;
  }


  @Override public void handshake() {
    poaConnector.startHandshake(context);
  }

  @Override public void sendProof(Bundle bundle) {
    if (poaConnector.connectToService(context)) {
      poaConnector.sendMessage(context, MSG_SEND_PROOF, bundle);
    }
  }

  @Override public void registerCampaign(Bundle bundle) {
    if (poaConnector.connectToService(context)) {
      poaConnector.sendMessage(context, MSG_REGISTER_CAMPAIGN, bundle);
    }
  }

  @Override public void init(Application application) {
    this.context = application;
    LifeCycleListener.get(application).setListener(PoAManager.get(application, poaConnector));
  }
}
