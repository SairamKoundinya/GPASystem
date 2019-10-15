package com.android.gpasystem.repos;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.gpasystem.databases.GPADatabase;
import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.model.FinalGPA;

import java.util.List;


/**
 * Created by Admin on 8/10/2019.
 */
public class FinalGPARepo {

    private FinalGPADao mgpadao;
    private LiveData<List<FinalGPA>> mAllGpa;
    private LiveData<Float> sgpa;

   public FinalGPARepo(Application application) {
        GPADatabase db = GPADatabase.getDatabase(application);
        mgpadao = db.finalgpaDao();
        mAllGpa = mgpadao.getGpa();
    }
    public FinalGPARepo(Application application,int id) {
        GPADatabase db = GPADatabase.getDatabase(application);
        mgpadao = db.finalgpaDao();
        sgpa = mgpadao.getGpa(id);
    }

    public LiveData<List<FinalGPA>> getGpa() {
        return mAllGpa;
    }


    public void update (int num,float gpa) {
        new insertAsyncTask(mgpadao).execute(new FinalGPA(num,gpa));
    }

    public LiveData<Float> getSGPA() {
       return sgpa;
    }

    private static class insertAsyncTask extends AsyncTask<FinalGPA, Void, Void> {

        private FinalGPADao mAsyncTaskDao;

        insertAsyncTask(FinalGPADao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FinalGPA... params) {
            mAsyncTaskDao.updateSem(params[0]);
            return null;
        }
    }
}