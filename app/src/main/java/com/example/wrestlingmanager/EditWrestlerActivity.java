package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Switch;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditWrestlerActivity extends AppCompatActivity {
    private Wrestler wrestler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wrestler);

        wrestler = (Wrestler) getIntent().getSerializableExtra("editWrestler");

        //Set up Textviews
        TextView WrestlerName = findViewById(R.id.EditWrestlerNameText);
        WrestlerName.setText(wrestler.getName());

        TextView WrestlerGrade = findViewById(R.id.EditWrestlerGradeText);
        WrestlerGrade.setText(Integer.toString(wrestler.getGrade()));

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch genderSwitch = findViewById(R.id.EditWrestlerGenderSwitch);
        if(wrestler.getGender()) {
            genderSwitch.setChecked(false);
        }
        else {
            genderSwitch.setChecked(true);
        }

        TextView accomplishments = findViewById(R.id.EditWrestlerAccText);
        int length = wrestler.getAccomplishments().size();
        String text = new String();
        if(wrestler.getAccomplishments().isEmpty()) {
            text = "none";
        }
        else {
            text = wrestler.getAccomplishments().get(0) + "\n";

            for (int i = 1; i < length; i++) {
                text += wrestler.getAccomplishments().get(i) +"\n";
            }
        }
        accomplishments.setText(text);

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public void onFinishEditingClicked(View view) {
        TextView WrestlerName = findViewById(R.id.EditWrestlerNameText);
        TextView WrestlerGrade = findViewById(R.id.EditWrestlerGradeText);
        Switch genderSwitch = findViewById(R.id.EditWrestlerGenderSwitch);
        TextView accomplishments = findViewById(R.id.EditWrestlerAccText);

        wrestler.setName(WrestlerName.getText().toString());
        wrestler.setGrade(Integer.parseInt(WrestlerGrade.getText().toString()));
        if(!genderSwitch.isChecked()) {
            wrestler.setGender(true);
        }
        else {
            wrestler.setGender(false);
        }
        String text = accomplishments.getText().toString();
        String[] lines = text.split(System.lineSeparator());
        wrestler.getAccomplishments().clear();
        for(int i = 0; i < lines.length;i++) {
            wrestler.getAccomplishments().add(lines[i]);
        }

        //return to prior Activity while sending Wrestler back with new information
        Intent intent = new Intent(this,ViewWrestlerActivity.class);
        intent.putExtra("viewWrestler",wrestler);
        startActivity(intent);


    }
}