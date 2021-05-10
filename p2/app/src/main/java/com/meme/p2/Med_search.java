package com.meme.p2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class Med_search extends AppCompatActivity {
    GridView gridView;
    String [] labels = {"Location","Photo","Search"};
    int [] images = {R.drawable.ic_location,R.drawable.ic_image,R.drawable.ic_ss};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_search);

        gridView=findViewById(R.id.imagesgrid);
        SearchAdapter adapter=new SearchAdapter(Med_search.this,labels,images);
        gridView.setAdapter(adapter);
    }
}