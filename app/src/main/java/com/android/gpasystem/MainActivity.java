package com.android.gpasystem;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.gpasystem.databinding.ActivityMainBinding;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.viewmodel.FinalGPAVM;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FinalGPAVM viewModel;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(FinalGPAVM.class);

        viewModel.getAllGpa().observe(this, new Observer<List<FinalGPA>>() {
            @Override
            public void onChanged(@Nullable final List<FinalGPA> allgpa) {
                // Update the cached copy of the words in the adapter.

                    binding.setSem1(allgpa.get(0).getGpa());
                    binding.setSem2(allgpa.get(1).getGpa());
                    binding.setSem3(allgpa.get(2).getGpa());
                    binding.setSem4(allgpa.get(3).getGpa());

            }
        });

        binding.bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.update(1,Float.parseFloat(binding.ed1.getText().toString()));
            }
        });
        binding.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.update(2,Float.parseFloat(binding.ed2.getText().toString()));
            }
        });
        binding.bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.update(3,Float.parseFloat(binding.ed3.getText().toString()));
            }
        });
        binding.bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.update(4,Float.parseFloat(binding.ed4.getText().toString()));
            }
        });

    }

}





