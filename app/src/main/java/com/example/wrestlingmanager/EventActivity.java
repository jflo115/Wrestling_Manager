package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;
    ImageButton dual;
    ImageButton tourney;
    private WrestlerDatabase wrestlerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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
        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(myCallBack).build();

        /* For some reason, despite not needing the database, not setting it up in this Activity was
         * causing crashes when we traversed to another activity, back to this one, and hit the ImageButton
         */

        dual = findViewById(R.id.DualMeetButton);

        dual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this,DualMeetActivity.class);
                startActivity(intent);
            }
        });

        tourney = findViewById(R.id.TournamentButton);
        tourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this,TourneyActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onEventBackButtonClicked(View view) {
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);
    }
}