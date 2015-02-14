package com.example.faizan.touristguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.faizan.touristguide.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebad Ali on 1/25/2015.
 */
public class Splash extends Activity {

    int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
