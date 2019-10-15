package com.android.gpasystem.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.gpasystem.model.SemesterGPAModel;
import com.android.gpasystem.repos.SGPARepo;

import java.util.List;


/**
 * Created by Admin on 8/10/2019.
 */

public class SGPAVM extends AndroidViewModel {

    private SGPARepo mRepository;

    private LiveData<List<SemesterGPAModel>> mAllgpa;

     SGPAVM (Application application,int i) {
        super(application);
         mRepository = new SGPARepo(application,i);
         mAllgpa = mRepository.getSGpa();
    }
    SGPAVM (Application application) {
        super(application);
        mRepository = new SGPARepo(application);
        mAllgpa = mRepository.getSGpa();
    }

    public LiveData<List<SemesterGPAModel>> getAllGpa() {

         return mAllgpa; }

    public void updateSubname(int id,String subname) {

        mRepository.updateSubname(id,subname);
    }
    public void updateMarks(int id,int marks) {

        mRepository.updateMarks(id,marks);
    }
    public void updateCredits(int id,int credits) {

        mRepository.updateCredits(id,credits);
    }

    public  void insert(String subname,int marks,int credits){
        mRepository.insert(subname,marks,credits);
    }


     public void delete(int id){ mRepository.delete(id);}
}


