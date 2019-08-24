package com.android.gpasystem.databases;

import android.os.AsyncTask;

import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.model.SemesterGPAModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/10/2019.
 */
 class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final FinalGPADao mDao;
    private final GPADatabase db;

    PopulateDbAsync(GPADatabase db) {
        this.db=db;
        mDao = db.finalgpaDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {

        if(mDao.getCount()==0) {
            for(int i=1;i<9;i++)
            mDao.insert(new FinalGPA(i,0.0f));
        }
        List<SemesterGPAModel> sgpa=new ArrayList<>();
        for(int i=1;i<6;i++)
        sgpa.add(new SemesterGPAModel(0,"Subject "+i,0,0));

        if(db.sem1gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem1gpaDao().insert(sm);
        if(db.sem2gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem2gpaDao().insert(sm);
        if(db.sem3gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem3gpaDao().insert(sm);
        if(db.sem4gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem4gpaDao().insert(sm);
        if(db.sem5gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem5gpaDao().insert(sm);
        if(db.sem6gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem6gpaDao().insert(sm);
        if(db.sem7gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem7gpaDao().insert(sm);
        if(db.sem8gpaDao().getCount()==0)
        for(SemesterGPAModel sm:sgpa) db.sem8gpaDao().insert(sm);

        return null;
    }
}


