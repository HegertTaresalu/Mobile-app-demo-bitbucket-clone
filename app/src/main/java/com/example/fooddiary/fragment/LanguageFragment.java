package com.example.fooddiary.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.fooddiary.Activity.NavbarActivity;
import com.example.fooddiary.R;

import java.util.Locale;
import java.util.Objects;

public class LanguageFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    NavbarActivity navbarActivity;
    NavController navController;
    RadioGroup languages;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Locale locale;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navbarActivity = (NavbarActivity) getActivity();
        sharedPref = getActivity().getSharedPreferences("pref",0);
        editor = sharedPref.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.language));
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        int languageSP = sharedPref.getInt("languageSP",3);
        RadioButton english = view.findViewById(R.id.btnEng);
        RadioButton estonian = view.findViewById(R.id.btnEst);
        RadioButton welsh = view.findViewById(R.id.btnCym);

        if(languageSP == 0) {
            locale = new Locale("en");
            english.setChecked(true);
        } else if (languageSP == 1) {
            locale = new Locale("et");
            estonian.setChecked(true);
        } else if (languageSP == 2) {
            locale = new Locale("cy");
            welsh.setChecked(true);
        }

        languages = view.findViewById(R.id.rgLanguages);
        languages.setOnCheckedChangeListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        ((NavbarActivity) LanguageFragment.this.getActivity()).switchLang(getView(),editor);

    }

}