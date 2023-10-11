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
    long addWrestler(Wrestler wrestler);

    @Insert
    List<Long> addAll(List<Wrestler> wrestlerList);

    @Query("SELECT * FROM `wrestler_items` WHERE `id`=:id")
    Wrestler getWrestler(long id);

    @Query("SELECT * FROM `wrestler_items`")
    List<Wrestler> getAll();

    @Update
    public void updateWrestler(Wrestler wrestler);

    @Delete
    public void deleteWrestler(Wrestler wrestler);
}
