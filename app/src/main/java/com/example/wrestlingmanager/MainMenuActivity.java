package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if(roster == null) {
            roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        }

        /*if(roster == null) {
            roster = new ArrayList<Wrestler>();
            Wrestler test1 = new Wrestler("Ahmad Saleh", 9, true);
            Wrestler test2 = new Wrestler("Charlie Petersen", 10, false);
            Wrestler test3 = new Wrestler("Rei Ikeda", 11, false);
            Wrestler test4 = new Wrestler("Armaan Nikoo", 10, true);
            Wrestler test5 = new Wrestler("Beni Magyar", 12, true);
            Wrestler test6 = new Wrestler("Joseph Tierney", 11, true);
            Wrestler test7 = new Wrestler("Milla Kulish", 9, false);
            Wrestler test8 = new Wrestler("Lily Mendez", 10, false);

            roster.add(test1);
            roster.add(test2);
            roster.add(test3);
            roster.add(test4);
            roster.add(test5);
            roster.add(test6);
            roster.add(test7);
            roster.add(test8);
        }*/
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
}