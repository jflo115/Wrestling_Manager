package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TourneyActivity extends AppCompatActivity {

    private ConstraintLayout selectScene;
    private ArrayList<Wrestler> roster, filtered,selected;
    private ArrayList<Integer> selectedIND;
    private ListView list, selectedList;
    private TourneyRosterAdapter adapter, adapterSelected;
    private TabLayout BGTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourney);
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        filtered = RosterFilter.filterBoys(roster);

        list = (ListView) findViewById(R.id.TourneyRosterList);
        selectedList = (ListView) findViewById(R.id.TourneySelectedList);

        adapter = new TourneyRosterAdapter(this,filtered);
        list.setAdapter(adapter);
        selected = new ArrayList<Wrestler>();
        selectedIND = new ArrayList<Integer>();
        adapterSelected = new TourneyRosterAdapter(this, selected);
        selectedList.setAdapter(adapterSelected);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l){
               Wrestler wrestler = filtered.get(pos);
               if(!selected.contains(wrestler)) {
                   selected.add(wrestler);
                   int realPos = roster.indexOf(wrestler);
                   selectedIND.add(realPos);
                   adapterSelected.changeList(selected);
                   selectedList.setAdapter(adapterSelected);
               }

           }
        });

        selectedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                selected.remove(pos);
                selectedIND.remove(pos);
                adapterSelected.changeList(selected);
                selectedList.setAdapter(adapterSelected);
            }
        });

        BGTab = (TabLayout) findViewById(R.id.TourneyTabs);
        BGTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0: filtered = RosterFilter.filterBoys(roster);
                        adapter.changeList(filtered);
                        list.setAdapter(adapter);
                        selected.clear();
                        selectedIND.clear();
                        adapterSelected.changeList(selected);
                        selectedList.setAdapter(adapterSelected);
                        break;
                    case 1: filtered = RosterFilter.filterGirls(roster);
                        adapter.changeList(filtered);
                        list.setAdapter(adapter);
                        selected.clear();
                        selectedIND.clear();
                        adapterSelected.changeList(selected);
                        selectedList.setAdapter(adapterSelected);
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



    }

    public void onTourneyContinueButton(View view) {
        Intent intent = new Intent(this, StartTourneyActivity.class);
        intent.putExtra("Roster", roster);
        intent.putExtra("Selected", selectedIND);
        intent.putExtra("Boys", BGTab.getSelectedTabPosition() == 0);
        startActivity(intent);
    }
}