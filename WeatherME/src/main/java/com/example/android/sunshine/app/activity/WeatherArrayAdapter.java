package com.example.android.sunshine.app.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.beans.DateWeatherForecast;
import com.example.android.sunshine.app.utility.ApplicationContext;

import java.util.List;

/**
 * Created by nazar.dovhyy on 08.03.2017.
 */
public class WeatherArrayAdapter extends ArrayAdapter<DateWeatherForecast> {

    private Context context;
    private int resource;
    private List<DateWeatherForecast> dateWeatherForecasts;

    public WeatherArrayAdapter(Context context, int resource, List<DateWeatherForecast> dateWeatherForecasts) {
        super(context, resource, dateWeatherForecasts);
        this.context = context;
        this.resource = resource;
        this.dateWeatherForecasts = dateWeatherForecasts;

    }

    @Override
    public int getCount() {
        return dateWeatherForecasts.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) this.context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(resource, parent, false);
        DateWeatherForecast dateWeatherForecast = dateWeatherForecasts.get(position);
        //mark current day weather
        if (ApplicationContext.getCurrentDate().equalsIgnoreCase(dateWeatherForecast.getDate())) {
            row.setBackgroundColor(Color.CYAN);
        }
        ImageView weatherIcon = (ImageView) row.findViewById(R.id.weatherIcon);
        TextView weatherDate = (TextView) row.findViewById(R.id.weatherDate);
        TextView weatherDescr = (TextView) row.findViewById(R.id.weatherDescription);
        TextView weatherTemp = (TextView) row.findViewById(R.id.weatherTemp);
        weatherIcon.setImageResource(getWeatherIconResource(dateWeatherForecast.getWeatherIconId()));
        weatherDescr.setText(dateWeatherForecast.getDescription());
        weatherDate.setText(dateWeatherForecast.getDate());
        weatherTemp.setText(dateWeatherForecast.getTemp());
        return row;
    }

    private int getWeatherIconResource(String weatherIconId) {
        int iconResource;
        switch (weatherIconId) {
            case "01d":
                iconResource = R.mipmap.app_01d;
                break;
            case "01n":
                iconResource = R.mipmap.app_01n;
                break;
            case "02n":
                iconResource = R.mipmap.app_02n;
                break;
            case "02d":
                iconResource = R.mipmap.app_02d;
                break;
            case "03d":
                iconResource = R.mipmap.app_03d;
                break;
            case "03n":
                iconResource = R.mipmap.app_03n;
                break;
            case "04d":
                iconResource = R.mipmap.app_04d;
                break;
            case "04n":
                iconResource = R.mipmap.app_04n;
                break;
            case "09d":
                iconResource = R.mipmap.app_09d;
                break;
            case "09n":
                iconResource = R.mipmap.app_09n;
                break;
            case "10n":
                iconResource = R.mipmap.app_10n;
                break;
            case "10d":
                iconResource = R.mipmap.app_10d;
                break;
            case "11d":
                iconResource = R.mipmap.app_11d;
                break;
            case "11n":
                iconResource = R.mipmap.app_11n;
                break;
            case "13d":
                iconResource = R.mipmap.app_13d;
                break;
            case "13n":
                iconResource = R.mipmap.app_13n;
                break;
            case "50d":
                iconResource = R.mipmap.app_50d;
                break;
            case "50n":
                iconResource = R.mipmap.app_50n;
                break;
            default:
                iconResource = R.mipmap.app_02n;

        }

        return iconResource;
    }
}
