package edu.kacprzak.weatherapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    String apiKey;

//    private DBManager dbManager;

//    public static final int PLEASE_WAIT_DIALOG = 1;

//    String apiKey = "040754a4e689b45bedb7836ce6f2c49d";
//    private String basicWeaterUrl = "http://api.openweathermap.org/data/2.5/weather?q=";
//    String city = "Krakow";
//    String fullUrl = basicWeaterUrl + city + "&APPID=" + apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = getResources().getString(R.string.apikey);
        Log.d("Main respoonse: ", "> " + apiKey);
        new GetAndSaveWeather(this, "Krakow", "pl", apiKey).execute();


//        dbManager = new DBManager(this);
//        dbManager.open();

//        GetWeather taskGetWeather = new GetWeather();
//        String taskResponse = "";
//        try {
//            taskResponse = taskGetWeather.execute(fullUrl).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//        Log.d("Main respoonse: ", "> " + taskResponse);

    }

//    @Override
//    public Dialog onCreateDialog(int dialogId) {
//
//        switch (dialogId) {
//            case PLEASE_WAIT_DIALOG:
//                ProgressDialog dialog = new ProgressDialog(this);
//                dialog.setTitle("Obliczanie");
//                dialog.setMessage("Proszę czekać....");
//                dialog.setCancelable(true);
//                return dialog;
//
//            default:
//                break;
//        }
//
//        return null;
//    }

}
