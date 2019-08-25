package com.android.gpasystem.databases;

import android.os.AsyncTask;

import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.interfaces.SemesterGPADao;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.model.SemesterGPAModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/10/2019.
 */
 class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final FinalGPADao mDao;
    private final SemesterGPADao semgpaDao;

    PopulateDbAsync(GPADatabase db) {
        mDao = db.finalgpaDao();
        semgpaDao=db.semgpaDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {

        if(mDao.getCount()==0) {
            for(int i=1;i<9;i++)
            mDao.insert(new FinalGPA(i,0.0f));
        }
        for(int j=1;j<9;j++){
        if(semgpaDao.getCount(j)==0) {
            for (int i = 1; i < 6; i++)
                semgpaDao.insert(new SemesterGPAModel(0, "Subject " + i, 0, 0, j));
        }}
        return null;
    }
}


