package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RosterActivity extends AppCompatActivity {

    //Local Variables
    private RecyclerView recyclerView; //Will help list out wrestler names in roster
    private TextView getname; //Seeks out Name
    private TextView getgrade; //Seeks out Grade
    private ArrayList<Wrestler> roster = new ArrayList<Wrestler>();
    private Wrestler add = new Wrestler("test",100,false);
    private RosterListAdapter adapter = new RosterListAdapter();
    private RosterFilter rosterFilter = new RosterFilter(); //Filters out roster for tab clicks
    WrestlerDAO wrestlerDAO;

    /*private RosterListAdapter.OnWrestlerClickListener listener = new RosterListAdapter.OnWrestlerClickListener() {
        @Override
        public void onWrestlerClick(Wrestler wrestler) {
            wrestler.setName("OOOF");
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);

        //Adapter Setup
        //RosterListAdapter adapter = new RosterListAdapter();
        adapter.setHasStableIds(true);

        //RecyclerView Setup
        recyclerView = findViewById(R.id.roster_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        wrestlerDAO = WrestlerDatabase.getSingleton(this).wrestlerDao();
        List<Wrestler> wrestlers2 = wrestlerDAO.getAll();

        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        if(roster == null) {
            roster = new ArrayList<Wrestler>();
        }

        //Set Adapter to test Data
        adapter.setRosterData(roster);


        // TabLayout Setup
        TabLayout tabLayout = (TabLayout) findViewById(R.id.RosterTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ArrayList<Wrestler> filtered = new ArrayList<Wrestler>();
                switch(tab.getPosition()) {
                    case 0: adapter.setRosterData(roster);
                            break;
                    case 1: filtered = rosterFilter.filterBoys(roster);
                            adapter.setRosterData(filtered);
                            break;
                    case 2: filtered = rosterFilter.filterGirls(roster);
                            adapter.setRosterData(filtered);
                            break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //adapter.setRosterData(roster);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public void onAddWrestlerClicked(View view) {
        Intent intent = new Intent(this,AddWrestlerActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }


    public void onViewDetailsClicked(View view) {
        Intent intent = new Intent(this,ViewDetailsActivity.class);
        intent.putExtra("viewRoster",roster);
        startActivity(intent);
    }


    public void onRosterBackClicked(View view) {
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }
}