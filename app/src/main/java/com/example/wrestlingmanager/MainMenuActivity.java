package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster; // Roster containing all Wrestlers
    private TestRosterFill  testRosterFill = new TestRosterFill(); //This is used to debug and test features (DEV USE)
    WrestlerDatabase wrestlerDB; // Database for Wrestlers
    //MatchDatabase matchDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Callback set for Database, needed for database to work as intended
       RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        //matchDB = Room.databaseBuilder(getApplicationContext(),MatchDatabase.class,"matchDB").addCallback(myCallBack).build();
        //Set up wrestler Database
        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(myCallBack).build();
        backgroundThread thread = new backgroundThread();
        thread.start();
    }

    /*
     * Code for Roster button, will take us to RosterActivity and send Roster as data
     */
    public void onRosterClicked(View view) {
        Intent intent = new Intent(this,RosterActivity.class);
        //intent.putExtra("Roster",roster); Might not be needed.
        startActivity(intent);
    }

    /*
     * Code for Event button, will take us to Event Activity and send Roster as data
     */
    public void onEventClicked(View view) {
        Intent intent = new Intent(this,EventActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }

    /*
     * Code for DB button, will take us to DBTestActivity, this activity is meant for testing purposes pertaining to the Database
     * WILL NOT BE APART OF USER EXPERIENCE
     */

    public void onDBClicked(View view) {
        //backgroundThread thread = new backgroundThread();
        //thread.start();
        Intent intent = new Intent(this,DBTestActivity.class);
        startActivity(intent);
    }


    //BackgroundThread used to create Roster
    class backgroundThread extends Thread{
        @Override
        public void run(){
            roster = (ArrayList<Wrestler>) wrestlerDB.getWrestlerDao().getAll();
        }
    }
}