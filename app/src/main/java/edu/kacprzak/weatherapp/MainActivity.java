package edu.kacprzak.weatherapp;

import android.Manifest;
import android.app.Activity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends Activity {

    private DBManager dbManager;

    private String apiKey;

    private static final int PERMISSION_REQUEST_GPS = 3124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        final Button btnGps = findViewById(R.id.btnGps);
        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGpsCoordinates();
            }
        });

        final Button btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbManager.fetchLastInsertedRow();

                tvUpdatedAt.setText(cursor.getString(1));
                tvCity.setText(cursor.getString(3));
                tvTemperature.setText(cursor.getString(9) + " \u2103");
                tvDescryption.setText(cursor.getString(8));
                tvPressure.setText(cursor.getString(10) + "hPa");
                tvHumidity.setText(cursor.getString(11) + "");
                tvWindSpeed.setText(cursor.getString(12));
                tvWindDirection.setText(cursor.getString(13));
                tvSunriseTime.setText(cursor.getString(15));
                tvSunsetTime.setText(cursor.getString(16));
            }
        });

        apiKey = getResources().getString(R.string.apikey);
        Log.d("Main respoonse: ", "> " + apiKey);
        new GetAndSaveWeather(this, "Krakow", "pl", apiKey).execute();


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
                                Log.d("gps respoonse: ", location.getLatitude() + " vvvvv " + location.getLongitude());
                            }
                        }
                    });

        }

    }

}
