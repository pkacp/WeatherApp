package edu.kacprzak.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.scheduleJob(context);
//        Intent i = new Intent("com.prac.test.MyPersistingService");
//        i.setClass(context, GetWeather.class);
//        context.startService(i);
    }
}
