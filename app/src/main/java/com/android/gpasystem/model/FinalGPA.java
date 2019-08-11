package com.android.gpasystem.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 8/10/2019.
 */
@Entity(tableName = "finalgpa")
public class FinalGPA {

    @PrimaryKey
    private int num;

    private float gpa;

    public FinalGPA(int num,float gpa) {
        this.num=num;
        this.gpa=gpa;
    }

    public float getGpa(){return this.gpa;}

    public int getNum(){return this.num;}
}