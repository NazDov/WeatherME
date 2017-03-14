package com.example.android.sunshine.app.service;

import android.net.Uri;

/**
 * Created by User on 08.03.2017.
 */
public interface WeatherDataServiceURLBuilder {
    Uri buildWeatherDataServiceUrl(String param);
}
