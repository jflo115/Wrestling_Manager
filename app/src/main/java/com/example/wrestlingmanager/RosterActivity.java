package com.example.wrestlingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
    private ArrayList<Wrestler> roster = null;
    private Wrestler add = new Wrestler("test",100,false);
    private RosterListAdapter adapter = new RosterListAdapter();
    private RosterFilter rosterFilter = new RosterFilter(); //Filters out roster for tab clicks
    WrestlerDatabase wrestlerDB;

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

        // Needed to set up Database
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

        //Adapter Setup
        //RosterListAdapter adapter = new RosterListAdapter();
        adapter.setHasStableIds(true);

        //RecyclerView Setup
        recyclerView = findViewById(R.id.roster_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //Room not working as of rn
        //wrestlerDAO = WrestlerDatabase.getSingleton(this).wrestlerDao();
        //List<Wrestler> wrestlers2 = wrestlerDAO.getAll();

        // Creates a background thread which sets up the roster
        RosterThread rosterthread = new RosterThread();
        rosterthread.start();

        /* This while loops stalls the program until the roster is set up
         * Prob not the best way to do this but many times program would go too fast and
         * our list would be empty despite having a full roster.
         */

        //Set Adapter to test Data
        //adapter.setRosterData(roster); moved to background thread


        // TabLayout Setup
        TabLayout tabLayout = (TabLayout) findViewById(R.id.RosterTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ArrayList<Wrestler> filtered = new ArrayList<Wrestler>();
                switch(tab.getPosition()) {
                    case 0: adapter.setRosterData(roster);
                            break;
                    // Filters list to only show boys on roster
                    case 1: filtered = rosterFilter.filterBoys(roster);
                            adapter.setRosterData(filtered);
                            break;
                    // Filters list to only show girls on roster
                    case 2: filtered = rosterFilter.filterGirls(roster);
                            adapter.setRosterData(filtered);
                            break;
                }
            }

            /*
             * These Overrided functions show up automatically when you do tabLayout
             * seem to not have a purpose for the apps functionality. Will keep in case.
             */
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //adapter.setRosterData(roster);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    /*
     * Code for addWrestler button, takes user to AddWrestlerActivity, sends Roster as data
     */
    public void onAddWrestlerClicked(View view) {
        Intent intent = new Intent(this,AddWrestlerActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }

    /*
     * Code for viewDetails button, takes user to ViewDetailsActivity, sends Roster as data
     */
    public void onViewDetailsClicked(View view) {
        Intent intent = new Intent(this,ViewDetailsActivity.class);
        intent.putExtra("viewRoster",roster);
        startActivity(intent);
    }

    /*
     * Code for RosterBack button, takes user back to MainMenuActivity, sends Roster as data
     */
    public void onRosterBackClicked(View view) {
        Intent intent = new Intent(this,MainMenuActivity.class);
        //intent.putExtra("Roster",roster);
        startActivity(intent);
    }

    /*
     * Background thread that sets up Roster from accessing data from the database
     */
    private class RosterThread extends Thread{
        public RosterThread(){}

        @Override
        public void run(){
            roster = (ArrayList<Wrestler>) wrestlerDB.getWrestlerDao().getAll();
            //Set Adapter to test Data
            adapter.setRosterData(roster);
        }
    }
}