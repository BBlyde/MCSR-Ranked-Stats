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
import org.w3c.dom.Text;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UserProfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);
        initComponents();
    }

    private void initComponents() {
        TextView textViewTitle = findViewById(R.id.textViewTitle7);
        TextView textViewTitle2 = findViewById(R.id.textViewTitle8);
        Typeface chocolateFont = Typeface.createFromAsset(getAssets(), "TT Chocolates Trial Bold.otf");
        textViewTitle.setTypeface(chocolateFont);
        textViewTitle2.setTypeface(chocolateFont);

        String username = getIntent().getStringExtra("username");

        Button buttonBack = findViewById(R.id.buttonBackUser);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUserStats(username);
    }

    private void getUserStats(String username){
        final TextView textView = (TextView) findViewById(R.id.textViewVolley);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mcsrranked.com/api/users/" + username;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject dataObject = jsonObject.getJSONObject("data");

                            String username = dataObject.getString("nickname");
                            int elo_rate = dataObject.getInt("elo_rate");
                            int latest_time = dataObject.getInt("latest_time");
                            int total_played = dataObject.getInt("total_played");
                            int highest_winstreak = dataObject.getInt("highest_winstreak");
                            int current_winstreak = dataObject.getInt("current_winstreak");
                            int best_record_time = dataObject.getInt("best_record_time");

                            String discord = "discord";
                            String twitch = "twitch";
                            String youtube = "youtube";
                            JSONObject connectionObject = dataObject.getJSONObject("connections");
                            if(!connectionObject.isNull(discord)){
                                JSONObject discordObject = connectionObject.getJSONObject(discord);
                                String discordId = discordObject.getString("id");
                                String discordName = discordObject.getString("name");
                            }
                            if(!connectionObject.isNull(twitch)){
                                JSONObject twitchObject = connectionObject.getJSONObject(twitch);
                                String twitchId = twitchObject.getString("id");
                                String twitchName = twitchObject.getString("name");
                            }
                            if(!connectionObject.isNull(youtube)){
                                JSONObject youtubeObject = connectionObject.getJSONObject(youtube);
                                String youtubeId = youtubeObject.getString("id");
                                String youtubeName = youtubeObject.getString("name");
                            }

                            long hours = TimeUnit.MILLISECONDS.toHours(best_record_time);
                            long minutes = TimeUnit.MILLISECONDS.toMinutes(best_record_time) % 60;
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(best_record_time) % 60;
                            long millis = TimeUnit.MILLISECONDS.toMillis(best_record_time) % 1000;

                            String formattedDuration = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, millis);

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(username).append(" Elo : ").append(elo_rate)
                                    .append(" Last Seen : ").append(latest_time)
                                    .append(" Total Games : ").append(total_played)
                                    .append(" Highest Win Streak : ").append(highest_winstreak)
                                    .append(" Current Win Streak : ").append(current_winstreak)
                                    .append(" Any% Record : ").append(formattedDuration)
                                    .append("\n");

                            textView.setText(stringBuilder.toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorResponse = "Request Error";
                textView.setText(errorResponse);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}