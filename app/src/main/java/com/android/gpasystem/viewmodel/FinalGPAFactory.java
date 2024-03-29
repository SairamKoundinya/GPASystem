package com.android.gpasystem.viewmodel;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

/**
 * Created by Admin on 8/23/2019.
 */
public class FinalGPAFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int id;


    public FinalGPAFactory(Application application, int idd) {
        mApplication = application;
        id = idd;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FinalGPAVM(mApplication, id);
    }
}