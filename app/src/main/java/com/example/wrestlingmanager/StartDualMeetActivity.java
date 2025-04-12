package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartDualMeetActivity extends AppCompatActivity {

    private ConstraintLayout scene1;
    private ConstraintLayout scene2;
    private ConstraintLayout scene3;
    private String[] weights = {"106","113","120","126","132","138","144","150","157","165","175","190","215","285"};
    private ArrayList<Wrestler> roster;
    private ArrayList<Integer> selectedIND;
    private Wrestler[] lineup;
    private int[] lineupIND;
    private String[] oppNames;
    private Match[] matches = new Match[14];
    private String schoolName;
    //String selectedWeight = "";
    private int selectedWeight = -1;
    private int currMatchNumber = 0;
    private TextView schoolNameTextview,IrvineWrestlerTextview,OppWrestlerTextview, WeightClassTextview;
    private TextView HomeScore, OppScore, HomeTime, OppTime,HomeTeamScore,OppTeamScore;
    private CheckBox HomePin, OppPin;
    private TextView DualMeetResultsText;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> adapterItems;
    private int HomeTeamScoreNum = 0;
    private int OppTeamScoreNum = 0;
    WrestlerDatabase wrestlerDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_dual_meet);

        //Get Extras from intent
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
        lineupIND = (int[]) getIntent().getSerializableExtra("Lineup");
        oppNames = (String[]) getIntent().getSerializableExtra("oppNames");
        schoolName = (String) getIntent().getStringExtra("schoolName");

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

        //Set up lineup
        lineup = new Wrestler[14];
        for(int i = 0; i < lineup.length;i++){
            if(lineupIND[i] != -1) {
                lineup[i] = roster.get(lineupIND[i]);
            }
            else{
                lineup[i] = null;
            }
        }

        //Set up Constraint Layouts
        scene1 = findViewById(R.id.DualMeetFirstLayout);
        scene2 = findViewById(R.id.DualMeetInitiatedLayout);
        scene3 = findViewById(R.id.DualMeetFinalLayout);

        //Set up Visibilities of Scenes
        scene1.setVisibility(View.VISIBLE);
        scene2.setVisibility(View.GONE);
        scene3.setVisibility(View.GONE);

        // Set up Title textview
        TextView title = findViewById(R.id.DualMeetTitle);
        title.setText("Irvine vs " + schoolName);

        // Set up Wrestler and Opponent Names Textviews
        IrvineWrestlerTextview = findViewById(R.id.startDualMeetWrestlerNameText);
        schoolNameTextview = findViewById(R.id.StartDualMeetOppSchoolText);
        OppWrestlerTextview = findViewById(R.id.startDualMeetOppText);
        WeightClassTextview = findViewById(R.id.DualMeetWeightClass);

        //Set up Score/Time Textviews and Pin checkboxes
        HomeScore = findViewById(R.id.DualMeetWrestlerScore);
        OppScore = findViewById(R.id.DualMeetOppScore);
        HomeTime = findViewById(R.id.DualMeetWrestlerTime);
        OppTime = findViewById(R.id.DualMeetOppTime);
        HomePin = findViewById(R.id.DualMeetWrestlerPinBox);
        OppPin = findViewById(R.id.DualMeetOppPinBox);

        //Set up TeamScore Textviews
        HomeTeamScore = findViewById(R.id.DualMeetHomeTeamScore);
        OppTeamScore = findViewById(R.id.DualMeetOppTeamScore);


        //Set up DropDown Box
        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this,R.layout.weight_list,weights);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                selectedWeight = i;
                Toast.makeText(StartDualMeetActivity.this,item,Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onStartDualButton2Clicked(View view) {
        scene1.setVisibility(View.GONE);
        scene2.setVisibility(View.VISIBLE);

        currMatchNumber++;  //Will be 1 at this point
        schoolNameTextview.setText(schoolName);
        WeightClassTextview.setText(weights[selectedWeight]);
        if(oppNames[selectedWeight].equals("") || oppNames[selectedWeight].equals("BYE")){
            OppWrestlerTextview.setText("Bye");
        }
        else {
            OppWrestlerTextview.setText(oppNames[selectedWeight]);
        }
        if(lineup[selectedWeight] == null){
            IrvineWrestlerTextview.setText("Bye");
        }
        else {
            IrvineWrestlerTextview.setText(lineup[selectedWeight].getName());
        }
    }

    public void onStartDualNextClicked(View view) {
        //Catching Errors/things we can not continue with
        boolean err = false;
        if(HomePin.isChecked()){
            if(HomeTime.getText().toString().equals("")){
                Utilities.showAlert(this,"Home Wrestler was indicated to have won by pin, please fill out a time of pin");
                err = true;
            }
        }
        else if(OppPin.isChecked()){
            if(OppTime.getText().toString().equals("")){
                Utilities.showAlert(this,"Opponent was indicated to have won by pin, please fill out a time of pin");
                err = true;
            }
        }
        else if(HomeScore.getText().toString().equals("") || OppScore.getText().toString().equals("")){
            Utilities.showAlert(this,"Atleast one of scores has not been filled out");
            err = true;
        }
        else if(Integer.parseInt(HomeScore.getText().toString()) ==
                Integer.parseInt(OppScore.getText().toString())) {
            Utilities.showAlert(this,"A tie has been detected, please make sure score is correct.");
            err = true;
        }

        if(!err) {
            //Determine Match result
            Boolean win;
            String score;
            if (HomePin.isChecked()) {
                win = true;
                score = "WBF " + HomeTime.getText().toString();
                HomeTeamScoreNum += 6;
                HomeTeamScore.setText(Integer.toString(HomeTeamScoreNum));
            } else if (OppPin.isChecked()) {
                win = false;
                score = "LBF " + OppTime.getText().toString();
                OppTeamScoreNum += 6;
                OppTeamScore.setText(Integer.toString(OppTeamScoreNum));
            } else if (Integer.parseInt(HomeScore.getText().toString()) >
                    Integer.parseInt(OppScore.getText().toString())) {
                win = true;
                score = HomeScore.getText().toString() + "-" + OppScore.getText().toString();
                int matchScoreDiff = Integer.parseInt(HomeScore.getText().toString()) - Integer.parseInt(OppScore.getText().toString());
                if(matchScoreDiff >= 15){
                    HomeTeamScoreNum += 5;
                    HomeTeamScore.setText(Integer.toString(HomeTeamScoreNum));
                }
                else if(matchScoreDiff >= 8){
                    HomeTeamScoreNum += 4;
                    HomeTeamScore.setText(Integer.toString(HomeTeamScoreNum));
                }
                else{
                    HomeTeamScoreNum += 3;
                    HomeTeamScore.setText(Integer.toString(HomeTeamScoreNum));
                }

            } else {
                win = false;
                score = HomeScore.getText().toString() + "-" + OppScore.getText().toString();
                int matchScoreDiff = Integer.parseInt(OppScore.getText().toString()) - Integer.parseInt(HomeScore.getText().toString());
                if(matchScoreDiff >= 15){
                    OppTeamScoreNum += 5;
                    OppTeamScore.setText(Integer.toString(OppTeamScoreNum));
                }
                else if(matchScoreDiff >= 8){
                    OppTeamScoreNum += 4;
                    OppTeamScore.setText(Integer.toString(OppTeamScoreNum));
                }
                else{
                    OppTeamScoreNum += 3;
                    OppTeamScore.setText(Integer.toString(OppTeamScoreNum));
                }
            }
            matches[selectedWeight] = new Match(OppWrestlerTextview.getText().toString(),
                    schoolName,
                    win,
                    score);

            //If we already were on the 14th Match, we should be finished with the Dual
            if (currMatchNumber == 14) {
                for (int i = 0; i < 14; i++) {
                    if (lineup[i] != null && !oppNames[i].equals("")) {
                        lineup[i].getRecord().addMatch(matches[i]);
                        backgroundThread thread = new backgroundThread(lineup[i]);
                        thread.start();
                    }
                }
                scene2.setVisibility(View.GONE);
                scene3.setVisibility(View.VISIBLE);

                DualMeetResultsText = findViewById(R.id.DualMeetResultsText);
                if(HomeTeamScoreNum > OppTeamScoreNum){
                    DualMeetResultsText.setText("Irvine Wins \n" + HomeTeamScoreNum
                            + "-" + OppTeamScoreNum + "\n\n CONGRATS!");
                    DualMeetResultsText.setTextColor(Color.GREEN);
                }
                else if(OppTeamScoreNum > HomeTeamScoreNum){
                    DualMeetResultsText.setText(schoolName + " Wins \n"
                    + OppTeamScoreNum + "-" + HomeTeamScoreNum + "\n\n MAYBE NEXT TIME");
                    DualMeetResultsText.setTextColor(Color.RED);
                }
                else{
                    DualMeetResultsText.setText("There Was A Tie \n" + HomeTeamScoreNum
                            + "-" + OppTeamScoreNum);
                }
                /*
                Intent intent = new Intent(this, MainMenuActivity.class);
                intent.putExtra("Roster", roster);
                startActivity(intent); */
            }


            currMatchNumber++;
            selectedWeight++;


            //Prevents out of bounds for array, will have to cycle back to 106
            if (selectedWeight > 13) {
                selectedWeight = 0;
            }
            //We are now currently on the 14th match. Button Text will change
            if (currMatchNumber == 14) {
                Button next = findViewById(R.id.StartDualNextButton);
                next.setText("Finish Dual");
            }

            //Scene Setup
            if(oppNames[selectedWeight].equals("") || oppNames[selectedWeight].equals("BYE")){
                OppWrestlerTextview.setText("Bye");
            }
            else {
                OppWrestlerTextview.setText(oppNames[selectedWeight]);
            }
            if(lineup[selectedWeight] == null){
                IrvineWrestlerTextview.setText("Bye");
            }
            else {
                IrvineWrestlerTextview.setText(lineup[selectedWeight].getName());
            }
            //Changes Weight Class - lets user know weight class of match
            WeightClassTextview.setText(weights[selectedWeight]);

            // Reset scores and Textviews
            HomeScore.setText("");
            OppScore.setText("");
            HomeTime.setText("");
            OppTime.setText("");
            HomeScore.setVisibility(View.VISIBLE);
            OppScore.setVisibility(View.VISIBLE);
            HomeTime.setVisibility(View.GONE);
            OppTime.setVisibility(View.GONE);
            HomePin.setChecked(false);
            OppPin.setChecked(false);


        }
    }

    public void onDualMeetWrestlerPinClicked(View view) {
        //Check if Opp pin is checked. We will uncheck it and set visibility
        if(OppPin.isChecked()){
            OppPin.setChecked(false);
            OppTime.setVisibility(View.GONE);
            HomeTime.setVisibility(View.VISIBLE);
        }
        else if(HomeTime.getVisibility() == View.VISIBLE){
            HomeTime.setVisibility(View.GONE);
            HomeScore.setVisibility(View.VISIBLE);
            OppScore.setVisibility(View.VISIBLE);
        }
        else{
            HomeScore.setVisibility(View.GONE);
            OppScore.setVisibility(View.GONE);
            HomeTime.setVisibility(View.VISIBLE);
        }

    }

    public void onDualMeetOppPinClicked(View view) {
        if(HomePin.isChecked()){
            HomePin.setChecked(false);
            HomeTime.setVisibility(View.GONE);
            OppTime.setVisibility(View.VISIBLE);
        }
        else if(OppTime.getVisibility() == View.VISIBLE){
            OppTime.setVisibility(View.GONE);
            HomeScore.setVisibility(View.VISIBLE);
            OppScore.setVisibility(View.VISIBLE);
        }
        else{
            OppTime.setVisibility(View.VISIBLE);
            HomeScore.setVisibility(View.GONE);
            OppScore.setVisibility(View.GONE);
        }
    }

    public void onDualMeetContinue2Clicked(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Roster", roster);
        startActivity(intent);
    }

    public class backgroundThread extends Thread{
        Wrestler wrestler;

        public backgroundThread(Wrestler wrestler){
            this.wrestler = wrestler;
        }

        @Override
        public void run(){
            wrestlerDB.getWrestlerDao().updateWrestler(wrestler);
        }
    }
}