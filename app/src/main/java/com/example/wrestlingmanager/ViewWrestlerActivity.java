package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

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