package com.example.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.BaseAdapter;

import Model.Inspection;
import Model.InspectionManager;
import Model.Restaurant;
import Model.RestaurantsManager;

public class ListActivity extends AppCompatActivity {

    private RestaurantsManager restaurantsManager;
    private BaseAdapter adapter;
    private InspectionManager inspectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        //add the restaurants to the RestaurantsManager
        addrestaurants();






    }

    private void addrestaurants() {

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2020, 1, 22},
                                              "Routine",2,
                                               3,"Moderate",
                new String[]{"303,Critical,Equipment/facilities/hot & cold water for sanitary maintenance not adequate [s. 17(3); s. 4(1)(f)],Not Repeat",
                             "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat",},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                             "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeatt",
                             "311,Not Critical,Premises not maintained as per approved plans [s. 6(1)(b)],Not Repeat"}));
        

        restaurantsManager.add(new Restaurant("Lee Yuen Seafood Restaurant", "14755 104 Ave",
                                              "SWOD-AHZUMF", 122.8136896,
                                              49.19166808, "Surrey",
                                              "Restaurant", inspectionManager));







    }
}
