package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "records")
public class Record implements Serializable {

    @ColumnInfo(name = "record_id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    //@TypeConverters(Converters.class)
    private ArrayList<Match> matches;
    @ColumnInfo(name="wins")
    private int wins;
    @ColumnInfo(name="losses")
    private int losses;

    public Record() {
        this.matches = new ArrayList<Match>();
        wins = 0;
        losses = 0;
    }

    public void addMatch(Match match) {
        this.matches.add(match);
        if(match.getWin()) {
            wins++;
        }
        else{
            losses++;
        }
    }


    public void setMatches(@NonNull ArrayList<Match> matches) {
        this.matches = matches;
    }

    public ArrayList<Match> getMatches() {
        return this.matches;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWins(){
        return this.wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getLosses() {
        return this.losses;
    }

    public String toString(){
        return wins + " - " + losses;
    }
}
