package com.android.gpasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    //private List<FinalGPA> list;

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
                //list=allgpa;
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
        View view=getLayoutInflater().inflate(R.layout.cgpa_edit,null);
        builder.setView(view);
        final EditText edittext=view.findViewById(R.id.value);
        edittext.requestFocus();
        final InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        //  edittext.setText(String.valueOf(list.get(num-1).getGpa()));
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

               if(edittext.getText().toString().length()!=0)
               viewModel.update(num,Float.parseFloat(edittext.getText().toString()));
               else toastForEmpty();
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(edittext.getWindowToken(),0);
                }

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(edittext.getWindowToken(),0);
                }
            }
        });
        builder.create();
        builder.show();
        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.getText().clear();
            }
        });

    }


    public void moveIntent(View view) {

        Intent it=new Intent(this,SemesterGPA.class);
        it.putExtra("sem",view.getTag().toString());
        startActivity(it);
    }
    private void toastForEmpty() {
        Toast toast=Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.guide) {
           startActivity(new Intent(this,AppGuide.class));
        }
        return super.onOptionsItemSelected(item);
    }



}





