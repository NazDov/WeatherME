package com.example.android.sunshine.app.utility.parser;

import com.example.android.sunshine.app.utility.ApplicationContext;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;

/**
 * Created by User on 12.11.2016.
 */

public class WeatherJsonParserTest extends TestCase {

    private static final String LOG_TAG = WeatherJsonParserTest.class.getSimpleName();
    private static final String WEATHER_TEST_JSON = "{\"cod\":\"200\",\"message\":0.0032,\n" +
            "\"city\":{\"id\":1851632,\"name\":\"Shuzenji\",\n" +
            "\"coord\":{\"lon\":138.933334,\"lat\":34.966671},\n" +
            "\"country\":\"JP\"},\n" +
            "\"cnt\":10,\n" +
            "\"list\":[{\n" +
            "    \"dt\":1406080800,\n" +
            "    \"temp\":{\n" +
            "        \"day\":297.77,\n" +
            "        \"min\":293.52,\n" +
            "        \"max\":297.77,\n" +
            "        \"night\":293.52,\n" +
            "        \"eve\":297.77,\n" +
            "        \"morn\":297.77},\n" +
            "    \"pressure\":925.04,\n" +
            "    \"humidity\":76,\n" +
            "    \"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}]}\n" +
            "        ]}";

    private AbstractWeatherJsonParser<String[]> dummyWeatherJsonParser = new AbstractWeatherJsonParser<String[]>(null) {

        @Override
        public String[] parse(String parseParam) throws JSONException, ParseException {
            return new String[0];
        }

    };

    private JSONObject weatherListJson;

    @Override
    protected void setUp() throws Exception {
        JSONObject weatherJSOn = new JSONObject(WEATHER_TEST_JSON);
        weatherListJson = dummyWeatherJsonParser.getParsedWeatherJsonArrayByAttr(weatherJSOn, ApplicationContext.WEATHER_LIST_ATTR).getJSONObject(0);
    }

    public void testGetParsedWeatherConditionCode() throws JSONException {
        int weatherCondCode = dummyWeatherJsonParser.getParsedWeatherConditionCode(weatherListJson);
        assertEquals(803, weatherCondCode);
    }

    public void testGetParsedWeatherDescription() throws JSONException {
        String weatherParsedDescr = dummyWeatherJsonParser.getParsedWeatherDescription(weatherListJson);
        assertEquals("broken clouds", weatherParsedDescr);
    }

}