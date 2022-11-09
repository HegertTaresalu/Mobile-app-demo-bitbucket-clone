package com.example.fooddiary.fragment;

import static com.example.fooddiary.ValidInputControl.isValidEmail;

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
import android.widget.Button;
import android.widget.Switch;

import com.example.fooddiary.R;
import com.example.fooddiary.ViewModel.LoginViewModel;
import com.google.android.material.textfield.TextInputLayout;


public class RecoveryFragment extends Fragment implements View.OnClickListener {

    LoginViewModel loginViewModel;
    TextInputLayout emailInput;
    NavController navController;
    Button recoverBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recovery, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.btnRecover).setOnClickListener(this);
        emailInput = view.findViewById(R.id.etRecoverEmail);
        recoverBtn = view.findViewById(R.id.btnRecover);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRecover:
                String email = emailInput.getEditText().getText().toString();
                if (isValidEmail(email)){
                    loginViewModel.resetPassword(email);
                    navController.navigate(R.id.action_recoveryFragment_to_loginFragment2);
                }

                break;

        }
    }
}