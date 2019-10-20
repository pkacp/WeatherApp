package edu.kacprzak.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends Activity {

    private DBManager dbManager;

    private String apiKey;
    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String cityIdKey = "cityId";
    public static final String latKey = "lat";
    public static final String lonKey = "lon";

    private static final int PERMISSION_REQUEST_GPS = 3124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnGps = findViewById(R.id.btnGps);
        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocationToSharedPreferences(getGpsCoordinates(), null);
            }
        });

        final Button btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewsValues();
            }
        });

        apiKey = getResources().getString(R.string.apikey);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.contains(cityIdKey)){
            new GetAndSaveWeather(this, 1, "pl", apiKey).execute(); //TODO get language depending on os lang
        }else if (sharedpreferences.contains(latKey) && sharedpreferences.contains(lonKey)){
            new GetAndSaveWeather(this, (double) sharedpreferences.getFloat(latKey, 0.0f), (double) sharedpreferences.getFloat(lonKey, 0.0f), "pl", apiKey).execute();
        }else{
            String msg = getString(R.string.unable_to_read_from_shr_pref);
            Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            toast.show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_GPS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String msg = getString(R.string.gps_permission_granted);
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                String msg = getString(R.string.gps_permission_not_granted);
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    protected GeoCoordinates getGpsCoordinates() {

        final GeoCoordinates[] result = new GeoCoordinates[1];

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_GPS);
            } else {
            }
        } else {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.d("gps respoonse: ", location.getLatitude() + " vvvvv " + location.getLongitude());
                                result[0] = new GeoCoordinates(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
        }
        return result[0];
    }

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
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"}; //TODO change to string res
        return directions[(int) Math.round(((Double.parseDouble(deg) % 360) / 45)) % 8];
    }

    private void saveLocationToSharedPreferences(GeoCoordinates gc, String cityId) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        if (cityId != null && gc == null) {
            editor.putString(cityIdKey, cityId);
        } else if (cityId == null && gc != null) {
            editor.putFloat(latKey, (float) gc.getLat());
            editor.putFloat(lonKey, (float) gc.getLon());
        }
        editor.commit();
    }

    private void getLocationFromSharedPreferences(){

    }
}

