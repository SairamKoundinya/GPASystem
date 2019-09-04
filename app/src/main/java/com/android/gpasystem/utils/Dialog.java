package com.android.gpasystem.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.gpasystem.R;
import com.android.gpasystem.viewmodel.FinalGPAVM;
import com.android.gpasystem.viewmodel.SGPAVM;

/**
 * Created by Admin on 9/4/2019.
 */

public class Dialog {

    // method for displaying dialog to update sgpa
    public static void show(final FinalGPAVM viewModel, final int num, AlertDialog.Builder builder, View view, final InputMethodManager inputMethodManager) {
        builder.setView(view);
        final EditText edittext=view.findViewById(R.id.value);
        edittext.requestFocus();
        displayKeyBoard(inputMethodManager);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(edittext.getText().toString().length()!=0) viewModel.update(num,Float.parseFloat(edittext.getText().toString()));
                hideKeyBoard(inputMethodManager,edittext);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               hideKeyBoard(inputMethodManager,edittext);
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

    // method for displaying dialog to update sgpa fields
    public static void display(final SGPAVM viewmodel,final View v, AlertDialog.Builder builder, View view,final InputMethodManager systemService) {
        builder.setView(view);
        final EditText edittext=view.findViewById(R.id.value);
        edittext.requestFocus();
        TextView textView=view.findViewById(R.id.cardtitle);
        final String tag=v.getTag().toString();
        switch (tag.charAt(0)){
            case 'S': edittext.setInputType(InputType.TYPE_CLASS_TEXT);edittext.setText(((TextView)v).getText());
                edittext.setSelection(edittext.getText().length());textView.setText(R.string.Updatesub); break;
            case 'M': textView.setText(R.string.entergrade); edittext.setInputType(InputType.TYPE_CLASS_NUMBER); break;
            case 'C': textView.setText(R.string.entercredits); edittext.setInputType(InputType.TYPE_CLASS_NUMBER); break;
        }
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(edittext.getText().toString().length()!=0) {
                    switch (tag.charAt(0)) {
                        case 'S':viewmodel.updateSubname(Integer.parseInt(tag.substring(1)), edittext.getText().toString());
                            break;
                        case 'M':viewmodel.updateMarks(Integer.parseInt(tag.substring(1)), Integer.parseInt(edittext.getText().toString()));
                            break;
                        case 'C':viewmodel.updateCredits(Integer.parseInt(tag.substring(1)), Integer.parseInt(edittext.getText().toString()));
                            break;
                    }
                }
                hideKeyBoard(systemService,edittext);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hideKeyBoard(systemService,edittext);
            }
        });
        displayKeyBoard(systemService);
        builder.create();
        builder.show();
        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edittext.getText().clear();
            }
        });

    }

    // method for insert new subject into table
    public static void insert(final SGPAVM viewmodel, AlertDialog.Builder builder, View view,final InputMethodManager systemService) {
        builder.setView(view);
        final EditText edittext=view.findViewById(R.id.sub);
        edittext.requestFocus();
        final EditText edittext2=view.findViewById(R.id.mark);
        final EditText edittext3=view.findViewById(R.id.credit);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(edittext.getText().toString().length()!=0 && edittext2.getText().toString().length()!=0 && edittext3.getText().toString().length()!=0)
                    viewmodel.insert(edittext.getText().toString(),Integer.parseInt(edittext2.getText().toString()),Integer.parseInt(edittext3.getText().toString()));
                hideKeyBoard(systemService,edittext);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hideKeyBoard(systemService,edittext);
            }
        });
        displayKeyBoard(systemService);
        builder.create();
        builder.show();
    }

    // method for displaying keyboard
    private static void displayKeyBoard(InputMethodManager inputMethodManager){
        if (inputMethodManager != null)
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    // method for hiding keyboard
    private static void hideKeyBoard(InputMethodManager inputMethodManager,EditText edittext){
        if (inputMethodManager != null) inputMethodManager.hideSoftInputFromWindow(edittext.getWindowToken(),0);
    }
}
