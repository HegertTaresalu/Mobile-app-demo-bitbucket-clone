package com.example.fooddiary.ViewModel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddiary.models.Recipe;
import com.example.fooddiary.repository.RecipeRepository;

import java.util.ArrayList;

public class BrowserViewModel extends AndroidViewModel{

    private final RecipeRepository repository;
    private final MutableLiveData<ArrayList<Recipe>> recipeLiveData;
    private final MutableLiveData<ArrayList<Recipe>> bookMarkedRecipeLiveData;

    public BrowserViewModel(@NonNull Application application) {
       super(application);
       repository = new RecipeRepository(application);
       recipeLiveData = repository.getRecipeLiveData();
       bookMarkedRecipeLiveData = repository.getBookMarkedRecipeLiveData();
    }

    public void getRecipeData(){repository.getRecipeInfo();}

    public MutableLiveData<ArrayList<Recipe>> getRecipeLiveData(){
        return recipeLiveData;
    }

    public MutableLiveData<ArrayList<Recipe>> getBookMarkedRecipeLiveData(){
        return bookMarkedRecipeLiveData;
    }




    public void addData(Bundle args){
        repository.saveRecipe(args);
    }
    public void getData(){
        repository.getRecipes();
    }

    public void deleteData(int id){
        repository.deleteRecipe(id);

    }
}