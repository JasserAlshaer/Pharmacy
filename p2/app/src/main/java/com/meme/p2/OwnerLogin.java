package com.meme.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class OwnerLogin extends AppCompatActivity {
    Animation topAnim,bottomAinm;
    ImageView imagelogo;
    TextView textlogo;
    TextInputLayout email,password;
    TextView go,forget;
    Intent intent1,intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.slide_down);
       // bottomAinm=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        imagelogo=findViewById(R.id.ownerLogo);
        textlogo=findViewById(R.id.textlogo);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        go=findViewById(R.id.go);
        forget=findViewById(R.id.forget);

        imagelogo.setAnimation(topAnim);
        textlogo.setAnimation(topAnim);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(OwnerLogin.this,ContactComp.class);
                startActivity(intent1);
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2=new Intent(OwnerLogin.this,Analysis1.class);
                startActivity(intent2);
            }
        });
    }

}