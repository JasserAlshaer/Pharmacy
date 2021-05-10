package com.meme.p2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import maes.tech.intentanim.CustomIntent;

public class Graph extends AppCompatActivity {
    BottomNavigationView bV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);

        bV=findViewById(R.id.bottom_navigation);
        bV.setSelectedItemId(R.id.nav_graph);

        bV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.nav_graph:
                        return true;
                    case R.id.nav_quantities:
                        startActivity(new Intent(getApplicationContext(),Analysis1.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),Login.class));
                        CustomIntent.customType(Graph.this,"left-to-right");
                        return true;
                }
                return false;
            }
        });
    }
}