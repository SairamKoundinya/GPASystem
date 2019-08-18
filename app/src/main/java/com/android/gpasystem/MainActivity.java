package com.android.gpasystem;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.gpasystem.databinding.ActivityMainBinding;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.viewmodel.FinalGPAVM;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FinalGPAVM viewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(FinalGPAVM.class);
      //  binding.setViewmodel(viewModel);

        viewModel.getAllGpa().observe(this, new Observer<List<FinalGPA>>() {
            @Override
            public void onChanged(@Nullable final List<FinalGPA> allgpa) {
                // Update the cached copy of the words in the adapter.
               // Log.d("checks",String.valueOf(allgpa.size()));
                if(allgpa.size()!=0)
                   setTextValues(allgpa);
            }
        });

//        binding.bt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                viewModel.update(1,Float.parseFloat(binding.ed1.getText().toString()));
//            }
//        });
//        binding.bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewModel.update(2,Float.parseFloat(binding.ed2.getText().toString()));
//            }
//        });
//        binding.bt3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewModel.update(3,Float.parseFloat(binding.ed3.getText().toString()));
//            }
//        });
//        binding.bt4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewModel.update(4,Float.parseFloat(binding.ed4.getText().toString()));
//            }
//        });

    }

    private void setTextValues(final List<FinalGPA> allgpa) {

        String form="#.##";
        binding.tx1.setText(new DecimalFormat(form).format(allgpa.get(0).getGpa()));
        binding.tx2.setText(new DecimalFormat(form).format(allgpa.get(1).getGpa()));
        binding.tx3.setText(new DecimalFormat(form).format(allgpa.get(2).getGpa()));
        binding.tx4.setText(new DecimalFormat(form).format(allgpa.get(3).getGpa()));
        binding.tx5.setText(new DecimalFormat(form).format(allgpa.get(4).getGpa()));
        binding.tx6.setText(new DecimalFormat(form).format(allgpa.get(5).getGpa()));
        binding.tx7.setText(new DecimalFormat(form).format(allgpa.get(6).getGpa()));
        binding.tx8.setText(new DecimalFormat(form).format(allgpa.get(7).getGpa()));

        setAggregate(allgpa);
    }

    private void setAggregate(List<FinalGPA> allgpa) {
        int count=0;
        float sum=0;
        for(FinalGPA gpa:allgpa){

            if(gpa.getGpa()>0) {
                sum+=gpa.getGpa();
                count++;
            }
        }
        if(count>0) {
            float res = sum / count;
            binding.cgpa.setText(new DecimalFormat("#.##").format(res));
            binding.percen.setText(new DecimalFormat("##.#").format((res  * 10)-5));
        }
        else{
            binding.cgpa.setText(String.valueOf(0.0f));
            binding.percen.setText(String.valueOf(0));
        }
    }

    public void getGPA(View v){
        final int num=Integer.parseInt(v.getTag().toString());
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final EditText view=(EditText)getLayoutInflater().inflate(R.layout.editdialog,null);
        builder.setTitle("Semester "+num+" EditBox");
        builder.setMessage("Enter here sem"+num+" G.P.A");
        builder.setView(view);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

               viewModel.update(num,Float.parseFloat(view.getText().toString()));
            }
        });
        builder.setNegativeButton(R.string.cancel,null);
        builder.create();
        builder.show();
    }

//    public void change1(View v){
//
//        getGPA(1);
//    }
//    public void change2(View v){
//
//        getGPA("Semester 2 EditBox","Enter here sem2 G.P.A",2);
//    }
//    public void change3(View v){
//
//        getGPA("Semester 3 EditBox","Enter here sem3 G.P.A",3);
//    }
//    public void change4(View v){
//
//        getGPA("Semester 4 EditBox","Enter here sem4 G.P.A",4);
//    }
//
//    public void change5(View v){
//
//        getGPA("Semester 5 EditBox","Enter here sem5 G.P.A",5);
//    }
//    public void change6(View v){
//
//        getGPA("Semester 6 EditBox","Enter here sem6 G.P.A",6);
//    }
//    public void change7(View v){
//
//        getGPA("Semester 7 EditBox","Enter here sem7 G.P.A",7);
//    }
//    public void change8(View v){
//
//        getGPA("Semester 8 EditBox","Enter here sem8 G.P.A",8);
//    }

    }





