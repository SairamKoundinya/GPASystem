package com.android.gpasystem.databases;

/**
 * Created by Admin on 8/10/2019.
 */

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.model.FinalGPA;


@Database(entities = {FinalGPA.class}, version = 1, exportSchema = false)
public abstract class GPADatabase extends RoomDatabase {

    public abstract FinalGPADao finalgpaDao();

    private static volatile GPADatabase INSTANCE;

    public static GPADatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GPADatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GPADatabase.class, "gpa_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}