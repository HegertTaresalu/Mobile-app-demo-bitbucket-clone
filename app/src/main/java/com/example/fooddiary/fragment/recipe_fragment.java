package com.example.fooddiary.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fooddiary.R;
import com.example.fooddiary.ViewModel.BrowserViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;
//import com.squareup.picasso.Picasso;

public class recipe_fragment extends Fragment {

    TextView title;
    ImageView image;
    TextView dishType;
    TextView sourceUrl;
    TextView prepTime;
    TextView isDairyFree;
    TextView isVegetarian;
    TextView isVegan;
    Bundle args;
    Button button;
    NavController navController;
    BrowserViewModel browserViewModel;
    TextView summary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }





    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.drawer_browser));
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        setHasOptionsMenu(true);

        button = view.findViewById(R.id.calendarBtn);
        dishType = view.findViewById(R.id.recipeDishTypeTxt);
        title = view.findViewById(R.id.recipeTitleTxt);
        image = view.findViewById(R.id.recipeImg);
        sourceUrl = view.findViewById(R.id.recipeSrcUrl);
        prepTime = view.findViewById(R.id.recipePrepTimeTxt);
        isDairyFree = view.findViewById(R.id.recipeIsDairyFree);
        isVegetarian = view.findViewById(R.id.isVegetarian);
        isVegan = view.findViewById(R.id.isVeganTxt);
        summary = view.findViewById(R.id.summaryTxt);
        browserViewModel = new ViewModelProvider(this).get(BrowserViewModel.class);

        args = getArguments();
        dishType.setText(args.getString("recipe_type"));
        title.setText(args.getString("recipe_title"));
        sourceUrl.setClickable(true);
        sourceUrl.setMovementMethod(LinkMovementMethod.getInstance());
        String url = args.getString("recipe_src_url");

        String text = "<a href ="+url+">Link</a>";
        sourceUrl.setText(Html.fromHtml(text));
        Picasso.get().load(args.getString("image")).into(image);
        prepTime.setText("Ready in " + args.getInt("prep_time") + " min");
        isDairyFree.setText("Does contain dairy: " + args.getBoolean("isDairy"));
        isVegetarian.setText("Is vegetarian: " + args.getBoolean("isVegetarian"));
        isVegan.setText("Is vegan: " + args.getBoolean("isVegan"));
        summary.setClickable(true);
        summary.setMovementMethod(LinkMovementMethod.getInstance());
        summary.setText(Html.fromHtml(args.getString("summary")));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        button.setOnClickListener(view1 -> {
            navController.navigate(R.id.action_recipe_fragment_to_calendarFragment,args);
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("item", String.valueOf(item.getItemId()));
        Log.i("saverecioe", String.valueOf(R.id.save_recipe));
        switch (item.getItemId()){

            case R.id.save_recipe:

            Runnable data = () -> browserViewModel.addData(args);
            Thread addData = new Thread(data);
            addData.start();

            break;
            case R.id.delete_recipe:

            Runnable data_ = () -> browserViewModel.deleteData(args.getInt("id"));
            Thread addData_ = new Thread(data_);
            addData_.start();

            break;

        }
        return super.onOptionsItemSelected(item);
    }
}