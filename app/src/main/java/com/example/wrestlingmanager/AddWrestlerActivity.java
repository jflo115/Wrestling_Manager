package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class AddWrestlerActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;
    WrestlerDatabase wrestlerDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wrestler);
        // Roster set up by extracting data sent over with Intent
        //roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");

        // Needed for Database to work
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
        // Database setup
        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(myCallBack).build();

        //Creates background thread which sets up Roster
        RosterThread rosterThread = new RosterThread();
        rosterThread.start();

    }

    /*
     * Code for back button, takes user back to RosterActivity WITHOUT creating a new wrestler
     * sends Roster as data.
     */
    public void onAddWrestler2Clicked(View view) {
        Intent intent = new Intent(this,RosterActivity.class);
        //intent.putExtra("Roster",roster); Not needed
        startActivity(intent);
    }

    /*
     * Code for CreateWrestler button, with date that was filled out by user, a new Wrestler object will be created
     * and will be added to the Roster. Then user will be taken back to RosterActivity with roster sent as data.
     */
    public void onCreateWrestlerClicked(View view) {
        //New Wrestler created
        TextView name = findViewById(R.id.addWrestlerName); // Textview set for where the name of Wrestler-to-be should be
        TextView grade = findViewById(R.id.addWrestlerGrade); // Textview set for where the grade(int only) of Wrestler-to-be should be
        Switch sexSwitch = findViewById(R.id.WrestlerSwitch); //Switch set to determine the sex of Wrestler-to-be
        // App cant continue if some boxes were not filled, will notify user and wont create a wrestler
        if(name.getText().toString().equals("") || grade.getText().toString().equals("")) {
            Utilities.showAlert(this,"Fil out all boxes or press back");
        }
        else {
            String gradeString = grade.getText().toString();
            int gradeInt = Integer.parseInt(gradeString);
            Wrestler newWrestler = new Wrestler(name.getText().toString(), gradeInt, !sexSwitch.isChecked());

            roster.add(new Wrestler(name.getText().toString(), gradeInt, !sexSwitch.isChecked()));
            //Thread started in background to add wrestler to Database
            backgroundThread thread = new backgroundThread(newWrestler);
            thread.start();

            //return to prior Activity while sending Roster back with new Wrestler
            Intent intent = new Intent(this, RosterActivity.class);
            //intent.putExtra("Roster", roster); Not needed
            startActivity(intent);
        }
    }

    private class backgroundThread extends Thread{
        Wrestler wrestler;
        public backgroundThread(Wrestler wrestler){
            this.wrestler = wrestler;
        }
        @Override
        public void run(){
            wrestlerDB.getWrestlerDao().addWrestler(wrestler);
        }
    }

    private class RosterThread extends Thread{
        public RosterThread(){}

        @Override
        public void run(){
            roster = (ArrayList<Wrestler>) wrestlerDB.getWrestlerDao().getAll();
        }
    }
}