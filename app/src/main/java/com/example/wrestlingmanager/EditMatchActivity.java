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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class EditMatchActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;
    private int pos;
    private int MatchPos;
    private Wrestler wrestler;
    private Match match;


    private TextView school;
    private TextView opponent;
    private TextView score;
    private TextView time;
    private CheckBox win;
    private CheckBox pin;
    private int source;
    private ArrayList<Integer> selectedIND;
    WrestlerDatabase wrestlerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match);
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("editMatch");
        pos = getIntent().getIntExtra("position",0);
        MatchPos = getIntent().getIntExtra("MatchPosition",0);
        source = getIntent().getIntExtra("source",source);
        selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
        wrestler = roster.get(pos);
        match = wrestler.getRecord().getMatches().get(MatchPos);

        //Set up Text views
        school = findViewById(R.id.EditMatchSchoolText);
        opponent = findViewById(R.id.EditMatchOpponentText);
        score  = findViewById(R.id.EditMatchScoreText);
        time = findViewById(R.id.EditMatchTimeText);
        win = findViewById(R.id.EditMatchCheckBoxWin);
        pin = findViewById(R.id.EditMatchCheckBoxPin);

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

        school.setText(match.getSchool());
        opponent.setText(match.getOpponent());

        win.setChecked(match.getWin());
        pin.setChecked( match.getScore().contains("WBF") || match.getScore().contains("LBF"));
        if(pin.isChecked()) {
            score.setVisibility(View.INVISIBLE);
            time.setText(match.getScore());
        }
        else{
            time.setVisibility(View.INVISIBLE);
            score.setText(match.getScore());
        }



    }

    public void onEditMatchBackButtonClicked(View view) {
        Intent intent = new Intent(this,ViewWrestlerActivity.class);
        intent.putExtra("position",pos);
        intent.putExtra("viewWrestler",(Serializable) roster);
        intent.putExtra("source",source);
        intent.putExtra("Selected",(Serializable) selectedIND);
        startActivity(intent);
    }

    public void onEditMatchSaveChangesButtonClicked(View view) {
        String name = opponent.getText().toString();
        String sch = school.getText().toString();
        String scoreOrTime = (pin.isChecked()) ? time.getText().toString() : score.getText().toString();
        if(name.equals("") || sch.equals("") || scoreOrTime.equals("")) {
            Utilities.showAlert(this,"Fill out all boxes or press back");
        }
        else{
            match.setOpponent(name);
            match.setSchool(sch);
            match.setScore(scoreOrTime);
            match.setWin(win.isChecked());

            backgroundThread thread = new backgroundThread(wrestler);
            thread.start();

            Intent intent = new Intent(this,ViewWrestlerActivity.class);
            intent.putExtra("position",pos);
            intent.putExtra("viewWrestler",(Serializable) roster);
            intent.putExtra("source",source);
            intent.putExtra("Selected",(Serializable) selectedIND);
            startActivity(intent);

        }

    }

    public void onEditPinCheckClicked(View view) {
        if(pin.isChecked()) {
            time.setVisibility(TextView.VISIBLE);
            score.setVisibility(TextView.INVISIBLE);
        }
        else {
            time.setVisibility(TextView.INVISIBLE);
            score.setVisibility(TextView.VISIBLE);
        }
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