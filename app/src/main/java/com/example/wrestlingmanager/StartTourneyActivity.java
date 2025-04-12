package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartTourneyActivity extends AppCompatActivity {

    private ArrayList<Wrestler> roster;
    private ArrayList<Integer> selectedIND;
    private boolean boys;
    private ArrayList<Wrestler> selected;
    private ListView WrestlerList;
    private TourneyStartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_tourney);

        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
        boys = getIntent().getBooleanExtra("Boys",true);
        selected = new ArrayList<Wrestler>();
        //TextView test = (TextView) findViewById(R.id.testStartTourney);
        for(int i = 0; i < selectedIND.size(); i++){
            selected.add(roster.get(selectedIND.get(i)));
        }
        WrestlerList = (ListView) findViewById(R.id.WrestlersAtTourneyView);
        adapter = new TourneyStartAdapter(this, selected,roster,selectedIND);
        WrestlerList.setAdapter(adapter);

        //Listener for LISTVIEW, Button is in TourneyStartAdapter
        WrestlerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StartTourneyActivity.this,ViewWrestlerActivity.class);
                Wrestler temp = selected.get(i);
                int realPos = roster.indexOf(temp);
                intent.putExtra("viewWrestler", (Serializable) roster);
                intent.putExtra("position",realPos);
                intent.putExtra("Selected",(Serializable) selectedIND);
                intent.putExtra("source",2);
                startActivity(intent);
            }
        });



    }

    public ArrayList<Integer> getSelectedIND() {
        return selectedIND;
    }

    public void onStartTourneyBackClicked(View view) {
        Intent intent = new Intent(this,TourneyActivity.class);
        intent.putExtra("Roster", (Serializable) roster);
        startActivity(intent);
    }

    public void onStartTourneyFinishClicked(View view) {
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.putExtra("Roster", (Serializable) roster);
        startActivity(intent);
    }
}