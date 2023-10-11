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
public interface MatchDAO {
    @Insert
    public void addMatch(Match match);

    @Insert
    List<Long> addAll(List<Match> matchList);

    @Update
    public void updateMatch(Match match);

    @Delete
    public void deleteMatch(Match match);

    @Query("select * from Matches")
    public List<Match> getAllMatches();

    @Query("select * from Matches where match_id==:match_id")
    public Match getMatch(int match_id);

}
