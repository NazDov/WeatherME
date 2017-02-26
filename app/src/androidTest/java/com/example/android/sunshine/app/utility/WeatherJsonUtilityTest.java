package com.example.android.sunshine.app.utility;

import android.util.Log;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 * Created by User on 12.11.2016.
 */

public class WeatherJsonUtilityTest extends TestCase {

    private static String LOG_TAG = WeatherJsonUtilityTest.class.getSimpleName();
    private static String WEATHER_TEST_DATA = "{  \n" +
            "   \"city\":{  \n" +
            "      \"id\":702550,\n" +
            "      \"name\":\"Lviv\",\n" +
            "      \"coord\":{  \n" +
            "         \"lon\":24.023239,\n" +
            "         \"lat\":49.838261\n" +
            "      },\n" +
            "      \"country\":\"UA\",\n" +
            "      \"population\":0,\n" +
            "      \"sys\":{  \n" +
            "         \"population\":0\n" +
            "      }\n" +
            "   },\n" +
            "   \"cod\":\"200\",\n" +
            "   \"message\":0.4444,\n" +
            "   \"cnt\":12,\n" +
            "   \"list\":[  \n" +
            "      {  \n" +
            "         \"dt\":1478962800,\n" +
            "         \"main\":{  \n" +
            "            \"temp\":0.7,\n" +
            "            \"temp_min\":0.7,\n" +
            "            \"temp_max\":2.13,\n" +
            "            \"pressure\":996.43,\n" +
            "            \"sea_level\":1031.15,\n" +
            "            \"grnd_level\":996.43,\n" +
            "            \"humidity\":98,\n" +
            "            \"temp_kf\":-1.43\n" +
            "         },\n" +
            "         \"weather\":[  \n" +
            "            {  \n" +
            "               \"id\":500,\n" +
            "               \"main\":\"Rain\",\n" +
            "               \"description\":\"light rain\",\n" +
            "               \"icon\":\"10n\"\n" +
            "            }\n" +
            "         ],\n" +
            "         \"clouds\":{  \n" +
            "            \"all\":92\n" +
            "         },\n" +
            "         \"wind\":{  \n" +
            "            \"speed\":8.32,\n" +
            "            \"deg\":57.5004\n" +
            "         },\n" +
            "         \"rain\":{  \n" +
            "            \"3h\":0.74\n" +
            "         },\n" +
            "         \"snow\":{  \n" +
            "            \"3h\":0.08\n" +
            "         },\n" +
            "         \"sys\":{  \n" +
            "            \"pod\":\"n\"\n" +
            "         },\n" +
            "         \"dt_txt\":\"2016-11-12 15:00:00\"\n" +
            "      },\n" +
            "      {  \n" +
            "         \"dt\":1478973600,\n" +
            "         \"main\":{  \n" +
            "            \"temp\":-0.5,\n" +
            "            \"temp_min\":-0.5,\n" +
            "            \"temp_max\":0.57,\n" +
            "            \"pressure\":994.76,\n" +
            "            \"sea_level\":1029.74,\n" +
            "            \"grnd_level\":994.76,\n" +
            "            \"humidity\":97,\n" +
            "            \"temp_kf\":-1.07\n" +
            "         },\n" +
            "         \"weather\":[  \n" +
            "            {  \n" +
            "               \"id\":500,\n" +
            "               \"main\":\"Rain\",\n" +
            "               \"description\":\"light rain\",\n" +
            "               \"icon\":\"10n\"\n" +
            "            }\n" +
            "         ],\n" +
            "         \"clouds\":{  \n" +
            "            \"all\":92\n" +
            "         },\n" +
            "         \"wind\":{  \n" +
            "            \"speed\":8.3,\n" +
            "            \"deg\":53.5001\n" +
            "         },\n" +
            "         \"rain\":{  \n" +
            "            \"3h\":0.75\n" +
            "         },\n" +
            "         \"snow\":{  \n" +
            "            \"3h\":4.715\n" +
            "         },\n" +
            "         \"sys\":{  \n" +
            "            \"pod\":\"n\"\n" +
            "         },\n" +
            "         \"dt_txt\":\"2016-11-12 18:00:00\"\n" +
            "      }" +
            "] \n" +
            "} \n" +
            "} \n";

//    public void testWeatherDataParse() throws JSONException {
//        assertEquals(2.13, WeatherJsonUtility.getMaxTemperatureFromWeatherJsonString(WEATHER_TEST_DATA, 0));
//
//    }
//
//    public void testGetReadableDateString() throws ParseException {
////        String readableDateString = WeatherJsonUtility.getReadableDateInfo("2016-11-12 15:00:00");
////        Log.v(LOG_TAG, readableDateString);
////        assertEquals("Сб лист. 12 15:00", readableDateString);
//    }
//
//    public void testParseWeatherDescription() throws JSONException {
//        String weatherDescription = WeatherJsonUtility.parseWeatherDescription(
//                new JSONObject(WEATHER_TEST_DATA)
//                        .getJSONArray("list")
//                        .getJSONObject(0)
//        );
//        Log.v(LOG_TAG, weatherDescription);
//        assertEquals("light rain", weatherDescription);
//    }
//
//
//    public void testMinMaxTempConvert() {
//        String texmMaxMin = WeatherJsonUtility.roundHighLows(-2.5, 1.7);
//        Log.v(LOG_TAG, texmMaxMin);
//        assertEquals("2 / -2", texmMaxMin);
//    }
//
//    public void testRemoveDuplicates() {
//
//
//
//    }

}