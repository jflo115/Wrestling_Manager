package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBTestActivity extends AppCompatActivity {

    MatchDatabase matchDB;
    RecordDatabase recordDB;
    WrestlerDatabase wrestlerDB;
    TextView name, wrestlerName;
    TextView school,grade;
    CheckBox win,gender;
    TextView score;
    List<Match> matches = new ArrayList<>();
    Record currRecord;
    Match currMatch;
    Wrestler currWrestler;
    ArrayList<Wrestler> roster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        currRecord = new Record();
        currMatch = null;
        currWrestler = null;
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
        matchDB = Room.databaseBuilder(getApplicationContext(),MatchDatabase.class,"matchDB").addCallback(myCallBack).build();

        recordDB = Room.databaseBuilder(getApplicationContext(),RecordDatabase.class,"recordDB").addCallback(myCallBack).build();

        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(myCallBack).build();

        name = findViewById(R.id.textDBName);
        school = findViewById(R.id.textDBSchool);
        win = findViewById(R.id.DBWin);
        score = findViewById(R.id.DB_Score);

        wrestlerName = findViewById(R.id.textDBName2);
        grade = findViewById(R.id.DBTestGrade);
        gender = findViewById(R.id.DBGender);


    }

    public void onGetDataClicked(View view) {
        getDataThread thread = new getDataThread();
        thread.start();

    }

    public void onDBAddClicked(View view) {
        backgroundThread thread = new backgroundThread();
        currMatch = new Match(name.getText().toString(),school.getText().toString(),
                win.isChecked(),
                score.getText().toString());
        thread.start();
        currRecord.addMatch(currMatch);
        Toast.makeText(this, "Match added", Toast.LENGTH_SHORT).show();
    }

    public void onCreateRecordClicked(View view) {
        createRecordThread thread = new createRecordThread();
        currWrestler = new Wrestler(wrestlerName.getText().toString(),
                Integer.parseInt(grade.getText().toString()),
                gender.isChecked());
        thread.start();
        Toast.makeText(this, "Record created", Toast.LENGTH_SHORT).show();
    }

    public void onCreateRosterClicked(View view) {
        createRosterThread thread = new createRosterThread();
        thread.start();

    }

    class backgroundThread extends Thread{
        @Override
        public void run(){
            matchDB.getMatchDAO().addMatch(currMatch);
        }
    }

    class getDataThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            matches = matchDB.getMatchDAO().getAllMatches();
            StringBuilder sb = new StringBuilder();
            for(Match match: matches){
                sb.append(match.getOpponent()+" : "+match.getSchool()+" : "+match.getScore());
                sb.append("\n");
            }
            String finalData = sb.toString();
            Toast.makeText(DBTestActivity.this, finalData, Toast.LENGTH_SHORT).show();
        }
    }

    class createRecordThread extends Thread{
        @Override
        public void run(){
            recordDB.getrecordDao().addRecord(currRecord);
            currWrestler.setRecord(currRecord);
            wrestlerDB.getWrestlerDao().addWrestler(currWrestler);
            currRecord = new Record();
            currWrestler = null;
        }
    }

    class createRosterThread extends Thread{
        @Override
        public void run(){
            Looper.prepare();
            roster = (ArrayList) wrestlerDB.getWrestlerDao().getAll();
            StringBuilder sb = new StringBuilder();
            for(Wrestler w: roster){
                sb.append(w.getName() + ",");
                sb.append(String.valueOf(w.getGrade()) + ",");
                sb.append(String.valueOf(w.getGender()) + "\n");
            }
            Toast.makeText(DBTestActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}