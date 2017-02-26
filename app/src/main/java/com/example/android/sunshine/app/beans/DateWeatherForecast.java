package com.example.android.sunshine.app.beans;

import java.io.Serializable;

/**
 * Created by User on 14.11.2016.
 */
public class DateWeatherForecast implements Serializable {

    private String date;
    private String temp;
    private String description;

    public DateWeatherForecast() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateWeatherForecast)) return false;

        DateWeatherForecast that = (DateWeatherForecast) o;

        if (!date.equals(that.date)) return false;
        if (!temp.equals(that.temp)) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + temp.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
