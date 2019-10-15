package com.android.gpasystem.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.gpasystem.model.SemesterGPAModel;
import java.util.List;

/**
 * Created by Admin on 8/23/2019.
 */
@Dao
public interface SemesterGPADao {

    @Insert
    void insert(SemesterGPAModel gpa);

    @Query("SELECT count(*) from sgpa where sumnum=:sumnum")
    int getCount(int sumnum);

    @Query("DELETE FROM sgpa where id=:id and sumnum=:sumnum")
    void deleteSem(int id,int sumnum);

    @Query("update sgpa set subname=:subname where id=:id and sumnum=:sumnum")
    void updateSubname(int id, String subname,int sumnum);

    @Query("update sgpa set marks=:marks where id=:id and sumnum=:sumnum")
    void updateMarks(int id,int marks,int sumnum);

    @Query("update sgpa set credits=:credits where id=:id and sumnum=:sumnum")
    void updateCredits(int id,int credits,int sumnum);

    @Query("SELECT * from sgpa where sumnum=:sumnum")
    LiveData<List<SemesterGPAModel>> getSGpa(int sumnum);

    @Query("SELECT * from sgpa where marks=0 and credits!=0 order by sumnum")
    LiveData<List<SemesterGPAModel>> getSupplies();


}
