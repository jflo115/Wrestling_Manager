package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class AddMatchActivity extends AppCompatActivity {

    //Local Variables
    private Wrestler wrestler;
    private TextView School;
    private TextView Name;
    private TextView Time;
    private TextView Score;
    private CheckBox Win;
    private CheckBox Pin;
    private ArrayList<Wrestler> roster;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        roster = (ArrayList<Wrestler>)getIntent().getSerializableExtra("AddMatch");
        pos = getIntent().getIntExtra("position",pos);
        wrestler = roster.get(pos);


        //Set up Views
        School = findViewById(R.id.AddOpponentSchool);
        Name = findViewById(R.id.AddOpponentName);
        Time = findViewById(R.id.AddMatchTime);
        Score = findViewById(R.id.AddMatchScore);
        Win = findViewById(R.id.WinCheckBox);
        Pin = findViewById(R.id.PinCheckBox);

        //Possible Refactor?
        if(Pin.isChecked()) {
            Time.setVisibility(TextView.VISIBLE);
            Score.setVisibility(TextView.INVISIBLE);
        } else{
            Time.setVisibility(TextView.INVISIBLE);
            Score.setVisibility(TextView.VISIBLE);
        }



    }

    public void onPinClicked(View view) {
        if(Pin.isChecked()) {
            Time.setVisibility(TextView.VISIBLE);
            Score.setVisibility(TextView.INVISIBLE);
        } else{
            Time.setVisibility(TextView.INVISIBLE);
            Score.setVisibility(TextView.VISIBLE);
        }

    }

    public void onAddMatchClicked2(View view) {
        String name = Name.getText().toString();
        String school = School.getText().toString();
        String scoreOrTime = (Pin.isChecked()) ? Time.getText().toString() : Score.getText().toString();
        if(name.equals("") || school.equals("") || scoreOrTime.equals("")) {
            Utilities.showAlert(this,"Fill out all boxes or press back");
        }
        else {
            wrestler.getRecord().addMatch(new Match(
                    Name.getText().toString(),
                    School.getText().toString(),
                    Win.isChecked(), scoreOrTime
            ));
            Intent intent = new Intent(this, ViewWrestlerActivity.class);
            intent.putExtra("viewWrestler", (Serializable) roster);
            intent.putExtra("position", pos);
            startActivity(intent);
        }
    }



    public void onAddMatchBackClicked(View view) {
        Intent intent = new Intent(this,ViewWrestlerActivity.class);
        intent.putExtra("viewWrestler", (Serializable) roster);
        intent.putExtra("position",pos);
        startActivity(intent);
    }
}