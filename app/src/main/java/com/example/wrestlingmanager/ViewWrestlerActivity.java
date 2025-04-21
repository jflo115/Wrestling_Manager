package com.example.wrestlingmanager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    WrestlerDatabase wrestlerDB;
    private ImageButton wrestlerPic;
    private SharedPreferences preferences;

    //private static final int SELECT_PICTURE = 1;
    //private String selectedImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wrestler);

        pos = getIntent().getIntExtra("position",0);


        //Needed to set up Database
        RoomDatabase.Callback mycallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
            }
        };

        //Set up Wrestler Database
        wrestlerDB = Room.databaseBuilder(getApplicationContext(),WrestlerDatabase.class,"wrestlerDB").addCallback(mycallback).build();

        // Creates a background thread which sets up the roster
        RosterThread rosterthread = new RosterThread();
        rosterthread.start();

        preferences = getApplicationContext().getSharedPreferences("User",Context.MODE_PRIVATE);
        String userPhoto = preferences.getString("userPhoto","NULL");
        wrestlerPic = (ImageButton) findViewById(R.id.wrestlerPic);
        if(userPhoto.equals("NULL")){
            wrestlerPic.setBackgroundResource(R.drawable.trash);
        } else{
            Bitmap bitmap;
            try{
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(userPhoto));
            } catch (IOException e){
                wrestlerPic.setBackgroundResource(R.drawable.trash);
                return;
            }
            wrestlerPic.setImageBitmap(bitmap);
        }


        wrestlerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });


        //roster = (ArrayList<Wrestler>) getIntent().getSerializableExtra("viewWrestler");
        source = getIntent().getIntExtra("source",source); // Tells us where we are accessing Activity from
        selectedIND = (ArrayList<Integer>) getIntent().getSerializableExtra("Selected");
        // Sources : 1 == viewRoster, 2 == StartTourney
        if(source == 2){
            Button addMatch = findViewById(R.id.AddMatchButton);
            Button editWrestler = findViewById(R.id.EditWrestlerButton);
            addMatch.setVisibility(View.GONE);
            editWrestler.setVisibility(View.GONE);
        }

        //Set up Textviews


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                wrestlerPic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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

    public void pickUser(View view) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
        intent.setType("image/*");
        startActivityForResult(intent, 3645);
    }




    /*
     * Background thread that sets up Roster from accessing data from the database
     */
    private class RosterThread extends Thread{
        public RosterThread(){}

        @Override
        public void run(){
            roster = (ArrayList<Wrestler>) wrestlerDB.getWrestlerDao().getAll();
            wrestler = roster.get(pos);
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
            adapter = new MatchListAdapter(ViewWrestlerActivity.this,wrestler.getRecord().getMatches());
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
    }
}