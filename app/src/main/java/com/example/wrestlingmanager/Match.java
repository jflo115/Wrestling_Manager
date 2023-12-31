package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Matches")
public class Match implements Serializable {

    @ColumnInfo(name = "match_id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @NonNull
    //private Wrestler wrestler; //Wrestler that Match is about.
    @ColumnInfo(name = "opponent")
    private String opponent; //Last name of Opponent our Wrestler is facing in Match
    @ColumnInfo(name = "school")
    private String school; //Opponent's school
    @ColumnInfo(name = "win")
    private Boolean win; //True if our Wrestler got the win, false if our wrestler Lost
    @ColumnInfo(name = "score")
    private String score; //The score of the match in form : "12 - 3" or WBF/LBF and time.

    public Match(String opponent,String school,Boolean win, String score) {
        //this.wrestler = wrestler;
        this.opponent = opponent;
        this.school = school;
        this.win = win;
        this.score = score;
    }

    //public void setWrestler(@NonNull Wrestler wrestler) {
        //this.wrestler = wrestler;
   //}

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getOpponent() {
        return this.opponent;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    public String getSchool() { return this.school;}

    public void setWin(Boolean win) {
        this.win = win;
    }
    public Boolean getWin(){return this.win;}

    public void setScore(String score) {
        this.score = score;
    }
    public String getScore(){ return this.score;}
}
