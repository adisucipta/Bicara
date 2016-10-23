package com.sharinproject.bicara;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPref = new SharedPref(this);

        Intent serviceIntent = new Intent(SplashActivity.this, BackgroundService.class);
        startService(serviceIntent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPref.getAlias().equals("")){
                    Log.w("Splash", "No Alias Found");
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Log.w("Splash", "No Alias Found");
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
