package com.example.android.sunshine.app.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.service.GeoLocationService;
import com.example.android.sunshine.app.service.Location;
import com.example.android.sunshine.app.utility.ApplicationData;
import com.example.android.sunshine.app.activity.WeatherActivity;
import com.example.android.sunshine.app.utility.parser.impl.ResultWeatherStringJsonParser;
import com.example.android.sunshine.app.utility.WeatherJsonUtility;
import com.example.android.sunshine.app.service.WeatherDataConnector;

import org.json.JSONException;

import java.text.ParseException;

/**
 * Created by User on 05.11.2016.
 */
public class WeatherLocationRequestAsyncTask extends AsyncTask<GeoLocationService, Void, String> {

    private WeatherDataConnector weatherDataConnector;
    private Context context;
    private static String LOG_TAG = "WeatherLocationRequestAsyncTask";
    private WeatherActivity.WeatherFragment weatherFragment;


    public WeatherLocationRequestAsyncTask(WeatherDataConnector weatherDataConnector,
                                           WeatherActivity.WeatherFragment weatherFragment) {
        this.weatherDataConnector = weatherDataConnector;
        this.weatherFragment = weatherFragment;

    }

    @Override
    protected void onPreExecute() {
        this.weatherFragment.getSpinner().setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(GeoLocationService... locationServices) {
        GeoLocationService geoLocationService = locationServices[0];
        Location location = geoLocationService.getLocation();
        return weatherDataConnector.httpGetWeatherDataJSON(location.getCity());
    }


    @Override
    protected void onPostExecute(String response) {
        weatherFragment.getSpinner().setVisibility(View.GONE);

        if (response == null) {
            return;
        }

        if (response == ApplicationData.WRONG_CONNECTION_URL) {
            Toast.makeText(context, ApplicationData.WRONG_CONNECTION_URL, Toast.LENGTH_LONG).show();
            return;
        }

        if (response == ApplicationData.DATA_PARSE_ERROR) {
            Toast.makeText(context, ApplicationData.DATA_PARSE_ERROR, Toast.LENGTH_LONG).show();
            return;
        }

        if (response.length() > 0) {
            Log.i(LOG_TAG, response);
            String[] weatherData;
            try {
                weatherData = WeatherJsonUtility.parse(response, new ResultWeatherStringJsonParser());
                context = ApplicationData.getContext();
                populateWeatherDataIntoListView(weatherData);
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }


    }

    private void populateWeatherDataIntoListView(String[] weatherData) {
        ListView listView = (ListView) weatherFragment.getRootView().findViewById(R.id.listview_forecast);
        listView.setAdapter(getArrayAdapter(weatherData));
        /**
         * register onclick listener that will process click of a single list element
         */
        registerOnclickListener(listView);
    }

    @NonNull
    private ArrayAdapter<String> getArrayAdapter(String[] forecastData) {
        return new ArrayAdapter<>(
                context,
                R.layout.listitem_forecats,
                R.id.list_item_forecast_text,
                forecastData);
    }

    private void registerOnclickListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedWeatherElem = (String) parent.getItemAtPosition(position);
                Toast.makeText(context, clickedWeatherElem, Toast.LENGTH_LONG).show();
            }
        });
    }


}
