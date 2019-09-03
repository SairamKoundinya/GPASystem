package com.android.gpasystem;

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

import com.android.gpasystem.databinding.ActivitySemesterGpaBinding;
import com.android.gpasystem.model.SemesterGPAModel;
import com.android.gpasystem.viewmodel.FinalGPAFactory;
import com.android.gpasystem.viewmodel.FinalGPAVM;
import com.android.gpasystem.viewmodel.SGPAVM;
import com.android.gpasystem.viewmodel.SGPAVmFactory;

import java.util.List;

public class SemesterGPA extends AppCompatActivity {

    private ActivitySemesterGpaBinding binding;
    private SGPAVM viewmodel;
    private  int position;
    private  FinalGPAVM vModel;
    private InputMethodManager inputMethodManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {getSupportActionBar().hide();}
        catch (NullPointerException e){Log.e("Exception","null");}
        binding= DataBindingUtil.setContentView(this,R.layout.activity_semester_gpa);

        position=Integer.parseInt(getIntent().getStringExtra("sem"));
        displaySGPA();
        mainLoad();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertSub();
            }
        });
    }

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

    private void mainLoad() {
        binding.setSemnum("Semester "+position);

        viewmodel = ViewModelProviders.of(this,new SGPAVmFactory(this.getApplication(), position)).get(SGPAVM.class);
        viewmodel.getAllGpa().observe(this, new Observer<List<SemesterGPAModel>>() {
            @Override
            public void onChanged(@Nullable final List<SemesterGPAModel> list) {

                updateSGPA(list);
                setUpTable(list);

            }});

    }

    private void setUpTable(List<SemesterGPAModel> list) {
        binding.tablelayout.removeAllViews();
        if(list!=null && list.size()!=0){
            for (final SemesterGPAModel li:list) {

                TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row, null);
                TextView tv = (TextView) tableRow.getChildAt(0);
                tv.setText(li.getSubname());
                tv.setTag("S" + li.getId());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showdialog(view.getTag().toString(),view);
                    }
                });
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        viewmodel.delete(li.getId());
                        return false;
                    }
                });
                tv = (TextView) tableRow.getChildAt(1);
                tv.setText(String.valueOf(li.getMarks()));
                tv.setTag("M" + li.getId());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showdialog(view.getTag().toString(),view);
                    }
                });
                tv = (TextView) tableRow.getChildAt(2);
                tv.setText(String.valueOf(li.getCredits()));
                tv.setTag("C" + li.getId());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showdialog(view.getTag().toString(),view);
                    }
                });
                binding.tablelayout.addView(tableRow);
            }}
    }

    private void updateSGPA(List<SemesterGPAModel> list) {

        float sumcredits=0.0f,total=0.0f;
        for(SemesterGPAModel iterator:list){

            int marks=iterator.getMarks();
            int credits=iterator.getCredits();
            if(marks!=0 && credits!=0){
                total+=marks*credits;
                sumcredits+=credits;
            }
        }
        if(sumcredits!=0) {
            float sgpa = total / sumcredits;

            if(!String.valueOf(sgpa).equals(binding.sgpa.getText())){
                vModel.update(position,sgpa);
            }

        }
        if(list.size()==0) vModel.update(position,0.0f);

    }

    private void showdialog(final String tag,View v) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final View view=getLayoutInflater().inflate(R.layout.cgpa_edit,null);
        builder.setView(view);
        final EditText edittext=view.findViewById(R.id.value);
        edittext.requestFocus();
       showKeyboard();
        TextView textView=view.findViewById(R.id.cardtitle);
        switch (tag.charAt(0)){
            case 'S': edittext.setInputType(InputType.TYPE_CLASS_TEXT);
                edittext.setText(((TextView)v).getText());
            textView.setText(R.string.Updatesub); break;
            case 'M': textView.setText(R.string.entergrade); edittext.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            case 'C': textView.setText(R.string.entercredits); edittext.setInputType(InputType.TYPE_CLASS_NUMBER); break;
        }
          builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  if(edittext.getText().toString().length()!=0) {
                      switch (tag.charAt(0)) {
                          case 'S':
                              viewmodel.updateSubname(Integer.parseInt(tag.substring(1)), edittext.getText().toString());
                              break;
                          case 'M':
                              viewmodel.updateMarks(Integer.parseInt(tag.substring(1)), Integer.parseInt(edittext.getText().toString()));
                              break;
                          case 'C':
                              viewmodel.updateCredits(Integer.parseInt(tag.substring(1)), Integer.parseInt(edittext.getText().toString()));
                              break;
                      }
                  }
                  else toastForEmpty();
                hideKeyboard(edittext);
              }
          });
          builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  hideKeyboard(edittext);
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

    private void toastForEmpty() {
        Toast toast=Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT);
        toast.show();
    }

    private void insertSub() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final View view=getLayoutInflater().inflate(R.layout.new_card_sub,null);
        builder.setView(view);
        final EditText edittext=view.findViewById(R.id.sub);
        edittext.requestFocus();
        showKeyboard();
        final EditText edittext2=view.findViewById(R.id.mark);
        final EditText edittext3=view.findViewById(R.id.credit);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(edittext.getText().toString().length()!=0 && edittext2.getText().toString().length()!=0 && edittext3.getText().toString().length()!=0)
                viewmodel.insert(edittext.getText().toString(),Integer.parseInt(edittext2.getText().toString()),Integer.parseInt(edittext3.getText().toString()));
               else toastForEmpty();
               hideKeyboard(edittext);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               hideKeyboard(edittext);
            }
        });
        builder.create();
        builder.show();
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void showKeyboard(){
          inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }
    public void hideKeyboard(EditText editText){
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
        }
    }

}
