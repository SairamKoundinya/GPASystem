package com.android.gpasystem.activities;

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

import com.android.gpasystem.R;
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
         hideActionBar();
         bindToLayout();
    }

    // method for hiding action bar
    private void hideActionBar() {
        try {getSupportActionBar().hide();}
        catch (NullPointerException e){ Log.e("Exception","null");}
    }

    // method for bind activity to layout
    private void bindToLayout(){
        binding= DataBindingUtil.setContentView(this, R.layout.activity_supplies);
        binding.subject.midtext.setText(R.string.sem);
        viewmodel = ViewModelProviders.of(this).get(SGPAVM.class);
        viewmodel.getAllGpa().observe(this, new Observer<List<SemesterGPAModel>>() {
            @Override
            public void onChanged(@Nullable List<SemesterGPAModel> list) {
                setUpTable(list);
            }});
    }

    // method for displaying supplies
    private void setUpTable(List<SemesterGPAModel> list) {
        binding.tablelayout.removeAllViews();
        binding.supply.setVisibility(View.INVISIBLE);
        String title=getString(R.string.supplies)+"("+list.size()+")";
        binding.actionbar.title.setText(title);

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
        else binding.supply.setVisibility(View.VISIBLE);

    }

   // method for navigation to previous activity
    public void goBack(View view) {
        super.onBackPressed();
    }

    public void update(View view){ }
}
