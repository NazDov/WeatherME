package com.example.android.sunshine.app.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.sunshine.app.service.GeoLocationService;
import com.example.android.sunshine.app.beans.Location;
import com.example.android.sunshine.app.utility.ApplicationContext;
import com.example.android.sunshine.app.activity.WeatherActivity;
import com.example.android.sunshine.app.service.WeatherDataService;

/**
 * Created by nazardovhuy on 05.11.2016.
 */
public class WeatherLocationRequestAsyncTask extends AsyncTask<GeoLocationService, Void, String> {

    private static String LOG_TAG = WeatherLocationRequestAsyncTask.class.getSimpleName();
    private WeatherDataService weatherDataService;
    private WeatherActivity.WeatherFragment weatherFragment;


    public WeatherLocationRequestAsyncTask(WeatherDataService weatherDataService,
                                           WeatherActivity.WeatherFragment weatherFragment) {
        this.weatherDataService = weatherDataService;
        this.weatherFragment = weatherFragment;

    }

    @Override
    protected void onPreExecute() {
        this.weatherFragment.getSpinner().setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(GeoLocationService... locationServices) {
        return weatherDataService.httpGetWeatherDataJSON(getGeoLocation(locationServices[0]).getCity());
    }

    private Location getGeoLocation(GeoLocationService geoLocationService) {

        if (geoLocationService == null) {
            throw new IllegalStateException("GeoLocationService is undefined");
        }

        return geoLocationService.getGeoLocation();
    }


    @Override
    protected void onPostExecute(String response) {
        weatherFragment.getSpinner().setVisibility(View.GONE);
        switch (response) {
            case ApplicationContext.RESPONSE_OK:
                Log.i(LOG_TAG, response);
                weatherFragment.processWeatherDataResponse(ApplicationContext.getResultWeatherData());
                break;
            case ApplicationContext.WRONG_CONNECTION_URL:
                Toast.makeText(ApplicationContext.getContext(), ApplicationContext.WRONG_CONNECTION_URL, Toast.LENGTH_LONG).show();
                return;
            case ApplicationContext.DATA_PARSE_ERROR:
                Toast.makeText(ApplicationContext.getContext(), ApplicationContext.DATA_PARSE_ERROR, Toast.LENGTH_LONG).show();
                return;
            case ApplicationContext.NO_SERVER_RESPONSE:
                Toast.makeText(ApplicationContext.getContext(), ApplicationContext.NO_SERVER_RESPONSE, Toast.LENGTH_LONG).show();
        }
    }


}
