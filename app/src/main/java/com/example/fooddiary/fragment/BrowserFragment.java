package com.example.fooddiary.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddiary.R;
import com.example.fooddiary.RecipeAdapter;
import com.example.fooddiary.ViewModel.BrowserViewModel;

import java.util.Objects;

public class BrowserFragment extends Fragment {
    BrowserViewModel browserViewModel;

    private RecipeAdapter recipeAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.drawer_browser));
        View view = inflater.inflate(R.layout.fragment_browser,container,false);
        browserViewModel = new ViewModelProvider(this).get(BrowserViewModel.class);



        Runnable runnable = () ->  browserViewModel.getRecipeData();

        Thread bgThread = new Thread(runnable);
        bgThread.start();


        browserViewModel.getRecipeLiveData()
                .observe(getViewLifecycleOwner(),recipes ->
                        recipeAdapter.setRecipeList(recipes)
                );


        RecyclerView recyclerView = view.findViewById(R.id.recipeRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(false);
        recipeAdapter = new RecipeAdapter();
        recyclerView.setAdapter(recipeAdapter);



        return view;

    }
}