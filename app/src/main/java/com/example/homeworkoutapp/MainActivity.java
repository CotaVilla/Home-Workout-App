package com.example.homeworkoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_main);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer time = new Timer();
        time.schedule(task,2000);
    }
}