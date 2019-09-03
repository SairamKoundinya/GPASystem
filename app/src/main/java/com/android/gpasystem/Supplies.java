package com.android.gpasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.gpasystem.databinding.ActivitySuppliesBinding;
import com.android.gpasystem.model.SemesterGPAModel;
import com.android.gpasystem.viewmodel.SGPAVM;
import com.android.gpasystem.viewmodel.SGPAVmFactory;

import java.util.List;

public class Supplies extends AppCompatActivity {
    private SGPAVM viewmodel;
    private ActivitySuppliesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {getSupportActionBar().hide();}
        catch (NullPointerException e){ Log.e("Exception","null");}

        binding= DataBindingUtil.setContentView(this,R.layout.activity_supplies);

        viewmodel = ViewModelProviders.of(this).get(SGPAVM.class);
        viewmodel.getAllGpa().observe(this, new Observer<List<SemesterGPAModel>>() {
            @Override
            public void onChanged(@Nullable List<SemesterGPAModel> list) {
                setUpTable(list);
            }
        });


    }

    private void setUpTable(List<SemesterGPAModel> list) {
        binding.tablelayout.removeAllViews();
        binding.supply.setVisibility(View.INVISIBLE);
        String title=getString(R.string.supplies)+"("+list.size()+")";
        binding.title.setText(title);
        if(list.size()!=0){
            for (final SemesterGPAModel li:list) {

                TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
                TextView tv = (TextView) tableRow.getChildAt(0);
                tv.setText(li.getSubname());
                tv = (TextView) tableRow.getChildAt(1);
                tv.setText(String.valueOf(li.getSumnum()));
                tv = (TextView) tableRow.getChildAt(2);
                tv.setText(String.valueOf(li.getCredits()));
                binding.tablelayout.addView(tableRow);
            }}
            else
            binding.supply.setVisibility(View.VISIBLE);

    }


    public void back(View view) {
        super.onBackPressed();
    }
}
