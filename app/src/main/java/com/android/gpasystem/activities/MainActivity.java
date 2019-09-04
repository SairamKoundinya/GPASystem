 package com.android.gpasystem.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.gpasystem.databinds.ForMainActivity;
import com.android.gpasystem.R;
import com.android.gpasystem.databinding.ActivityMainBinding;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.utils.Dialog;
import com.android.gpasystem.viewmodel.FinalGPAVM;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FinalGPAVM viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindToLayout();
        attachViewModel();
    }

    // method for binding layout to activity
    private void bindToLayout() {
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        ForMainActivity.setUpLayout(binding);
    }

    // method for view model instance creation
    private void attachViewModel() {
        viewModel = ViewModelProviders.of(this).get(FinalGPAVM.class);
        viewModel.getAllGpa().observe(this, new Observer<List<FinalGPA>>() {
            @Override
            public void onChanged(@Nullable final List<FinalGPA> allgpa) {
                if (allgpa != null && allgpa.size() != 0) setTextValues(allgpa);
            }
        });
    }

    // method for displaying sgpa
    private void setTextValues(List<FinalGPA> allgpa) {
        ForMainActivity.setUpGpa(binding,allgpa);
        setAggregate(allgpa);
    }

    // method for displaying cgpa
    private void setAggregate(List<FinalGPA> allgpa) {
        int count=0; float sum=0;
        for(FinalGPA gpa:allgpa){
            if(gpa.getGpa()>0) { sum+=gpa.getGpa(); count++;
        }}
        if(count>0) {
            float res = sum / count;
            binding.displaycgpa.cgpa.setText(new DecimalFormat("#.##").format(res));
            binding.displaycgpa.percen.setText(new DecimalFormat("##.#").format((res  * 10)-5));
        }
    }

    //method for updating sgpa
    public void getGPA(View v){
        Dialog.show(viewModel,
                Integer.parseInt(v.getTag().toString()),
                new AlertDialog.Builder(this),
                getLayoutInflater().inflate(R.layout.cgpa_edit,null),
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    // method for creating action bar menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return true;
    }

    // method for action bar menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.guide) startActivity(new Intent(this,AppGuide.class));
        else  if (item.getItemId() == R.id.supplies) startActivity(new Intent(this,Supplies.class));
        return super.onOptionsItemSelected(item);
    }

    // method for navigation to semesters
    public void moveIntent(View view) {
        Intent it=new Intent(this,SemesterGPA.class);
        it.putExtra("sem",view.getTag().toString());
        startActivity(it);
    }

}