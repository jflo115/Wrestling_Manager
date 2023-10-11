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

    private ArrayList<Wrestler> roster;
    private TestRosterFill  testRosterFill = new TestRosterFill();
    WrestlerDatabase wrestlerDB;
    //MatchDatabase matchDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

    public void onRosterClicked(View view) {
        Intent intent = new Intent(this,RosterActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }

    public void onEventClicked(View view) {
        Intent intent = new Intent(this,EventActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }

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
            roster = (ArrayList) wrestlerDB.getWrestlerDao().getAll();
        }
    }
}