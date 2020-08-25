package io.okhi.android_okcollect;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import io.okhi.android_core.OkHiCore;
import io.okhi.android_core.interfaces.OkHiSignInRequestHandler;
import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;

import static io.okhi.android_okcollect.Constants.SCOPES;

public class OkCollect extends OkHiCore {
    private OkHiAppContext okHiAppContext;
    private String primaryColor;
    private String url;
    private String appBarColor;
    private Boolean enableStreetView;
    private Activity activity;
    private static OkCollectCallback <OkHiUser, OkHiLocation> okCollectCallback;

    private OkCollect(Builder builder) {
        super(builder.okHiAuth);
        this.activity = builder.activity;
        this.primaryColor = builder.primaryColor;
        this.url = builder.url;
        this.appBarColor = builder.appBarColor;
        this.enableStreetView = builder.enableStreetView;
    }

    public void launch(@NonNull final OkHiUser user, @NonNull final OkCollectCallback <OkHiUser,
            OkHiLocation> okCollectCallback){
        this.okCollectCallback = okCollectCallback;
        Intent intent = new Intent(activity, OkHeartActivity.class);
        intent.putExtra("phone", user.getPhone());
        intent.putExtra("firstName", user.getFirstName());
        intent.putExtra("lastName", user.getLastName());
        activity.startActivity(intent);
    }

    public void signin(){
        anonymousSignWithPhoneNumber("phone", SCOPES, new OkHiSignInRequestHandler() {
            @Override
            public void onSuccess(String authorizationToken) {

            }

            @Override
            public void onError(OkHiException exception) {
                OkCollect.getOkCollectCallback().onError(exception);
            }
        });
    }

    public static class Builder {
        private OkHiAuth okHiAuth;
        private String primaryColor;
        private String url;
        private String appBarColor;
        private Boolean enableStreetView;
        private Activity activity;
        public Builder(@NonNull OkHiAuth okHiAuth, @NonNull Activity activity) {
            this.okHiAuth = okHiAuth;
            this.activity = activity;
        }
        public Builder withTheme(@NonNull OkHiTheme okHiTheme){
            this.primaryColor = okHiTheme.getPrimaryColor();
            this.url = okHiTheme.getUrl();
            this.appBarColor = okHiTheme.getAppBarColor();
            return this;
        }
        public Builder withConfig(@NonNull OkHiConfig okHiConfig){
            this.enableStreetView = okHiConfig.getEnableStreetView();
            return this;
        }
        public OkCollect build() {
            return new OkCollect(this);
        }
    }

    public OkHiAppContext getOkHiAppContext() {
        return okHiAppContext;
    }

    public void setOkHiAppContext(OkHiAppContext okHiAppContext) {
        this.okHiAppContext = okHiAppContext;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppBarColor() {
        return appBarColor;
    }

    public void setAppBarColor(String appBarColor) {
        this.appBarColor = appBarColor;
    }

    public Boolean getEnableStreetView() {
        return enableStreetView;
    }

    public void setEnableStreetView(Boolean enableStreetView) {
        this.enableStreetView = enableStreetView;
    }

    public static OkCollectCallback<OkHiUser, OkHiLocation> getOkCollectCallback() {
        return okCollectCallback;
    }

    public void setOkCollectCallback(OkCollectCallback<OkHiUser, OkHiLocation> okCollectCallback) {
        this.okCollectCallback = okCollectCallback;
    }

    private void displayLog(String log){
        Log.i("OkCollect", log);
    }
}
