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
    @ColumnInfo(name = "Accomplishments")
    private ArrayList<String> accomplishments; // Accomplishments from the year, Can be worded any way
    @ColumnInfo(name = "name")
    private String name; //Name of wrestler
    @ColumnInfo(name = "grade")
    private int grade; // Grade level of Wrestler, can only be from 9 - 12
    //private int wins, losses; // Number of wins and losses of Wrestler
    @ColumnInfo(name = "gender")
    private Boolean gender; //True for Boy, False for Girl
    @ColumnInfo(name = "record")
    private Record record; //Current record of Wrestler;


    //Constructor for Wrestler
    public Wrestler(@NonNull String name, int grade, boolean gender) {
        this.name = name;
        this.grade = grade;
        this.accomplishments = new ArrayList<String>();
        //accomplishments.add("None");
        this.record = new Record();
        this.gender = gender;
    }

    // Gets Name
    public String getName() {
        return name;
    }

    // Sets Name
    public void setName(String name) {
        this.name = name;
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    @NonNull
    public ArrayList<String> getAccomplishments() {
        return accomplishments;
    }


    public void setAccomplishments(ArrayList<String> accomplishments){
        this.accomplishments.clear();
        this.accomplishments.addAll(accomplishments);
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
        return String.valueOf(this.record.getWins()) + "-" + String.valueOf(this.record.getLosses());
    }
}
