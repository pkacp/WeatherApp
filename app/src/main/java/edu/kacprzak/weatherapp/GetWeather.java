package edu.kacprzak.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetWeather extends AsyncTask<Void, Void, String> {


    private Context context;
    private String urlBeginWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private String fullUrlString;
    private Activity callingActivity;
    private URL fullUrl;

    public GetWeather(Context context, String city, String language, String apikey) {
        this.context = context;
        this.callingActivity = (Activity) context;
        fullUrlString = (urlBeginWeather + "q=" + city + "&units=metric" + "&lang=" + language + "&APPID=" + apikey);
    }

    public GetWeather(Context context, double lat, double len, String language, String apikey){
        this.context = context;
        fullUrlString = (urlBeginWeather + "lat=" + lat + "&lon=" + len + "&units=metric" + "&lang=" + language + "&APPID=" + apikey);
    }

    public GetWeather(Context context, int city_id, String language, String apikey) {
        this.context = context;
        fullUrlString = (urlBeginWeather + "id=" + city_id + "&units=metric" + "&lang=" + language + "&APPID=" + apikey);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            fullUrl = new URL(fullUrlString);
            connection = (HttpURLConnection) fullUrl.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);

            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Intent intent = new Intent(context, SaveWeatherToDb.class);
        intent.putExtra("dataToSave", result);
        context.startService(intent);

    }

}
