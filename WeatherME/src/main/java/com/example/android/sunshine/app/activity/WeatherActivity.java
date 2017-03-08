package com.example.android.sunshine.app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.asynctask.WeatherLocationRequestAsyncTask;
import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.service.GeoLocationService;
import com.example.android.sunshine.app.service.WeatherDataServiceByDateUrl;
import com.example.android.sunshine.app.utility.ApplicationContext;
import com.example.android.sunshine.app.service.WeatherDataService;
import com.example.android.sunshine.app.utility.WeatherJsonUtility;
import com.example.android.sunshine.app.utility.parser.impl.DateWeatherForecastJsonParser;

import org.json.JSONException;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;


public class WeatherActivity extends AppCompatActivity {

    private static Logger logger = Logger.getLogger("WeatherActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherFragment weatherFragment = new WeatherFragment();
        ApplicationContext.setContext(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, weatherFragment)
                    .commit();
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class WeatherFragment extends Fragment implements View.OnClickListener {

        private View rootView;
        private ProgressBar spinner;
        private Button _5dayWeatherBtn;
        private Button _10dayWeatherButton;
        private Button __15dayWeatherButton;

        public WeatherFragment() {
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            init(inflater, container);
            loadDateWeatherStats();
            return rootView;
        }

        private void init(LayoutInflater inflater, ViewGroup container) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            View buttonView = rootView.findViewById(R.id.selectionTabs);
            _5dayWeatherBtn = (Button) buttonView.findViewById(R.id.day5btn);
            _10dayWeatherButton = (Button) buttonView.findViewById(R.id.day10btn);
            __15dayWeatherButton = (Button) buttonView.findViewById(R.id.day15btn);
            _5dayWeatherBtn.setOnClickListener(this);
            _10dayWeatherButton.setOnClickListener(this);
            __15dayWeatherButton.setOnClickListener(this);
            spinner = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        }

        private void loadDateWeatherStats() {
            if (isNetworkConnectionEstablished()) {
                loadDefaultDateWeatherForecastList();
            } else {
                showNoActiveNetDialogErr();
            }
        }

        private void showNoActiveNetDialogErr() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle(R.string.dialog_app_title_exit)
                    .setMessage(R.string.dialog_no_internet_message)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });
            dialogBuilder.create().show();
        }

        private void loadDefaultDateWeatherForecastList() {
            final GeoLocationService geoLocationService = new GeoLocationService();
            final WeatherLocationRequestAsyncTask weatherLocationRequestAsyncTask =
                    new WeatherLocationRequestAsyncTask(new WeatherDataService(getActivity()), WeatherFragment.this);
            weatherLocationRequestAsyncTask.execute(geoLocationService);
        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.forecastfragment_menu, menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.refresh_btn:
                    if (isNetworkConnectionEstablished())
                        loadDefaultDateWeatherForecastList();
                    return true;

            }

            return super.onOptionsItemSelected(item);
        }


        private boolean isNetworkConnectionEstablished() {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getActivity().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                logger.info("network connection established");
                Toast.makeText(getActivity(), "network connection established", Toast.LENGTH_LONG).show();
                return true;
            } else {
                logger.warning("no network connection available");
                Toast.makeText(getActivity(), "no network connection available", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        public void processWeatherDataResponse(Object response) {
            List<DateWeatherForecast> weatherData;
            try {
                weatherData = WeatherJsonUtility.parse((String) response, new DateWeatherForecastJsonParser(getActivity()));
                populateWeatherDataIntoListView(weatherData);
            } catch (JSONException | ParseException e) {
                throw new RuntimeException(e);
            }


        }

        private void populateWeatherDataIntoListView(List<DateWeatherForecast> weatherData) {
            ListView listView = (ListView) getRootView().findViewById(R.id.listview_forecast);
            listView.setAdapter(getArrayAdapter(weatherData));
            /**
             * register onclick listener that will process click of a single list element
             */
            registerOnclickListener(listView);
        }

        @NonNull
        private ArrayAdapter<DateWeatherForecast> getArrayAdapter(List<DateWeatherForecast> forecastData) {
            return new WeatherArrayAdapter(ApplicationContext.getContext(), R.layout.listitem_dateweather, forecastData);
        }

        private void registerOnclickListener(ListView listView) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DateWeatherForecast selectedDateWeatherForecastElem = (DateWeatherForecast) parent.getItemAtPosition(position);
                    Toast.makeText(ApplicationContext.getContext(), selectedDateWeatherForecastElem.getDate(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public ProgressBar getSpinner() {
            return spinner;
        }

        public View getRootView() {
            return rootView;
        }

        @Override
        public void onClick(View v) {

            if (!isNetworkConnectionEstablished()) {
                showNoActiveNetDialogErr();
                return;
            }
            WeatherDataServiceByDateUrl weatherDataServiceByDateUrl;
            switch (v.getId()) {

                case R.id.day5btn:
                    Toast.makeText(getActivity(), "5 days weather", Toast.LENGTH_LONG).show();
                    weatherDataServiceByDateUrl = new WeatherDataServiceByDateUrl("5");
                    loadDateSpecificDateWeatherForecastList(new WeatherDataService(getActivity(), weatherDataServiceByDateUrl));

                    break;

                case R.id.day10btn:
                    Toast.makeText(getActivity(), "10 days weather", Toast.LENGTH_LONG).show();
                    weatherDataServiceByDateUrl = new WeatherDataServiceByDateUrl("10");
                    loadDateSpecificDateWeatherForecastList(new WeatherDataService(getActivity(), weatherDataServiceByDateUrl));
                    break;

                case R.id.day15btn:
                    Toast.makeText(getActivity(), "15 days weather", Toast.LENGTH_LONG).show();
                    weatherDataServiceByDateUrl = new WeatherDataServiceByDateUrl("15");
                    loadDateSpecificDateWeatherForecastList(new WeatherDataService(getActivity(), weatherDataServiceByDateUrl));
                    break;
            }


        }

        private void loadDateSpecificDateWeatherForecastList(WeatherDataService weatherDataService) {
            final GeoLocationService geoLocationService = new GeoLocationService();
            final WeatherLocationRequestAsyncTask weatherLocationRequestAsyncTask =
                    new WeatherLocationRequestAsyncTask(weatherDataService, WeatherFragment.this);
            weatherLocationRequestAsyncTask.execute(geoLocationService);
        }
    }
}
