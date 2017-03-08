package com.example.android.sunshine.app.beans;

import java.io.Serializable;

/**
 * Created by nazar.dovhuy on 14.11.2016.
 */
public class DateWeatherForecast implements Serializable {

    private String date;
    private String temp;
    private String maxTemp;
    private String minTemp;
    private String dayTemp;
    private String nightTemp;
    private String mornTemp;
    private String description;
    private String weatherIconId;

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

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (temp != null ? !temp.equals(that.temp) : that.temp != null) return false;
        if (maxTemp != null ? !maxTemp.equals(that.maxTemp) : that.maxTemp != null) return false;
        if (minTemp != null ? !minTemp.equals(that.minTemp) : that.minTemp != null) return false;
        if (dayTemp != null ? !dayTemp.equals(that.dayTemp) : that.dayTemp != null) return false;
        if (nightTemp != null ? !nightTemp.equals(that.nightTemp) : that.nightTemp != null)
            return false;
        if (mornTemp != null ? !mornTemp.equals(that.mornTemp) : that.mornTemp != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        return !(weatherIconId != null ? !weatherIconId.equals(that.weatherIconId) : that.weatherIconId != null);

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (temp != null ? temp.hashCode() : 0);
        result = 31 * result + (maxTemp != null ? maxTemp.hashCode() : 0);
        result = 31 * result + (minTemp != null ? minTemp.hashCode() : 0);
        result = 31 * result + (dayTemp != null ? dayTemp.hashCode() : 0);
        result = 31 * result + (nightTemp != null ? nightTemp.hashCode() : 0);
        result = 31 * result + (mornTemp != null ? mornTemp.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (weatherIconId != null ? weatherIconId.hashCode() : 0);
        return result;
    }

    public String getMornTemp() {
        return mornTemp;
    }

    public void setMornTemp(String mornTemp) {
        this.mornTemp = mornTemp;
    }

    public String getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(String nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(String dayTemp) {
        this.dayTemp = dayTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setWeatherIconId(String weatherIconId) {
        this.weatherIconId = weatherIconId;
    }

    public String getWeatherIconId() {
        return weatherIconId;
    }
}
