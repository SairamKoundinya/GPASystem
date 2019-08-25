package com.android.gpasystem;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.gpasystem.databinding.ActivitySemesterGpaBinding;
import com.android.gpasystem.model.SemesterGPAModel;
import com.android.gpasystem.viewmodel.FinalGPAVM;
import com.android.gpasystem.viewmodel.SGPAVM;
import com.android.gpasystem.viewmodel.SGPAVmFactory;

import java.util.List;

public class SemesterGPA extends AppCompatActivity {

    private ActivitySemesterGpaBinding binding;
    private SGPAVM viewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {getSupportActionBar().hide();}
        catch (NullPointerException e){Log.e("Exception","null");}
        binding= DataBindingUtil.setContentView(this,R.layout.activity_semester_gpa);

        mainLoad(Integer.parseInt(getIntent().getStringExtra("sem")));
    }


    private void mainLoad(int h) {
        binding.setSemnum("Semester "+h);

        viewmodel = ViewModelProviders.of(this,new SGPAVmFactory(this.getApplication(), h)).get(SGPAVM.class);


        viewmodel.getAllGpa().observe(this, new Observer<List<SemesterGPAModel>>() {
            @Override
            public void onChanged(@Nullable List<SemesterGPAModel> list) {

                if(list!=null && list.size()!=0){
                    binding.tablelayout.removeAllViews();
                int len = list.size();

                for (int it = 0; it < len; it++) {

                    TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
                    TextView tv = (TextView) tableRow.getChildAt(0);
                    tv.setText(list.get(it).getSubname());
                    tv.setTag("S" + list.get(it).getId());
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showdialog(view.getTag().toString());
                        }
                    });
                    tv = (TextView) tableRow.getChildAt(1);
                    tv.setText(String.valueOf(list.get(it).getMarks()));
                    tv.setTag("M" + list.get(it).getId());
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showdialog(view.getTag().toString());
                        }
                    });
                    tv = (TextView) tableRow.getChildAt(2);
                    tv.setText(String.valueOf(list.get(it).getCredits()));
                    tv.setTag("C" + list.get(it).getId());
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showdialog(view.getTag().toString());
                        }
                    });
                    binding.tablelayout.addView(tableRow);
                }}
            }});

    }

    private void showdialog(String tag) {

        Log.d("checking",tag);
        viewmodel.updateSubname(1,"Android Development");
    }
}
