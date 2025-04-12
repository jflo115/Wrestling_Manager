package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewWrestlerActivity extends AppCompatActivity {
    private Wrestler wrestler;
    private ArrayList<Wrestler> roster = new ArrayList<Wrestler>();
    private ListView listView;
    private MatchListAdapter adapter;
    private ArrayList<Integer> selectedIND;
    private int pos;
    private int source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wrestler);

        pos = getIntent().getIntExtra("position",0);
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("viewWrestler");
        source = getIntent().getIntExtra("source",source); // Tells us where we are accessing Activity from
        selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
        // Sources : 1 == viewRoster, 2 == StartTourney
        if(source == 2){
            Button addMatch = findViewById(R.id.AddMatchButton);
            Button editWrestler = findViewById(R.id.EditWrestlerButton);
            addMatch.setVisibility(View.GONE);
            editWrestler.setVisibility(View.GONE);
        }
        wrestler = roster.get(pos);

        //Set up Textviews
        TextView WrestlerName = findViewById(R.id.ViewWrestlerName);
        WrestlerName.setText(wrestler.getName());
        TextView WrestlerRecord = findViewById(R.id.WrestlerRecord);
        WrestlerRecord.setText(wrestler.toStringRecord());
        TextView WrestlerAccomplishments = findViewById(R.id.ViewWrestlerAccomplishments);
        String AccText = "";
        ArrayList<String> acc = new ArrayList<>();
        acc = wrestler.getAccomplishments();
        if(acc.isEmpty()) {
            AccText = "None";
        }
        else{
            int n = acc.size();
            for(int i = 0; i < n;i++) {
                if(i == n-1) {
                    AccText += acc.get(i);
                }
                else{
                    AccText += acc.get(i);
                    AccText += ", ";
                }
            }
        }
        WrestlerAccomplishments.setText(AccText);

        //Set up ListView and Adapter
        listView = (ListView) findViewById(R.id.listMatches);
        adapter = new MatchListAdapter(this,wrestler.getRecord().getMatches());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int Matchpos, long l) {
                Intent intent = new Intent(ViewWrestlerActivity.this,EditMatchActivity.class);
                intent.putExtra("editMatch",(Serializable) roster);
                intent.putExtra("position",pos);
                intent.putExtra("source",source);
                intent.putExtra("Selected",(Serializable) selectedIND);
                intent.putExtra("MatchPosition", Matchpos);
                startActivity(intent);
            }

        });

    }

    public void onEditWrestlerClicked(View view) {
        Intent intent = new Intent(this,EditWrestlerActivity.class);
        intent.putExtra("editWrestler", (Serializable) roster);
        intent.putExtra("position",pos);
        startActivity(intent);
    }

    public void onAddMatchClicked(View view) {
        Intent intent = new Intent(this,AddMatchActivity.class);
        intent.putExtra("AddMatch", (Serializable) roster);
        intent.putExtra("position",pos);
        intent.putExtra("source",1);
        startActivity(intent);
    }

    public void onViewWrestlerBackClicked(View view) {
        if(source == 1) {
            Intent intent = new Intent(this, ViewDetailsActivity.class);
            intent.putExtra("viewRoster", (Serializable) roster);
            startActivity(intent);
        }
        else if(source == 2){
            Intent intent = new Intent(this,StartTourneyActivity.class);
            intent.putExtra("Roster",(Serializable) roster);
            intent.putExtra("Selected",(Serializable) selectedIND);
            startActivity(intent);
        }
        else{
            Utilities.showAlert(this,"Unexpected Error");
        }

    }
}