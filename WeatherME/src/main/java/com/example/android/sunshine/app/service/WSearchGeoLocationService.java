package com.example.android.sunshine.app.service;

import com.example.android.sunshine.app.beans.Location;

/**
 * Created by nazar.dovhuy on 12.03.2017.
 */
public class WSearchGeoLocationService extends WGeoLocationService {

    private String searchName;

    public WSearchGeoLocationService(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public Location getGeoLocation() {
        Location location = new Location();
        location.setCity(searchName);
        return location;
    }
}
