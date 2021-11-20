package com.example.homeworkoutapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Timer;
import java.util.TimerTask;

public class Loading_Screen extends Activity {

    private SharedPreferences appSettingsPrefs;
    private SharedPreferences.Editor sharedPrefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appSettingsPrefs = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
        sharedPrefsEdit = appSettingsPrefs.edit();
        boolean exist = appSettingsPrefs.contains("dark_mode");
        exist = appSettingsPrefs.getBoolean("dark_mode",false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        if(appSettingsPrefs.getBoolean("dark_mode",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_layout_initiation);



        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Loading_Screen.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer time = new Timer();
        time.schedule(task,2000);
        super.onCreate(savedInstanceState);
    }
}