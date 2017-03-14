package com.example.android.sunshine.app.utility.parser.impl;

import android.content.Context;

import static com.example.android.sunshine.app.utility.WApplicationContext.*;

import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.utility.parser.AbstractWeatherJsonParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by nazar.dovhuy on 03.12.2016.
 */
public class ResultWeatherStringJsonParser extends AbstractWeatherJsonParser<String[]> {
    public ResultWeatherStringJsonParser(Context context) {
        super(context);
    }

    @Override
    public String[] parse(String weatherDataJson) throws JSONException, ParseException {
        JSONObject weatherJsonObject = new JSONObject(weatherDataJson);
        JSONArray weatherJsonArray = getParsedWeatherJsonArrayByAttr(weatherJsonObject, WEATHER_LIST_ATTR);
        String[] resultWeather = new String[weatherJsonArray.length()];
        DateWeatherForecast dateWeatherForecast = new DateWeatherForecast();
        for (int i = 0; i < weatherJsonArray.length(); i++) {
            weatherJsonObject = weatherJsonArray.getJSONObject(i);
            dateWeatherForecast.setDescription(getParsedWeatherDescription(weatherJsonObject));
            JSONObject mainWeatherInfo = getParsedWeatherJsonObjectByAttr(weatherJsonObject, WEATHER_MAIN_ATTR);
            dateWeatherForecast.setTemp(getParsedTemperature(mainWeatherInfo));
            dateWeatherForecast.setDate(getParsedDateInfo(i));
            resultWeather[i] = dateWeatherForecast.getDate() + " " +
                    dateWeatherForecast.getDescription() + " " + dateWeatherForecast.getTemp();

        }
        return resultWeather;
    }
}
