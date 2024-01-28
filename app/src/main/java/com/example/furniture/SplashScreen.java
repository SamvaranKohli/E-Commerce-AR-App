package com.example.furniture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    // left side and right side animation
    Animation leftAnim, rightAnim;
    TextView topTextView, bottomTextView;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //set xml id
        leftAnim = AnimationUtils.loadAnimation(this, R.anim.left_anim);
        rightAnim = AnimationUtils.loadAnimation(this, R.anim.right_anim);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        topTextView = findViewById(R.id.furniture);
        bottomTextView = findViewById(R.id.store);

        topTextView.setAnimation(leftAnim);
        bottomTextView.setAnimation(rightAnim);

        // check if user had signed in
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                Intent myIntent;

                // if user never signed in, move to signIn page
                if(preferences.getString("Login", "false").equals("false"))
                {
                    myIntent = new Intent(SplashScreen.this, SignIn.class);
                }
                // if user signed in, move to product list page
                else
                {
                    myIntent = new Intent(SplashScreen.this, productListing.class);
                }

                SplashScreen.this.startActivity(myIntent);

            }
        }, 2000);

    }
}