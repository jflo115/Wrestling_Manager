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
    private Wrestler wrestler;  // Textview for : Wrestler from user's roster which is participant in Match
    private TextView School; // Textview for : School name of the opponent of the match
    private TextView Name; // Textview for : Name of the opponent of the match
    private TextView Time; // Textview for : Time it took for a pin in the match (if applicable)
    private TextView Score; // Textview for : Score of the match
    private CheckBox Win; // Checkbox to determine if Wrestler from our roster won the match or not
    private CheckBox Pin; // Checkbox to determine if there was a pin involved in the match
    private ArrayList<Wrestler> roster; // User Roster containing all wrestlers
    private int pos; // Position of current wrestler in the Roster (arraylist), should be sent from prior intent
    private int source; // Int to determine which Activity we are arriving from, ensures we return and send info back to correct Act.
    WrestlerDatabase wrestlerDB; // Database of Wrestlers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        //Roster setup from prior intent
        roster = (ArrayList<Wrestler>)getIntent().getSerializableExtra("AddMatch");
        pos = getIntent().getIntExtra("position",pos); //Position of current wrestler taken from prior intent
        source = getIntent().getIntExtra("source",source); // Source/Activity we have traversed from
        // source = 1 == ViewWrestler, 2 == StartTourney
        wrestler = roster.get(pos); //Using pos we get the wrestler we were currently viewing before adding a match


        //Set up Views
        School = findViewById(R.id.AddOpponentSchool);
        Name = findViewById(R.id.AddOpponentName);
        Time = findViewById(R.id.AddMatchTime);
        Score = findViewById(R.id.AddMatchScore);
        Win = findViewById(R.id.WinCheckBox);
        Pin = findViewById(R.id.PinCheckBox);

        //Needed for Wrestler database
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



        // Checks if pin checkbox is checked, if so we have to hide Score textview and show Time textview
        // If it is not checked, if so we have to hide Time textview and show Score textview

        //possible refactor
        if(Pin.isChecked()) {
            Time.setVisibility(TextView.VISIBLE);
            Score.setVisibility(TextView.INVISIBLE);
        } else{
            Time.setVisibility(TextView.INVISIBLE);
            Score.setVisibility(TextView.VISIBLE);
        }



    }

    /*
     * Code for pin checkbox, if pin is checked or unchecked, necessary changes will be made
     */
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
        // Depending on whether a pin is determined to be made in the match (using the pin checkbox)
        // we will either extract the string from the Time textview or Score textview
        String scoreOrTime = (Pin.isChecked()) ? Time.getText().toString() : Score.getText().toString();
        // Checks to see if all textviews are filled out before Match is made
        if(name.equals("") || school.equals("") || scoreOrTime.equals("")) {
            Utilities.showAlert(this,"Fill out all boxes or press back");
        }
        else {
            // Adding WBF or LBF to make records look nicer
            if(Pin.isChecked() && Win.isChecked()){
                scoreOrTime = "WBF " + scoreOrTime;
            }
            else if(Pin.isChecked() && !Win.isChecked()){
                scoreOrTime = "LBF " + scoreOrTime;
            }
            // Match made and added to the record of current Wrestler
            Match matchToBeAdded = new Match(Name.getText().toString(), School.getText().toString(),
                    Win.isChecked(), scoreOrTime);
            wrestler.getRecord().addMatch(matchToBeAdded);


            // Background thread where wrestler will be updated with its new match in its record
            backgroundThread thread = new backgroundThread(wrestler);
            thread.start();

            // Used will now be taken back to ViewWrestlerActivity or StartTourneyActivity
            if(source == 1){
                Intent intent = new Intent(this,ViewWrestlerActivity.class);
                intent.putExtra("viewWrestler", (Serializable) roster);
                intent.putExtra("position",pos);
                startActivity(intent);
            }
            else if(source == 2){
                Intent intent = new Intent(this,StartTourneyActivity.class);
                intent.putExtra("Roster", (Serializable) roster);
                ArrayList<Integer> selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
                intent.putExtra("Selected", selectedIND);
                intent.putExtra("position",pos);
                startActivity(intent);
            }
            else{
                Utilities.showAlert(this,"Unknown Error");
            }
        }
    }


    /*
     * Code for AddMatchBack button, User will be taken back to ViewWrestlerActivity WITHOUT
     * creating a match and adding it to the record of the current wrestler
     */
    public void onAddMatchBackClicked(View view) {
        if(source == 1){
            Intent intent = new Intent(this,ViewWrestlerActivity.class);
            intent.putExtra("viewWrestler", (Serializable) roster);
            intent.putExtra("position",pos);
            startActivity(intent);
        }
        else if(source == 2){
            Intent intent = new Intent(this,StartTourneyActivity.class);
            intent.putExtra("Roster", (Serializable) roster);
            ArrayList<Integer> selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
            intent.putExtra("Selected", selectedIND);
            intent.putExtra("position",pos);
            startActivity(intent);
        }
        else{
            Utilities.showAlert(this,"Unknown Error");
        }
    }

    /*
     * Background thread that will update our current wrestler in the database
     */
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