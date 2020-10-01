package io.okhi.android_okcollect;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import io.okhi.android_core.OkHiCore;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.activity.OkHeartActivity;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;
import io.okhi.android_okcollect.utilities.OkHiConfig;
import io.okhi.android_okcollect.utilities.OkHiTheme;

import static io.okhi.android_okcollect.utilities.Constants.SCOPES;

/** OkCollect class.
 * @author Ramogi Ochola
 * @author www.okhi.com
 */
public class OkCollect extends OkHiCore {
    private OkHiAppContext okHiAppContext;
    private String primaryColor;
    private String logoUrl;
    private String appBarColor;
    private String environment;
    private String organisationName;
    private String developer;
    private Boolean enableStreetView;
    private Boolean enableAppBar;
    private static Activity activity;

    private OkCollect(Builder builder) {
        super(builder.okHiAuth);
        this.activity = builder.activity;
        this.primaryColor = builder.primaryColor;
        this.logoUrl = builder.logoUrl;
        this.appBarColor = builder.appBarColor;
        this.enableStreetView = builder.enableStreetView;
        this.enableAppBar = builder.enableAppBar;
        this.environment = builder.okHiAuth.getContext().getMode();
        this.organisationName = builder.okHiAuth.getContext().getAppMeta().getName();
        this.developer = builder.okHiAuth.getContext().getDeveloper();
    }
    /** launch okhi address creation.
     * @param okCollectCallback the callback param to communicate with the parent class.
     * @param user the user object to create an address for.
     */
    public void launch(@NonNull final OkHiUser user, @NonNull final OkCollectCallback <OkHiUser,
            OkHiLocation> okCollectCallback){
        getAuth(user);
        Intent intent = new Intent(activity, OkHeartActivity.class);
        intent.putExtra("params", getParameters(user));
        activity.startActivity(intent);
        OkHeartActivity.setOkCollectCallback(okCollectCallback);
    }

    private String getParameters(OkHiUser user ){
        String params = null;
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", user.getPhone());
            jsonObject.put("firstName", user.getFirstName());
            jsonObject.put("lastName", user.getLastName());
            jsonObject.put("environment", environment);
            jsonObject.put("primaryColor", primaryColor);
            jsonObject.put("logoUrl", logoUrl);
            jsonObject.put("appBarColor", appBarColor);
            jsonObject.put("enableStreetView", enableStreetView);
            jsonObject.put("enableAppBar", enableAppBar);
            jsonObject.put("developerName", developer);
            jsonObject.put("organisationName", organisationName);
            params = jsonObject.toString();
        }
        catch (Exception e){
            displayLog("Json processing error "+e.toString());
        }
        return params;
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
    /** OkCollect builder class.
     */
    public static class Builder {
        private OkHiAuth okHiAuth;
        private String primaryColor;
        private String logoUrl;
        private String appBarColor;
        private Boolean enableStreetView;
        private Boolean enableAppBar;
        private Activity activity;
        /** OkCollect builder.
         * @param okHiAuth the auth object to enable authentication.
         * @param activity the context to run okhi webview.
         */
        public Builder(@NonNull OkHiAuth okHiAuth, @NonNull Activity activity) {
            this.okHiAuth = okHiAuth;
            this.activity = activity;
        }
        /** Configure the look and feel of the webview.
         * Makes the app bar visible. Default is false, app bar not visible.
         * @param okHiTheme object to configure the look and feel of the webview.
         */
        public Builder withTheme(@NonNull OkHiTheme okHiTheme){
            this.primaryColor = okHiTheme.getPrimaryColor();
            this.logoUrl = okHiTheme.getLogoUrl();
            this.appBarColor = okHiTheme.getAppBarColor();
            this.enableAppBar = okHiTheme.isAppBarVisible();
            return this;
        }
        /** launch okhi address creation.
         * Enables streetview functionality in address creation. Default is false, streetview functionality not enabled.
         * @param okHiConfig object to enable streetview.
         */
        public Builder withConfig(@NonNull OkHiConfig okHiConfig){
            this.enableStreetView = okHiConfig.isStreetViewEnabled();
            return this;
        }
        /** Create an instance of okcollect
         */
        public OkCollect build() {
            return new OkCollect(this);
        }
    }

    public static Activity getActivity() {
        return activity;
    }

    private void displayLog(String log){
        Log.i("OkCollect", log);
    }
}
