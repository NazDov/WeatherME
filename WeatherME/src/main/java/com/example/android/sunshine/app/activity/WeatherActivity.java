package com.example.android.sunshine.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.widget.*;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.asynctask.WeatherDataRequestAsyncTask;
import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.service.WDataRequestConnectionService;
import com.example.android.sunshine.app.service.WGeoLocationService;
import com.example.android.sunshine.app.service.OpenWeatherMapWeatherDataServiceURLBuilder;
import com.example.android.sunshine.app.service.WSearchGeoLocationService;
import com.example.android.sunshine.app.service.WeatherDataServiceURLBuilder;
import com.example.android.sunshine.app.utility.WApplicationContext;
import com.example.android.sunshine.app.service.OpenWeatherMapWDataRequestConnectionService;
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
        WApplicationContext.setContext(this);
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
        private TextView cityNameTextView;
        private TextView countryNameTextView;
        private SearchView searchView;
        private Button _5dayWeatherBtn;
        private Button _10dayWeatherButton;
        private Button __15dayWeatherButton;
        private SharedPreferences settings;

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
            WApplicationContext.updateWAppLang();
            loadDateWeatherStats();
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            WApplicationContext.updateWAppLang();
        }

        private void init(LayoutInflater inflater, ViewGroup container) {
            settings = WApplicationContext.getSettings();

            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            searchView = (SearchView) rootView.findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getActivity(), "search text " + query, Toast.LENGTH_LONG).show();
                    if (query != null && query.trim().length() > 0) {
                        if (isNetworkConnectionEstablished()) {
                            settings.edit().putString("search_query", query).apply();
                            requestWeatherDataBySearchParam(new OpenWeatherMapWDataRequestConnectionService(getActivity()),
                                    new WSearchGeoLocationService(query));
                        } else {
                            showDialog(R.string.dialog_app_title_err_exit,
                                    R.string.dialog_no_internet_message);
                        }
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            cityNameTextView = (TextView) rootView.findViewById(R.id.cityDescrNamText);
            countryNameTextView = (TextView) rootView.findViewById(R.id.countryDescrNameText);

            View buttonView = rootView.findViewById(R.id.selectionTabs);
            _5dayWeatherBtn = (Button) buttonView.findViewById(R.id.day5btn);
            _10dayWeatherButton = (Button) buttonView.findViewById(R.id.day10btn);
            __15dayWeatherButton = (Button) buttonView.findViewById(R.id.day15btn);
            _5dayWeatherBtn.setOnClickListener(this);
            _10dayWeatherButton.setOnClickListener(this);
            __15dayWeatherButton.setOnClickListener(this);
        }

        private void loadDateWeatherStats() {
            if (isNetworkConnectionEstablished()) {
                requestWeatherData(new OpenWeatherMapWDataRequestConnectionService(getActivity()));
            } else {
                showDialog(R.string.dialog_app_title_err_exit,
                        R.string.dialog_no_internet_message);
            }
        }

        public final void showDialog(int titleId, int textMessageId) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setCancelable(false);
            dialog.setTitle(titleId);
            TextView dialogTextView = (TextView) dialog.findViewById(R.id.customDialogText);
            dialogTextView.setText(textMessageId);
            Button okBtn = (Button) dialog.findViewById(R.id.customDialogOk);
            Button cnxlBtn = (Button) dialog.findViewById(R.id.customDialogCancel);
            cnxlBtn.setVisibility(View.INVISIBLE);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    getActivity().finish();
                }
            });
            dialog.show();

        }


        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.forecastfragment_menu, menu);
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings_menu_item:
                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
                    startActivity(intent);
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
                populateLocationInfo(weatherData);
                weatherData.remove(0);
                populateWeatherDataIntoListView(weatherData);
            } catch (JSONException | ParseException e) {
                throw new RuntimeException(e);
            }


        }

        private void populateLocationInfo(List<DateWeatherForecast> weatherData) {
            DateWeatherForecast locationDateWeatherForecast = weatherData.get(0);
            if (locationDateWeatherForecast != null) {
                cityNameTextView.setText(locationDateWeatherForecast.getCity());
                countryNameTextView.setText(locationDateWeatherForecast.getCountry());
            }
        }

        private void populateWeatherDataIntoListView(List<DateWeatherForecast> weatherData) {
            ListView listView = (ListView) getRootView().findViewById(R.id.listview_forecast);
            listView.setAdapter(getArrayAdapter(weatherData));
            registerOnclickListener(listView);
        }

        @NonNull
        private ArrayAdapter<DateWeatherForecast> getArrayAdapter(List<DateWeatherForecast> forecastData) {
            return new WeatherArrayAdapter(WApplicationContext.getContext(), R.layout.listitem_dateweather, forecastData);
        }

        private void registerOnclickListener(ListView listView) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (!isNetworkConnectionEstablished()) {
                        showDialog(R.string.dialog_app_title_err_exit,
                                R.string.dialog_no_internet_message);
                    }

                    DateWeatherForecast selectedDateWeatherForecastElem = (DateWeatherForecast) parent.getItemAtPosition(position);
                    Toast.makeText(WApplicationContext.getContext(), selectedDateWeatherForecastElem.getDate(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public View getRootView() {
            return rootView;
        }

        @Override
        public void onClick(View v) {

            if (!isNetworkConnectionEstablished()) {
                showDialog(R.string.dialog_app_title_err_exit,
                        R.string.dialog_no_internet_message);
                return;
            }
            WeatherDataServiceURLBuilder weatherDataServiceURLBuilder;
            switch (v.getId()) {

                case R.id.day5btn:
                    Toast.makeText(getActivity(), "5 days weather", Toast.LENGTH_LONG).show();
                    weatherDataServiceURLBuilder = new OpenWeatherMapWeatherDataServiceURLBuilder() {
                        @Override
                        protected String getWeatherForDays() {
                            return WApplicationContext.W_5_DAYS;
                        }
                    };
                    requestWeatherData(new OpenWeatherMapWDataRequestConnectionService(getActivity(), weatherDataServiceURLBuilder));

                    break;

                case R.id.day10btn:
                    Toast.makeText(getActivity(), "10 days weather", Toast.LENGTH_LONG).show();
                    weatherDataServiceURLBuilder = new OpenWeatherMapWeatherDataServiceURLBuilder() {
                        @Override
                        protected String getWeatherForDays() {
                            return WApplicationContext.W_10_DAYS;
                        }
                    };
                    requestWeatherData(new OpenWeatherMapWDataRequestConnectionService(getActivity(), weatherDataServiceURLBuilder));
                    break;

                case R.id.day15btn:
                    Toast.makeText(getActivity(), "15 days weather", Toast.LENGTH_LONG).show();
                    weatherDataServiceURLBuilder = new OpenWeatherMapWeatherDataServiceURLBuilder() {
                        @Override
                        protected String getWeatherForDays() {
                            return WApplicationContext.W_15_DAYS;
                        }
                    };
                    requestWeatherData(new OpenWeatherMapWDataRequestConnectionService(getActivity(), weatherDataServiceURLBuilder));
                    break;
            }


        }

        private void requestWeatherData(final WDataRequestConnectionService WDataRequestConnectionService) {
            WGeoLocationService WGeoLocationService = new WGeoLocationService();
            WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(WDataRequestConnectionService, WeatherFragment.this);
            weatherDataRequestAsyncTask.execute(WGeoLocationService);
        }

        private void requestWeatherDataBySearchParam(final WDataRequestConnectionService wDataRequestConnectionService,
                                                     final WGeoLocationService wGeoLocationService) {
            WeatherDataRequestAsyncTask weatherDataRequestAsyncTask = new WeatherDataRequestAsyncTask(wDataRequestConnectionService, WeatherFragment.this);
            weatherDataRequestAsyncTask.execute(wGeoLocationService);
        }


    }
}
