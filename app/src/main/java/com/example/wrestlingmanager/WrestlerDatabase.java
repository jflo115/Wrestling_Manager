package com.example.wrestlingmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Wrestler.class}, version = 1)
@TypeConverters(WrestlerConverters.class)
public abstract class WrestlerDatabase extends RoomDatabase {
    private static WrestlerDatabase singleton = null;

    public abstract WrestlerDAO getWrestlerDao();

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
