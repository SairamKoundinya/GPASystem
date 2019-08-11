package com.android.gpasystem.databases;

import android.os.AsyncTask;

import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.model.FinalGPA;

/**
 * Created by Admin on 8/10/2019.
 */
 class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final FinalGPADao mDao;

    PopulateDbAsync(GPADatabase db) {
        mDao = db.finalgpaDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {

        if(mDao.getCount()==0) {
            mDao.insert(new FinalGPA(1,0.0f));
            mDao.insert(new FinalGPA(2,0.0f));
            mDao.insert(new FinalGPA(3,0.0f));
            mDao.insert(new FinalGPA(4,0.0f));
        }
        return null;
    }
}
