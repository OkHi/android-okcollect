package io.okhi.android_okcollect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;
import io.okhi.android_okcollect.utilities.OkHiConfig;
import io.okhi.android_okcollect.utilities.OkHiTheme;

public class MainActivity extends AppCompatActivity {
    public static final String DEV_CLIENT_KEY = "4d380065-71e5-48b8-8fb3-29fe61299c4b";
    public static final String DEV_CLIENT_BRANCH = "yhCvQnGG1z";

    private static final OkHiAppContext okhiAppContext = new OkHiAppContext.Builder("dev")
            .setDeveloper("OkHi")
            .setPlatform("Android")
            .setAppMeta("OkHi", "1.0.0", 1)
            .build();

    private static final OkHiAuth okhiAuth = new OkHiAuth.Builder(DEV_CLIENT_BRANCH, DEV_CLIENT_KEY)
            .withContext(okhiAppContext)
            .build();

    private OkCollect okCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final OkHiUser user = new OkHiUser.Builder("+254713567907")
                .withFirstName("Ramogi")
                .withLastName("Ochola")
                .build();

        OkHiTheme theme = new OkHiTheme.Builder("#333")
                .withAppBar("https://mydomain.com/logo.png", "#222")
                .build();

        OkHiConfig config = new OkHiConfig.Builder()
                .withStreetView()
                .build();

        okCollect = new OkCollect.Builder(okhiAuth, this)
                .withTheme(theme)
                .withConfig(config)
                .build();

        Button launch = findViewById(R.id.launchBtn);
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLog("launch button clicked");
                okCollect.launch(user, new OkCollectCallback<OkHiUser, OkHiLocation>() {
                    @Override
                    public void onSuccess(OkHiUser result1, OkHiLocation result2) {
                        displayLog("onsuccess");
                    }

                    @Override
                    public void onError(OkHiException e) {
                        displayLog("onerror");
                    }
                });
            }
        });
    }
    private void displayLog(String log){
        Log.i("MainActivity", log);
    }
}