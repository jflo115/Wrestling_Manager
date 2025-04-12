package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewDetailsActivity extends AppCompatActivity {

    private ListView list;
    private ViewRosterDetailsAdapter adapter;
    private ArrayList<Wrestler> roster;
    private RosterFilter rosterFilter = new RosterFilter();
    private ArrayList<Wrestler> filtered = new ArrayList<Wrestler>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        //Receive roster from past Activity
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("viewRoster");
        filtered = rosterFilter.filterBoys(roster);
        //Set up adapter
        list = (ListView) findViewById(R.id.listViewRoster);
        adapter = new ViewRosterDetailsAdapter(this,filtered);
        list.setAdapter(adapter);

        //TabLayout Setup
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabViewDetails);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0: filtered = rosterFilter.filterBoys(roster);
                        adapter.changeList(filtered);
                        list.setAdapter(adapter);
                        break;
                    case 1: filtered = rosterFilter.filterGirls(roster);
                        adapter.changeList(filtered);
                        list.setAdapter(adapter);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent intent = new Intent(ViewDetailsActivity.this,ViewWrestlerActivity.class);
                Wrestler temp = filtered.get(pos);
                int realPos = roster.indexOf(temp);
                intent.putExtra("viewWrestler", (Serializable) roster);

                intent.putExtra("position",realPos);
                intent.putExtra("source",1);
                //intent.putExtra("viewWrestler", (Serializable) list.getItemAtPosition(pos));
                startActivity(intent);
            }
        });
    }

    public void onViewDetailsBackClicked(View view) {
        Intent intent = new Intent(this,RosterActivity.class);
        intent.putExtra("Roster",(Serializable) roster);
        startActivity(intent);
    }
}