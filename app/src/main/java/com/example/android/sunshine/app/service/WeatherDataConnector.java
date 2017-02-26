package com.example.android.sunshine.app.service;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.sunshine.app.WeatherServerProperty;
import com.example.android.sunshine.app.utility.ApplicationData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nazar.dovhyi on 05.11.2016.
 */
public class WeatherDataConnector {
    private static final String LOG_TAG = WeatherDataConnector.class.getSimpleName();
    private WeatherServerProperty weatherServerProperty;
    private HttpURLConnection urlConnection;

    public WeatherDataConnector(Context context) {
        weatherServerProperty = new WeatherServerProperty(context);
    }



    public String httpGetWeatherDataJSON(String param) {
        String resultWeatherData = null;
        InputStream responseInputStream = null;
        try {
            responseInputStream = getServerWeatherInfoByParam(param);
            if (responseInputStream == null) {
                return null;
            }
            resultWeatherData = parseResultWeatherData(responseInputStream);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, ApplicationData.WRONG_CONNECTION_URL);
            return ApplicationData.WRONG_CONNECTION_URL;
        } catch (IOException e) {
            Log.e(LOG_TAG, ApplicationData.DATA_PARSE_ERROR);
            return ApplicationData.DATA_PARSE_ERROR;
        } finally {
            closeConnections(responseInputStream);
        }
        return resultWeatherData;
    }

    private void closeConnections(InputStream responseInputStream) {
        if (responseInputStream != null)
            try {
                responseInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (urlConnection != null)
            urlConnection.disconnect();
    }

    @NonNull
    private String parseResultWeatherData(InputStream responseInputStream) throws IOException {
        String resultWeatherData;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseInputStream));
        String line;
        StringBuilder buffer = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            buffer.append(line).append("\n");
        }
        resultWeatherData = buffer.toString();
        return resultWeatherData;
    }

    private InputStream getServerWeatherInfoByParam(String param) throws IOException {
        InputStream responseInputStream;
        URL url;
        url = new URL(buildUri(param).toString());
        Log.i(LOG_TAG, url.toString());
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        responseInputStream = urlConnection.getInputStream();
        return responseInputStream;
    }

    private Uri buildUri(String param) {
        return Uri.parse(weatherServerProperty.getAttribute("REQUEST_URI")).buildUpon()
                .appendQueryParameter(weatherServerProperty.getAttribute("CITY_PARAM"), param)
                .appendQueryParameter(weatherServerProperty.getAttribute("MODE_PARAM"),
                        weatherServerProperty.getAttribute("MODE_VAL"))
                .appendQueryParameter(weatherServerProperty.getAttribute("UNIT_PARAM"),
                        weatherServerProperty.getAttribute("UNIT_VAL"))
                .appendQueryParameter(weatherServerProperty.getAttribute("API_KEY_PARAM"),
                        weatherServerProperty.getAttribute("API_KEY_VAL"))
                .appendQueryParameter(weatherServerProperty.getAttribute("DAY_NUM_PARAM"),
                        weatherServerProperty.getAttribute("DAY_NUM_VAL"))
                .build();
    }

}
