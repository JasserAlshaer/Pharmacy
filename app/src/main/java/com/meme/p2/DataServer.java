package com.meme.p2;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class DataServer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("myappID")
                        .clientKey("cnVpfceO89ds")
                        .server("http://100.26.109.247/parse/")
                        .build());

    }
}
