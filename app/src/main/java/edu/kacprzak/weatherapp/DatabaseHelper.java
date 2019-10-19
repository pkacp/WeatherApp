package edu.kacprzak.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "WEATHER_HISTORY";

    // Table columns
    public static final String _ID = "_id";
    public static final String CREATED_AT = "created_at";
    public static final String CITY_ID = "city_id";
    public static final String CITY_NAME = "city_name";
    public static final String LON = "lon";
    public static final String LAT = "lat";
    public static final String WEATHER_ID = "weather_id";
    public static final String WEATHER_MAIN = "weather_main";
    public static final String WEATHER_DESC = "weather_desc";
    public static final String MAIN_TEMP = "main_temp";
    public static final String MAIN_PRESSURE = "weather_pressure";
    public static final String MAIN_HUMIDITY = "weather_humidity";
    public static final String WIND_SPEED = "wind_speed";
    public static final String WIND_DEG = "wind_deg";
    public static final String CLOUDS_ALL = "clouds_all";
    public static final String SUNRISE = "sunrise";
    public static final String SUNSET = "sunset";
//    public static final String JSON = "json";
//    public static final String DESC = "description";

    // Database Information
    static final String DB_NAME = "WEATHER.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CREATED_AT + " DATETIME NOT NULL,"
            + CITY_ID + " INTEGER, "
            + CITY_NAME + " TEXT, "
            + LON + " TEXT, "
            + LAT +" TEXT, "
            + WEATHER_ID + " NUMBER, "
            + WEATHER_MAIN + " TEXT, "
            + WEATHER_DESC + " TEXT, "
            + MAIN_TEMP + " TEXT, "
            + MAIN_PRESSURE + " TEXT, "
            + MAIN_HUMIDITY + " TEXT, "
            + WIND_SPEED + " TEXT, "
            + WIND_DEG + " TEXT, "
            + CLOUDS_ALL + " TEXT, "
            + SUNRISE + " DATETIME, "
            + SUNSET + " DATETIME "
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}