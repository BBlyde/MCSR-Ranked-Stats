package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initComponents();
    }

    private void initComponents() {
        // Retrieve the username from the Main Activity's editText
        String response = getIntent().getStringExtra("response");

        /* Setting-up the back button */
        Button buttonBack = findViewById(R.id.buttonBackUser);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUserStats(response);
    }

    private void getUserStats(String response){
        /* Initializing all the views and layouts */
        final TextView textViewUserNickname = (TextView) findViewById(R.id.textViewUserNickname);
        final TextView textViewUserElo = (TextView) findViewById(R.id.textViewUserElo);
        final TextView textViewUserPlace = (TextView) findViewById(R.id.textViewUserPlace);
        final TextView textViewUserLastSeen = (TextView) findViewById(R.id.textViewUserLastSeen);
        final TextView textViewUserPB = (TextView) findViewById(R.id.textViewUserPB);
        final TextView textViewUserTotalPlayed = (TextView) findViewById(R.id.textViewUserTotalPlayed);
        final TextView textViewUserSeasonPlayed = (TextView) findViewById(R.id.textViewUserSeasonPlayed);
        final TextView textViewUserHWS = (TextView) findViewById(R.id.textViewUserHWS);
        final TextView textViewUserCWS = (TextView) findViewById(R.id.textViewUserCWS);
        final TextView textViewBestElo = (TextView) findViewById(R.id.textViewBestElo);
        final TextView textViewPrevElo = (TextView) findViewById(R.id.textViewPrevElo);
        final TextView textViewUserWinP = (TextView) findViewById(R.id.textViewUserWinP);

        ImageView imageViewYoutubeLogo = findViewById(R.id.imageViewYoutubeLogo);
        ImageView imageViewTwitchLogo = findViewById(R.id.imageViewTwitchLogo);
        ImageView imageViewDiscordLogo = findViewById(R.id.imageViewDiscordLogo);

        LinearLayout containerLayout = findViewById(R.id.containerLayout);

        /* String const for social media */
        String discord = "discord";
        String twitch = "twitch";
        String youtube = "youtube";

        try {
            JSONObject responseObject = new JSONObject(response);
            JSONObject dataObject = responseObject.getJSONObject("data");

            String username = dataObject.getString("nickname");

            /* Calls the mc-heads api to retrieve the head of the user */
            String imageUrl = "https://mc-heads.net/avatar/" + username;
            ImageView imageView = findViewById(R.id.imageView5);
            Picasso.get().load(imageUrl).into(imageView);

            int elo_rate = dataObject.getInt("elo_rate");
            int elo_rank = -1;
            if (!dataObject.isNull("elo_rank")){
                elo_rank = dataObject.getInt("elo_rank");
            }
            int latest_time = dataObject.getInt("latest_time");
            int total_played = dataObject.getInt("total_played");
            int season_played = dataObject.getInt("season_played");
            int highest_winstreak = dataObject.getInt("highest_winstreak");
            int current_winstreak = dataObject.getInt("current_winstreak");
            int prev_elo_rate = dataObject.getInt("prev_elo_rate");
            int best_elo_rate = dataObject.getInt("best_elo_rate");
            int best_record_time = dataObject.getInt("best_record_time");

            JSONObject recordObject = dataObject.getJSONObject("records");
            JSONObject seasonObject = recordObject.getJSONObject("2");
            int win = seasonObject.getInt("win");

            /* Setting-up the social media imageViews if linked */
            JSONObject connectionObject = dataObject.getJSONObject("connections");

            if(!connectionObject.isNull(youtube)) {
                JSONObject youtubeObject = connectionObject.getJSONObject(youtube);
                String youtubeId = youtubeObject.getString("id");
                imageViewYoutubeLogo.setVisibility(View.VISIBLE);
                imageViewYoutubeLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://www.youtube.com/channel/" + youtubeId;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
            }
            if(!connectionObject.isNull(twitch)){
                JSONObject twitchObject = connectionObject.getJSONObject(twitch);
                String twitchName = twitchObject.getString("name");
                imageViewTwitchLogo.setVisibility(View.VISIBLE);
                imageViewTwitchLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://www.twitch.tv/" + twitchName;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
            }

            if(!connectionObject.isNull(discord)){
                JSONObject discordObject = connectionObject.getJSONObject(discord);
                String discordName = discordObject.getString("name");
                imageViewDiscordLogo.setVisibility(View.VISIBLE);
                imageViewDiscordLogo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = new TextView(UserProfile.this);
                        textView.setText(discordName);
                        textView.setTextSize(18);
                        String hexColor = "#142C34";
                        int customColor = Color.parseColor(hexColor);
                        textView.setTextColor(customColor);
                        Typeface typeface = Typeface.createFromAsset(getAssets(), "quicksand_semibold.ttf");
                        textView.setTypeface(typeface);
                        textView.setGravity(Gravity.CENTER);
                        containerLayout.addView(textView);
                        imageViewDiscordLogo.setEnabled(false);
                    }
                });
            }

            /* Attributing each properties to each view */
            textViewUserNickname.setText(username);

            String string_elo_rate = " [" + elo_rate + "]";
            textViewUserElo.setText(string_elo_rate);
            String string_elo_rank = " #" + elo_rank;
            textViewUserPlace.setText(string_elo_rank);

            CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(latest_time * 1000L, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
            textViewUserLastSeen.setText(relativeTime);

            String best_record_time_converted = "-1";
            if (best_record_time < 3600000){
                best_record_time_converted = convertTime(best_record_time);
            } else{
                best_record_time_converted = convertTimeHours(best_record_time);
            }
            textViewUserPB.setText(best_record_time_converted);

            textViewUserTotalPlayed.setText(String.valueOf(total_played));
            textViewUserSeasonPlayed.setText(String.valueOf(season_played));
            textViewUserHWS.setText(String.valueOf(highest_winstreak));
            textViewUserCWS.setText(String.valueOf(current_winstreak));
            textViewBestElo.setText(String.valueOf(best_elo_rate));
            textViewPrevElo.setText(String.valueOf(prev_elo_rate));

            float winP100 = ((float) win / (float) season_played)*100;
            String winP_string = Math.round(winP100) + "%";
            textViewUserWinP.setText(winP_string);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("DefaultLocale")
    private String convertTimeHours(int timeMS){
        long hours = TimeUnit.MILLISECONDS.toHours(timeMS);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeMS) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMS) % 60;
        long millis = TimeUnit.MILLISECONDS.toMillis(timeMS) % 1000;

        return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, millis);
    }

    @SuppressLint("DefaultLocale")
    private String convertTime(int timeMS){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeMS) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeMS) % 60;
        long millis = TimeUnit.MILLISECONDS.toMillis(timeMS) % 1000;

        return String.format("%02d:%02d.%02d", minutes, seconds, millis);
    }
}