package com.example.restaurantlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import Model.InspectionManager;
import Model.RestaurantsManager;

public class inspectionDetailsActivity extends AppCompatActivity {
    private int index;
    private static final String EXTRA_MESSAGE = "Extra-Message";

/***    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, inspectionDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }***/

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, inspectionDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        index = intent.getIntExtra(restaurantDetailsActivity.INDEX, -1);

        TextView name = findViewById(R.id.textSetDate);
        name.setText(InspectionManager.getInstance().get(index).getHazardRating());


        /***TextView address = findViewById(R.id.detail_address);
        address.setText(RestaurantsManager.getInstance().get(index).getAddress());

        TextView gps = findViewById(R.id.detail_gps);
        gps.setText("(" + RestaurantsManager.getInstance().get(index).getLatitude() + " , " + RestaurantsManager.getInstance().get(index).getLongitude() + ")");***/
    }



}