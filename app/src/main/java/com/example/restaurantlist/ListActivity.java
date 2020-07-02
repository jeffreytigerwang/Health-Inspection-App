package com.example.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.BaseAdapter;

import Model.Restaurant;
import Model.RestaurantsManager;

public class ListActivity extends AppCompatActivity {

    private RestaurantsManager restaurants;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //add the restaurants to the RestaurantsManager
        addrestaurants();








    }

    private void addrestaurants() {
        restaurants.add(new Restaurant());





    }
}
