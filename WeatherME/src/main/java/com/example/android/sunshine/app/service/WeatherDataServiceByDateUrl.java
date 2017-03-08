package com.example.android.sunshine.app.service;

import android.net.Uri;

import com.example.android.sunshine.app.WeatherServerProperty;
import com.example.android.sunshine.app.utility.ApplicationContext;

/**
 * Created by User on 08.03.2017.
 */
public class WeatherDataServiceByDateUrl {

    private WeatherServerProperty weatherServerProperty;
    private String dayNumVal;

    public WeatherDataServiceByDateUrl(String dayNum) {
        weatherServerProperty = new WeatherServerProperty(ApplicationContext.getContext());
        this.dayNumVal = dayNum;
    }


    public Uri buildWeatherDataServiceUrl(String param) {
        return Uri.parse(weatherServerProperty.getAttribute("REQUEST_URI")).buildUpon()
                .appendQueryParameter(weatherServerProperty.getAttribute("CITY_PARAM"), param)
                .appendQueryParameter(weatherServerProperty.getAttribute("MODE_PARAM"),
                        weatherServerProperty.getAttribute("MODE_VAL"))
                .appendQueryParameter(weatherServerProperty.getAttribute("UNIT_PARAM"),
                        weatherServerProperty.getAttribute("UNIT_VAL"))
                .appendQueryParameter(weatherServerProperty.getAttribute("API_KEY_PARAM"),
                        weatherServerProperty.getAttribute("API_KEY_VAL"))
                .appendQueryParameter(weatherServerProperty.getAttribute("DAY_NUM_PARAM"),
                        dayNumVal)
                .build();
    }
}
