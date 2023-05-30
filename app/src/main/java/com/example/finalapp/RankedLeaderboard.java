package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class RankedLeaderboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_leaderboard);
        initComponents();
    }

    private void initComponents() {
        // Retrieve the username from the Main Activity's editText
        String response = getIntent().getStringExtra("response");

        Button buttonBack = findViewById(R.id.buttonBackRanked);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getRankedLeaderboard(response);
    }

    private void getRankedLeaderboard(String response){
        final TextView textViewRankedLeaderboardPlace = (TextView) findViewById(R.id.textViewRankedLeaderboardPlace);
        final TextView textViewRankedLeaderboardPlayer = (TextView) findViewById(R.id.textViewRankedLeaderboardPlayer);
        final TextView textViewRankedLeaderboardTime = (TextView) findViewById(R.id.textViewRankedLeaderboardTime);

        StringBuilder stringBuilderPlace = new StringBuilder();
        StringBuilder stringBuilderPlayer = new StringBuilder();
        StringBuilder stringBuilderTime = new StringBuilder();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject matchObject = dataArray.getJSONObject(i);
                int rank = matchObject.getInt("final_time_rank");
                int final_time = matchObject.getInt("final_time");
                String final_time_formatted = convertTime(final_time);

                JSONObject userObject = matchObject.getJSONObject("user");
                String nickname = userObject.getString("nickname");

                stringBuilderPlace.append(rank).append("\n");
                stringBuilderPlayer.append(nickname).append("\n");
                stringBuilderTime.append(final_time_formatted).append("\n");
            }
            textViewRankedLeaderboardPlace.setText(stringBuilderPlace.toString());
            textViewRankedLeaderboardPlayer.setText(stringBuilderPlayer.toString());
            textViewRankedLeaderboardTime.setText(stringBuilderTime.toString());
            setStyleLeaderboard(textViewRankedLeaderboardPlace, "#1F6A6A");
            setStyleLeaderboard(textViewRankedLeaderboardPlayer, "#142C34");
            setStyleLeaderboard(textViewRankedLeaderboardTime, "#84CC34");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("DefaultLocale")
    private String convertTime(int timeMS){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeMS) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMS) % 60;
        long millis = TimeUnit.MILLISECONDS.toMillis(timeMS) % 1000;

        return String.format("%02d:%02d.%02d", minutes, seconds, millis);
    }

    private void setStyleLeaderboard(TextView textView, String hexColor){
        textView.setTextSize(18);
        int customColor = Color.parseColor(hexColor);
        textView.setTextColor(customColor);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "quicksand_medium.ttf");
        textView.setTypeface(typeface);
    }
}