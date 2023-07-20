package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class AddWrestlerActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wrestler);
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");


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
        String gradeString = grade.getText().toString();
        int gradeInt = Integer.parseInt(gradeString);
        Wrestler newWrestler = new Wrestler(name.getText().toString(),gradeInt,!genderSwitch.isChecked());
        roster.add(newWrestler);

        //return to prior Activity while sending Roster back with new Wrestler
        Intent intent = new Intent(this,RosterActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }
}