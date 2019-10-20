package edu.kacprzak.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String cityIdKey = "cityId";
    public static final String cityNameKey = "cityName";
    public static final String apiKey = "040754a4e689b45bedb7836ce6f2c49d";
    private static final int PERMISSION_REQUEST_GPS = 3124;
    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Button btnGps = findViewById(R.id.btnGps);
        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGpsCoordinates();
                GeoCoordinates gc = new GeoCoordinates(lat, lon);
                new GetCityIdByGeoCoordinates(SettingsActivity.this).execute(gc);
            }
        });

        final Button btnLocationList = findViewById(R.id.btnLocationList);
        btnLocationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "Krakow";
                saveLocationToSharedPreferences("3094802", name);
                setCityValueTextView(name);
            }
        });

        final Button btnGoToWeather = findViewById(R.id.btnGoToWeather);
        btnGoToWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });

        final Button btnGoToForecast = findViewById(R.id.btnGoToForecast);
        btnGoToForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void saveLocationToSharedPreferences(String cityId, String cityName) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.putInt(cityIdKey, Integer.parseInt(cityId));
        editor.putString(cityNameKey, cityName);
        editor.apply();
    }

    private void setCityValueTextView(String cityName){
        TextView tvCityValue = findViewById(R.id.tvSelectedCityValue);
        tvCityValue.setText(cityName);
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

    protected void getGpsCoordinates() {

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
                                Log.d("GPS coordinates >>>>> ", location.getLatitude() + " : " + location.getLongitude());
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    });
        }
    }

    protected class GetCityIdByGeoCoordinates extends AsyncTask<GeoCoordinates, Void, String> {

        Activity callingActivity;
        ProgressDialog dialog;
        String fullUrlString;

        GetCityIdByGeoCoordinates(Activity activity) {
            this.callingActivity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(callingActivity, getResources().getString(R.string.loading), getResources().getString(R.string.please_wait), true);
        }

        @Override
        protected String doInBackground(GeoCoordinates... geoCoordinates) {

            fullUrlString = "http://api.openweathermap.org/data/2.5/weather?lat=" + geoCoordinates[0].getLat() + "&lon=" + geoCoordinates[0].getLon() + "&units=metric&APPID=" + apiKey;

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

            String cityId;
            String cityName;

            try {
                JSONObject obj = new JSONObject(response);

                cityId = (obj.get("id")).toString();
                cityName = (obj.get("name")).toString();

                Log.d("Json res location: ", "city:" + cityId + " " + cityName + "URL: " + fullUrlString);

                saveLocationToSharedPreferences(cityId, cityName);
                setCityValueTextView(cityName);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            dialog.dismiss();
        }
    }



}
