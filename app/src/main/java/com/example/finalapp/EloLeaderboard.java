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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        final TextView textView = (TextView) findViewById(R.id.textVolley);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mcsrranked.com/api/leaderboard";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataObject = jsonObject.getJSONObject("data");

                            JSONArray usersArray = dataObject.getJSONArray("users");
                            StringBuilder stringBuilder = new StringBuilder();

                            for (int i = 0; i < usersArray.length(); i++) {
                                JSONObject userObject = usersArray.getJSONObject(i);
                                int rank = userObject.getInt("elo_rank");
                                String nickname = userObject.getString("nickname");
                                int elo = userObject.getInt("elo_rate");

                                stringBuilder.append("Nickname : ").append(nickname)
                                        .append(" rank : ").append(rank)
                                        .append(" elo : ").append(elo).append("\n");
                            }
                            textView.setText(stringBuilder.toString());

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String test = "e";
                textView.setText(test);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}