package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;
    private TestRosterFill  testRosterFill = new TestRosterFill();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        if(roster == null) {
            roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        }
        if(roster == null) {
            roster = testRosterFill.create();
        }
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