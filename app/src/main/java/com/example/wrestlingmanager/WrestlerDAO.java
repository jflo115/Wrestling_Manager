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
public interface WrestlerDAO {
    @Insert
    long insert(Wrestler wrestler);

    @Insert
    List<Long> insertAll(List<Wrestler> wrestlerList);

    @Query("Select * FROM `wrestler_items` WHERE `id`=:id")
    Wrestler get(long id);

    @Query("SELECT * FROM `wrestler_items`")
    List<Wrestler> getAll();

    @Update
    int update(Wrestler wrestler);

    @Delete
    int delete(Wrestler wrestler);
}
