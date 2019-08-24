package com.android.gpasystem.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * Created by Admin on 8/23/2019.
 */
public class SGPAVmFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private int id;


    public SGPAVmFactory(Application application, int idd) {
        mApplication = application;
        id = idd;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SGPAVM(mApplication, id);
    }
}