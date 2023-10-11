package com.example.wrestlingmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RecordDAO {
    @Insert
    public void addRecord(Record record);

    @Insert
    List<Long> addAll(List<Record> recordList);

    @Query("SELECT * FROM `records` WHERE `record_id`=:id")
    public Record getRecord(long id);

    @Query("SELECT * FROM `records`")
    List<Record> getAll();

    @Update
    int updateRecord(Record record);

    @Delete
    int deleteRecord(Record record);
}
