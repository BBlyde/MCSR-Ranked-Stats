package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

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
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonSearchProfile) {
            // Disable the button while the api is called to avoid multiple request
            buttonSearchProfile.setEnabled(false);
            // Retrieve the username parsed in the editView
            String username = ((EditText) findViewById(R.id.editTextUsername)).getText().toString();
            String url = "https://mcsrranked.com/api/users/" + username;
            startRequest(url);
        } else if(view.getId() == R.id.buttonEloLeaderboard){
            // Disable the button while the api is called to avoid multiple request
            buttonEloLeaderboard.setEnabled(false);
            String url = "https://mcsrranked.com/api/leaderboard";
            startRequest(url);
        } else if(view.getId() == R.id.buttonRankedLeaderboard){
            // Disable the button while the api is called to avoid multiple request
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
                            /* Create and retrieve what's inside the response using a jsonObject*/
                            JSONObject jsonObject = new JSONObject(response);
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
                        } catch (JSONException e) {
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