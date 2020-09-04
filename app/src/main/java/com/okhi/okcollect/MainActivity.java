package com.okhi.okcollect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import io.okhi.android_core.models.OkHiAppContext;
import io.okhi.android_core.models.OkHiAuth;
import io.okhi.android_core.models.OkHiException;
import io.okhi.android_core.models.OkHiLocation;
import io.okhi.android_core.models.OkHiMode;
import io.okhi.android_core.models.OkHiUser;
import io.okhi.android_okcollect.OkCollect;
import io.okhi.android_okcollect.callbacks.OkCollectCallback;
import io.okhi.android_okcollect.utilities.OkHiConfig;
import io.okhi.android_okcollect.utilities.OkHiTheme;

public class MainActivity extends AppCompatActivity {

    private static final OkHiAppContext okhiAppContext = new OkHiAppContext.Builder("dev")
            .setDeveloper("OkHi")
            .setPlatform("Android")
            .setAppMeta("OkHi", "1.0.0", 1)
            .build();

    private static final OkHiAuth okhiAuth = new OkHiAuth.Builder(Secret.branch, Secret.clientkey)
            .withContext(okhiAppContext)
            .build();

    private OkCollect okCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final OkHiUser user = new OkHiUser.Builder(Secret.phone)
                .withFirstName("Ramogi")
                .withLastName("Ochola")
                .build();

        OkHiTheme theme = new OkHiTheme.Builder("#ba0c2f")
                .withAppBar("https://cdn.okhi.co/icon.png", "#ba0c2f")
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
                    public void onSuccess(OkHiUser user, OkHiLocation location) {
                        displayLog("onsuccess "+user.getPhone()+" "+location.getId());
                    }

                    @Override
                    public void onError(OkHiException e) {
                        displayLog("onerror "+e.toString());
                    }
                });
            }
        });
    }
    private void displayLog(String log){
        Log.i("MainActivity", log);
    }
}