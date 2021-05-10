package com.meme.p2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class OwnerLogin extends AppCompatActivity {
    Animation topAnim;
    ImageView imagelogo;
    TextView textlogo;
    TextInputLayout email,password;
    TextView go,forget;
    Intent intent1,intent2;
    Animation scaleUp,scaleDown;
    FirebaseAuth auth;
    TextInputEditText email1,password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_owner_login);

        topAnim= AnimationUtils.loadAnimation(this,R.anim.slide_down);
        imagelogo=findViewById(R.id.ownerLogo);
        textlogo=findViewById(R.id.textlogo);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        email1=findViewById(R.id.email1);
        password1=findViewById(R.id.password1);
        go=findViewById(R.id.go);
        forget=findViewById(R.id.forget);
        auth =FirebaseAuth.getInstance();

        imagelogo.setAnimation(topAnim);
        textlogo.setAnimation(topAnim);

        scaleUp= AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this,R.anim.scale_down);

        forget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    forget.startAnimation(scaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    forget.startAnimation(scaleDown);
                }

                intent1=new Intent(OwnerLogin.this,ContactComp.class);
                startActivity(intent1);
                CustomIntent.customType(OwnerLogin.this,"fadein-to-fadeout");
                return false;
            }

        });


       /* go.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    go.startAnimation(scaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    go.startAnimation(scaleDown);
                }


                return false;
            }

        });*/
        /*forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(OwnerLogin.this,ContactComp.class);
                startActivity(intent1);
            }
        });*/

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=email1.getText().toString().trim();
                String userPassword=password1.getText().toString().trim();
                if(TextUtils.isEmpty(userEmail))
                {
                    email1.setError("Please Enter Your Email !");
                    return;
                }

                if(TextUtils.isEmpty(userPassword))
                {
                    password1.setError("Please Enter Your Password !");
                    return;
                }
                if(userPassword.length()<6)
                {
                    password1.setError("Your Password is less than 6 digits,Please re-enter your password");
                    return;
                }
                auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(OwnerLogin.this,"Successfully logged in !",Toast.LENGTH_SHORT).show();
                            go.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    if(event.getAction()==MotionEvent.ACTION_UP)
                                    {
                                        go.startAnimation(scaleUp);
                                    }else if(event.getAction()==MotionEvent.ACTION_DOWN)
                                    {
                                        go.startAnimation(scaleDown);
                                    }

                                    startActivity(new Intent(getApplicationContext(),Analysis_begin.class));
                                    return false;
                                }

                            });

                        }
                        else
                        {
                            Toast.makeText(OwnerLogin.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

}