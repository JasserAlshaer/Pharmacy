package com.meme.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    private static int splash=3500;
    Animation topAnim,bottomAinm;
    ImageView imageview;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAinm=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageview=findViewById(R.id.imageView);
        text=findViewById(R.id.textView);

        imageview.setAnimation(topAnim);
        text.setAnimation(bottomAinm);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Login.class);
                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(imageview,"logo_image");
                pairs[1]=new Pair<View,String>(text,"logo_text");

                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());

            }
        },splash);



    }
}