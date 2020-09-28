package io.okhi.android_okcollect.interfaces;

import android.util.Log;
import android.webkit.JavascriptInterface;

import io.okhi.android_okcollect.activity.OkHeartActivity;

public class WebAppInterface {
    OkHeartActivity mContext;
    public WebAppInterface(OkHeartActivity c) {
        mContext = c;
    }
    @JavascriptInterface
    public void receiveMessage(String results) {
        mContext.receiveMessage(results);
    }
}

