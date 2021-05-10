package com.meme.p2;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;

public class Login extends AppCompatActivity {
    ImageView image;
    TextView text2;
    TextView plog, olog;
    Intent intent1, intent2;
    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        plog = findViewById(R.id.plog);
        olog = findViewById(R.id.olog);
        image = findViewById(R.id.logoimage);
        text2 = findViewById(R.id.logotext);
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                onDestroy();
            }
        };

        plog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    plog.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    plog.startAnimation(scaleDown);
                }

                intent1 = new Intent(Login.this, Med_search.class);
                startActivity(intent1);
                CustomIntent.customType(Login.this, "left-to-right");
                return false;
            }

        });

        olog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    olog.startAnimation(scaleUp);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    olog.startAnimation(scaleDown);
                }

                intent2 = new Intent(Login.this, OwnerLogin.class);
                startActivity(intent2);
                CustomIntent.customType(Login.this, "left-to-right");
                return false;
            }

        });
        /*plog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(Login.this,Med_search.class);
                startActivity(intent1);
            }
        });*/

        /*olog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2=new Intent(Login.this,OwnerLogin.class);
                startActivity(intent2);


            }
        });*/
    }
}