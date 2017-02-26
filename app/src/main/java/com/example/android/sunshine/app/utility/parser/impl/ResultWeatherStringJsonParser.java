package com.example.android.sunshine.app.utility.parser.impl;

import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.utility.parser.AbstractWeatherJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by User on 03.12.2016.
 */
public class ResultWeatherStringJsonParser extends AbstractWeatherJsonParser<String[]> {
    @Override
    public String[] parse(String weatherDataJson) throws JSONException, ParseException {
        JSONObject weatherJsonObject = new JSONObject(weatherDataJson);
        JSONArray weatherJsonArray = returnJsonArrayByAttr(weatherJsonObject, "WEATHER_LIST_ATTR");
        String[] resultWeather = new String[weatherJsonArray.length()];
        DateWeatherForecast dateWeatherForecast = new DateWeatherForecast();
        for (int i = 0; i < weatherJsonArray.length(); i++) {
            weatherJsonObject = weatherJsonArray.getJSONObject(i);
            dateWeatherForecast.setDescription(getWeatherDescription(weatherJsonObject));
            JSONObject mainWeatherInfo = returnJsonObjectByAttr(weatherJsonObject, "WEATHER_MAIN_ATTR");
            dateWeatherForecast.setTemp(getFormattedTemperature(mainWeatherInfo));
            dateWeatherForecast.setDate(getReadableDateInfo(i));
            resultWeather[i] = dateWeatherForecast.getDate() + " " +
                    dateWeatherForecast.getDescription() + " " + dateWeatherForecast.getTemp();
        }
        return resultWeather;
    }
}
