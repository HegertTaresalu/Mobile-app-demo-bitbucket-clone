package com.example.fooddiary.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fooddiary.R;

import java.util.Objects;

public class CalendarFragment extends Fragment implements View.OnClickListener {

    EditText title;
    EditText link;
    EditText personal;
    Button addRecipe;
    Bundle args;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.add_to_calendar));
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.etTitle);
        link = view.findViewById(R.id.etLink);
        personal = view.findViewById(R.id.etPersonal);


        title.setText(args.getString("recipe_title"));
        link.setText(args.getString("recipe_src_url"));
        view.findViewById(R.id.btnAddRecipe).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, link.getText().toString() + "\n\n" + personal.getText().toString());

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), getString(R.string.error_intent), Toast.LENGTH_SHORT).show();
        }
    }
}