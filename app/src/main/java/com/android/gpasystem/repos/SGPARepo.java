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
    private LiveData<List<SemesterGPAModel>> mAllGpa;
    private int semnum;

    public SGPARepo(Application application,int i) {
        semnum=i;
        GPADatabase db = GPADatabase.getDatabase(application);
        mgpadao=db.semgpaDao();
        mAllGpa = mgpadao.getSGpa(i);
    }
    public SGPARepo(Application application) {
        GPADatabase db = GPADatabase.getDatabase(application);
        mgpadao=db.semgpaDao();
        mAllGpa = mgpadao.getSupplies();
    }

    public LiveData<List<SemesterGPAModel>> getSGpa() {
        return mAllGpa;
    }


    public void updateSubname (int id,String subname) {
        new insertAsyncTask(mgpadao,1).execute(new SemesterGPAModel(id,subname,0,0,semnum));
    }
    public void updateMarks (int id,int marks) {
        new insertAsyncTask(mgpadao,2).execute(new SemesterGPAModel(id,"",marks,0,semnum));
    }
    public void updateCredits (int id,int credits) {
        new insertAsyncTask(mgpadao,3).execute(new SemesterGPAModel(id,"",0,credits,semnum));
    }
    public void delete(int id){
        new insertAsyncTask(mgpadao,4).execute(new SemesterGPAModel(id,"",0,0,semnum));
    }
    public void insert(String subname,int marks,int credits){
        new insertAsyncTask(mgpadao,5).execute(new SemesterGPAModel(0,subname,marks,credits,semnum));
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
                case 1: mAsyncTaskDao.updateSubname(params[0].getId(), params[0].getSubname(),params[0].getSumnum()); break;
                case 2: mAsyncTaskDao.updateMarks(params[0].getId(), params[0].getMarks(),params[0].getSumnum()); break;
                case 3: mAsyncTaskDao.updateCredits(params[0].getId(), params[0].getCredits(),params[0].getSumnum()); break;
                case 4: mAsyncTaskDao.deleteSem(params[0].getId(),params[0].getSumnum()); break;
                case 5: mAsyncTaskDao.insert(params[0]); break;
            }
            return null;
        }
    }

}
