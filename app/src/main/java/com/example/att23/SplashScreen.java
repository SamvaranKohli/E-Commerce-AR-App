package com.example.att23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    Animation left_anim, right_anim;
    TextView furniture, store;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        left_anim = AnimationUtils.loadAnimation(this, R.anim.left_anim);
        right_anim = AnimationUtils.loadAnimation(this, R.anim.right_anim);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        furniture = findViewById(R.id.furniture);
        store = findViewById(R.id.store);

        furniture.setAnimation(left_anim);
        store.setAnimation(right_anim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                Intent myIntent = new Intent(SplashScreen.this, SignIn.class);

                if(preferences.getString("Login", "false").equals("false"))
                {
                    myIntent = new Intent(SplashScreen.this, SignIn.class);
                }
                else
                {
                    myIntent = new Intent(SplashScreen.this, LandingPage.class);
                }

                SplashScreen.this.startActivity(myIntent);

            }
        }, 2000);

    }
}