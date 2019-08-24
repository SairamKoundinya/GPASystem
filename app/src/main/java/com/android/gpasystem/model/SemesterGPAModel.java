package com.android.gpasystem.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 8/10/2019.
 */
@Entity(tableName = "sgpa")
public class SemesterGPAModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String subname;
    private int marks;
    private int credits;


    public SemesterGPAModel(int id,String subname,int marks,int credits) {
        this.id=id;
        this.subname=subname;
        this.marks=marks;
        this.credits=credits;
    }

    public int getId() { return this.id;}
    public String getSubname() { return this.subname;}
    public int getMarks() { return this.marks;}
    public int getCredits() { return this.credits;}



}