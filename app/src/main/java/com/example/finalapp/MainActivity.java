package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSearchProfile;
    private Button buttonEloLeaderboard;
    private Button buttonRankedLeaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        buttonSearchProfile = findViewById(R.id.buttonSearchProfile);
        buttonEloLeaderboard = findViewById(R.id.buttonEloLeaderboard);
        buttonRankedLeaderboard = findViewById(R.id.buttonRankedLeaderboard);

        buttonSearchProfile.setOnClickListener(this);
        buttonEloLeaderboard.setOnClickListener(this);
        buttonRankedLeaderboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearchProfile){
            startActivity(new Intent(this, UserProfil.class));
        } else if(view.getId() == R.id.buttonEloLeaderboard){
            startActivity(new Intent(this, EloLeaderboard.class));
        } else if(view.getId() == R.id.buttonRankedLeaderboard){
            startActivity(new Intent(this, RankedLeaderboard.class));
        }
    }
}