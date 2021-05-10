package com.meme.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    ImageView image;
    TextView text2;
    TextView plog,olog;
    Intent intent1,intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        plog=findViewById(R.id.plog);
        olog=findViewById(R.id.olog);
        image=findViewById(R.id.logoimage);
        text2=findViewById(R.id.logotext);
        plog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(Login.this,Med_search.class);
                startActivity(intent1);
            }
        });

        olog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2=new Intent(Login.this,OwnerLogin.class);
                startActivity(intent2);


            }
        });
    }
}