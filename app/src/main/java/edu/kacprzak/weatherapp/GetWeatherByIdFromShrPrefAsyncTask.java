package edu.kacprzak.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetWeatherByIdFromShrPrefAsyncTask extends AsyncTask<Void, Void, String> {

    //    private Activity callingActivity;
//    ProgressDialog dialog;
    private Context context;
    private String language = "";
    private String urlBeginWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String apiKey = "040754a4e689b45bedb7836ce6f2c49d";
    private String fullUrlString;
    private static final String MyPREFERENCES = "WeatherAppSavedOptions";
    private static final String cityIdKey = "cityId";
    private static final String cityNameKey = "cityName";

    public GetWeatherByIdFromShrPrefAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int city_id = sharedpreferences.getInt(cityIdKey, 0);
        fullUrlString = (urlBeginWeather + "id=" + city_id + "&units=metric" + "&lang=" + language + "&APPID=" + apiKey);

    }

    @Override
    protected String doInBackground(Void... voids) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;


        try {
            URL fullUrl = new URL(fullUrlString);
            connection = (HttpURLConnection) fullUrl.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
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
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        Log.d("response --------", response);
        Intent intent = new Intent(context, SaveWeatherToDb.class);
        intent.putExtra("dataToSave", response);
        context.startService(intent);

    }
}
