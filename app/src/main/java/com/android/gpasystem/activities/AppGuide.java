package com.android.gpasystem.activities;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.gpasystem.R;
import com.android.gpasystem.databinding.ActivityAppGuideBinding;

public class AppGuide extends AppCompatActivity {

    ActivityAppGuideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindToLayout();
        hideActionBar();
        setActivityTitle();
    }

    // method for binding activity to layout
    private void bindToLayout() {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_app_guide);
    }

    // method for hiding action bar for this activity
    private void hideActionBar(){
        try {getSupportActionBar().hide();}
        catch (NullPointerException e){ Log.e("Exception","null");}
    }

    // method for setting action bar title
    private void setActivityTitle(){
        binding.actionbar.title.setText(R.string.application_guide);
    }

    // method for back button
    public void goBack(View view) {
        super.onBackPressed();
    }
}
