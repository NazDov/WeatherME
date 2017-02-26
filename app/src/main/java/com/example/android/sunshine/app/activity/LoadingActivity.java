package com.example.android.sunshine.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.android.sunshine.app.R;

public class LoadingActivity extends Activity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private final int max = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar = (ProgressBar) findViewById(R.id.loading_progress_bar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < max) {
                    progressStatus += updateProgress();
                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startWeatherActivity();
            }
        }, 1500);

    }

    private void startWeatherActivity() {
        Intent i = new Intent(LoadingActivity.this, WeatherActivity.class);
        startActivity(i);
        finish();
    }


    private int updateProgress() {
        return  10;
    }
}

