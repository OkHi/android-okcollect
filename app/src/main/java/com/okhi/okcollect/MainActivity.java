package com.okhi.okcollect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.OkCollect;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;
import io.okhi.android_okcollect.utilities.OkHiConfig;
import io.okhi.android_okcollect.utilities.OkHiTheme;

public class MainActivity extends AppCompatActivity {
    private OkCollect okCollect;
    private EditText emailTextField;
    private EditText firstNameTextField;
    private EditText lastNameTextField;
    private EditText phoneNumberTextField;

    private Switch enablePermissionsOnboardingSwitch;
    private OkHiUser user;

    private OkHiTheme theme = new OkHiTheme.Builder("#333")
        .setAppBarLogo("https://cdn.okhi.co/icon.png")
        .setAppBarColor("#795548")
        .build();
    private OkHiConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailTextField = findViewById(R.id.emailTextField);
        firstNameTextField = findViewById(R.id.firstNameTextField);
        lastNameTextField = findViewById(R.id.lastNameTextField);
        phoneNumberTextField = findViewById(R.id.phoneNumberTextField);
        enablePermissionsOnboardingSwitch = findViewById(R.id.enablePermissionsOnboarding);
    }

    private void launchOkCollect(){
        config = new OkHiConfig.Builder()
          .withStreetView()
          .withWorkAddressType(false)
          .withHomeAddressType(true)
          .withPermissionsOnboarding(enablePermissionsOnboardingSwitch.isChecked())
          .build();
        try {
            okCollect = new OkCollect.Builder(this)
              .withTheme(theme)
              .withConfig(config)
              .build();
            okCollect.launch(user, new OkCollectCallback<OkHiUser, OkHiLocation>() {
                @Override
                public void onSuccess(OkHiUser user, OkHiLocation location) {
                    showMessage(user.getId() + ":" + location.getId());
                }
                @Override
                public void onError(OkHiException e) {
                    showMessage(e.getCode()+":"+e.getMessage());
                }
                @Override
                public void onClose() {
                    showMessage("User closed");
                }
            });
        } catch (OkHiException e) {
            showMessage(e.getCode()+":"+e.getMessage());
        }
    }

    private void showMessage(String log){
        Toast.makeText(MainActivity.this,log,Toast.LENGTH_LONG).show();
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public void handleButtonTap(View view) {
        String email = getText(emailTextField);
        String phoneNumber = getText(phoneNumberTextField);
        String firstName = getText(firstNameTextField);
        String lastName = getText(lastNameTextField);
        if (email.length() > 0 && phoneNumber.length() > 0 && firstName.length() > 0 && lastName.length() > 0) {
            user = new OkHiUser.Builder(phoneNumber).withEmail(email).withFirstName(firstName).withLastName(lastName).build();
            launchOkCollect();
        } else {
            showMessage("Fill out all required fields");
        }
    }
}