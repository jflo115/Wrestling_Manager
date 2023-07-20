package com.example.wrestlingmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Wrestler.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class WrestlerDatabase extends RoomDatabase {
    private static WrestlerDatabase singleton = null;

    public abstract WrestlerDAO wrestlerDao();

    public synchronized static WrestlerDatabase getSingleton(Context context){
        if(singleton == null) {
            singleton = WrestlerDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static WrestlerDatabase makeDatabase(Context context){
        return Room.databaseBuilder(context,WrestlerDatabase.class,"wrestler_app.db")
                .allowMainThreadQueries()
                .build();
    }
}
