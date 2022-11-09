package com.example.fooddiary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddiary.models.Recipe;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private ArrayList<Recipe> recipeList;
    Bundle args;
    public RecipeAdapter(){
        this.recipeList = new ArrayList<>();
    }
    NavController navController;


    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe,parent,false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.title.setText(recipe.getTitle());
        holder.dishType.setText(recipe.getDishType());
        holder.prepTime.setText(Integer.toString(recipe.getReadyIn()) + " min");

        holder.cardView.setOnClickListener(view -> {
            args = new Bundle();
            args.putInt("id",recipe.getId());
            args.putString("image",recipe.getImage());
            args.putString("recipe_title",recipe.getTitle());
            args.putInt("prep_time",recipe.getReadyIn());
            args.putString("recipe_type", recipe.getDishType());
            args.putString("recipe_image", recipe.getImage());
            args.putString("recipe_src_url", recipe.getSourceUrl());
            args.putBoolean("isDairy",recipe.getDairyFree());
            args.putBoolean("isVegan",recipe.getVegan());
            args.putBoolean("isVegetarian",recipe.getVegetarian());
            args.putString("summary",recipe.getSummary());
            navController = Navigation.findNavController(view);
            navController.navigate(R.id.recipe_fragment,args);
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(final ArrayList<Recipe> recipeList){this.recipeList = recipeList; notifyDataSetChanged();}


    static class RecipeViewHolder extends RecyclerView.ViewHolder{
        private RecipeViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            dishType = itemView.findViewById(R.id.dishTypeTxt);
            prepTime = itemView.findViewById(R.id.preptimeTxt);
            cardView = itemView.findViewById(R.id.recipeCardView);




        }

        private final TextView title;
        private final TextView dishType;
        private final TextView prepTime;
        private final CardView cardView;

    }


}
