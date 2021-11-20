package com.example.homeworkoutapp.ui.options;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.homeworkoutapp.R;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class OptionsFragment extends Fragment {
    SharedPreferences appSettingsPrefs;
    SharedPreferences.Editor sharedPrefsEdit;

    TextView title;
    Switch dark_mode;
    Switch sound_next_countdown;
    Switch sound_next_exercise;
    Switch sound_routine_finish;
    Switch narrator;


    public OptionsFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appSettingsPrefs = getActivity().getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
        sharedPrefsEdit = appSettingsPrefs.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_options, container, false);

        title = root.findViewById(R.id.frag_title);
        dark_mode = root.findViewById(R.id.switch_dark_mode);
        sound_next_countdown = root.findViewById(R.id.switch_sound_next_countdown);
        sound_next_exercise = root.findViewById(R.id.switch_sound_next_exercise);
        sound_routine_finish = root.findViewById(R.id.switch_sound_routine_finish);
        narrator = root.findViewById(R.id.switch_narration);

        title.setText("Opciones");
        dark_mode.setChecked(appSettingsPrefs.getBoolean("dark_mode",false));
        boolean tem =appSettingsPrefs.contains("sound_next_countdown");
        sound_next_countdown.setChecked(appSettingsPrefs.getBoolean("sound_next_countdown",true));
        sound_next_exercise.setChecked(appSettingsPrefs.getBoolean("sound_next_exercise",true));
        sound_routine_finish.setChecked(appSettingsPrefs.getBoolean("sound_routine_finish",true));
        narrator.setChecked(appSettingsPrefs.getBoolean("narrator",true));

        dark_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPrefsEdit.putBoolean("dark_mode_changed",true);
                    sharedPrefsEdit.putBoolean("dark_mode",true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    sharedPrefsEdit.putBoolean("dark_mode_changed",true);
                    sharedPrefsEdit.putBoolean("dark_mode",false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                sharedPrefsEdit.apply();
            }
        });

        sound_next_countdown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPrefsEdit.putBoolean("sound_next_countdown",true);
                }
                else {
                    sharedPrefsEdit.putBoolean("sound_next_countdown",false);
                }
                sharedPrefsEdit.apply();
            }
        });

        sound_next_exercise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPrefsEdit.putBoolean("sound_next_exercise",true);
                }
                else {
                    sharedPrefsEdit.putBoolean("sound_next_exercise",false);
                }
                sharedPrefsEdit.apply();
            }
        });

        sound_routine_finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPrefsEdit.putBoolean("sound_routine_finish",true);
                }
                else {
                    sharedPrefsEdit.putBoolean("sound_routine_finish",false);
                }
                sharedPrefsEdit.apply();
            }
        });

        narrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPrefsEdit.putBoolean("narrator",true);
                }
                else {
                    sharedPrefsEdit.putBoolean("narrator",false);
                }
                sharedPrefsEdit.apply();
            }
        });

        return root;
    }
}