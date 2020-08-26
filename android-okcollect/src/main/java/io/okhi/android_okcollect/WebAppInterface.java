package io.okhi.android_okcollect;

import android.util.Log;
import android.webkit.JavascriptInterface;

class WebAppInterface {
    OkHeartActivity mContext;
    WebAppInterface(OkHeartActivity c) {
        mContext = c;
    }
    @JavascriptInterface
    public void receiveMessage(String results) {
        displayLog(results);
        mContext.receiveMessage(results);
    }
    private void displayLog(String log){
        Log.i("WebAppInterface", log);
    }
}

