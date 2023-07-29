package com.example.wrestlingmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/* This class is the depicts a wrestler on a team and all the information that each
   wrestler will contain.
 */
@Entity(tableName = "wrestler_items")
public class Wrestler implements Serializable {
    // Public Fields
    @PrimaryKey(autoGenerate = true)
    public long id;


    @NonNull
    private ArrayList<String> accomplishments; // Accomplishments from the year, Can be worded any way
    private String name; //Name of wrestler
    private int grade; // Grade level of Wrestler, can only be from 9 - 12
    private int wins, losses; // Number of wins and losses of Wrestler
    private Boolean gender; //True for Boy, False for Girl
    private Record record; //Current record of Wrestler;


    //Constructor for Wrestler
    public Wrestler(@NonNull String name, int grade, boolean gender) {
        this.name = name;
        this.grade = grade;
        this.wins = 0;
        this.losses = 0;
        this.accomplishments = new ArrayList<String>();
        //accomplishments.add("None");
        this.record = new Record();
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWins() {
        return wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getLosses() {
        return losses;
    }

    @NonNull
    public ArrayList<String> getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(ArrayList<String> accomplishments){
        this.accomplishments = accomplishments;
    }

    public void setRecord(Record record) {
       this.record = record;
    }

    public Record getRecord() {
        return record;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Boolean getGender() {
        return gender;
    }

    public String toStringRecord() {
        return String.valueOf(this.wins) + "-" + String.valueOf(this.losses);
    }
}
