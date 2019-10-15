package com.android.gpasystem.databases;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.android.gpasystem.interfaces.FinalGPADao;
import com.android.gpasystem.interfaces.SemesterGPADao;
import com.android.gpasystem.model.FinalGPA;
import com.android.gpasystem.model.SemesterGPAModel;


@Database(entities = {FinalGPA.class , SemesterGPAModel.class}, version = 1, exportSchema = false)
public abstract class GPADatabase extends RoomDatabase {

    public abstract FinalGPADao finalgpaDao();
    public abstract SemesterGPADao semgpaDao();

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