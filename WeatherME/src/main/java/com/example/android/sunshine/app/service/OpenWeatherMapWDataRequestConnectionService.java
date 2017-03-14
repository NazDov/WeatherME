package com.example.android.sunshine.app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.sunshine.app.WeatherServerProperty;
import com.example.android.sunshine.app.utility.WApplicationContext;

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
public class OpenWeatherMapWDataRequestConnectionService implements WDataRequestConnectionService {
    private static final String LOG_TAG = OpenWeatherMapWDataRequestConnectionService.class.getSimpleName();
    private WeatherServerProperty weatherServerProperty;
    private HttpURLConnection urlConnection;
    private WeatherDataServiceURLBuilder weatherDataServiceUrl;

    public OpenWeatherMapWDataRequestConnectionService(Context context) {
        weatherServerProperty = new WeatherServerProperty(context);
    }


    public OpenWeatherMapWDataRequestConnectionService(Context context, WeatherDataServiceURLBuilder weatherDataServiceUrl) {
        this(context);
        this.weatherDataServiceUrl = weatherDataServiceUrl;

    }


    @Override
    public String httpGetWeatherDataJSON(String param) {

        SharedPreferences settings = WApplicationContext.getSettings();
        String searchParam = settings.getString("search_query", null);
        if (searchParam != null) {
            param = searchParam;
        }

        String resultWeatherData;
        InputStream responseInputStream = null;
        try {
            responseInputStream = getServerWeatherInfoByParam(param);
            resultWeatherData = parseResultWeatherData(responseInputStream);
            if (resultWeatherData.trim().length() == 0) {
                return WApplicationContext.DATA_NOT_FOUND;
            }
            WApplicationContext.setResultWeatherData(resultWeatherData);
            return WApplicationContext.RESPONSE_OK;
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, WApplicationContext.WRONG_CONNECTION_URL);
            return WApplicationContext.WRONG_CONNECTION_URL;
        } catch (IOException e) {
            Log.e(LOG_TAG, WApplicationContext.DATA_PARSE_ERROR);
            return WApplicationContext.DATA_PARSE_ERROR;
        } catch (ServerWeatherNoResponseException e) {
            Log.e(LOG_TAG, WApplicationContext.NO_SERVER_RESPONSE);
            return WApplicationContext.NO_SERVER_RESPONSE;
        } finally {
            closeConnections(responseInputStream);
        }
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

    private InputStream getServerWeatherInfoByParam(String param) throws MalformedURLException,
            ServerWeatherNoResponseException {
        InputStream responseInputStream;
        URL url;
        try {
            if (weatherDataServiceUrl == null) {
                url = new URL(buildWeatherDataServiceUrl(param).toString());
                Log.i(LOG_TAG, url.toString());
            } else {
                url = new URL(weatherDataServiceUrl.buildWeatherDataServiceUrl(param).toString());
            }
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(30000);
            urlConnection.connect();
            responseInputStream = urlConnection.getInputStream();
            return responseInputStream;
        } catch (IOException e) {
            throw new ServerWeatherNoResponseException(e.getMessage());
        }
    }

    private Uri buildWeatherDataServiceUrl(String param) {
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

    private class ServerWeatherNoResponseException extends Exception {
        public ServerWeatherNoResponseException(String message) {
            super(message);
        }
    }
}
