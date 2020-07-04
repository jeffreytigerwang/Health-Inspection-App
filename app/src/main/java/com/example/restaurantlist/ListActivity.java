package com.example.restaurantlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.Inspection;
import Model.InspectionManager;
import Model.Restaurant;
import Model.RestaurantsManager;

public class ListActivity extends AppCompatActivity {

    private RestaurantsManager restaurantsManager;
    private InspectionManager inspectionManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        restaurantsManager = RestaurantsManager.getInstance();
        inspectionManager = InspectionManager.getInstance();

        // add the restaurants to the RestaurantsManager
        if(restaurantsManager.getcount()==0)
        { addrestaurants();}

        pupulateListView();


    }

    private void pupulateListView() {
        ArrayAdapter<RestaurantsManager> adapter = new MyListAdapter();
        ListView listView;
        listView = findViewById(R.id.listvieww);
        listView.setAdapter(adapter);

    }

    private void addrestaurants() {

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2020, 1, 22},
                "Routine", 2,
                3, "Moderate",
                new String[]{"303,Critical,Equipment/facilities/hot & cold water for sanitary maintenance not adequate [s. 17(3); s. 4(1)(f)],Not Repeat",
                        "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat",},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeatt",
                        "311,Not Critical,Premises not maintained as per approved plans [s. 6(1)(b)],Not Repeat"}));


        restaurantsManager.add(new Restaurant("Lee Yuen Seafood Restaurant", "14755 104 Ave",
                "SWOD-AHZUMF", 122.8136896,
                49.19166808, "Surrey",
                "Restaurant", inspectionManager));


        restaurantsManager.setcount(2);



    }

    private class MyListAdapter extends ArrayAdapter<RestaurantsManager> {
        public MyListAdapter() {
            super(ListActivity.this, R.layout.item,restaurantsManager.get());
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //make sure we have a view to work with(may have been given null)
            View itemview = convertView;
            if (itemview == null) {
                itemview = getLayoutInflater().inflate(R.layout.item, parent, false);
            }

            //find the restaurantmanager to work with
            Restaurant currentrestaurant = restaurantsManager.get(position);
            int number;
            // the number of issues
            number=currentrestaurant.getInspections().get(0).getNumCritical()
                             + currentrestaurant.getInspections().get(0).getNumNonCritical();


              //fill the view
              TextView t1 = itemview.findViewById(R.id.text11);
              t1.setText(currentrestaurant.getRestaurantName());

              TextView t2= itemview.findViewById(R.id.text2);
              t2.setText("Issues Found: " + number);

              TextView t3= itemview.findViewById(R.id.text3);
              t3.setText("Inspection date: "+ currentrestaurant.getInspections().get(0).getInspectionDate()[0]+" "+
                      currentrestaurant.getInspections().get(0).getInspectionDate()[1]+" "+
                      currentrestaurant.getInspections().get(0).getInspectionDate()[2]);






              TextView t4= itemview.findViewById(R.id.text4);
              t4.setText("Hazard Level: " + currentrestaurant.getInspections().get(0).getColour());

              ImageView color = itemview.findViewById(R.id.hazard);
              switch(currentrestaurant.getInspections().get(0).getHazardRating()) {
                case "Low":
                    color.setImageResource(R.drawable.blue);
                    break;
                case "Moderate":
                    color.setImageResource(R.drawable.yellow);
                    break;
                default:
                    color.setImageResource(R.drawable.red);
            }





            return itemview;


        }


    }
}