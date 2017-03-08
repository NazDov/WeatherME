package com.example.android.sunshine.app.utility;

import com.example.android.sunshine.app.utility.parser.AbstractWeatherJsonParser;

import org.json.JSONException;

import java.text.ParseException;
import java.util.List;

/**
 * Created by User on 12.11.2016.
 */
public class WeatherJsonUtility {

    private WeatherJsonUtility() {

    }

//    public static <T> T[] parse(String weatherDataJson, AbstractWeatherJsonParser<T[]> parser)
//            throws JSONException, ParseException {
//        return parser.parse(weatherDataJson);
//    }

    public static <T> List<T> parse(String weatherDataJson, AbstractWeatherJsonParser<List<T>> parser)
            throws JSONException, ParseException {
        return parser.parse(weatherDataJson);
    }


}
