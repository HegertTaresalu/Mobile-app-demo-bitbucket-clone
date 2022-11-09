package com.example.fooddiary.fragment;

import static com.example.fooddiary.ValidInputControl.isValidEmail;
import static com.example.fooddiary.ValidInputControl.isValidPassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fooddiary.Activity.NavbarActivity;
import com.example.fooddiary.R;
import com.example.fooddiary.ViewModel.LoginViewModel;
import com.example.fooddiary.ViewModel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    private UserViewModel userViewModel;
    private NavController navController;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private Activity activity;


    public void importFragment(Activity activity) {
        this.activity = activity;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null)
            {
                if (getView() != null){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), NavbarActivity.class);
                    getActivity().startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        emailInput = view.findViewById(R.id.etEmail);
        passwordInput = view.findViewById(R.id.etPassword);
        view.findViewById(R.id.btnSignIn).setOnClickListener(this);
        view.findViewById(R.id.txtForgotPass).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSignIn:
                String email = Objects.requireNonNull(emailInput.getEditText()).getText().toString();
                String password = Objects.requireNonNull(passwordInput.getEditText()).getText().toString();

                    if (isValidPassword(password) && isValidEmail(email)){
                        loginViewModel.Login(email,password);

                    }





                break;
            case R.id.txtForgotPass:
                navController.navigate(R.id.action_loginFragment_to_recoveryFragment2);
                break;
        }
    }
}