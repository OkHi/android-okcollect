package io.okhi.android_okcollect;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import io.okhi.android_core.OkHiCore;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;
import io.okhi.android_okcollect.utilities.OkHiConfig;
import io.okhi.android_okcollect.utilities.OkHiTheme;

import static io.okhi.android_okcollect.utilities.Constants.SCOPES;

public class OkCollect extends OkHiCore {
    private OkHiAppContext okHiAppContext;
    private String primaryColor;
    private String url;
    private String appBarColor;
    private String environment;
    private String organisationName;
    private String developer;
    private Boolean enableStreetView;
    private Activity activity;

    private OkCollect(Builder builder) {
        super(builder.okHiAuth);
        this.activity = builder.activity;
        this.primaryColor = builder.primaryColor;
        this.url = builder.url;
        this.appBarColor = builder.appBarColor;
        this.enableStreetView = builder.enableStreetView;
        this.environment = builder.okHiAuth.getContext().getMode();
        this.organisationName = builder.okHiAuth.getContext().getAppMeta().getName();
        this.developer = builder.okHiAuth.getContext().getDeveloper();
    }

    public void launch(@NonNull final OkHiUser user, @NonNull final OkCollectCallback <OkHiUser,
            OkHiLocation> okCollectCallback){
        getAuth(user);
        Intent intent = new Intent(activity, OkHeartActivity.class);
        intent.putExtra("phone", user.getPhone());
        intent.putExtra("firstName", user.getFirstName());
        intent.putExtra("lastName", user.getLastName());
        intent.putExtra("environment", environment);
        intent.putExtra("primaryColor", primaryColor);
        intent.putExtra("url", url);
        intent.putExtra("appBarColor", appBarColor);
        intent.putExtra("enableStreetView", enableStreetView);
        intent.putExtra("developerName", developer);
        intent.putExtra("organisationName", organisationName);
        activity.startActivity(intent);
        OkHeartActivity.setOkCollectCallback(okCollectCallback);
    }

    private void getAuth(OkHiUser user){
        anonymousSignWithPhoneNumber(user.getPhone(), SCOPES, new OkHiRequestHandler<String>() {
            @Override
            public void onResult(String result) {
                OkHeartActivity.setAuthorization(result);
            }
            @Override
            public void onError(OkHiException exception) {
                OkHeartActivity.setAuthorization("error");
                OkHeartActivity.setOkHiException(exception);
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
    private void displayLog(String log){
        Log.i("OkCollect", log);
    }
}
