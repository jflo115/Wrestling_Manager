package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Switch;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditWrestlerActivity extends AppCompatActivity {
    private Wrestler wrestler;
    private ArrayList<Wrestler> roster;
    private int pos;
    WrestlerDatabase wrestlerDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wrestler);

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

        // Creates a background thread which sets up the roster
        RosterThread rosterthread = new RosterThread();
        rosterthread.start();


        //roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("editWrestler");
        pos = getIntent().getIntExtra("position",0);
        wrestler = roster.get(pos);

        //Set up Textviews
        TextView WrestlerName = findViewById(R.id.EditWrestlerNameText);
        WrestlerName.setText(wrestler.getName());

        TextView WrestlerGrade = findViewById(R.id.EditWrestlerGradeText);
        WrestlerGrade.setText(Integer.toString(wrestler.getGrade()));

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch genderSwitch = findViewById(R.id.EditWrestlerGenderSwitch);
        if(wrestler.getGender()) {
            genderSwitch.setChecked(false);
        }
        else {
            genderSwitch.setChecked(true);
        }

        TextView accomplishments = findViewById(R.id.EditWrestlerAccText);
        int length = wrestler.getAccomplishments().size();
        String text = new String();
        if(wrestler.getAccomplishments().isEmpty()) {
            text = "none";
        }
        else {
            text = wrestler.getAccomplishments().get(0) + "\n";

            for (int i = 1; i < length; i++) {
                text += wrestler.getAccomplishments().get(i) +"\n";
            }
        }
        accomplishments.setText(text);

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public void onFinishEditingClicked(View view) {
        TextView WrestlerName = findViewById(R.id.EditWrestlerNameText);
        TextView WrestlerGrade = findViewById(R.id.EditWrestlerGradeText);
        Switch genderSwitch = findViewById(R.id.EditWrestlerGenderSwitch);
        TextView accomplishments = findViewById(R.id.EditWrestlerAccText);

        if(WrestlerName.getText().toString().equals("") || WrestlerGrade.getText().toString().equals("")){
            Utilities.showAlert(this,"Fill out all boxes or press back");
        }
        else {
            wrestler.setName(WrestlerName.getText().toString());
            wrestler.setGrade(Integer.parseInt(WrestlerGrade.getText().toString()));

            //TODO- REFACTOR //wrestler.setGender(!genderSwitch.isChecked());
            if (!genderSwitch.isChecked()) {
                wrestler.setGender(true);
            } else {
                wrestler.setGender(false);
            }
            String text = accomplishments.getText().toString();
            if (text.equals("")) {
                wrestler.getAccomplishments().clear();
                wrestler.getAccomplishments().add("none");
            } else {
                String[] lines = text.split(System.lineSeparator());
                wrestler.getAccomplishments().clear();
                for (int i = 0; i < lines.length; i++) {
                    wrestler.getAccomplishments().add(lines[i]);
                }
            }

            backgroundThread thread = new backgroundThread(wrestler);
            thread.start();

            //return to prior Activity while sending Wrestler back with new information
            Intent intent = new Intent(this, ViewWrestlerActivity.class);
            intent.putExtra("viewWrestler", roster);
            intent.putExtra("position", pos);
            startActivity(intent);
        }


    }

    public void onDeleteWrestlerClicked(View view) {
        showDeleteAlert(this);
    }

    public void showDeleteAlert(Activity activity) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        String message = "You have selected to delete this wrestler from the roster. Continuing will erase data and cannot be undone. Are you sure you want to continue?";
        alertBuilder
                .setTitle("Warning")
                .setMessage(message)
                .setNegativeButton("Cancel", (dialog,id) -> {
                    dialog.cancel();
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBackgroundTread thread = new deleteBackgroundTread(wrestler);
                        thread.start();
                        roster.remove(pos);
                        Intent intent = new Intent(EditWrestlerActivity.this,ViewDetailsActivity.class);
                        intent.putExtra("viewRoster", roster);
                        intent.putExtra("position", pos);
                        startActivity(intent);
                    }
                })

                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private class backgroundThread extends Thread{
        private Wrestler wrestler;
        public backgroundThread(Wrestler wrestler){
            this.wrestler = wrestler;
        }

        @Override
        public void run(){
            wrestlerDB.getWrestlerDao().updateWrestler(wrestler);
        }
    }

    private class deleteBackgroundTread extends Thread{
        private Wrestler wrestler;
        public deleteBackgroundTread(Wrestler wrestler){
            this.wrestler = wrestler;
        }

        @Override
        public void run(){
            wrestlerDB.getWrestlerDao().deleteWrestlerByID(wrestler.id);
        }
    }
    /*
     * Background thread that sets up Roster from accessing data from the database
     */
    private class RosterThread extends Thread{
        public RosterThread(){}

        @Override
        public void run(){
            roster = (ArrayList<Wrestler>) wrestlerDB.getWrestlerDao().getAll();
        }
    }
}