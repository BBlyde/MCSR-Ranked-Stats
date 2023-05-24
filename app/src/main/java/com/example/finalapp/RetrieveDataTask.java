package com.example.finalapp;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetrieveDataTask extends AsyncTask<String, Void, String>{
    private TextView textViewData;
    public RetrieveDataTask(TextView textViewData) {
        this.textViewData = textViewData;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lecture de la réponse de l'API
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            // Fermeture de la connexion
            connection.disconnect();

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String jsonData) {
        if (jsonData != null) {
            // Vous pouvez maintenant traiter les données JSON
            // Par exemple, vous pouvez les analyser avec la classe JSONObject
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                // Traitez les données JSON ici...
                textViewData.setText(jsonData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
