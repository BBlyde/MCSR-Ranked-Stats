package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSearchProfile;
    private Button buttonEloLeaderboard;
    private Button buttonRankedLeaderboard;
    private EditText editTextUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        /* Setting up front-end header and main buttons*/
        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewTitle2 = findViewById(R.id.textViewTitle2);
        Typeface chocolateFont = Typeface.createFromAsset(getAssets(), "TT Chocolates Trial Bold.otf");
        textViewTitle.setTypeface(chocolateFont);
        textViewTitle2.setTypeface(chocolateFont);

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
            String username = ((EditText)findViewById(R.id.editTextUsername)).getText().toString();
            Intent intent = new Intent(this, UserProfil.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if(view.getId() == R.id.buttonEloLeaderboard){
            startActivity(new Intent(this, EloLeaderboard.class));
        } else if(view.getId() == R.id.buttonRankedLeaderboard){
            startActivity(new Intent(this, RankedLeaderboard.class));
        }
    }
}