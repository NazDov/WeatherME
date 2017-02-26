package com.example.android.sunshine.app.activity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.asynctask.WeatherLocationRequestAsyncTask;
import com.example.android.sunshine.app.service.GeoLocationService;
import com.example.android.sunshine.app.utility.ApplicationData;
import com.example.android.sunshine.app.service.WeatherDataConnector;

import java.util.logging.Logger;


public class WeatherActivity extends ActionBarActivity {

    private static Logger logger = Logger.getLogger("WeatherActivity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherFragment weatherFragment = new WeatherFragment();
        ApplicationData.setContext(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, weatherFragment)
                    .commit();
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class WeatherFragment extends Fragment {

        private View rootView;
        private ProgressBar spinner;


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
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            spinner = (ProgressBar) rootView.findViewById(R.id.progressBar1);
            if (isNetworkConnectionEstablished()) {
                getWeatherByLocationData();
            } else {
                Toast.makeText(getActivity(), "Please make sure that your network connection is enabled",
                        Toast.LENGTH_LONG).show();
            }
            return rootView;
        }

        private void getWeatherByLocationData() {
            final GeoLocationService geoLocationService = new GeoLocationService();
            final WeatherLocationRequestAsyncTask weatherLocationRequestAsyncTask =
                    new WeatherLocationRequestAsyncTask(new WeatherDataConnector(getActivity()), WeatherFragment.this);
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
                        getWeatherByLocationData();
                    return true;

            }

            return super.onOptionsItemSelected(item);
        }

        public ProgressBar getSpinner() {
            return spinner;
        }

        public View getRootView() {
            return rootView;
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

//        private void configureListView(View view) {
//
//        }

    }
}
