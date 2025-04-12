package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class DualMeetSetupActivity extends AppCompatActivity {

    private DualMeetRosterAdapter adapter;
    private ListView selectedWrestlersList;
    private ArrayList<Wrestler> selected;
    private ArrayList<Integer> selectedIND;
    private ArrayList<Wrestler> selectedCopy;
    private ArrayList<Wrestler> roster;
    private TextView[] textViews = new TextView[14];
    private String[] oppNames = new String[14];
    private TextView[] oppTextViews = new TextView[14];
    private TextView curr;
    private Wrestler[] Lineup = new Wrestler[14];
    private int[] LineupIND = new int[14];
    private TextView currOpp;
    private TabLayout tabLayout;
    private TextView oppSchoolTextview;
    private Boolean Boys;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dual_meet_setup);
        //Extract Serializable Extras
        roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("Roster");
        selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
        Boys = getIntent().getBooleanExtra("Boys", true);
        selected = new ArrayList<Wrestler>();
        for(int i = 0; i < selectedIND.size();i++){
            selected.add(roster.get(selectedIND.get(i)));
        }
        selectedCopy = new ArrayList<Wrestler>();
        for(Wrestler w: selected){
            selectedCopy.add(w);
        }

        //Set up Lineup inds
        Arrays.fill(LineupIND,-1);
        //Set up Oppnames so it doesnt have any null values
        //Arrays.fill(oppNames,"");

        //Set up Listview
        selectedWrestlersList = (ListView) findViewById(R.id.SelectedWrestlersList);


        //Set up Adapter
        adapter = new DualMeetRosterAdapter(this,selectedCopy);
        selectedWrestlersList.setAdapter(adapter);

        //Set up all Textviews
        textViews[0] = findViewById(R.id.WrestlerName106);
        textViews[1] = findViewById(R.id.WrestlerName113);
        textViews[2] = findViewById(R.id.WrestlerName120);
        textViews[3] = findViewById(R.id.WrestlerName126);
        textViews[4] = findViewById(R.id.WrestlerName132);
        textViews[5] = findViewById(R.id.WrestlerName138);
        textViews[6] = findViewById(R.id.WrestlerName144);
        textViews[7] = findViewById(R.id.WrestlerName150);
        textViews[8] = findViewById(R.id.WrestlerName157);
        textViews[9] = findViewById(R.id.WrestlerName165);
        textViews[10] = findViewById(R.id.WrestlerName175);
        textViews[11] = findViewById(R.id.WrestlerName190);
        textViews[12] = findViewById(R.id.WrestlerName215);
        textViews[13] = findViewById(R.id.WrestlerName285);

        oppTextViews[0] = findViewById(R.id.OpponentName106);
        oppTextViews[1] = findViewById(R.id.OpponentName113);
        oppTextViews[2] = findViewById(R.id.OpponentName120);
        oppTextViews[3] = findViewById(R.id.OpponentName126);
        oppTextViews[4] = findViewById(R.id.OpponentName132);
        oppTextViews[5] = findViewById(R.id.OpponentName138);
        oppTextViews[6] = findViewById(R.id.OpponentName144);
        oppTextViews[7] = findViewById(R.id.OpponentName150);
        oppTextViews[8] = findViewById(R.id.OpponentName157);
        oppTextViews[9] = findViewById(R.id.OpponentName165);
        oppTextViews[10] = findViewById(R.id.OpponentName175);
        oppTextViews[11] = findViewById(R.id.OpponentName190);
        oppTextViews[12] = findViewById(R.id.OpponentName215);
        oppTextViews[13] = findViewById(R.id.OpponentName285);

        oppSchoolTextview = (TextView) findViewById(R.id.DualMeetOpponentSchool);

        curr = textViews[0];
        curr.setVisibility(View.VISIBLE);
        currOpp = oppTextViews[0];
        currOpp.setVisibility(View.VISIBLE);


        tabLayout = (TabLayout) findViewById(R.id.BoysWeightsTabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAB",String.valueOf(tab.getPosition()));
                curr.setVisibility(View.INVISIBLE);
                currOpp.setVisibility(View.INVISIBLE);
                currOpp = oppTextViews[tab.getPosition()];
                curr = textViews[tab.getPosition()];

                Log.d("Name", curr.getText().toString());
                currOpp.setVisibility((View.VISIBLE));
                curr.setVisibility(View.VISIBLE);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(!Boys){
            String[] weights = {"100","105","110","115","120","125","130","135","140","145","155","170","190","235"};
            for(int i = 0; i < 14; i++) {
                tabLayout.getTabAt(i).setText(weights[i]);
            }
        }

        selectedWrestlersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                int currTab = tabLayout.getSelectedTabPosition();
                if(Lineup[currTab] == null) {
                    curr.setText(selectedCopy.get(pos).getName());
                    Lineup[currTab] = selectedCopy.get(pos);
                    int realPOS = roster.indexOf(selectedCopy.get(pos));
                    LineupIND[currTab] = realPOS;
                    selectedCopy.remove(pos);
                    adapter.changeList(selectedCopy);
                    selectedWrestlersList.setAdapter(adapter);
                }
                else{
                    Utilities.showAlert(DualMeetSetupActivity.this,"Wrestler already selected, please remove wrestler first before adding another to this match.");
                }
            }
        });

    }

    public void showDualMeetAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setNegativeButton("Keep Editing", (dialog,id) -> {
                    dialog.cancel();
                })
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < 14; j++){
                            oppNames[j] = oppTextViews[j].getText().toString();
                        }
                        Intent intent = new Intent(DualMeetSetupActivity.this, StartDualMeetActivity.class );
                        intent.putExtra("Roster", roster);
                        intent.putExtra("Lineup", LineupIND);
                        intent.putExtra("Selected", selectedIND);
                        intent.putExtra("oppNames", oppNames);
                        intent.putExtra("schoolName", oppSchoolTextview.getText().toString());
                        startActivity(intent);
                    }
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void onDualMeetDeleteButtonClicked(View view) {
        curr.setText("");
        selectedCopy.add(Lineup[tabLayout.getSelectedTabPosition()]);
        adapter.changeList(selectedCopy);
        selectedWrestlersList.setAdapter(adapter);
        Lineup[tabLayout.getSelectedTabPosition()] = null;
        LineupIND[tabLayout.getSelectedTabPosition()] = -1;

    }

    public void onStartDualButtonClicked(View view) {
        if (oppSchoolTextview.getText().toString().equals("")) {
            Utilities.showAlert(this, "Fill out the Opponent School name");
        }
        else {
            if (Arrays.asList(Lineup).contains(null)) {
                showDualMeetAlert(this, "Some Weight Classes dont have a" +
                        " wrestler associated with it. Continue editing or continue with byes");
            } else {
                for (int i = 0; i < 14; i++) {
                    oppNames[i] = oppTextViews[i].getText().toString();
                }
                if (Arrays.asList(oppNames).contains("")) {
                    showDualMeetAlert(this, "Some opponent names have not been filled out" +
                            " continue editing or continue with byes");
                } else {
                    Intent intent = new Intent(this, StartDualMeetActivity.class);
                    intent.putExtra("Roster", roster);
                    intent.putExtra("Lineup", LineupIND);
                    intent.putExtra("Selected", selectedIND);
                    intent.putExtra("oppNames", oppNames);
                    intent.putExtra("schoolName", oppSchoolTextview.getText().toString());
                    intent.putExtra("Boys",Boys);
                    startActivity(intent);
                }
            }
        }
    }

    public void onDualMeetWeightBoysBackButtonClicked(View view) {
        Intent intent = new Intent(this,DualMeetActivity.class);
        intent.putExtra("Roster",roster);
        startActivity(intent);
    }

    public void onDualMeetSetupDevFillClicked(View view) {
        oppTextViews[0].setText("Test 106");
        oppTextViews[1].setText("Test 113");
        oppTextViews[2].setText("Test 120");
        oppTextViews[3].setText("Test 126");
        oppTextViews[4].setText("Test 132");
        oppTextViews[5].setText("Test 138");
        oppTextViews[6].setText("Test 144");
        oppTextViews[7].setText("Test 150");
        oppTextViews[8].setText("Test 157");
        oppTextViews[9].setText("Test 165");
        oppTextViews[10].setText("Test 175");
        oppTextViews[11].setText("Test 190");
        oppTextViews[12].setText("Test 215");
        oppTextViews[13].setText("Test 285");

        oppSchoolTextview.setText("Test School");

    }
}