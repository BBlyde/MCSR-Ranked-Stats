package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RankedLeaderboard extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_leaderboard);
        initComponents();
    }

    private void initComponents() {
        TextView textViewTitle = findViewById(R.id.textViewTitle5);
        TextView textViewTitle2 = findViewById(R.id.textViewTitle6);
        Typeface chocolateFont = Typeface.createFromAsset(getAssets(), "TT Chocolates Trial Bold.otf");
        textViewTitle.setTypeface(chocolateFont);
        textViewTitle2.setTypeface(chocolateFont);

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
        final TextView textView = (TextView) findViewById(R.id.textVolley);

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray dataArray = jsonObject.getJSONArray("data");

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject matchObject = dataArray.getJSONObject(i);
                int rank = matchObject.getInt("final_time_rank");
                int final_time = matchObject.getInt("final_time");
                int match_date = matchObject.getInt("match_date");

                JSONObject userObject = matchObject.getJSONObject("user");
                String nickname = userObject.getString("nickname");

                stringBuilder.append("Nickname : ").append(nickname)
                        .append(" rank : ").append(rank)
                        .append(" final_time : ").append(final_time)
                        .append(" match_date : ").append(match_date).append("\n\n");
            }
            textView.setText(stringBuilder.toString());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}