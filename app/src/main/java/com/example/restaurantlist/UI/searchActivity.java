package com.example.restaurantlist.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;

//Lets user search for specific restaurants
public class searchActivity extends AppCompatActivity {

    //Search filters
    private EditText searchField;
    private EditText violationCountField;
    private Button searchBtn;
    private Button clearBtn;
    private Spinner hazardSpinner;
    private Spinner comparatorSpinner;
    private Spinner favouriteSpinner;
    private boolean searchFavourite;


    private RestaurantsManager manager = RestaurantsManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDefaultIntent();
        setupSearch();
    }

    private void setupSearch() {
        setupFields();
        setupButtons();
        setupSpinners();

    }

    private void setupFields() {
        searchField = (EditText) findViewById(R.id.search2);
        violationCountField = (EditText) findViewById(R.id.count_text_search);
    }

    private void setupButtons() {
        searchBtn = (Button) findViewById(R.id.btn_search);
        clearBtn = (Button) findViewById(R.id.clear_button_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSearch();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters();
                finish();
            }
        });
    }

    private void setupSpinners() {
        hazardSpinner = (Spinner) findViewById(R.id.hazard_spinner_search);
        ArrayAdapter<CharSequence> hazardAdapter = ArrayAdapter.createFromResource(this,
                R.array.hazard_level_array, android.R.layout.simple_spinner_dropdown_item);
        hazardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hazardSpinner.setAdapter(hazardAdapter);
        hazardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manager.setHazardLevelFilter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                manager.setHazardLevelFilter(0);
            }
        });

        comparatorSpinner = (Spinner) findViewById(R.id.count_hazard_spinner_search);
        ArrayAdapter<CharSequence> comparatorAdapter = ArrayAdapter.createFromResource(this,
                R.array.comparator, android.R.layout.simple_spinner_dropdown_item);
        comparatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comparatorSpinner.setAdapter(comparatorAdapter);
        comparatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manager.setComparator(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                manager.setComparator(0);
            }
        });
        favouriteSpinner=(Spinner)findViewById(R.id.favourite);
        ArrayAdapter<CharSequence> favouriteAdapter = ArrayAdapter.createFromResource(this,
                R.array.FavoriteChoose, android.R.layout.simple_spinner_dropdown_item);
        favouriteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        favouriteSpinner.setAdapter(favouriteAdapter);
        favouriteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               boolean check=position!=0;
               searchFavourite=check;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchFavourite=false;
            }
        });
    }


    private void submitSearch() {
        updateViolationCountRestriction();
        String searchTerm = searchField.getText().toString();
        manager.setSearchTerm(searchTerm);
        manager.setSearchFavourite(searchFavourite);
        this.finish();
    }


    private void clearFilters() {
        manager.setSearchTerm("");
        manager.setHazardLevelFilter(0);
        manager.setComparator(0);
        manager.setSearchFavourite(false);

    }

    private void setDefaultIntent() {
        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
    }

    private void updateViolationCountRestriction() {
        try {
            int limit = Integer.parseInt(violationCountField.getText().toString());
            manager.setViolationLimit(limit);
        }
        catch (Exception e) {}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action back Home/Up buttons
        switch (item.getItemId()) {
            case (android.R.id.home):
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
