package io.okhi.android_okcollect;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiMode;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;


public class OkHeartActivity extends AppCompatActivity {
    private static WebView myWebView;
    private Boolean enableStreetView;
    private String phone, firstName, lastName,environment,developerName,
            authorizationToken,primaryColor,url,appBarColor, organisationName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okheart);
        myWebView = findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        processBundle(bundle);
        setupWebView();
    }
    private void processBundle(Bundle bundle){
        try {
            phone = bundle.getString("phone");
        }
        catch (Exception e){
            displayLog("phone bundle.get error "+phone);
        }
        try{
            firstName = bundle.getString("firstName");
        }
        catch (Exception e){
            displayLog("firstName bundle.get error "+firstName);
        }
        try{
            lastName = bundle.getString("lastName");
        }
        catch (Exception e){
            displayLog("lastName bundle.get error "+lastName);
        }
        try{
            environment = bundle.getString("environment");
        }
        catch (Exception e){
            displayLog("environment bundle.get error "+environment);
        }
        try{
            authorizationToken = bundle.getString("authorizationToken");
        }
        catch (Exception e){
            displayLog("authorizationToken bundle.get error "+authorizationToken);
        }
        try{
            primaryColor = bundle.getString("primaryColor");
        }
        catch (Exception e){
            displayLog("primaryColor bundle.get error "+primaryColor);
        }
        try{
            developerName = bundle.getString("developerName");
        }
        catch (Exception e){
            displayLog("developerName bundle.get error "+developerName);
        }
        try{
            url = bundle.getString("url");
        }
        catch (Exception e){
            displayLog("url bundle.get error "+url);
        }
        try{
            appBarColor = bundle.getString("appBarColor");
        }
        catch (Exception e){
            displayLog("appBarColor bundle.get error "+appBarColor);
        }
        try{
            organisationName = bundle.getString("organisationName");
        }
        catch (Exception e){
            displayLog("organisationName bundle.get error "+organisationName);
        }
        try{
            enableStreetView = bundle.getBoolean("enableStreetView");
        }
        catch (Exception e){
            displayLog("enableStreetView bundle.get error "+enableStreetView);
        }
    }

    private void setupWebView(){
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        myWebView.addJavascriptInterface(new WebAppInterface(io.okhi.android_okcollect.OkHeartActivity.this), "Android");
        if(environment.equalsIgnoreCase(OkHiMode.PROD)){
            myWebView.loadUrl("https://manager-v5.okhi.io");
        }
        else if(environment.equalsIgnoreCase(OkHiMode.SANDBOX)){
            myWebView.loadUrl("https://sandbox-manager-v5.okhi.io");
        }
        else{
            myWebView.loadUrl("https://dev-manager-v5.okhi.io");
        }
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
    }

    public void receiveMessage(String results) {
        try {
            final JSONObject jsonObject = new JSONObject(results);
            String message = jsonObject.optString("message");
            JSONObject payload = jsonObject.optJSONObject("payload");
            switch (message) {
                case "app_state":
                    launchHeart();
                    break;
                case "location_created":
                    displayLog("location_created");
                    processResponse(results);
                    break;
                case "location_updated":
                    displayLog("location_updated");
                    processResponse(results);
                    break;
                case "location_selected":
                    displayLog("location_selected");
                    processResponse(results);
                    break;
                case "fatal_exit":
                    displayLog("fatal_exit");
                    processError(results);
                    break;
                default:
                    displayLog("default");
                    processError(results);
                    break;
            }
        } catch (JSONException e) {
            displayLog("Json object error "+e.toString());
        }
    }

    private void launchHeart(){
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("message", "select_location");
                        JSONObject payload1 = new JSONObject();
                        JSONObject style = new JSONObject();
                        JSONObject base = new JSONObject();
                        if (primaryColor != null) {
                            if (primaryColor.length() > 0) {
                                base.put("color", primaryColor);
                            }
                        }
                        if (organisationName != null) {
                            if (organisationName.length() > 0) {
                                base.put("name", organisationName);
                            }
                        }
                        if (url != null) {
                            if (url.length() > 0) {
                                base.put("logo", url);
                            }
                        }
                        style.put("base", base);
                        payload1.put("style", style);

                        JSONObject user = new JSONObject();
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("phone", phone);
                        payload1.put("user", user);

                        JSONObject auth = new JSONObject();
                        auth.put("authToken", authorizationToken);
                        payload1.put("auth", auth);

                        JSONObject context = new JSONObject();
                        JSONObject container = new JSONObject();
                        container.put("name", "okCollectMobileAndroid");
                        container.put("version", BuildConfig.VERSION_NAME);
                        context.put("container", container);

                        JSONObject developer = new JSONObject();
                        developer.put("name", developerName);
                        context.put("developer", developer);

                        JSONObject library = new JSONObject();
                        library.put("name", "okCollectMobileAndroid");
                        library.put("version", BuildConfig.VERSION_NAME);
                        context.put("library", library);

                        JSONObject platform = new JSONObject();
                        platform.put("name", "mobile");
                        context.put("platform", platform);
                        payload1.put("context", context);

                        JSONObject config = new JSONObject();
                        if (enableStreetView != null) {
                            config.put("streetView", enableStreetView);
                        }
                        JSONObject appBar = new JSONObject();
                        if (appBarColor != null) {
                            if (appBarColor.length() > 0) {
                                appBar.put("color", appBarColor);
                                appBar.put("visible", true);
                                config.put("appBar", appBar);
                            }
                            else{
                                appBar.put("visible", false);
                                config.put("appBar", appBar);
                            }
                        }
                        else{
                            appBar.put("visible", false);
                            config.put("appBar", appBar);
                        }
                        payload1.put("config", config);
                        jsonObject.put("payload", payload1);
                        Log.i("okheart", jsonObject.toString().replace("\\", ""));
                        myWebView.evaluateJavascript("javascript:receiveAndroidMessage(" +
                                jsonObject.toString().replace("\\", "") + ")", null);
                    } catch (Exception e) {
                        displayLog( "Json object error "+e.toString());
                    }
                }
            });
        }
        catch (Exception e){
            displayLog("error running on UI thread "+e.toString());
        }
    }

    private void processResponse(String response){
        try{
            JSONObject responseObject = new JSONObject(response);
            JSONObject payloadObject = responseObject.optJSONObject("payload");
            JSONObject locationObject = payloadObject.optJSONObject("location");
            JSONObject userObject = payloadObject.optJSONObject("user");
            OkHiUser user = new OkHiUser.Builder(userObject.optString("phone"))
                    .withFirstName(userObject.optString("first_name"))
                    .withLastName(userObject.optString("last_name"))
                    .withOkHiUserId(userObject.optString("id"))
                    .build();
            String ualId = locationObject.optString("id");
            Double lat = locationObject.optJSONObject("geo_point").getDouble("lat");
            Double lng = locationObject.optJSONObject("geo_point").getDouble("lon");
            String streetName = locationObject.optString("street_name");
            String propertyName = locationObject.optString("property_name");
            String propertyNumber = locationObject.optString("property_number");
            String directions = locationObject.optString("directions");
            String placeId = locationObject.optString("place_id");
            String url = locationObject.optString("url");
            String title = locationObject.optString("title");
            String photo = locationObject.optString("photo");
            String subtitle = locationObject.optString("subtitle");
            String plusCode = locationObject.optString("plus_code");
            String displayTitle = locationObject.optString("display_title");
            OkHiLocation location = new OkHiLocation.Builder(ualId,lat,lng)
                    .setDirections(directions)
                    .setUrl(url)
                    .setPlaceId(placeId)
                    .setPlusCode(plusCode)
                    .setTitle(title)
                    .setSubtitle(subtitle)
                    .setPropertyName(propertyName)
                    .setStreetName(streetName)
                    .setOtherInformation(displayTitle)
                    .build();
            OkCollectApplication.getOkCollectCallback().onSuccess(user,location);
            finish();
        }
        catch (Exception e){
            displayLog("Json object error "+e.toString());
        }
    }

    private void processError(String response){
        try {
            final JSONObject jsonObject = new JSONObject(response);
            String message = jsonObject.optString("message");
            JSONObject payload = jsonObject.optJSONObject("payload");
            if (payload == null) {
                String backuppayload = jsonObject.optString("payload");
                if (backuppayload != null) {
                    payload = new JSONObject();
                    payload.put("error", backuppayload);
                }
            }
            jsonObject.put("payload", payload);
            jsonObject.put("message", "fatal_exit");
            OkCollectApplication.getOkCollectCallback().onError(new OkHiException("fatal_exit", payload.toString()));
            finish();
        }
        catch (Exception e){
            displayLog("json error exception "+e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String urlString) {
        }
    }
    private void displayLog(String log){
        Log.i("OkHeartActivity", log);
    }
}