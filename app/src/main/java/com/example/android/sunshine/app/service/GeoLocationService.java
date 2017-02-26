package com.example.android.sunshine.app.service;

import com.example.android.sunshine.app.utility.ApplicationData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by nazardovhuy on 05.11.2016.
 */
public class GeoLocationService {


    public GeoLocationService() {

    }


    public Location getLocation() {
        String geoLocationJSON = null;
        try {
            geoLocationJSON = getLocation(getIPAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONObject jsonObject = new JSONObject(geoLocationJSON);
            Location myLocation = new Location();

            if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                myLocation.setCountryName(jsonObject.getString("country"));
                myLocation.setCity(jsonObject.getString("city"));
                myLocation.setLatitude(jsonObject.getString("lat"));
                myLocation.setLongitude(jsonObject.getString("lon"));
            } else {
                throw new GeoLocationErrorStatusException("no response");
            }

            return myLocation;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getLocation(String ipAddress) {

        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        String locationString = null;

        try {
            URL geoLocationUrl = new URL("http://ip-api.com/json/" + ipAddress);
            httpURLConnection = (HttpURLConnection) geoLocationUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            is = httpURLConnection.getInputStream();

            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = bf.readLine()) != null) {
                sb.append(line).append("\n");
            }

            locationString = sb.toString();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return locationString;
    }


    protected String getIPAddress() throws UnknownHostException {

        URL whatismyip = null;
        String ip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            ip = in.readLine();
            return ip;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private class GeoLocationErrorStatusException extends RuntimeException {
        public GeoLocationErrorStatusException(String errorMessage) {
            super(errorMessage);
        }
    }
}
