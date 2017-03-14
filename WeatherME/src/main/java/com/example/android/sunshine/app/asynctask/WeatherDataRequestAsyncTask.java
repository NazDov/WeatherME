package com.example.android.sunshine.app.asynctask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.service.WDataRequestConnectionService;
import com.example.android.sunshine.app.service.WGeoLocationService;
import com.example.android.sunshine.app.beans.Location;
import com.example.android.sunshine.app.utility.WApplicationContext;
import com.example.android.sunshine.app.activity.WeatherActivity;

import org.w3c.dom.Text;

/**
 * Created by nazardovhuy on 05.11.2016.
 */
public class WeatherDataRequestAsyncTask extends AsyncTask<WGeoLocationService, Void, String> {

    private static String LOG_TAG = WeatherDataRequestAsyncTask.class.getSimpleName();
    private WDataRequestConnectionService WDataRequestConnectionService;
    private WeatherActivity.WeatherFragment weatherFragment;
    private ProgressDialog loadingDialog;


    public WeatherDataRequestAsyncTask(WDataRequestConnectionService WDataRequestConnectionService,
                                       WeatherActivity.WeatherFragment weatherFragment) {
        this.WDataRequestConnectionService = WDataRequestConnectionService;
        this.weatherFragment = weatherFragment;
        Activity context = (Activity) WApplicationContext.getContext();
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setMessage(context.getResources().getString(R.string.loading_text));
        loadingDialog.setMax(100);

    }

    @Override
    protected void onPreExecute() {
        loadingDialog.show();
    }

    @Override
    protected String doInBackground(WGeoLocationService... locationServices) {
        return WDataRequestConnectionService.httpGetWeatherDataJSON(getGeoLocation(locationServices[0]).getCity());
    }

    private Location getGeoLocation(WGeoLocationService WGeoLocationService) {
        if (WGeoLocationService == null) {
            throw new IllegalStateException("WGeoLocationService is undefined");
        }
        return WGeoLocationService.getGeoLocation();
    }


    @Override
    protected void onPostExecute(String response) {
        loadingDialog.dismiss();
        switch (response) {
            case WApplicationContext.RESPONSE_OK:
                Log.i(LOG_TAG, response);
                weatherFragment.processWeatherDataResponse(WApplicationContext.getResultWeatherData());
                break;
            case WApplicationContext.WRONG_CONNECTION_URL:
                errDialog(WApplicationContext.WRONG_CONNECTION_URL);
                return;
            case WApplicationContext.DATA_PARSE_ERROR:
                errDialog(WApplicationContext.DATA_PARSE_ERROR);
                return;
            case WApplicationContext.NO_SERVER_RESPONSE:
                errDialog(WApplicationContext.NO_SERVER_RESPONSE);
                return;
            case WApplicationContext.DATA_NOT_FOUND:
                errDialog(((Activity) WApplicationContext.getContext()).getResources().getString(R.string.no_weather_data_found_search_by_city));

        }
    }

    public void errDialog(String errMessage) {
        final Dialog dialog = new Dialog(WApplicationContext.getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.dialog_app_title_err_exit);
        TextView dialogTextView = (TextView) dialog.findViewById(R.id.customDialogText);
        dialogTextView.setText(errMessage);
        Button okBtn = (Button) dialog.findViewById(R.id.customDialogOk);
        Button cnxlBtn = (Button) dialog.findViewById(R.id.customDialogCancel);
        cnxlBtn.setVisibility(View.INVISIBLE);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


}
