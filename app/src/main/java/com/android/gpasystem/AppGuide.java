package com.android.gpasystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AppGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_guide);

        try {getSupportActionBar().hide();}
        catch (NullPointerException e){
            Log.e("Exception","null");}

    }

    public void back(View view) {
        super.onBackPressed();
    }
}
