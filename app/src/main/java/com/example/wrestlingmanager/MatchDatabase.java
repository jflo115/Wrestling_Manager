package com.example.wrestlingmanager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Match.class}, version = 1)
public abstract class MatchDatabase extends RoomDatabase{
    private static MatchDatabase singleton = null;

    public abstract MatchDAO getMatchDAO();

    public synchronized  static MatchDatabase getSingleton(Context context) {
        if(singleton == null) {
            singleton = MatchDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static MatchDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context,MatchDatabase.class,"Match_app.db")
                .allowMainThreadQueries()
                .build();
    }
}
