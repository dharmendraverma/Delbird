package in.delbird.chemist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import in.delbird.chemist.R;
import in.delbird.chemist.services.GeoLocationService;


/**
 * Created by Dharmendra on 15/1/16.
 */
public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, GeoLocationService.class));
    }
}
