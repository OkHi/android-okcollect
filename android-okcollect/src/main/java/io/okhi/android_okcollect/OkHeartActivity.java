package io.okhi.android_okcollect;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class OkHeartActivity extends AppCompatActivity {
    private static WebView myWebView;
    private String phone, firstName, lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okheart);
        myWebView = findViewById(R.id.webview);
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
        myWebView.loadUrl("https://dev-manager-v5.okhi.io");
        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");
        firstName = bundle.getString("firstName");
        lastName = bundle.getString("lastName");
    }

    public void receiveMessage(String results) {
        try {
            final JSONObject jsonObject = new JSONObject(results);
            String message = jsonObject.optString("message");
            JSONObject payload = jsonObject.optJSONObject("payload");
            switch (message) {
                case "app_state":
                    startApp();
                    break;
                case "location_created":
                    displayLog("location_created");
                    break;
                case "location_updated":
                    displayLog("location_updated");
                    break;
                case "location_selected":
                    displayLog("location_selected");
                    break;
                case "fatal_exit":
                    displayLog("fatal_exit");
                    break;
                default:
                    displayLog("default");
                    break;
            }
        } catch (JSONException e) {
            displayLog("Json object error "+e.toString());
        }
    }
    protected void startApp(){
        displayLog("start app");
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