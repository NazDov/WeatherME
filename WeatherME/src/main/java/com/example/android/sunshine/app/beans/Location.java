package com.example.android.sunshine.app.beans;

import java.io.Serializable;

/**
 * Created by User on 05.11.2016.
 */
public class Location implements Serializable {
    private String countryName;
    private String city;
    private String latitude;
    private String longitude;

    public Location(){

    }

    public Location(String countryName, String city, String latitude, String longitude){
        this.countryName = countryName;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
