package edu.kacprzak.weatherapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private DBManager dbManager;

    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final Button btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewsValues();
            }
        });

    }

//    private void callGetData(){
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//
//        if(sharedpreferences.contains(cityIdKey)){
//            new GetAndSaveWeather(this, sharedpreferences.getInt(cityIdKey, 2643743), "pl", apiKey).execute(); //TODO get language depending on os lang
//        }else if (sharedpreferences.contains(latKey) && sharedpreferences.contains(lonKey)){
//            new GetAndSaveWeather(this, (double) sharedpreferences.getFloat(latKey, 0.0f), (double) sharedpreferences.getFloat(lonKey, 0.0f), "pl", apiKey).execute();
//        }else{
//            String msg = getString(R.string.unable_to_read_from_shr_pref);
//            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
//            toast.show();
//        }
//    }



    public void setTextViewsValues() {
        dbManager = new DBManager(this);
        dbManager.open();

        final TextView tvUpdatedAt = findViewById(R.id.tvUpdatedAtValue);
        final TextView tvCity = findViewById(R.id.tvCityValue);
        final TextView tvTemperature = findViewById(R.id.tvTemperatureValue);
        final TextView tvDescryption = findViewById(R.id.tvDescriptionValue);
        final TextView tvPressure = findViewById(R.id.tvPressureValue);
        final TextView tvHumidity = findViewById(R.id.tvHumidityValue);
        final TextView tvWindSpeed = findViewById(R.id.tvWindSpeedValue);
        final TextView tvWindDirection = findViewById(R.id.tvWindDirectionValue);
        final TextView tvSunriseTime = findViewById(R.id.tvSunriseTimeValue);
        final TextView tvSunsetTime = findViewById(R.id.tvSunsetTimeValue);

        Cursor cursor = dbManager.fetchLastInsertedRow();

        tvUpdatedAt.setText(cursor.getString(1));
        tvCity.setText(cursor.getString(3));
        tvTemperature.setText(cursor.getString(9) + " \u2103");
        tvDescryption.setText(cursor.getString(8));
        tvPressure.setText(cursor.getString(10) + " hPa");
        tvHumidity.setText(cursor.getString(11) + "%");
        tvWindSpeed.setText(cursor.getString(12) + " km/h");
        tvWindDirection.setText(getWindDirection(cursor.getString(13)));
        tvSunriseTime.setText(cursor.getString(15));
        tvSunsetTime.setText(cursor.getString(16));
    }

    private String getWindDirection(String deg) {
        if(!deg.equals("")) {
            String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"}; //TODO change to string res
            return directions[(int) Math.round(((Double.parseDouble(deg) % 360) / 45)) % 8];
        }else return "";
    }



    private void getLocationFromSharedPreferences(){

    }
}

