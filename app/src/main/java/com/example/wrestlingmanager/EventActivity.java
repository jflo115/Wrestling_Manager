package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
    }

    public void onDualMeetButtonClicked(View view) {
        Intent intent = new Intent(this,DualMeetActivity.class);
        intent.putExtra("Roster", roster);
        startActivity(intent);
    }

    public void onTournamentButtonClicked(View view) {
        //Utilities.showAlert(this,"Not available yet");
        Intent intent = new Intent(this, TourneyActivity.class);
        intent.putExtra("Roster", roster);
        startActivity(intent);
    }

    public void onEventBackButtonClicked(View view) {
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }
}