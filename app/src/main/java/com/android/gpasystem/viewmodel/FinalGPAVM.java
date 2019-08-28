package com.android.gpasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.repos.FinalGPARepo;

import java.util.List;


/**
 * Created by Admin on 8/10/2019.
 */

public class FinalGPAVM extends AndroidViewModel {

    private FinalGPARepo mRepository;

    private LiveData<List<FinalGPA>> mAllgpa;

    private LiveData<Float> sgpa;

    public FinalGPAVM (Application application) {
        super(application);
        mRepository = new FinalGPARepo(application);
        mAllgpa = mRepository.getGpa();
    }
    public FinalGPAVM (Application application,int id) {
        super(application);
        mRepository = new FinalGPARepo(application,id);
        sgpa=mRepository.getSGPA();
    }

    public LiveData<List<FinalGPA>> getAllGpa() { return mAllgpa; }

    public LiveData<Float> getsgpa() { return sgpa; }

    public void update(int num,float gpa) {

        mRepository.update(num,gpa);
    }


   // public void delete(int num){ mRepository.update(num,0.0f);}
}


