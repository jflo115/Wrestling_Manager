package com.example.wrestlingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onEnterClicked(View view) {
        //First Page kind of redundant for now.
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);
    }
}