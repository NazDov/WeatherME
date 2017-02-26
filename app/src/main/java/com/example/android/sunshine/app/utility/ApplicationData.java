package com.example.android.sunshine.app.utility;

import android.app.Activity;
import android.content.Context;

/**
 * Created by User on 05.11.2016.
 */
public class ApplicationData {

    public static final String WRONG_CONNECTION_URL = "wrong or malformed URL address populated";
    public static final String DATA_PARSE_ERROR = "could not parse data";
    private static Context context;

    public static void setContext(Activity context) {
        ApplicationData.context = context;
    }

    public static Context getContext() {
        return context;
    }

    private ApplicationData() {

    }
}

