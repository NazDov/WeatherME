package com.example.android.sunshine.app.utility.parser;

import static com.example.android.sunshine.app.utility.ApplicationContext.*;

import android.content.Context;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.utility.ApplicationContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nazar.dovhuy on 28.11.2016.
 */
public abstract class AbstractWeatherJsonParser<T> {

    private Context context;
    private final String degree = "\u00b0";

    public AbstractWeatherJsonParser(Context context) {
        this.context = context;
    }

    public abstract T parse(String parseParam) throws JSONException, ParseException;


    protected String getParsedWeatherDescription(JSONObject weatherJson) throws JSONException {
        String description;
        int wCondCode = getParsedWeatherConditionCode(weatherJson);
        description = getWeatherDescriptionByConditionId(wCondCode);
        return description;
    }

    protected String getWeatherDescriptionByConditionId(int wCondId) {
        if (context == null) {
            context = ApplicationContext.getContext();
            if (context == null) throw new RuntimeException("undefined application context");
        }
        String wDescr = null;
        switch (wCondId) {
            case 200:
                wDescr = context.getResources().getString(R.string.c200);
                break;
            case 201:
                wDescr = context.getResources().getString(R.string.c201);
                break;
            case 202:
                wDescr = context.getResources().getString(R.string.c202);
                break;
            case 210:
                wDescr = context.getResources().getString(R.string.c210);
                break;
            case 211:
                wDescr = context.getResources().getString(R.string.c211);
                break;
            case 212:
                wDescr = context.getResources().getString(R.string.c212);
                break;
            case 221:
                wDescr = context.getResources().getString(R.string.c221);
                break;
            case 230:
                wDescr = context.getResources().getString(R.string.c230);
                break;
            case 231:
                wDescr = context.getResources().getString(R.string.c231);
                break;
            case 232:
                wDescr = context.getResources().getString(R.string.c232);
                break;
            case 300:
                wDescr = context.getResources().getString(R.string.c300);
                break;
            case 301:
                wDescr = context.getResources().getString(R.string.c301);
                break;
            case 302:
                wDescr = context.getResources().getString(R.string.c302);
                break;
            case 310:
                wDescr = context.getResources().getString(R.string.c310);
                break;
            case 311:
                wDescr = context.getResources().getString(R.string.c311);
                break;
            case 312:
                wDescr = context.getResources().getString(R.string.c312);
                break;
            case 313:
                wDescr = context.getResources().getString(R.string.c313);
                break;
            case 314:
                wDescr = context.getResources().getString(R.string.c314);
                break;
            case 321:
                wDescr = context.getResources().getString(R.string.c321);
                break;
            case 500:
                wDescr = context.getResources().getString(R.string.c500);
                break;
            case 501:
                wDescr = context.getResources().getString(R.string.c501);
                break;
            case 502:
                wDescr = context.getResources().getString(R.string.c502);
                break;
            case 503:
                wDescr = context.getResources().getString(R.string.c503);
                break;
            case 504:
                wDescr = context.getResources().getString(R.string.c504);
                break;
            case 511:
                wDescr = context.getResources().getString(R.string.c511);
                break;
            case 520:
                wDescr = context.getResources().getString(R.string.c520);
                break;
            case 521:
                wDescr = context.getResources().getString(R.string.c521);
                break;
            case 522:
                wDescr = context.getResources().getString(R.string.c522);
                break;
            case 531:
                wDescr = context.getResources().getString(R.string.c531);
                break;
            case 600:
                wDescr = context.getResources().getString(R.string.c600);
                break;
            case 601:
                wDescr = context.getResources().getString(R.string.c601);
                break;
            case 602:
                wDescr = context.getResources().getString(R.string.c602);
                break;
            case 611:
                wDescr = context.getResources().getString(R.string.c611);
                break;
            case 612:
                wDescr = context.getResources().getString(R.string.c612);
                break;
            case 615:
                wDescr = context.getResources().getString(R.string.c615);
                break;
            case 616:
                wDescr = context.getResources().getString(R.string.c616);
                break;
            case 620:
                wDescr = context.getResources().getString(R.string.c620);
                break;
            case 621:
                wDescr = context.getResources().getString(R.string.c621);
                break;
            case 622:
                wDescr = context.getResources().getString(R.string.c622);
                break;
            case 701:
                wDescr = context.getResources().getString(R.string.c701);
                break;
            case 711:
                wDescr = context.getResources().getString(R.string.c711);
                break;
            case 721:
                wDescr = context.getResources().getString(R.string.c721);
                break;
            case 731:
                wDescr = context.getResources().getString(R.string.c731);
                break;
            case 741:
                wDescr = context.getResources().getString(R.string.c741);
                break;
            case 751:
                wDescr = context.getResources().getString(R.string.c751);
                break;
            case 761:
                wDescr = context.getResources().getString(R.string.c761);
                break;
            case 762:
                wDescr = context.getResources().getString(R.string.c762);
                break;
            case 771:
                wDescr = context.getResources().getString(R.string.c771);
                break;
            case 781:
                wDescr = context.getResources().getString(R.string.c781);
                break;
            case 800:
                wDescr = context.getResources().getString(R.string.c800);
                break;
            case 801:
                wDescr = context.getResources().getString(R.string.c801);
                break;
            case 802:
                wDescr = context.getResources().getString(R.string.c802);
                break;
            case 803:
                wDescr = context.getResources().getString(R.string.c803);
                break;
            case 804:
                wDescr = context.getResources().getString(R.string.c804);
                break;


        }

        return wDescr;
    }


