package com.example.fooddiary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fooddiary.Activity.NavbarActivity;

public class SharedPrefs {
    NavbarActivity navbarActivity;
    String localeKey = "locale";
    String setbackLocale = "en";
    String databaseName = "database";
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    public SharedPrefs(Context context) {
        this.context = context;
        this.editor = this.context.getSharedPreferences(databaseName, Context.MODE_PRIVATE).edit();
        this.sharedPreferences = this.context.getSharedPreferences(databaseName, Context.MODE_PRIVATE);
    }

    public String getLocale() {
        if (this.sharedPreferences.contains(localeKey)) {
            return sharedPreferences.getString(localeKey, "");
        } else {
            return setbackLocale;
        }
    }

    public void setLocale(String lang) {
        editor.putString(localeKey, lang);
        editor.apply();
    }
}
