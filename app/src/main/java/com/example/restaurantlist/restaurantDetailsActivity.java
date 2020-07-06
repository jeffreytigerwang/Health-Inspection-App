package com.example.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Inspection;
import Model.InspectionManager;
import Model.RestaurantsManager;
import Model.inspectionListAdapter;

public class restaurantDetailsActivity extends AppCompatActivity {

    private int index;

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, restaurantDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Intent intent = getIntent();
        index = intent.getIntExtra(ListActivity.INDEX, -1);

        populateListView();

        //sets the texts
        TextView name = findViewById(R.id.detail_name);
        name.setText(RestaurantsManager.getInstance().get(index).getRestaurantName());

        TextView address = findViewById(R.id.detail_address);
        address.setText(RestaurantsManager.getInstance().get(index).getAddress());

        TextView gps = findViewById(R.id.detail_gps);
        gps.setText("(" + RestaurantsManager.getInstance().get(index).getLatitude() + " , " + RestaurantsManager.getInstance().get(index).getLongitude() + ")");
    }

    private void populateListView() {

        String idTag = RestaurantsManager.getInstance().get(index).getTrackingNumber();
        List<Inspection> inspectionsTemp = new ArrayList<>();

        //Finds the specific inspections for restaurant
        for(int i = 0; i< InspectionManager.getInstance().getList().size(); i++) {
            //checks if tracking numbers are the same
            if(idTag.equals(InspectionManager.getInstance().get(i).getTrackingNum())) {
                inspectionsTemp.add(InspectionManager.getInstance().get(i));
            }
        }



        ListView mListView = (ListView) findViewById(R.id.singleInspectionView);
        inspectionListAdapter adapter = new inspectionListAdapter(this, R.layout.details, (ArrayList<Inspection>) inspectionsTemp);
        mListView.setAdapter(adapter);

    }

}