package com.example.android.sunshine.app.utility;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nazar.dovhuy on 05.11.2016.
 */
public class ApplicationContext extends Application {

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
    public static final String WRONG_CONNECTION_URL = "wrong or malformed URL address populated";
    public static final String DATA_PARSE_ERROR = "could not parse data";
    public static final String NO_SERVER_RESPONSE = "no server response";
    public static final String RESPONSE_OK = "ok";
    private static Context context;
    private static String resultWeatherData;

    public static void setContext(Activity context) {
        ApplicationContext.context = context;
    }

    public static Context getContext() {
        return context;
    }

    public static void setResultWeatherData(String resultWeatherData) {
        ApplicationContext.resultWeatherData = resultWeatherData;
    }

    public static String getResultWeatherData() {
        return resultWeatherData;
    }


    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return simpleDateFormat.format(calendar.getTime());
    }
}

