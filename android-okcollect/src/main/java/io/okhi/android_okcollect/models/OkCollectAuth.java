package io.okhi.android_okcollect.models;

import android.content.Context;

import androidx.annotation.NonNull;

import io.okhi.android_core.OkHiCore;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiAccessScope;
import io.okhi.android_core.models.OkHiException;

public class OkCollectAuth extends OkHiCore {
  public OkCollectAuth(@NonNull Context context) throws OkHiException {
    super(context);
  }

  public void fetchAuthToken(String phone, OkHiRequestHandler<String> handler) {
    String[] scopes = {OkHiAccessScope.VERIFY};
    anonymousSignWithPhoneNumber(phone, scopes, new OkHiRequestHandler<String>() {
      @Override
      public void onResult(String s) {
        handler.onResult(s);
      }
      @Override
      public void onError(OkHiException e) {
        handler.onError(e);
      }
    });
  }
}
