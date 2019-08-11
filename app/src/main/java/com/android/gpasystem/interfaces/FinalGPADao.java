package com.android.gpasystem.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.gpasystem.model.FinalGPA;

import java.util.List;


/**
 * Created by Admin on 8/10/2019.
 */

@Dao
public interface FinalGPADao {

    @Insert
    void insert(FinalGPA gpa);

    @Query("SELECT count(*) from finalgpa")
    int getCount();

    @Query("DELETE FROM finalgpa where num=:num")
    void deleteSem(int num);

    @Update
    void updateSem(FinalGPA gpa);

    @Query("SELECT * from finalgpa")
    LiveData<List<FinalGPA>> getGpa();
}
