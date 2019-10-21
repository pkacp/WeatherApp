package edu.kacprzak.weatherapp;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class CyclicWeatherUpdateService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("JOB START", "START HERE !!!!!!!!!!!!!!!!!");
        new GetWeatherByIdFromShrPrefAsyncTask(getApplicationContext()).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
