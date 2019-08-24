package com.android.gpasystem.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.android.gpasystem.SemesterGPA;
import com.android.gpasystem.model.SemesterGPAModel;

import java.util.List;

/**
 * Created by Admin on 8/23/2019.
 */
@Dao
public interface SemesterGPADao {

    @Insert
    void insert(SemesterGPAModel gpa);

    @Query("SELECT count(*) from sgpa")
   int getCount();

   @Query("DELETE FROM sgpa where id=:id")
   void deleteSem(int id);

    @Query("update sgpa set subname=:subname where id=:id")
    void updateSubname(int id,String subname);

    @Query("update sgpa set marks=:marks where id=:id")
    void updateMarks(int id,int marks);

    @Query("update sgpa set credits=:credits where id=:id")
    void updateCredits(int id,int credits);

    @Query("SELECT * from sgpa")
    List<SemesterGPAModel> getSGpa();
}
