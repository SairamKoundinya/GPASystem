package com.android.gpasystem.repos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.android.gpasystem.SemesterGPA;
import com.android.gpasystem.databases.GPADatabase;
import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.interfaces.SemesterGPADao;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.model.SemesterGPAModel;

import java.util.List;


public class SGPARepo {

    private SemesterGPADao mgpadao;
    private List<SemesterGPAModel> mAllGpa;

    public SGPARepo(Application application,int i) {
        GPADatabase db = GPADatabase.getDatabase(application);
        switch (i) {
            case 1: mgpadao = db.sem1gpaDao(); break;
            case 2: mgpadao = db.sem2gpaDao(); break;
            case 3: mgpadao = db.sem3gpaDao(); break;
            case 4: mgpadao = db.sem4gpaDao(); break;
            case 5: mgpadao = db.sem5gpaDao(); break;
            case 6: mgpadao = db.sem6gpaDao(); break;
            case 7: mgpadao = db.sem7gpaDao(); break;
            case 8: mgpadao = db.sem8gpaDao(); break;
        }
        mAllGpa = mgpadao.getSGpa();
    }

    public List<SemesterGPAModel> getSGpa() {
        return mAllGpa;
    }


    public void updateSubname (int id,String subname) {
        new insertAsyncTask(mgpadao,1).execute(new SemesterGPAModel(id,subname,0,0));
    }
    public void updateMarks (int id,int marks) {
        new insertAsyncTask(mgpadao,2).execute(new SemesterGPAModel(id,"",marks,0));
    }
    public void updateCredits (int id,int credits) {
        new insertAsyncTask(mgpadao,3).execute(new SemesterGPAModel(id,"",0,credits));
    }
    public void delete(int id){
        new insertAsyncTask(mgpadao,4).execute(new SemesterGPAModel(id,"",0,0));
    }
    public void insert(String subname,int marks,int credits){
        new insertAsyncTask(mgpadao,5).execute(new SemesterGPAModel(0,subname,marks,credits));
    }

    private static class insertAsyncTask extends AsyncTask<SemesterGPAModel, Void, Void> {

        private SemesterGPADao mAsyncTaskDao;
        private int Switch;

        insertAsyncTask(SemesterGPADao dao,int Swit) {
            Switch=Swit;
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SemesterGPAModel... params) {
            switch(Switch) {
                case 1: mAsyncTaskDao.updateSubname(params[0].getId(), params[0].getSubname()); break;
                case 2: mAsyncTaskDao.updateMarks(params[0].getId(), params[0].getMarks()); break;
                case 3: mAsyncTaskDao.updateCredits(params[0].getId(), params[0].getCredits()); break;
                case 4: mAsyncTaskDao.deleteSem(params[0].getId());
                case 5: mAsyncTaskDao.insert(params[0]);
            }
            return null;
        }
    }
}