package com.okhi.okcollect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.okhi.android_core.OkHi;
import io.okhi.android_core.interfaces.OkHiRequestHandler;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.OkCollect;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;
import io.okhi.android_okcollect.utilities.OkHiConfig;
import io.okhi.android_okcollect.utilities.OkHiTheme;

import static com.okhi.okcollect.Secret.TEST_PHONE;

public class MainActivity extends AppCompatActivity {

    private OkCollect okCollect;
    private OkHi okhi;
    private Button launchBtn;
    private OkHiTheme theme = new OkHiTheme.Builder("#00fdaa")
        .setAppBarLogo("https://cdn.okhi.co/icon.png")
        .setAppBarColor("#ba0c2f")
        .build();
    private OkHiConfig config = new OkHiConfig.Builder()
        .withStreetView()
        .withWorkAddressType(false)
        .withHomeAddressType(true)
        .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchBtn = findViewById(R.id.launchBtn);
        try {
            okhi = new OkHi(this);
            okCollect = new OkCollect.Builder(this)
                .withTheme(theme)
                .withConfig(config)
                .build();
        } catch (OkHiException exception) {
            exception.printStackTrace();
        }
    }

    private void launchOkCollect(){
        boolean canStartOkCollect = canStartAddressCreation();
        if(canStartOkCollect) {
            OkHiUser user = new OkHiUser.Builder(TEST_PHONE)
                .withFirstName("Julius")
                .withLastName("Kiano")
                .build();
            okCollect.launch(user, new OkCollectCallback<OkHiUser, OkHiLocation>() {
                @Override
                public void onSuccess(OkHiUser user, OkHiLocation location) {
                    showMessage(user.getPhone() + " " + location.getId());
                }
                @Override
                public void onError(OkHiException e) {
                    showMessage(e.getCode() + " " + e.getMessage());
                }
            });
        }
    }

    private void showMessage(String log){
        Toast.makeText(MainActivity.this,log,Toast.LENGTH_LONG).show();
    }

    class Handler implements OkHiRequestHandler<Boolean> {
        @Override
        public void onResult(Boolean result) {
            if (result) launchOkCollect();
        }
        @Override
        public void onError(OkHiException exception) {
            showMessage(exception.getMessage());
        }
    }

    // Define a method you'll use to check if conditions are met to start okverify - this method will be added in the lib on the next update
    private boolean canStartAddressCreation() {
        Handler requestHandler = new Handler();
        // Check and request user to enable location services
        if (!OkHi.isLocationServicesEnabled(getApplicationContext())) {
            okhi.requestEnableLocationServices(requestHandler);
        } else if (!OkHi.isGooglePlayServicesAvailable(getApplicationContext())) {
            // Check and request user to enable google play services
            okhi.requestEnableGooglePlayServices(requestHandler);
        } else if (!OkHi.isLocationPermissionGranted(getApplicationContext())) {
            // Check and request user to grant location permission
            okhi.requestLocationPermission("Hey we need location permissions", "Pretty please..", requestHandler);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Pass permission results to okhi
        okhi.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass activity results results to okhi
        okhi.onActivityResult(requestCode, resultCode, data);
    }

    public void handleButtonTap(View view) {
        launchOkCollect();
    }
}