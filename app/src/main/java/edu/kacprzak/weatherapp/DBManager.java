package edu.kacprzak.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String city_id, String city_name, String lon, String lat, String weather_id,
                       String weather_main, String weather_desc, String main_temp, String main_pressure,
                       String main_humidity, String wind_speed, String wind_deg, String clouds_all, String sunrise, String sunset) {
        ContentValues contentValue = new ContentValues();
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        contentValue.put(DatabaseHelper.CREATED_AT, formatter.format(now));
        contentValue.put(DatabaseHelper.CITY_ID, city_id);
        contentValue.put(DatabaseHelper.CITY_NAME, city_name);
        contentValue.put(DatabaseHelper.LON, lon);
        contentValue.put(DatabaseHelper.LAT, lat);
        contentValue.put(DatabaseHelper.WEATHER_ID, weather_id);
        contentValue.put(DatabaseHelper.WEATHER_MAIN, weather_main);
        contentValue.put(DatabaseHelper.WEATHER_DESC, weather_desc);
        contentValue.put(DatabaseHelper.MAIN_TEMP, main_temp);
        contentValue.put(DatabaseHelper.MAIN_PRESSURE, main_pressure);
        contentValue.put(DatabaseHelper.MAIN_HUMIDITY, main_humidity);
        contentValue.put(DatabaseHelper.WIND_SPEED, wind_speed);
        contentValue.put(DatabaseHelper.WIND_DEG, wind_deg);
        contentValue.put(DatabaseHelper.CLOUDS_ALL, clouds_all);
        contentValue.put(DatabaseHelper.SUNRISE, sunrise);
        contentValue.put(DatabaseHelper.SUNSET, sunset);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

//    public Cursor fetch() {
//        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.JSON };
//        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }
//
//    public int update(long _id, String json) {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.JSON, json);
//        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
//        return i;
//    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}