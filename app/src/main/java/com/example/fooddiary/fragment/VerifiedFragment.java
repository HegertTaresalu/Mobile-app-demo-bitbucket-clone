package com.example.fooddiary.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fooddiary.Activity.MainActivity;
import com.example.fooddiary.Activity.NavbarActivity;
import com.example.fooddiary.R;
import com.example.fooddiary.ViewModel.LoginViewModel;

import java.util.Objects;

public class VerifiedFragment extends Fragment implements View.OnClickListener {

    NavController navController;
    LoginViewModel loginViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.drawer_profile));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verified, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoggedOutMutableLiveData().observe(this, loggedOut ->{
            if(loggedOut){
                if(getView() != null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.log_out).setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.log_out:
                loginViewModel.logout();
        }
    }
}