    protected int getParsedWeatherConditionCode(JSONObject weatherJson) throws JSONException {
        int conditionCode;
        conditionCode = getParsedWeatherJsonArrayByAttr(weatherJson, W_WEATHER).getJSONObject(0).getInt(W_ID);
        return conditionCode;
    }

    protected String getParsedWeatherImageIconCode(JSONObject weatherJson) throws JSONException {
        String weatherImageCode;
        weatherImageCode = getParsedWeatherJsonArrayByAttr(weatherJson, W_WEATHER).getJSONObject(0).getString(W_ICON);
        return weatherImageCode;
    }

    protected String getParsedTemperature(JSONObject mainWeatherJsonObject) throws JSONException {
        return formatTemp(extractTemp(mainWeatherJsonObject, MAX_TEMP_ATTR),
                extractTemp(mainWeatherJsonObject, MIN_TEMPO_ATTR));
    }

    protected String getParsedMaxTemp(JSONObject mainWeatherJsonObject) throws JSONException {
        return extractTemp(mainWeatherJsonObject, MAX_TEMP_ATTR) + "" + degree;
    }

    protected String getParsedMinTemp(JSONObject mainWeatherJsonObject) throws JSONException {
        return extractTemp(mainWeatherJsonObject, MIN_TEMPO_ATTR) + "" + degree;
    }

    private String formatTemp(double minTemp, double maxTemp) {
        long roundHigh = Math.round(maxTemp);
        long roundLow = Math.round(minTemp);
        return roundHigh + " " + degree + " / " + roundLow + " " + degree;
    }

    private double extractTemp(JSONObject weatherJson, String tempAttr) throws JSONException {
        return weatherJson.getDouble(tempAttr);
    }


    public String getParsedDateInfo(int day) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, day);
        return simpleDateFormat.format(calendar.getTime());
    }


    public JSONArray getParsedWeatherJsonArrayByAttr(JSONObject weatherJson, String attr) throws JSONException {
        return weatherJson.getJSONArray(attr);
    }

    public JSONObject getParsedWeatherJsonObjectByAttr(JSONObject weatherJson, String attr) throws JSONException {
        return weatherJson.getJSONObject(attr);
    }

}
