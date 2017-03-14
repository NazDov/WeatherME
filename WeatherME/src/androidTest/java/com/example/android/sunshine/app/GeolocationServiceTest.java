package com.example.android.sunshine.app;

import com.example.android.sunshine.app.service.WGeoLocationService;
import com.example.android.sunshine.app.beans.Location;

import junit.framework.TestCase;

import java.net.UnknownHostException;

/**
 * Created by User on 15.01.2017.
 */
public class GeolocationServiceTest extends TestCase {


    private WGeoLocationService successGeolocationServiceW = new WGeoLocationService() {

        @Override
        protected String getGeoLocation(String ipAddress) {
            return "{\n" +
                    "\t\"status\" : \"success\",\n" +
                    "\t\"statusMessage\" : \"Invalid API key.\",\n" +
                    "\t\"ipAddress\" : \"95.69.208.207\",\n" +
                    "\t\"countryCode\" : \"\",\n" +
                    "\t\"country\" : \"Ukraine\",\n" +
                    "\t\"regionName\" : \"\",\n" +
                    "\t\"city\" : \"Lviv\",\n" +
                    "\t\"zipCode\" : \"\",\n" +
                    "\t\"lat\" : \"0\",\n" +
                    "\t\"lon\" : \"0\",\n" +
                    "\t\"timeZone\" : \"\"\n" +
                    "}";
        }

    };

    private WGeoLocationService errorGeolocationServiceW = new WGeoLocationService() {

        @Override
        protected String getGeoLocation(String ipAddress) {
            return "{\n" +
                    "\t\"status\" : \"error\",\n" +
                    "\t\"statusMessage\" : \"Invalid API key.\",\n" +
                    "\t\"ipAddress\" : \"95.69.208.207\",\n" +
                    "\t\"countryCode\" : \"\",\n" +
                    "\t\"country\" : \"Ukraine\",\n" +
                    "\t\"regionName\" : \"\",\n" +
                    "\t\"city\" : \"Lviv\",\n" +
                    "\t\"zipCode\" : \"\",\n" +
                    "\t\"lat\" : \"0\",\n" +
                    "\t\"lon\" : \"0\",\n" +
                    "\t\"timeZone\" : \"\"\n" +
                    "}";
        }
    };

    public void testGetLocationReturnException() {
        try {
            errorGeolocationServiceW.getGeoLocation();
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    public void testGetLocation() throws UnknownHostException {
        Location location = successGeolocationServiceW.getGeoLocation();
        assertEquals("Lviv", location.getCity());
    }


}
