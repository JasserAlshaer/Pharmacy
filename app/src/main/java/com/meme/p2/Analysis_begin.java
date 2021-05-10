package com.meme.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import maes.tech.intentanim.CustomIntent;

public class Analysis_begin extends AppCompatActivity {
    LottieAnimationView lV;
    TextView t1,t2;
    ImageView goAn;
    Animation scaleUp,scaleDown,topAnim;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_analysis_begin);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.slide_down);
        lV=findViewById(R.id.animation);
        t1=findViewById(R.id.tt1);
        t2=findViewById(R.id.tt2);
        goAn=findViewById(R.id.goAn);

        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);

        lV.playAnimation();
        lV.setAnimation(topAnim);
        t1.setAnimation(topAnim);
        t2.setAnimation(topAnim);

        goAn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    goAn.startAnimation(scaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    goAn.startAnimation(scaleDown);
                }

                intent=new Intent(Analysis_begin.this,Analysis1.class);
                startActivity(intent);
                CustomIntent.customType(Analysis_begin.this,"bottom-to-up");
                return false;
            }
        });
    }
}