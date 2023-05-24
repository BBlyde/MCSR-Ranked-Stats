package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EloLeaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elo_leaderboard);
        initComponents();
    }

    private void initComponents() {
        /* Setting up front-end header */
        TextView textViewTitle = findViewById(R.id.textViewTitle3);
        TextView textViewTitle2 = findViewById(R.id.textViewTitle4);
        Typeface chocolateFont = Typeface.createFromAsset(getAssets(), "TT Chocolates Trial Bold.otf");
        textViewTitle.setTypeface(chocolateFont);
        textViewTitle2.setTypeface(chocolateFont);

        /* Return button */
        Button buttonBack = findViewById(R.id.buttonBackElo);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLeaderboard();
    }

    private void getLeaderboard(){
        TextView labelLeaderboard = findViewById(R.id.eloLeaderboard);
        String url = "https://mcsrranked.com/api/leaderboard";
        new RetrieveDataTask(labelLeaderboard).execute(url);
    }

}