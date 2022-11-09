package com.example.fooddiary.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

//import com.example.fooddiary.utils.LanguageConfig;
import com.example.fooddiary.R;
import com.example.fooddiary.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddiary.databinding.ActivityNavbarBinding;

import java.util.Locale;

public class NavbarActivity extends AppCompatActivity {
    int nightMode;
    Locale locale;
    SharedPrefs sharedPrefs;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor_theme;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavbarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //theme
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        nightMode = sharedPreferences.getInt("NightModeInt", 1);
        AppCompatDelegate.setDefaultNightMode(nightMode);


        //navigation bar
        binding = ActivityNavbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarNavbar.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_browser, R.id.nav_bookmark, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navbar);
       NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
       NavigationUI.setupWithNavController(navigationView, navController);
    }


    //theme
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        nightMode = AppCompatDelegate.getDefaultNightMode();

        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor_theme = sharedPreferences.edit();

        editor_theme.putInt("NightModeInt", nightMode);
        editor_theme.apply();
    }

    //navigation bar
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navbar);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //theme
    public void switchTheme(View view, SharedPreferences.Editor editor) {
        RadioButton darkTheme = view.findViewById(R.id.darkModeBtn);
        RadioButton lightTheme = view.findViewById(R.id.lightModeBtn);
        RadioButton systemDefault = view.findViewById(R.id.systemDefaultBtn);
        if (darkTheme.isChecked()) {
            editor.putInt("checkedSP", 0);
            editor.commit();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (lightTheme.isChecked()) {
            editor.putInt("checkedSP", 1);
            editor.commit();

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (systemDefault.isChecked()) {
            editor.putInt("checkedSP", 2);
            editor.commit();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    //language
    @Override
    protected void attachBaseContext(Context newBase) {
        sharedPrefs = new SharedPrefs(newBase);
        String languageCode = sharedPrefs.getLocale();
        super.attachBaseContext(newBase);
    }

    //language
    public void switchLang(View view, SharedPreferences.Editor editor) {
        RadioButton english = view.findViewById(R.id.btnEng);
        RadioButton estonian = view.findViewById(R.id.btnEst);
        RadioButton welsh = view.findViewById(R.id.btnCym);
        if (english.isChecked()) {
            locale = new Locale("en");
            editor.putInt("languageSP", 0);
            editor.commit();
        } else if (estonian.isChecked()) {
            locale = new Locale("et");
            editor.putInt("languageSP", 1);
            editor.commit();
        } else if (welsh.isChecked()) {
            locale = new Locale("cy");
            editor.putInt("languageSP", 2);
            editor.commit();
        }

        changeLanguage();

    }

    public void changeLanguage() {
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(configuration,
                this.getBaseContext().getResources().getDisplayMetrics());

        Intent i = new Intent();
        i.setClass(this, NavbarActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}