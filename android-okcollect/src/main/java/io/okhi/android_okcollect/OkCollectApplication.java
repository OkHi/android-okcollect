package io.okhi.android_okcollect;

import android.app.Application;

import java.util.HashMap;

import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;

public class OkCollectApplication extends Application {
    private static final String TAG = "OkCollectApplication";
    private static OkCollectCallback<OkHiUser, OkHiLocation> okCollectCallback;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public static OkCollectCallback<OkHiUser, OkHiLocation> getOkCollectCallback() {
        return okCollectCallback;
    }
    public static void setOkCollectCallback(OkCollectCallback<OkHiUser, OkHiLocation> okCollectCallback) {
        OkCollectApplication.okCollectCallback = okCollectCallback;
    }
}
