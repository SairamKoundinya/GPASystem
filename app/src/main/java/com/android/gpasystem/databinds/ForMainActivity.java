package com.android.gpasystem.databinds;

import android.graphics.Color;

import com.android.gpasystem.R;
import com.android.gpasystem.databinding.ActivityMainBinding;
import com.android.gpasystem.model.FinalGPA;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 9/4/2019.
 */

public class ForMainActivity {

    public static void setUpLayout(ActivityMainBinding binding) {

             setUpSemesterNum(binding);
             setUpTags(binding);
             setUpBackground(binding);
             setUpTextColor(binding);

    }

    // method for binding title to cards
    private static void setUpSemesterNum(ActivityMainBinding binding) {
        binding.firstcard.leftcard.semnum.setText(R.string.semester_1);
        binding.firstcard.rightcard.semnum.setText(R.string.semester_2);
        binding.secondcard.leftcard.semnum.setText(R.string.semester_3);
        binding.secondcard.rightcard.semnum.setText(R.string.semester_4);
        binding.thirdcard.leftcard.semnum.setText(R.string.semester_5);
        binding.thirdcard.rightcard.semnum.setText(R.string.semester_6);
        binding.fourthcard.leftcard.semnum.setText(R.string.semester_7);
        binding.fourthcard.rightcard.semnum.setText(R.string.semester_8);
    }

    // method for settags to textview
    private static void setUpTags(ActivityMainBinding binding) {
        binding.firstcard.leftcard.semnum.setTag(1);
        binding.firstcard.leftcard.sgpatext.setTag(1);
        binding.firstcard.rightcard.semnum.setTag(2);
        binding.firstcard.rightcard.sgpatext.setTag(2);
        binding.secondcard.leftcard.semnum.setTag(3);
        binding.secondcard.leftcard.sgpatext.setTag(3);
        binding.secondcard.rightcard.semnum.setTag(4);
        binding.secondcard.rightcard.sgpatext.setTag(4);
        binding.thirdcard.leftcard.semnum.setTag(5);
        binding.thirdcard.leftcard.sgpatext.setTag(5);
        binding.thirdcard.rightcard.semnum.setTag(6);
        binding.thirdcard.rightcard.sgpatext.setTag(6);
        binding.fourthcard.leftcard.semnum.setTag(7);
        binding.fourthcard.leftcard.sgpatext.setTag(7);
        binding.fourthcard.rightcard.semnum.setTag(8);
        binding.fourthcard.rightcard.sgpatext.setTag(8);
    }

    // method for card backgroung color
    private static void setUpBackground(ActivityMainBinding binding) {
        binding.firstcard.leftcard.cardview.setBackgroundResource(R.color.c1);
        binding.firstcard.rightcard.cardview.setBackgroundResource(R.color.c2);
        binding.secondcard.leftcard.cardview.setBackgroundResource(R.color.c3);
        binding.secondcard.rightcard.cardview.setBackgroundResource(R.color.c4);
        binding.thirdcard.leftcard.cardview.setBackgroundResource(R.color.c5);
        binding.thirdcard.rightcard.cardview.setBackgroundResource(R.color.c6);
        binding.fourthcard.leftcard.cardview.setBackgroundResource(R.color.c7);
        binding.fourthcard.rightcard.cardview.setBackgroundResource(R.color.c8);
    }

    // method for text color
    private static void setUpTextColor(ActivityMainBinding binding) {
        binding.firstcard.leftcard.semnum.setTextColor(Color.BLACK);
        binding.firstcard.leftcard.sgpatext.setTextColor(Color.BLACK);
        binding.firstcard.rightcard.semnum.setTextColor(Color.BLACK);
        binding.firstcard.rightcard.sgpatext.setTextColor(Color.BLACK);
        binding.secondcard.leftcard.semnum.setTextColor(Color.BLACK);
        binding.secondcard.leftcard.sgpatext.setTextColor(Color.BLACK);
        binding.secondcard.rightcard.semnum.setTextColor(Color.BLACK);
        binding.secondcard.rightcard.sgpatext.setTextColor(Color.BLACK);
        binding.thirdcard.leftcard.semnum.setTextColor(Color.WHITE);
        binding.thirdcard.leftcard.sgpatext.setTextColor(Color.WHITE);
        binding.thirdcard.rightcard.semnum.setTextColor(Color.WHITE);
        binding.thirdcard.rightcard.sgpatext.setTextColor(Color.WHITE);
        binding.fourthcard.leftcard.semnum.setTextColor(Color.WHITE);
        binding.fourthcard.leftcard.sgpatext.setTextColor(Color.WHITE);
        binding.fourthcard.rightcard.semnum.setTextColor(Color.WHITE);
        binding.fourthcard.rightcard.sgpatext.setTextColor(Color.WHITE);
    }


    public static void setUpGpa(ActivityMainBinding binding, List<FinalGPA> allgpa) {

        String form="#.##";
        binding.firstcard.leftcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(0).getGpa()));
        binding.firstcard.rightcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(1).getGpa()));
        binding.secondcard.leftcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(2).getGpa()));
        binding.secondcard.rightcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(3).getGpa()));
        binding.thirdcard.leftcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(4).getGpa()));
        binding.thirdcard.rightcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(5).getGpa()));
        binding.fourthcard.leftcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(6).getGpa()));
        binding.fourthcard.rightcard.sgpatext.setText(new DecimalFormat(form).format(allgpa.get(7).getGpa()));
    }
}


