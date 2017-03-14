package com.example.android.sunshine.app.utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nazar.dovhuy on 05.11.2016.
 */
public class WApplicationContext extends Application {

    public static final String WEATHER_LIST_ATTR = "list";
    public static final String WEATHER_MAIN_ATTR = "temp";
    public static final String W_WEATHER = "weather";
    public static final String W_DESCR = "description";
    public static final String W_ID = "id";
    public static final String W_ICON = "icon";
    public static final String MAX_TEMP_ATTR = "max";
    public static final String MIN_TEMPO_ATTR = "min";
    public static final String DAY_TEMP_ATTR = "day";
    public static final String NIGHT_TEMP_ATTR = "night";
    public static final String EVE_TEMP_ATTR = "eve";
    public static final String MORN_TEMP_ATTR = "morn";
    public static final String W_5_DAYS = "5";
    public static final String W_10_DAYS = "10";
    public static final String W_15_DAYS = "15";
    public static final String WRONG_CONNECTION_URL = "wrong or malformed URL address populated";
    public static final String DATA_PARSE_ERROR = "could not parse data";
    public static final String NO_SERVER_RESPONSE = "no server response";
    public static final String RESPONSE_OK = "ok";
    public static final String DATA_NOT_FOUND = "DATA NOT FOUND";
    private static Context context;
    private static String resultWeatherData;
    private static SharedPreferences settings;
    private static Resources res;

    public static void setContext(Activity context) {
        WApplicationContext.context = context;
    }

    public static Context getContext() {
        return context;
    }

    public static void setResultWeatherData(String resultWeatherData) {
        WApplicationContext.resultWeatherData = resultWeatherData;
    }

    public static String getResultWeatherData() {
        return resultWeatherData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        res = getBaseContext().getResources();
        settings = getSharedPreferences("settings", MODE_PRIVATE);
        settings.edit().putInt("create_code", 1).commit();
        int createStatus = settings.getInt("create_code", 0);
        if (createStatus != 0) {
            Log.i("settings created ", "" + createStatus);
            Toast.makeText(getApplicationContext(), "settings created " + createStatus, Toast.LENGTH_LONG).show();
        }


        updateWAppLang();

    }

    public static void updateWAppLang() {
        Locale locale = new Locale(settings.getString("lang", "uk"));
        Locale.setDefault(locale);
        Configuration configuration = res.getConfiguration();
        configuration.locale = locale;
        res.updateConfiguration(configuration, res.getDisplayMetrics());

    }

    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return simpleDateFormat.format(calendar.getTime());
    }


    public static SharedPreferences getSettings() {
        return settings;
    }
}

