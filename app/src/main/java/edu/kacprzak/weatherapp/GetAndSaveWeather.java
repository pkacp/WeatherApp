package edu.kacprzak.weatherapp;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAndSaveWeather extends AsyncTask<Void, Void, String> {

    private DBManager dbManager;

    private Context context;
    private String urlBeginWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private String city;
    private String language;
    private String apikey;
    private String lat;
    private String lon;
    private String fullUrlString;
    private URL fullUrl;

    public GetAndSaveWeather(Context context, String city, String language, String apikey) {
        this.context = context;
        this.city = city;
        this.language = language;
        this.apikey = apikey;
        fullUrlString = (urlBeginWeather + "q=" + city + "&units=metric" + "&lang=" + language + "&APPID=" + apikey);
    }

    //    public GetAndSaveWeather(String city, Context context) throws MalformedURLException {
//        this.city = city;
//        this.context = context;
//        fullUrl = new URL(urlBeginWeather + city + "&APPID=" + apikey);
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dbManager = new DBManager(context);
        dbManager.open();
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
                buffer.append(line+"\n");
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



        dbManager.insert(result);
    }

//    protected String[]

}
