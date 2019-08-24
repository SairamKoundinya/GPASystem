package com.android.gpasystem;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
       // setContentView(R.layout.activity_semester_gpa);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_semester_gpa);
        mainLoad(1);

    }

    private void mainLoad(int i) {

        viewmodel = ViewModelProviders.of(this,new SGPAVmFactory(this.getApplication(), i)).get(SGPAVM.class);

        int len=viewmodel.getAllGpa().size();
        List<SemesterGPAModel> list=viewmodel.getAllGpa();

        for(int it=0;it<len;it++){

            TableRow tableRow=(TableRow)getLayoutInflater().inflate(R.layout.table_row,null);
            TextView tv=(TextView) tableRow.getChildAt(0);
            tv.setText(list.get(it).getSubname());
            tv.setTag("S"+list.get(it).getId());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showdialog(view.getTag().toString());
                }
            });
            tv=(TextView) tableRow.getChildAt(1);
            tv.setText(list.get(it).getMarks());
            tv.setTag("M"+list.get(it).getId());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showdialog(view.getTag().toString());
                }
            });
            tv=(TextView) tableRow.getChildAt(2);
            tv.setText(list.get(it).getCredits());
            tv.setTag("C"+list.get(it).getId());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showdialog(view.getTag().toString());
                }
            });
            binding.tablelayout.addView(tableRow);
        }

    }

    private void showdialog(String tag) {
    }
}
