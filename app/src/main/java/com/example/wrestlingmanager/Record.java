package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "records")
public class Record implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    private ArrayList<Match> matches;
    private int wins;
    private int losses;

    public Record() {
        this.matches = new ArrayList<Match>();
        wins = 0;
        losses = 0;
    }

    public void addWin(Match match) {
        this.matches.add(match);
        wins++;
    }

    public void addLoss(Match match) {
        this.matches.add(match);
        losses++;
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
}
