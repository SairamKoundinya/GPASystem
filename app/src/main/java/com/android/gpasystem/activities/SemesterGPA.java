package com.android.gpasystem.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.gpasystem.R;
import com.android.gpasystem.databinding.ActivitySemesterGpaBinding;
import com.android.gpasystem.model.SemesterGPAModel;
import com.android.gpasystem.utils.Dialog;
import com.android.gpasystem.viewmodel.FinalGPAFactory;
import com.android.gpasystem.viewmodel.FinalGPAVM;
import com.android.gpasystem.viewmodel.SGPAVM;
import com.android.gpasystem.viewmodel.SGPAVmFactory;

import java.util.List;

public class SemesterGPA extends AppCompatActivity {

    private ActivitySemesterGpaBinding binding;
    private SGPAVM viewmodel;
    private int position;
    private FinalGPAVM vModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        bindToLayout();
        displaySGPA();
        displaySubjects();
    }

    // method for hiding action bar
    private void hideActionBar(){
        try {getSupportActionBar().hide();}
        catch (NullPointerException e){Log.e("Exception","null");}
    }

    // method for binding activity to layout
    private void bindToLayout(){
        binding= DataBindingUtil.setContentView(this, R.layout.activity_semester_gpa);
        position=Integer.parseInt(getIntent().getStringExtra("sem"));
        binding.actionbar.title.setText(R.string.semester+position);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSubject();
            }
        });
    }

    // method for displaying sgpa
    private void displaySGPA() {
        vModel = ViewModelProviders.of(this,new FinalGPAFactory(this.getApplication(),position)).get(FinalGPAVM.class);
        vModel.getsgpa().observe(this, new Observer<Float>() {

            @Override
            public void onChanged(@Nullable Float aFloat) {
                String result=""+aFloat;
                binding.sgpa.setText(result);
            }
        });
    }

    // method for displaying semester subjects
    private void displaySubjects() {
        viewmodel = ViewModelProviders.of(this,new SGPAVmFactory(this.getApplication(), position)).get(SGPAVM.class);
        viewmodel.getAllGpa().observe(this, new Observer<List<SemesterGPAModel>>() {
            @Override
            public void onChanged(@Nullable final List<SemesterGPAModel> list) {
                setUpTable(list);
                updateSGPA(list);
            }});
    }

    // method for table layout
    private void setUpTable(List<SemesterGPAModel> list) {
        binding.tablelayout.removeAllViews();
        if(list!=null && list.size()!=0){
            for (final SemesterGPAModel li:list) {
                TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
                TextView tv = (TextView) tableRow.getChildAt(0);
                tv.setText(li.getSubname());
                tv.setTag("S" + li.getId());
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        viewmodel.delete(li.getId());
                        return false;}
                });
                tv = (TextView) tableRow.getChildAt(1);
                tv.setText(String.valueOf(li.getMarks()));
                tv.setTag("M" + li.getId());
                tv = (TextView) tableRow.getChildAt(2);
                tv.setText(String.valueOf(li.getCredits()));
                tv.setTag("C" + li.getId());
                binding.tablelayout.addView(tableRow);
            }}
    }

    // method for updating sgpa
    private void updateSGPA(List<SemesterGPAModel> list) {
        float sumcredits=0.0f,total=0.0f;
        for(SemesterGPAModel iterator:list){
                total+=iterator.getMarks()*iterator.getCredits();
                sumcredits+=iterator.getCredits();
        }
        if(sumcredits!=0) {
            float sgpa = total / sumcredits;
            if(!String.valueOf(sgpa).equals(binding.sgpa.getText())) vModel.update(position,sgpa);
        }
        if(list.size()==0) vModel.update(position,0.0f);
    }

    // method for updating sgpa fields
    public void update(View v) {
        Dialog.display(viewmodel, v,
                new AlertDialog.Builder(this),
                getLayoutInflater().inflate(R.layout.cgpa_edit,null),
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    // method for inserting new subject into table
    private void insertSubject() {
        Dialog.insert(viewmodel,
                new AlertDialog.Builder(this),
                getLayoutInflater().inflate(R.layout.new_card_sub,null),
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
    }

    // method for navigation to previous activity
    public void goBack(View view) {
        super.onBackPressed();
    }

}
