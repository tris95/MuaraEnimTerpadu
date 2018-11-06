package id.go.muaraenimkab.mance.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.utils.Utilities;

/**
 * Created by Tris on 4/5/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {
    Thread splashTread;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View decorView = getWindow().getDecorView();
//
//        @SuppressLint("InlinedApi") int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.splash_screen);
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 1500) {
                        sleep(100);
                        waited += 100;
                    }
                    if (!Utilities.isFirstLaunch(SplashScreenActivity.this)) {
                        Utilities.setFirstLaunch(SplashScreenActivity.this);
                    }

                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        splashTread.start();
    }
}
