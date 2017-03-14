package com.example.android.sunshine.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.android.sunshine.app.R;
import com.example.android.sunshine.app.utility.WApplicationContext;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton ukrLangRadioBtn;
    private RadioButton enLangRadioBtn;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = WApplicationContext.getSettings();
        setContentView(R.layout.settings);

        ukrLangRadioBtn = (RadioButton) findViewById(R.id.ukrLangRadioButton);
        enLangRadioBtn = (RadioButton) findViewById(R.id.enLangRadioButton);
        String appLang = settings.getString("lang", "uk");
        if (appLang.equalsIgnoreCase("uk")) {
            ukrLangRadioBtn.setChecked(true);
            enLangRadioBtn.setChecked(false);
        } else {
            ukrLangRadioBtn.setChecked(false);
            enLangRadioBtn.setChecked(true);
        }


        ukrLangRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enLangRadioBtn.isChecked()) {
                        enLangRadioBtn.setChecked(false);
                    }
                    settings.edit().putString("lang", "uk").commit();
                    WApplicationContext.updateWAppLang();
                }
            }
        });


        enLangRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ukrLangRadioBtn.isChecked()) {
                        ukrLangRadioBtn.setChecked(false);
                    }
                    settings.edit().putString("lang", "en").commit();
                    WApplicationContext.updateWAppLang();
                }
            }
        });

        WApplicationContext.updateWAppLang();

    }

}
