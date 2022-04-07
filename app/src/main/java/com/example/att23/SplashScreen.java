package com.example.att23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    Animation left_anim, right_anim;
    TextView furniture, store;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    FirebaseDatabase data;

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

        data = FirebaseDatabase.getInstance();
        DatabaseReference myRef = data.getReference();

        myRef.child("IP").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {

                    String ip = String.valueOf(task.getResult().getValue());
                    editor = preferences.edit();
                    editor.putString("IP", ip);
                    editor.commit();
                }
            }
        });

    }
}