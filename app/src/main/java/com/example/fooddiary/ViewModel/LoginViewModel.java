package com.example.fooddiary.ViewModel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddiary.repository.AuthRepository;

public class LoginViewModel extends AndroidViewModel {

    private final AuthRepository authRepository;
    private final MutableLiveData userMutableData;
    public final MutableLiveData<Boolean> loggedOutMutableLiveData;

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        loggedOutMutableLiveData = authRepository.getLoggedOutMutableLiveData();
        userMutableData = authRepository.getUserMutableLiveData();


    }


    public MutableLiveData getUserMutableLiveData(){

        return userMutableData;
    }




    public void Login(String email, String password){
        authRepository.logIn(email,password);
    }


    public void userRegistration(String firstName, String surName, String email,String username,String password){
    authRepository.userRegistration(firstName,surName,email,username,password);

    }

    public void resetPassword(String email){
        authRepository.resetPassword(email) ;
    }


    public void logout(){
        authRepository.logOut();
    }

}
