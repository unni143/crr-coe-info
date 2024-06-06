package com.example.smapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.splashscreen);
        TextView textView = findViewById(R.id.user_main);

        /*slide(textView);
        zoom(imageView);*/


        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3 * 1000);

                    // After 5 seconds redirect to another intent
                    Intent i = new Intent(MainActivity.this, ChooseActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }

  /*  public void slide(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        view.startAnimation(animation1);
    }*/

  /*  public void zoom(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.clock);
        view.startAnimation(animation1);
    }*/

}