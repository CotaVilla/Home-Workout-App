package com.example.homeworkoutapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;


import com.example.homeworkoutapp.objects.Exercise;
import com.example.homeworkoutapp.objects.Rutine_Exercise;
import com.example.homeworkoutapp.ui.exercises.ExercisesFragment;
import com.example.homeworkoutapp.ui.options.OptionsFragment;
import com.example.homeworkoutapp.ui.routines.RoutinesFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.homeworkoutapp.databinding.ActivityStartBinding;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private SharedPreferences appSettingsPrefs;
    private SharedPreferences.Editor sharedPrefsEdit;

    private Fragment actualFragment;
    private Exercise pasableExercise;
    private Rutine_Exercise pasableRE;
    private Boolean darkModeChanged;

    public Exercise getPasableExercise() {
        return pasableExercise;
    }

    public void setPasableExercise(Exercise pasableExercise) {
        this.pasableExercise = pasableExercise;
    }


    public Rutine_Exercise getPasableRE() {
        return pasableRE;
    }

    public void setPasableRE(Rutine_Exercise pasable_RE) {
        this.pasableRE = pasable_RE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appSettingsPrefs = getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
        sharedPrefsEdit = appSettingsPrefs.edit();

        if(appSettingsPrefs.getBoolean("dark_mode",false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //load fragment
        darkModeChanged = appSettingsPrefs.getBoolean("dark_mode_changed",false);
        sharedPrefsEdit.putBoolean("dark_mode_changed",false);
        sharedPrefsEdit.apply();

        if(darkModeChanged){
            actualFragment = new OptionsFragment();
        }else {
            actualFragment = new RoutinesFragment();
        }

        loadFragment(actualFragment);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.nav_routines) {
                    loadFragment(new RoutinesFragment());
                } else if(id == R.id.nav_exercises) {
                    loadFragment(new ExercisesFragment(false));
                }
                else if(id == R.id.nav_settings) {
                    loadFragment(new OptionsFragment());
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        // empty backstack before loading new fragment
        if (fm.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(0);
            fm.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        // change fragment
        transaction.replace(R.id.nav_host_fragment_content_start, fragment);
        transaction.commit();
    }

}