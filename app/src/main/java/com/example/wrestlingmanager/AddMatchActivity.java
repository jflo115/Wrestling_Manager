package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
    WrestlerDatabase wrestlerDB;

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

        //Set up wrestler Database
        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(myCallBack).build();



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
            if(Pin.isChecked() && Win.isChecked()){
                scoreOrTime = "WBF " + scoreOrTime;
            }
            else if(Pin.isChecked() && !Win.isChecked()){
                scoreOrTime = "LBF " + scoreOrTime;
            }
            wrestler.getRecord().addMatch(new Match(
                    Name.getText().toString(),
                    School.getText().toString(),
                    Win.isChecked(), scoreOrTime
            ));

            backgroundThread thread = new backgroundThread(wrestler);
            thread.start();

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

    public class backgroundThread extends Thread{
        private Wrestler wrestler;

        public backgroundThread(Wrestler wrestler){
            this.wrestler = wrestler;
        }

        @Override
        public void run(){
            wrestlerDB.getWrestlerDao().updateWrestler(wrestler);
        }
    }
}