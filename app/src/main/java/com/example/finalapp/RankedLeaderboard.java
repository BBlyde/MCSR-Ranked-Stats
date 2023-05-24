package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RankedLeaderboard extends AppCompatActivity {

    private TextView labelLeaderboard;

    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_leaderboard);
    }

    private void initComponents() {
        labelLeaderboard = findViewById(R.id.rankedLeaderboard);
        String url = "https://mcsrranked.com/api/record-leaderboard";
        new RetrieveDataTask(labelLeaderboard).execute(url);

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}