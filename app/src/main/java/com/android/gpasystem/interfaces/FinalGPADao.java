package com.android.gpasystem.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

//    @Query("DELETE FROM finalgpa where num=:num")
//    void deleteSem(int num);

    @Update
    void updateSem(FinalGPA gpa);

    @Query("SELECT * from finalgpa")
    LiveData<List<FinalGPA>> getGpa();

    @Query("SELECT gpa from finalgpa where num=:num ")
    LiveData<Float> getGpa(int num);
}
