package com.android.gpasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        viewModel.getAllGpa().observe(this, new Observer<List<FinalGPA>>() {
            @Override
            public void onChanged(@Nullable final List<FinalGPA> allgpa) {

                if(allgpa.size()!=0)
                   setTextValues(allgpa);
            }
        });
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


    public void moveIntent(View view) {

        Intent it=new Intent(this,SemesterGPA.class);
        it.putExtra("sem",view.getTag().toString());
        startActivity(it);
    }
}





