package com.example.android.sunshine.app.utility.parser;

import android.content.Context;

import com.example.android.sunshine.app.WeatherServerProperty;
import com.example.android.sunshine.app.utility.ApplicationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 28.11.2016.
 */
public abstract class AbstractWeatherJsonParser<T> {

    protected Context context = ApplicationData.getContext();
    protected WeatherServerProperty weatherServerProperty = new WeatherServerProperty(context);

    public abstract T parse(String parseParam) throws JSONException, ParseException;


    protected String getWeatherDescription(JSONObject weatherJson) throws JSONException {
        String description;
        description = returnJsonArrayByAttr(weatherJson, "W_WEATHER")
                .getJSONObject(0)
                .getString("description");
        return description;
    }

    protected String getFormattedTemperature(JSONObject mainWeatherJsonObject) throws JSONException {
        return roundHighLows(extractTemp(mainWeatherJsonObject, weatherServerProperty.getAttribute("MIN_TEMPO_ATTR") ),
                extractTemp(mainWeatherJsonObject, weatherServerProperty.getAttribute("MAX_TEMP_ATTR")));
    }

    private String roundHighLows(double minTemp, double maxTemp) {
        long roundHigh = Math.round(maxTemp);
        long roundLow = Math.round(minTemp);
        return roundHigh + " / " + roundLow;
    }

    private double extractTemp(JSONObject weatherJson, String tempAttr) throws JSONException {
        return weatherJson.getDouble(tempAttr);
    }


    public String getReadableDateInfo(int day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, day);
        return simpleDateFormat.format(calendar.getTime());
    }

    public double getMaxTemperatureFromWeatherJsonString(String weatherJson, int dayIndex)
            throws JSONException {
        return extractTemp(new JSONObject(weatherJson)
                .getJSONArray("list")
                .getJSONObject(dayIndex)
                .getJSONObject("main"), "temp_max");
    }


    public JSONArray returnJsonArrayByAttr(JSONObject weatherJson, String attr) throws JSONException {
        return  weatherJson.getJSONArray(weatherServerProperty.getAttribute(attr));
    }

    public JSONObject returnJsonObjectByAttr(JSONObject weatherJson, String attr) throws JSONException {
        return  weatherJson.getJSONObject(weatherServerProperty.getAttribute(attr));
    }

}
