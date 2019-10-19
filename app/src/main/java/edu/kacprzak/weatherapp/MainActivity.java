package edu.kacprzak.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends Activity {

    private String apiKey;
    private Button btnGps;

    private static final int PERMISSION_REQUEST_GPS = 3124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGps = findViewById(R.id.btnGps);
        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGpsCoordinates();
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
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_GPS);
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
