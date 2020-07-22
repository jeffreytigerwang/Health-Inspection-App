package com.example.restaurantlist.UI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.Model.inspectionListAdapter;
import com.example.restaurantlist.R;

/**
 * show detail ( all inspections ) of a restaurant
 */




public class restaurantDetailsActivity extends AppCompatActivity {
    public static final String INDEX = "index";
    private int index;
    private InspectionManager inspectionManager;
    private String trackingNumber;
    private static final String EXTRA_MESSAGE = "ExtraMessage";


    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, restaurantDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


        index = RestaurantsManager.getInstance().getCurrentRestaurant();
        getSupportActionBar().setTitle("Restaurant Details");
        ActionBar back = getSupportActionBar();
        back.setDisplayHomeAsUpEnabled(true);


        //sets the texts
        TextView name = findViewById(R.id.detail_name);
        name.setText(RestaurantsManager.getInstance().get(index).getRestaurantName());

        TextView address = findViewById(R.id.detail_address);
        address.setText(RestaurantsManager.getInstance().get(index).getAddress());

        TextView gps = findViewById(R.id.detail_gps);
        gps.setText("(" + RestaurantsManager.getInstance().get(index).getLatitude() + " , " + RestaurantsManager.getInstance().get(index).getLongitude() + ")");

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MapsActivity.makeIntent(restaurantDetailsActivity.this,49.19205936);
                startActivity(intent);
            }
        });
        //this,RestaurantsManager.getInstance().get(index).getLatitude()

        trackingNumber = RestaurantsManager.getInstance().get(index).getTrackingNumber();

        populateListView();
        registerClickCallback();

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

    private void registerClickCallback(){
        //takes user to restaurant details when they click on a restaurant
        ListView mListView = (ListView) findViewById(R.id.singleInspectionView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                int i = 0;

                Intent intent = inspectionDetailsActivity.makeLaunchIntent(restaurantDetailsActivity.this);
                //Sends index of which inspection was click on in ViewList
                inspectionManager = InspectionManager.getInstance();
                for (Inspection inspection : inspectionManager){
                    if (trackingNumber.equals(inspection.getTrackingNum())) {
                        break;
                    }
                    else {
                        i++;
                    }

                }
                intent.putExtra(INDEX, position + i);
                startActivity(intent);
                int positioni = position + i;


                //extView textView = (TextView) viewClicked;
                //String message = "you clicked " + positioni;
                //Toast.makeText(restaurantDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

}