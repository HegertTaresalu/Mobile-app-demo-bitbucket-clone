package com.example.fooddiary.repository;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.fooddiary.R;
import com.example.fooddiary.models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeRepository {
    private static final String API_KEY = "fe722c39854643239dc98f5ef524d6a2";
    private static final String URL = "https://api.spoonacular.com/recipes/random?number=8&apiKey=%s";
    private final Application application;
    private final MutableLiveData<ArrayList<Recipe>> recipeLiveData;
    private final MutableLiveData<ArrayList<Recipe>> bookMarkedRecipeLiveData;
    private  final ArrayList<Recipe> arrayList = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    public static final String TAG = "RecipeRepository";
    public RecipeRepository(Application application) {
        this.application = application;
        recipeLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        bookMarkedRecipeLiveData = new MutableLiveData<>();

    }

    public void getRecipeInfo(/*int id*/) {
        if (arrayList.size() == 0)
        {
            Ion.with(application).load(String.format(URL, /*id,*/ API_KEY)).asJsonObject().setCallback((e, result) -> {
                Log.i("gaming",String.format(URL,/* id,*/ API_KEY));
                parseResults(result);
            });
        }

    }
    public MutableLiveData<ArrayList<Recipe>> getRecipeLiveData(){return recipeLiveData;}
    public MutableLiveData<ArrayList<Recipe>> getBookMarkedRecipeLiveData(){return bookMarkedRecipeLiveData;}

    private void parseResults(JsonObject result) {
        JsonArray recipes =  result.getAsJsonArray("recipes");


        for (int i = 0; i < recipes.size() ; i++) {
            String type;
            String image;
            Log.i("recipes", String.valueOf(recipes.size()));
            Log.i("recipes", String.valueOf(i));


                Log.i("reciperepo","reciperepos");
                JsonObject recipe = (JsonObject) recipes.get(i);

                int id = recipe.get("id").getAsInt();
                String title = recipe.get("title").getAsString();
                String sourceUrl = recipe.get("sourceUrl").getAsString();
                image = null;
                if (recipe.has("image")){
                     image = recipe.get("image").getAsString();
                     Log.i("image",image);
                }
                int readyIn = recipe.get("readyInMinutes").getAsInt();
                 type = application.getString(R.string.dishtype);
                if (recipe.has("dishTypes")){
                    JsonArray dishTypes =  recipe.getAsJsonArray("dishTypes");
                    if (dishTypes.size() != 0){
                        type = dishTypes.get(0).getAsString();
                    }
                }

                Boolean dairyFree = recipe.get("dairyFree").getAsBoolean();
                Boolean vegetarian = recipe.get("vegetarian").getAsBoolean();
                Boolean vegan = recipe.get("vegan").getAsBoolean();
                String summary = recipe.get("summary").getAsString();


                Recipe final_recipe = new Recipe(id, title, type, sourceUrl, readyIn, dairyFree, vegetarian, vegan,image,summary);
                arrayList.add(final_recipe);
                Log.i("gaming", String.valueOf(final_recipe.getDishType()));
                recipeLiveData.setValue(arrayList);

        }

    }



    public void saveRecipe(Bundle args){

        String userId = firebaseAuth.getCurrentUser().getUid();


        Map<String, Object> recipe = new HashMap<>();
        recipe.put("id",args.getInt("id"));
        recipe.put("title",args.getString("recipe_title"));
        recipe.put("recipe_type",args.getString("recipe_type"));
        recipe.put("url",args.getString("recipe_src_url"));
        recipe.put("preptime",args.getInt("prep_time"));
        recipe.put("isDairyFree",args.getBoolean("isDairy"));
        recipe.put("isVegetarian",args.getBoolean("isVegetarian"));
        recipe.put("isVegan",args.getBoolean("isVegan"));
        recipe.put("image",args.getString("recipe_image"));
        recipe.put("summary",args.getString("summary"));
        db.collection("recipes").document(userId).collection("recipes").document(String.valueOf(args.getInt("id"))).set(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.i(TAG, "DocumentSnapshot successfully written!");
                    }
                });

    }

    public void getRecipes(){
        String userId = firebaseAuth.getCurrentUser().getUid();

        arrayList.clear();
        db.collection("recipes").document(userId).collection("recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> recipe_ = document.getData();

                        Recipe recipe = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            recipe = new Recipe(Math.toIntExact((Long)recipe_.get("id")),String.valueOf(recipe_.get("title")),String.valueOf(recipe_.get("recipe_type")),String.valueOf(recipe_.get("url")),Math.toIntExact((Long)recipe_.get("preptime"))
                                     ,(Boolean) recipe_.get("isDairyFree"),(Boolean) recipe_.get("isVegetarian"),(Boolean) recipe_.get("isVegan"),String.valueOf(recipe_.get("image")),String.valueOf(recipe_.get("summary")));
                        }
                            arrayList.add(recipe);
                            Log.i("gaming", String.valueOf(recipe));
                            bookMarkedRecipeLiveData.setValue(arrayList);


                    }
                }
            }
        });
    }


    public void deleteRecipe(int id){
        String userId = firebaseAuth.getCurrentUser().getUid();

        db.collection("recipes").document(userId).collection("recipes").document(String.valueOf(id))
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });

    }
}



