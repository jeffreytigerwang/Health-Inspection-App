package com.example.restaurantlist.UI;


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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.ParseCSV;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Shows the list of stored restaurants
public class ListActivity extends AppCompatActivity {

    SharedPreferences mSharedPreferences;

    private static final String EXTRA_MESSAGE = "Extra";
    private RestaurantsManager manager;
    private int size = 0;
    private String[] restaurantStrings = new String[size];

    List<Restaurant> restaurants = new ArrayList<>();
    List<Restaurant> updatedRestaurants = new ArrayList<>();

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, ListActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            populateListView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        favourite();
        compareForUpdate();

        registerClickCallback();
    }

    private void compareForUpdate() {
        if (!updatedRestaurants.isEmpty()) {
            populateUpdatedRestaurants();
        } else {
            launchMap();
        }
    }

    private void populateUpdatedRestaurants() {

        final Button okButton = findViewById(R.id.button_ok_main);
        okButton.setVisibility(View.VISIBLE);

        restaurantStrings = new String[size];



        restaurants = updatedRestaurants;

        for (Restaurant temp : restaurants) {
            System.out.println("Test> " + temp.getRestaurantName());
        }

        ArrayAdapter<Restaurant> adapter = new RestaurantAdapter();
        ListView restaurantList = findViewById(R.id.listViewMain);
        restaurantList.setAdapter(adapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                okButton.setVisibility(View.INVISIBLE);

                // Launch map as soon as we populate the list of restaurants in instance
                launchMap();
            }
        });
    }
    private void favourite(){
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
                            updatedRestaurants.add(restaurant);
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

    private void launchMap() {
        Intent i1 = new Intent(this, MapsActivity.class);
        startActivityForResult(i1, 42);

    }

    private void populateListView() throws FileNotFoundException {
        manager = RestaurantsManager.getInstance();
        populateManager();


        if (manager.getRestaurants().isEmpty()) {

            restaurantStrings = new String[1];
            restaurantStrings[0] = getResources().getString(R.string.greeting);

            // Build Adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                    this,           // Context for view
                    R.layout.layout_list,     // Layout to use
                    restaurantStrings);               // Items to be displayed

            ListView restaurantList = findViewById(R.id.listViewMain);
            restaurantList.setAdapter(adapter);

        } else {
            restaurantStrings = new String[size];


            restaurants = manager.getRestaurants();
            ArrayAdapter<Restaurant> adapter = new RestaurantAdapter();
            ListView restaurantList = findViewById(R.id.listViewMain);
            restaurantList.setAdapter(adapter);
        }
    }

    private void repopulateListView() {
        manager = RestaurantsManager.getInstance();


        if (manager.getRestaurants().isEmpty()) {

            restaurantStrings = new String[1];
            restaurantStrings[0] = getResources().getString(R.string.greeting);

            // Build Adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                    this,           // Context for view
                    R.layout.layout_list,     // Layout to use
                    restaurantStrings);               // Items to be displayed

            ListView restaurantList = findViewById(R.id.listViewMain);
            restaurantList.setAdapter(adapter);

        } else {
            restaurantStrings = new String[size];

            restaurants = manager.getRestaurants();
            ArrayAdapter<Restaurant> adapter = new RestaurantAdapter();
            ListView restaurantList = findViewById(R.id.listViewMain);
            restaurantList.setAdapter(adapter);
        }
    }
    private void populateManager() throws FileNotFoundException {
        File file = method(ListActivity.this,"restaurants_itr1.csv");

        Intent i_receive = getIntent();
        String file_status = i_receive.getStringExtra(EXTRA_MESSAGE);

        InputStream is1 = null;
        if (file_status != null) {
            if (file_status.equals("OLD")) {
                is1 = getResources().openRawResource(R.raw.restaurants_itr1);
            } else {
                is1 = new FileInputStream(file);
            }
        } else {
            is1 = new FileInputStream(file);
        }

        ParseCSV csv = new ParseCSV(is1);

        // start row index at 1 to ignore the titles
        for (int row = 1; row < csv.getRowSize(); row++) {
            Restaurant restaurant = new Restaurant(
                    csv.getVal(row, 1).replace("\"", ""),
                    csv.getVal(row, 2).replace("\"", ""),
                    csv.getVal(row, 3).replace("\"", ""),
                    Float.valueOf(csv.getVal(row, 5)),
                    Float.valueOf(csv.getVal(row, 6)),
                    csv.getVal(row, 0).replace("\"", ""));

            manager.add(restaurant);

            mSharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
            Set<String> favourite = new HashSet<String>(mSharedPreferences.getStringSet("Favourite:", new HashSet<String>()));
            for (String s : favourite) {
                        Gson g = new Gson();
                        Restaurant previousRestaurant = g.fromJson(s, Restaurant.class);
                        if (previousRestaurant.getTrackingNumber()==restaurant.getTrackingNumber()) {
                            System.out.println("Test> " + previousRestaurant.getTrackingNumber() + " and " + restaurant.getTrackingNumber());
                            restaurant.setCheckFavourite(true);

                    }
                }
            }



        populateWithInspections();

        Collections.sort(manager.getRestaurants(), new Comparator<Restaurant>() {
            @Override
            public int compare(Restaurant o1, Restaurant o2) {
                return o1.getRestaurantName().compareTo(o2.getRestaurantName());
            }
        });

    }

    private void populateWithInspections() throws FileNotFoundException {
        File file2 = method(ListActivity.this,"inspectionreports_itr1.csv");

        Intent i_receive = getIntent();
        String file_status = i_receive.getStringExtra(EXTRA_MESSAGE);
        InputStream is2 = null;
        if (file_status != null) {
            if (file_status.equals("OLD")) {
                is2 = getResources().openRawResource(R.raw.inspectionreports_itr1);
            } else {
                is2 = new FileInputStream(file2);
            }
        } else {
            is2 = new FileInputStream(file2);
        }
        ParseCSV csv2 = new ParseCSV(is2);
        String viol = "";


        for (int row = 1; row < csv2.getRowSize(); row++) {
            Inspection inspect;

            // error handling: check for valid csv file lines
            if (csv2.getVal(row, 0).equals("")) {
                break;
            }

            // multiple violations,
            // so concatenate the strings
            else if (csv2.getColSize(row) > 7) {

                for (int col = 5; col < csv2.getColSize(row) - 1; col++) {
                    viol += csv2.getVal(row, col) + " ";
                }

                inspect = new Inspection(
                        csv2.getVal(row, 0),
                        csv2.getVal(row, 1),
                        csv2.getVal(row, 2),
                        Integer.valueOf(csv2.getVal(row, 3)),
                        Integer.valueOf(csv2.getVal(row, 4)),
                        csv2.getVal(row, csv2.getColSize(row) - 1),
                        viol);


                viol = "";

                for (Restaurant restaurant : manager) {
                    if (inspect.getTrackingNum().equals(restaurant.getTrackingNumber())) {
                        restaurant.inspections.add(inspect);
                        break;
                    }
                }
            }

            else {
                inspect = new Inspection(
                        csv2.getVal(row, 0),
                        csv2.getVal(row, 1),
                        csv2.getVal(row, 2),
                        Integer.valueOf(csv2.getVal(row, 3)),
                        Integer.valueOf(csv2.getVal(row, 4)),
                        csv2.getVal(row, 6),
                        csv2.getVal(row, 5).replace("\"", ""));


                for (Restaurant restaurant : manager) {
                    if (inspect.getTrackingNum().equals(restaurant.getTrackingNumber())) {
                        restaurant.inspections.add(inspect);
                        break;
                    }
                }
            }


        }

        for (Restaurant restaurant : manager) {
            Collections.sort(restaurant.inspections, new Comparator<Inspection>() {
                @Override
                public int compare(Inspection o1, Inspection o2) {
                    return o2.getTestDate().compareTo(o1.getTestDate());
                }
            });
        }
    }



    private class RestaurantAdapter extends ArrayAdapter<Restaurant> {

        public RestaurantAdapter() {
            super(ListActivity.this, R.layout.item, restaurants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with
            View itemView = convertView;

            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item, parent, false);
            }

            // Find the restaurant to work with.
            final Restaurant currentRestaurant = restaurants.get(position);
            final ImageView isFavourite=itemView.findViewById(R.id.favouritebtn);
            if(currentRestaurant.isCheckFavourite()){
                isFavourite.setImageResource(R.drawable.star_open);
            }
            else {
                isFavourite.setImageResource(R.drawable.star_close);
            }
            isFavourite.setTag(position);
            isFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Restaurant currentRestaurant = restaurants.get((Integer) view.getTag());
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



            // Fill the view
            ImageView logo = itemView.findViewById(R.id.item_restaurantLogo);
            logo.setImageResource(currentRestaurant.getIcon());



            TextView restaurantName = itemView.findViewById(R.id.item_restaurantName);
            String temp = currentRestaurant.getRestaurantName();
            if (temp.length() > 30) {
                restaurantName.setText(temp.substring(0, 30) + "...");
            } else {
                restaurantName.setText(temp);
            }


            Inspection mostRecentInspection = currentRestaurant.getInspection(0);
            if (mostRecentInspection != null) {

                int totalIssues = mostRecentInspection.getNumNonCritical() + mostRecentInspection.getNumCritical();
                TextView totIssues = itemView.findViewById(R.id.list_totalIssues);
                totIssues.setText(getString(R.string.Issues_Found) + totalIssues);

                TextView hazardLevel = itemView.findViewById(R.id.list_hazard_rating);
                hazardLevel.setText(getString(R.string.Hazard_Level) + mostRecentInspection.getHazardRating());

                TextView data = itemView.findViewById(R.id.data);
                hazardLevel.setText(getString(R.string.Date) + mostRecentInspection.dateFormatter() );


                ImageView hazard = itemView.findViewById(R.id.item_hazard);
                hazard.setImageResource(mostRecentInspection.getHazardIcon());


            }
            else{
                TextView totIssues = itemView.findViewById(R.id.list_totalIssues);
                totIssues.setText(getString(R.string.Issues_Found) + " 0");

                TextView hazardLevel = itemView.findViewById(R.id.list_hazard_rating);
                hazardLevel.setText(getString(R.string.Hazard_Level) + " Low");


                ImageView hazard = itemView.findViewById(R.id.item_hazard);
                hazard.setImageResource(R.drawable.blue);



            }
            return itemView;
        }

    }


    private void registerClickCallback() {
        ListView list = findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (restaurantStrings.length == 0) {
                    String message = manager.getRestaurants().get(position).toString();

                    Intent intent = restaurantDetailsActivity.makeLaunchIntent(ListActivity.this, "RestaurantActivity");
                    intent.putExtra("Extra", message);
                    ListActivity.this.startActivityForResult(intent, 45);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 42:
                int answer = data.getIntExtra("result", 0);
                if (answer == 1) {
                    this.finish();
                } else {
                    repopulateListView();
                }
                break;

            case 45:
                int ans = data.getIntExtra("result", 0);
                String id = data.getStringExtra("resID");
                // 1 = launch map with peg
                // 0 = return on back
                if(ans == 1) {
                    Intent i2 = MapsActivity.makeLaunchIntent(ListActivity.this, "MapsActivity");
                    String message = id;
                    i2.putExtra("Extra", message);
                    ListActivity.this.startActivityForResult(i2, 42);
                }
                break;
            case 58:
                repopulateListView();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.main_map_icon):
                Intent intent = new Intent(ListActivity.this, MapsActivity.class);
                startActivityForResult(intent, 42);
                return true;
            case (R.id.main_search_icon):
                Intent i3 = new Intent(ListActivity.this, searchActivity.class);
                startActivityForResult(i3, 58);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static File method(Context obj, String filename){
        return new File (obj.getFilesDir(), filename );
    }

    @Override
    public void onResume()
    {
        super.onResume();
        repopulateListView();

    }

}
