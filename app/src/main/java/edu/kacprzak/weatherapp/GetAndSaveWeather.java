package edu.kacprzak.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

        decodeAndSave(result);

    }

    protected void decodeAndSave(String response) {

        HashMap<String, String> dataHashMap = new HashMap<String, String>();

        dataHashMap.put("city_id", "");
        dataHashMap.put("city_name", "");
        dataHashMap.put("lon", "");
        dataHashMap.put("lat", "");
        dataHashMap.put("weather_id", "");
        dataHashMap.put("weather_main", "");
        dataHashMap.put("weather_desc", "");
        dataHashMap.put("main_temp", "");
        dataHashMap.put("main_pressure", "");
        dataHashMap.put("main_humidity", "");
        dataHashMap.put("wind_speed", "");
        dataHashMap.put("wind_deg", "");
        dataHashMap.put("clouds_all", "");
        dataHashMap.put("sunrise", "");
        dataHashMap.put("sunset", "");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            JSONObject obj = new JSONObject(response);

            dataHashMap.put("city_id", (obj.get("id")).toString());
            dataHashMap.put("city_name", (obj.get("name")).toString());
            dataHashMap.put("lon", (obj.getJSONObject("coord").get("lon")).toString());
            dataHashMap.put("lat", (obj.getJSONObject("coord").get("lat")).toString());
            dataHashMap.put("weather_id", (obj.getJSONArray("weather").getJSONObject(0).get("id")).toString());
            dataHashMap.put("weather_main", (obj.getJSONArray("weather").getJSONObject(0).get("main")).toString());
            dataHashMap.put("weather_desc", (obj.getJSONArray("weather").getJSONObject(0).get("description")).toString());
            dataHashMap.put("main_temp", (obj.getJSONObject("main").get("temp")).toString());
            dataHashMap.put("main_pressure", (obj.getJSONObject("main").get("pressure")).toString());
            dataHashMap.put("main_humidity", (obj.getJSONObject("main").get("humidity")).toString());
            dataHashMap.put("wind_speed", (obj.getJSONObject("wind").get("speed")).toString());
            dataHashMap.put("wind_deg", (obj.getJSONObject("wind").get("speed")).toString());
            dataHashMap.put("clouds_all", (obj.getJSONObject("clouds").get("all")).toString());
            dataHashMap.put("sunrise", formatter.format(new Date(Long.parseLong(obj.getJSONObject("sys").get("sunrise").toString())*1000)));
            dataHashMap.put("sunset", formatter.format(new Date(Long.parseLong(obj.getJSONObject("sys").get("sunset").toString())*1000)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dbManager.insert(
                dataHashMap.get("city_id"),
                dataHashMap.get("city_name"),
                dataHashMap.get("lon"),
                dataHashMap.get("lat"),
                dataHashMap.get("weather_id"),
                dataHashMap.get("weather_main"),
                dataHashMap.get("weather_desc"),
                dataHashMap.get("main_temp"),
                dataHashMap.get("main_pressure"),
                dataHashMap.get("main_humidity"),
                dataHashMap.get("wind_speed"),
                dataHashMap.get("wind_deg"),
                dataHashMap.get("clouds_all"),
                dataHashMap.get("sunrise"),
                dataHashMap.get("sunset")
        );

        /* sample response

            {
              "coord": {
                "lon": 19.94,
                "lat": 50.06
              },
              "weather": [
                {
                  "id": 803,
                  "main": "Clouds",
                  "description": "broken clouds",
                  "icon": "04n"
                }
              ],
              "base": "stations",
              "main": {
                "temp": 285.76,
                "pressure": 1016,
                "humidity": 76,
                "temp_min": 283.71,
                "temp_max": 287.59
              },
              "visibility": 10000,
              "wind": {
                "speed": 3.6,
                "deg": 240
              },
              "clouds": {
                "all": 75
              },
              "dt": 1571250799,
              "sys": {
                "type": 1,
                "id": 1701,
                "country": "PL",
                "sunrise": 1571202169,
                "sunset": 1571240918
              },
              "timezone": 7200,
              "id": 3094802,
              "name": "Krakow",
              "cod": 200
            }

         */

    }

}
