package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSearchProfile;
    private Button buttonEloLeaderboard;
    private Button buttonRankedLeaderboard;

    private final static String SHARED_PREFERENCES_PREFIX = "MainActivitySharedPrefix";
    private final static String SHARED_PREFERENCES_KEY_USERNAME = "username";

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

        TextView textViewMainWebSite = findViewById(R.id.textViewMainWebSite);
        TextView textViewMainAbout = findViewById(R.id.textViewMainAbout);

        buttonSearchProfile.setOnClickListener(this);
        buttonEloLeaderboard.setOnClickListener(this);
        buttonRankedLeaderboard.setOnClickListener(this);
        textViewMainWebSite.setOnClickListener(this);
        textViewMainAbout.setOnClickListener(this);

        /* Code to store last research */
        EditText inputEdit = findViewById(R.id.editTextUsername);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(SHARED_PREFERENCES_KEY_USERNAME, "");
        inputEdit.setText(username);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearchProfile) {
            // Disable the button while the api is called to avoid multiple request
            buttonSearchProfile.setEnabled(false);
            // Retrieve the username parsed in the editView and start the request with the corresponding url
            String username = ((AutoCompleteTextView) findViewById(R.id.editTextUsername)).getText().toString();

            /* Code to store last research */
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SHARED_PREFERENCES_KEY_USERNAME, username);
            editor.apply();

            String url = "https://mcsrranked.com/api/users/" + username;
            startRequest(url);
        } else if(view.getId() == R.id.buttonEloLeaderboard){
            buttonEloLeaderboard.setEnabled(false);
            String url = "https://mcsrranked.com/api/leaderboard";
            startRequest(url);
        } else if(view.getId() == R.id.buttonRankedLeaderboard){
            buttonRankedLeaderboard.setEnabled(false);
            String url = "https://mcsrranked.com/api/record-leaderboard";
            startRequest(url);
        } else if (view.getId() == R.id.textViewMainWebSite) {
            String url = "https://mcsrranked.com/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } else if (view.getId() == R.id.textViewMainAbout) {
            startActivity(new Intent(this, About.class));
        }
    }

    private void startRequest(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Context context = this;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Intent intent;
                            if(url.equals("https://mcsrranked.com/api/leaderboard")){
                                intent = new Intent(context, EloLeaderboard.class);
                            } else if (url.equals("https://mcsrranked.com/api/record-leaderboard")) {
                                intent = new Intent(context, RankedLeaderboard.class);
                            } else {
                                intent = new Intent(context, UserProfile.class);
                            }
                            intent.putExtra("response", response);
                            activateButton();
                            startActivity(intent);
                        } catch (Exception e) {
                            activateButton();
                            printError("Server error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                activateButton();
                String errorResponse = "Unregistered MCSR account";
                printError(errorResponse);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void activateButton(){
        buttonSearchProfile.setEnabled(true);
        buttonEloLeaderboard.setEnabled(true);
        buttonRankedLeaderboard.setEnabled(true);
    }

    private void printError(String errorT){
        Toast t = new Toast(this);
        t.setText(errorT);
        t.show();
    }
}