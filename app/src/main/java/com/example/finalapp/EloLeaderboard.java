package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class EloLeaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elo_leaderboard);
        initComponents();
    }

    private void initComponents() {
        // Retrieve the username from the Main Activity's editText
        String response = getIntent().getStringExtra("response");

        /* Return button */
        Button buttonBack = findViewById(R.id.buttonBackElo);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getEloLeaderboard(response);
    }

    private void getEloLeaderboard(String response) {
        final TextView textViewLeaderboardPlace = (TextView) findViewById(R.id.textViewLeaderboardPlace);
        final TextView textViewLeaderboardPlayer = (TextView) findViewById(R.id.textViewLeaderboardPlayer);
        final TextView textViewLeaderboardElo = (TextView) findViewById(R.id.textViewLeaderboardElo);

        StringBuilder stringBuilderPlace = new StringBuilder();
        StringBuilder stringBuilderPlayer = new StringBuilder();
        StringBuilder stringBuilderElo = new StringBuilder();

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONObject dataObject = responseObject.getJSONObject("data");
            JSONArray usersArray = dataObject.getJSONArray("users");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                int rank = userObject.getInt("elo_rank");
                String nickname = userObject.getString("nickname");
                int elo = userObject.getInt("elo_rate");

                stringBuilderPlace.append(rank).append("\n");
                stringBuilderPlayer.append(nickname).append("\n");
                stringBuilderElo.append(elo).append("\n");
            }
            textViewLeaderboardPlace.setText(stringBuilderPlace.toString());
            textViewLeaderboardPlayer.setText(stringBuilderPlayer.toString());
            textViewLeaderboardElo.setText(stringBuilderElo.toString());
            setStyleLeaderboard(textViewLeaderboardPlace, "#1F6A6A");
            setStyleLeaderboard(textViewLeaderboardPlayer, "#142C34");
            setStyleLeaderboard(textViewLeaderboardElo, "#84CC34");
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private void setStyleLeaderboard(TextView textView, String hexColor){
        textView.setTextSize(18);
        int customColor = Color.parseColor(hexColor);
        textView.setTextColor(customColor);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "quicksand_medium.ttf");
        textView.setTypeface(typeface);
    }
}