package com.example.android.sunshine.app;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by nazar.dovhyi on 20.11.2016.
 */
public final class WeatherServerProperty {

    private final Properties properties;

    public WeatherServerProperty(Context context) {
        AssetManager am = context.getAssets();
        properties = new Properties();
        try {
            properties.load(am.open("data_connection.properties"));
        } catch (IOException e) {
            throw new RuntimeException("error loading data_connection.properties file");
        }

    }

    public String getAttribute(String key) {
        return properties.getProperty(key);
    }

}
