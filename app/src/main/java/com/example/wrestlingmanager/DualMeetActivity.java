package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DualMeetActivity extends AppCompatActivity {

    //private RosterListAdapter adapter;
    private RosterFilter rosterFilter = new RosterFilter();
    private ArrayList<Wrestler> filtered = new ArrayList<Wrestler>();
    private DualMeetRosterAdapter adapter;
    private DualMeetRosterAdapter adapterSelected;
    private ArrayList<Wrestler> roster;
    private ArrayList<Wrestler> selected;
    private ArrayList<Integer> selectedIND;
    private ListView list;
    private ListView selectedList;
    private boolean warned = false;
    private TabLayout BGTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_meet);

        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        filtered = rosterFilter.filterBoys(roster);

        list = (ListView) findViewById(R.id.DualMeetRosterList);
        selectedList = (ListView) findViewById(R.id.DualMeetSelectedList);

        adapter = new DualMeetRosterAdapter(this,filtered);
        list.setAdapter(adapter);
        selected = new ArrayList<Wrestler>();
        selectedIND = new ArrayList<Integer>();
        adapterSelected = new DualMeetRosterAdapter(this, selected);
        selectedList.setAdapter(adapterSelected);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Wrestler wrestler = filtered.get(pos);
                if(!selected.contains(wrestler)) {
                    selected.add(wrestler);
                    int realPos = roster.indexOf(wrestler);
                    selectedIND.add(realPos);
                    adapterSelected.changeList(selected);
                    selectedList.setAdapter(adapterSelected);
                }
                Log.d("indexes", selectedIND.toString());
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

        BGTab = (TabLayout) findViewById(R.id.DualMeetTabs);
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


    public void onDualMeetContinueClicked(View view) {
        //Makes sure we cant continue without selectin atleast one Wrestler.
        if(selected.isEmpty() || selectedIND.isEmpty()){
            Utilities.showAlert(this,"No wrestlers have been selected");
        }
        /* Dual Meets usually have 14 matches. Although not necessary, we will remind the
         *  user to fill up as many spots as possible.
         */
        else if( (selected.size() < 14 || selectedIND.size() < 14) && !warned){
            Utilities.showAlert(this,"Less than 14 wrestlers selected, if this is not a mistake select continue again");
            //Since we warned the user, we no longer will show the message and continue regardless.
            warned = true;
        }
        else{
            Intent intent = new Intent(this, DualMeetSetupActivity.class);
            intent.putExtra("Roster", roster);
            intent.putExtra("Selected", selectedIND);
            intent.putExtra("Boys", BGTab.getSelectedTabPosition() == 0);
            startActivity(intent);
        }
    }

    /*
        Return back to Event Activity
     */
    public void onDualMeetBackClicked(View view) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("Roster", roster);
        startActivity(intent);
    }
}