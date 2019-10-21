package edu.kacprzak.weatherapp;

import android.content.Context;
import android.os.AsyncTask;

public class GetForecastByIdFromShrPrefAsyncTask extends AsyncTask<Void, Void, String> {

    //    private Activity callingActivity;
//    ProgressDialog dialog;
    private Context context;
    private String language = "";
    private String urlBeginWeather = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String apiKey = "040754a4e689b45bedb7836ce6f2c49d";
    private String fullUrlString;
    private static final String MyPREFERENCES = "WeatherAppSavedOptions";
    private static final String cityIdKey = "cityId";
    private static final String cityNameKey = "cityName";

    public GetForecastByIdFromShrPrefAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
//        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        int city_id = sharedpreferences.getInt(cityIdKey, 0);
//        fullUrlString = (urlBeginWeather + "id=" + city_id + "&units=metric" + "&lang=" + language + "&APPID=" + apiKey);
//        Log.d("FORECAST URL>>", fullUrlString);
    }

    @Override
    protected String doInBackground(Void... voids) {
//
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//
//
//        try {
//            URL fullUrl = new URL(fullUrlString);
//            connection = (HttpURLConnection) fullUrl.openConnection();
//            connection.connect();
//
//
//            InputStream stream = connection.getInputStream();
//
//            reader = new BufferedReader(new InputStreamReader(stream));
//
//            StringBuffer buffer = new StringBuffer();
//            String line = "";
//
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line + "\n");
//            }
//
//            return buffer.toString();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        return null;
    }

    @Override
    protected void onPostExecute(String response) {
//        super.onPostExecute(response);
//        Log.d("response --------", response);
//        parseData(response);
//        Intent intent = new Intent(context, SaveWeatherToDb.class);
//        intent.putExtra("dataToSave", response);
//        context.startService(intent);

    }

    private void parseData(String value){
//        HashMap<String, String> dataHashMap = new HashMap<String, String>();
//
//        ArrayList<HashMap> forecastData = new ArrayList<HashMap>();
//
//        dataHashMap.put("city_id", "");
//        dataHashMap.put("city_name", "");
//        dataHashMap.put("lon", "");
//        dataHashMap.put("lat", "");
//        dataHashMap.put("sunrise", "");
//        dataHashMap.put("sunset", "");
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        try {
//            JSONObject obj = new JSONObject(value);
//
//            dataHashMap.put("city_id", (obj.get("id")).toString());
//            dataHashMap.put("city_name", (obj.get("name")).toString());
//            dataHashMap.put("lon", (obj.getJSONObject("coord").get("lon")).toString());
//            dataHashMap.put("lat", (obj.getJSONObject("coord").get("lat")).toString());
//            JSONArray forecastArr = obj.getJSONArray("list");
//            JSONObject currentObject;
//            int len = forecastArr.length();
//            for(int i = 0; i < len; i++){
//                HashMap<String, String> forecastWeatherHashMap = new HashMap<String, String>();
//                currentObject = forecastArr.getJSONObject(i);
//                forecastWeatherHashMap.put("dt",currentObject.getString("dt"));
//                forecastWeatherHashMap.put("dt_txt",currentObject.getString("dt_txt"));
//                forecastWeatherHashMap.put("temp",currentObject.getJSONObject("main").getString("temp"));
//                forecastWeatherHashMap.put("pressure",currentObject.getJSONObject("main").getString("pressure"));
//                forecastWeatherHashMap.put("humidity",currentObject.getJSONObject("main").getString("humidity"));
//                forecastWeatherHashMap.put("description",currentObject.getJSONArray("weather").getJSONObject(0).getString("description"));
//                forecastWeatherHashMap.put("speed",currentObject.getJSONObject("wind").getString("speed"));
//                forecastWeatherHashMap.put("deg",currentObject.getJSONObject("wind").getString("deg"));
//                forecastData.add(forecastWeatherHashMap);
//            }
//            dataHashMap.put("sunrise", formatter.format(new Date(Long.parseLong(obj.getJSONObject("sys").get("sunrise").toString())*1000)));
//            dataHashMap.put("sunset", formatter.format(new Date(Long.parseLong(obj.getJSONObject("sys").get("sunset").toString())*1000)));

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        ForecastActivity forecastActivity = (ForecastActivity) context;
//        forecastActivity.setListViewWithData(dataHashMap, forecastData);
    }
}
