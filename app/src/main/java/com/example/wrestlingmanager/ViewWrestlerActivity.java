package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewWrestlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wrestler);

        Wrestler wrestler = (Wrestler) getIntent().getSerializableExtra("viewWrestler");

        //Set up Textviews
        TextView WrestlerName = findViewById(R.id.ViewWrestlerName);
        WrestlerName.setText(wrestler.getName());
        TextView WrestlerRecord = findViewById(R.id.WrestlerRecord);
        WrestlerRecord.setText(wrestler.toStringRecord());
    }

    public void onEditWrestlerClicked(View view) {
    }

    public void onAddMatchClicked(View view) {
    }
}