package com.example.android.sunshine.app.utility.parser.impl;

import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.utility.parser.AbstractWeatherJsonParser;
import org.json.JSONException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by User on 28.11.2016.
 */
public class DateWeatherForecastJsonParser extends AbstractWeatherJsonParser<List<DateWeatherForecast>> {


    @Override
    public List<DateWeatherForecast> parse(String parseParam) throws JSONException, ParseException {
        return null;
    }
}
