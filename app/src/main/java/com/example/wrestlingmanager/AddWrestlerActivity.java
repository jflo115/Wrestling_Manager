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
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");


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
        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(myCallBack).build();

    }

    public void onAddWrestler2Clicked(View view) {
        Intent intent = new Intent(this,RosterActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }

    public void onCreateWrestlerClicked(View view) {
        //New Wrestler created
        TextView name = findViewById(R.id.addWrestlerName);
        TextView grade = findViewById(R.id.addWrestlerGrade);
        Switch genderSwitch = findViewById(R.id.WrestlerSwitch);
        if(name.getText().toString().equals("") || grade.getText().toString().equals("")) {
            Utilities.showAlert(this,"Fil out all boxes or press back");
            //TODO fix the other classes like above
        }
        else {
            String gradeString = grade.getText().toString();
            int gradeInt = Integer.parseInt(gradeString);
            Wrestler newWrestler = new Wrestler(name.getText().toString(), gradeInt, !genderSwitch.isChecked());

            roster.add(newWrestler);
            backgroundThread thread = new backgroundThread(newWrestler);
            thread.start();

            //return to prior Activity while sending Roster back with new Wrestler
            Intent intent = new Intent(this, RosterActivity.class);
            intent.putExtra("Roster", roster);
            startActivity(intent);
        }
    }

    class backgroundThread extends Thread{
        Wrestler wrestler;
        public backgroundThread(Wrestler wrestler){
            this.wrestler = wrestler;
        }
        @Override
        public void run(){
            wrestlerDB.getWrestlerDao().addWrestler(wrestler);
        }
    }
}