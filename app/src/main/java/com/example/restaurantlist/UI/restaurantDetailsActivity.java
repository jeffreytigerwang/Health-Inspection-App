package com.example.restaurantlist.UI;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Shows Restaurant details and list of inspections for that restaurant.
public class restaurantDetailsActivity extends AppCompatActivity {
    SharedPreferences mSharedPreferences;


    private RestaurantsManager manager;
    private Restaurant restaurant;
    private int size = 0;
    int indexofrestaurant;
    private String[] inspectionStrings = new String[size];
    private static final String EXTRA_MESSAGE = "Extra";
    private String restaurantString;    // Name of calling restaurant object
    private ArrayList<Inspection> inspectionList;

    List<Inspection> inspections = new ArrayList<>();

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, restaurantDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateInspectionList();
        registerClickCallback();
        setupDefaultIntent();
        favourite1();
        addFavourite();
    }



    private void setupDefaultIntent() {
        Intent i = new Intent();
        i.putExtra("result", 0);
        setResult(Activity.RESULT_OK, i);
    }

    private void goToMapsActivity() {
        Intent i = new Intent();
        i.putExtra("result", 1);
        i.putExtra("resID", restaurant.getTrackingNumber());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void populateInspectionList() {

        manager = RestaurantsManager.getInstance();

        processInspections();
        size = restaurant.getInspectionSize();
        inspectionStrings = new String[size];

        // Start populating string
        for (Inspection inspection : inspectionList) {
            inspections.add(inspection);
        }

        ArrayAdapter<Inspection> adapter = new CustomAdapter();
        ListView list = (ListView) findViewById(R.id.restaurant_view);
        list.setAdapter(adapter);
    }

    private class CustomAdapter extends ArrayAdapter<Inspection> {
        public CustomAdapter() {
            super(restaurantDetailsActivity.this, R.layout.layout_inspection_details, inspections);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.layout_inspection_details, parent, false);
            }

            Inspection currentInspection = inspections.get(position);


            TextView textView5 = (TextView) itemView.findViewById(R.id.inspectiontext);
            textView5.setText(currentInspection.toString());

            ImageView imageView = (ImageView) itemView.findViewById(R.id.inspectionimage);
            imageView.setImageResource(currentInspection.getHazardIcon());

            TextView textView = (TextView) itemView.findViewById(R.id.inspection_crit);
            textView.setText(getString(R.string.of_critical_issues_found) + currentInspection.getNumCritical());

            TextView textView2 = (TextView) itemView.findViewById(R.id.inspection_noncrit);
            textView2.setText(getString(R.string.of_non_critical_issues_found) + currentInspection.getNumNonCritical());

            TextView textView3 = (TextView) itemView.findViewById(R.id.inspection_long);
            textView3.setText( currentInspection.dateFormatter());

            TextView textView4 = (TextView) itemView.findViewById(R.id.inspection_hazard);
            textView4.setText(getString(R.string.number_Hazard_Level) + currentInspection.getHazardRating());

            return itemView;
        }

    }

    private void favourite1(){
        manager = RestaurantsManager.getInstance();
        mSharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        Set<String> favourite = new HashSet<String>(mSharedPreferences.getStringSet("Favourite:", new HashSet<String>()));

        ArrayList<String> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();

        for (String s : favourite) {
            for (Restaurant restaurant : manager) {
                if (restaurant.isCheckFavourite()) {
                    System.out.println( "Test> "+restaurant.toString() + " Favourite: " + restaurant.isCheckFavourite());
                    Gson g = new Gson();
                    Restaurant previousRestaurant = g.fromJson(s, Restaurant.class);
                    System.out.println(  "Test> "+restaurant.toString() + " Favourite: " + restaurant.isCheckFavourite());
                    String js = new Gson().toJson(restaurant);
                    if (previousRestaurant.getTrackingNumber()==restaurant.getTrackingNumber()) {
                        if (!s.equals(js)) {
                            //updatedRestaurants.add(restaurant);
                            System.out.println("Test> "+restaurant.toString() + " Favourite: " + restaurant.isCheckFavourite());
                            a.add(s);
                            b.add(js);
                        }
                    }
                }
            }
        }
        favourite.removeAll(a);
        favourite.addAll(b);
        mSharedPreferences.edit().putStringSet("Favourite:", favourite).apply();
    }


    private void addFavourite() {
        //final Restaurant currentRestaurant = restaurants.get(position);
        final ImageView isFavourite = findViewById(R.id.favouritebtn2);
        if(restaurant.isCheckFavourite()){
            isFavourite.setImageResource(R.drawable.star_open);
        }
        else {
            isFavourite.setImageResource(R.drawable.star_close);
        }
        //isFavourite.setTag(position);
        isFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restaurant currentRestaurant = restaurant;
                if(currentRestaurant.isCheckFavourite()){
                    currentRestaurant.setCheckFavourite(false);
                    isFavourite.setImageResource(R.drawable.star_close);
                    mSharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                    Set<String> favourite= new HashSet<String>(mSharedPreferences.getStringSet("favourite", new HashSet<String>()));
                    Gson g = new Gson();
                    String j = g.toJson(currentRestaurant);
                    favourite.remove(j);
                    mSharedPreferences.edit().putStringSet("favourite:", favourite).apply();
                }
                else{
                    currentRestaurant.setCheckFavourite(true);
                    isFavourite.setImageResource(R.drawable.star_open);
                    mSharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                    Set<String> favourite = new HashSet<String>(mSharedPreferences.getStringSet("favourite", new HashSet<String>()));
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    Gson g = new Gson();
                    String j = g.toJson(currentRestaurant);
                    favourite.add(j);
                    editor.putStringSet("favourite:", favourite).apply();

                }
            }
        });
    }



    private void processInspections() {

        // Receive message from MainActivity
        // Message contains details of selected Restaurant
        Intent intent2 = getIntent();
        restaurantString = intent2.getStringExtra(EXTRA_MESSAGE);

        // Find the Restaurant and assign it to our local Restaurant object
        for (Restaurant temp : manager) {
            if (temp.toString().equals(restaurantString)) {
                restaurant = temp;
            }
        }

        // Populate the list of inspections
        inspectionList = restaurant.getInspections();
        TextView name = findViewById(R.id.restaurant_name);
        name.setText(restaurant.getRestaurantName());

        TextView address = findViewById(R.id.address_resActivity);
        address.setText(restaurant.getAddress());

        TextView latLng = findViewById(R.id.latitude_resActivity);
        latLng.setText((float) restaurant.getLatitude() + ", " + (float) restaurant.getLongitude());

    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.restaurant_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.inspectiontext);
                String message = textView.getText().toString();

                String restaurantTracking = restaurant.getTrackingNumber();

                Intent intent = inspectionDetailsActivity.makeLaunchIntent(restaurantDetailsActivity.this, "InspectionActivity");
                intent.putExtra("Extra", message);
                intent.putExtra("Restaurant", restaurantTracking);
                startActivity(intent);
            }
        });

        TextView latLng = findViewById(R.id.latitude_resActivity);
        latLng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMapsActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.restaurant_map_icon):
                goToMapsActivity();
                return true;
            case (android.R.id.home):
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
