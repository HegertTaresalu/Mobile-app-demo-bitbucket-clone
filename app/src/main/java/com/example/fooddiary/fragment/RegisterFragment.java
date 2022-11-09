package com.example.fooddiary.fragment;

import static com.example.fooddiary.ValidInputControl.isValidEmail;
import static com.example.fooddiary.ValidInputControl.isValidPassword;

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
import android.widget.Toast;

import com.example.fooddiary.R;
import com.example.fooddiary.ViewModel.LoginViewModel;
import com.example.fooddiary.ViewModel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private LoginViewModel loginViewModel;
    private UserViewModel userViewModel;
    NavController navController;
    TextInputLayout firstNameTextInput;
    TextInputLayout surNameTextInput;
    TextInputLayout emailTextInput;
    TextInputLayout usernameTextInput;
    TextInputLayout passwordTextInput;
    TextInputLayout passwordVerifyTextInput;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null){
                Toast.makeText(getContext(),"User logged in", Toast.LENGTH_SHORT).show();
            }
        });

        userViewModel.getLoggedOutMutableLiveData().observe(this,loggedOut ->{
            if (loggedOut){
                //todo redirect to legit destination
                if(getView()!= null) Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        firstNameTextInput = view.findViewById(R.id.etFirstName);
        surNameTextInput = view.findViewById(R.id.etSurname);
        emailTextInput= view.findViewById(R.id.etRegisterEmail);
        usernameTextInput = view.findViewById(R.id.etUsername);
        passwordTextInput = view.findViewById(R.id.etRegisterPassword);
        passwordVerifyTextInput = view.findViewById(R.id.etVerifyPass);
        view.findViewById(R.id.btnCompleteRegister).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        String firstName = Objects.requireNonNull(firstNameTextInput.getEditText()).getText().toString();
        String surName = Objects.requireNonNull(surNameTextInput.getEditText()).getText().toString();
        String email = Objects.requireNonNull(emailTextInput.getEditText()).getText().toString();
        String userName = Objects.requireNonNull(usernameTextInput.getEditText()).getText().toString();
        String password = Objects.requireNonNull(passwordTextInput.getEditText()).getText().toString();
        String passwordVerify = passwordTextInput.getEditText().getText().toString();
        switch (view.getId()){
            case R.id.btnCompleteRegister:
        if (isValidEmail(email) && isValidPassword(password) && password.equals(passwordVerify) && !firstName.isEmpty() && !surName.isEmpty() && !userName.isEmpty()  ){
            loginViewModel.userRegistration(firstName,surName,email,userName,password);
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        }
        else{
            if (firstName.isEmpty()){
                Snackbar.make(view, "First name field is empty",Snackbar.LENGTH_SHORT).show();

            }
            else if (surName.isEmpty()){
                Snackbar.make(view, "Last name field is empty",Snackbar.LENGTH_SHORT).show();


            }
            else if (!isValidEmail(email)){
                Snackbar.make(view,"Email is not valid", Snackbar.LENGTH_SHORT).show();
            }
            else if (!isValidPassword(password)){
                Snackbar.make(view,"Password is not valid",Snackbar.LENGTH_SHORT).show();
            }
            else if (!password.equals(passwordVerify) ){
                Snackbar.make(view,"passwords do not match",Snackbar.LENGTH_SHORT).show();

            }
            else{
                Snackbar.make(view,"Multiple inputs are not valid",Snackbar.LENGTH_SHORT).show();
            }


            break;
        }



        }
    }
}