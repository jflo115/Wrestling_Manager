package com.example.wrestlingmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Record.class}, version = 1)
@TypeConverters({RecordConverters.class})
public abstract class RecordDatabase extends RoomDatabase {
    private static RecordDatabase singleton = null;

    public abstract RecordDAO getrecordDao();

    public synchronized  static RecordDatabase getSingleton(Context context) {
        if(singleton == null) {
            singleton = RecordDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static RecordDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context,RecordDatabase.class,"record_app.db")
                .allowMainThreadQueries()
                .build();
    }
}
