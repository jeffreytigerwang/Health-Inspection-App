package com.example.restaurantlist.UI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;

public class searchActivity extends AppCompatActivity {

    private Button searchBtn;
    private Button clearBtn;
    private EditText searchField;
    private EditText ViolationCountField;
    private Spinner favoriteSpinner;
    private Spinner hazardSpinner;
    private Spinner violationSpinner;
    private RestaurantsManager manager = RestaurantsManager.getInstance();

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBtn = findViewById(R.id.search_search);
        clearBtn = findViewById(R.id.search_clear);
        searchField = findViewById(R.id.search_userEnter);
        ViolationCountField = (EditText) findViewById(R.id.count_text_search);


        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDefaultIntent();

        Button searchBtn = findViewById(R.id.search_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                finish();
            }
        });
        hazardSpinner = (Spinner) findViewById(R.id.hazard_level);

    }
    private void setDefaultIntent() {
        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
    }


    private void search(){

    }
    private void clear() {
        manager.setSearchIntent("");
        manager.setSearchHazardLevel(0);
        manager.setSearchViolationNumberEquality(0);
        manager.setSearchViolationBound(-1);
        manager.setSearchFavourites(false);
    }
}
