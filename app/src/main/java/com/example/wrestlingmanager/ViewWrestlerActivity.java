package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewWrestlerActivity extends AppCompatActivity {
    private Wrestler wrestler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wrestler);

        wrestler = (Wrestler) getIntent().getSerializableExtra("viewWrestler");

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
    }

    public void onEditWrestlerClicked(View view) {
        Intent intent = new Intent(this,EditWrestlerActivity.class);
        intent.putExtra("editWrestler", (Serializable) wrestler);
        startActivity(intent);
    }

    public void onAddMatchClicked(View view) {
        Intent intent = new Intent(this,AddMatchActivity.class);
        intent.putExtra("addMatch",(Serializable) wrestler);
        startActivity(intent);
    }

    public void onViewWrestlerBackClicked(View view) {
        Intent intent = new Intent(this, ViewDetailsActivity.class);
        startActivity(intent);
    }
}