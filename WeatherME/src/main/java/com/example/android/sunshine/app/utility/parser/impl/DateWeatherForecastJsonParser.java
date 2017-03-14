package com.example.android.sunshine.app.utility.parser.impl;

import android.content.Context;

import static com.example.android.sunshine.app.utility.WApplicationContext.*;

import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.utility.parser.AbstractWeatherJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazar.dovhuy on 28.11.2016.
 */
public class DateWeatherForecastJsonParser extends AbstractWeatherJsonParser<List<DateWeatherForecast>> {


    public DateWeatherForecastJsonParser(Context context) {
        super(context);
    }

    @Override
    public List<DateWeatherForecast> parse(String weatherDataJson) throws JSONException, ParseException {
        List<DateWeatherForecast> dateWeatherForecasts = new ArrayList<>();
        JSONObject weatherJsonObject = new JSONObject(weatherDataJson);
        DateWeatherForecast locationDescDateWeatherForecast = new DateWeatherForecast();
        JSONObject cityJson = weatherJsonObject.getJSONObject("city");
        locationDescDateWeatherForecast.setCity(cityJson.getString("name"));
        locationDescDateWeatherForecast.setCountry(cityJson.getString("country"));
        dateWeatherForecasts.add(locationDescDateWeatherForecast);
        JSONArray weatherJsonListArray = getParsedWeatherJsonArrayByAttr(weatherJsonObject, WEATHER_LIST_ATTR);
        for (int index = 0; index < weatherJsonListArray.length(); index++) {
            weatherJsonObject = weatherJsonListArray.getJSONObject(index);
            DateWeatherForecast dateWeatherForecast = new DateWeatherForecast();
            dateWeatherForecast.setWeatherIconId(getParsedWeatherImageIconCode(weatherJsonObject));
            dateWeatherForecast.setDescription(getParsedWeatherDescription(weatherJsonObject));
            JSONObject mainWeatherInfo = getParsedWeatherJsonObjectByAttr(weatherJsonObject, WEATHER_MAIN_ATTR);
            dateWeatherForecast.setTemp(getParsedTemperature(mainWeatherInfo));
            dateWeatherForecast.setMaxTemp(getParsedMaxTemp(mainWeatherInfo));
            dateWeatherForecast.setMinTemp(getParsedMinTemp(mainWeatherInfo));
            dateWeatherForecast.setDate(getParsedDateInfo(index));
            dateWeatherForecasts.add(dateWeatherForecast);
        }
        return dateWeatherForecasts;
    }
}
