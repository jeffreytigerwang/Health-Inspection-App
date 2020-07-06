package com.example.restaurantlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Model.Inspection;
import Model.InspectionManager;
import Model.Restaurant;
import Model.RestaurantsManager;

public class ListActivity extends AppCompatActivity {

    private RestaurantsManager restaurantsManager;
    private InspectionManager inspectionManager;
    public static final String INDEX = "index";



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

        registerClickCallback();

    }

    private void registerClickCallback(){
        //takes user to restaurant details when they click on a restaurant
        ListView list = findViewById(R.id.listvieww);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent intent = restaurantDetailsActivity.makeLaunchIntent(ListActivity.this);
                //Sends index of which lens was click on in ViewList
                intent.putExtra(INDEX, position);
                startActivity(intent);
            }
        });

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
                new String[]{"205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)],Not Repeat",
                        "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 11, 5},
                "Routine", 2,
                5, "High",
                new String[]{"203,Critical,Food not cooled in an acceptable manner [s. 12(a)],Not Repeat",
                        "205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "211,Not Critical,Frozen potentially hazardous food stored/displayed above -18 °C. [s. 14(3)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "309,Not Critical,Chemicals cleansers & similar agents stored or labeled improperly [s. 27],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 7, 29},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 7, 8},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));


        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 6, 24},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 6, 24},
                "Routine", 2,
                6, "High",
                new String[]{"302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "307,Not Critical,Equipment/utensils/food contact surfaces are not of suitable design/material [s. 16; s. 19],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 3, 1},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 2, 26},
                "Routine", 7,
                6, "High",
                new String[]{"201,Critical,Food contaminated or unfit for human consumption [s. 13],Not Repeat",
                        "202,Critical,Food not processed in a manner that makes it safe to eat [s. 14(1)],Not Repeat",
                        "205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)]",
                        "206,Critical,Hot potentially hazardous food stored/displayed below 60 °C. [s. 14(2)],Not Repeat",
                        "301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "210,Not Critical,Food not thawed in an acceptable manner [s. 14(2)],Not Repeat",
                        "304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 2, 1},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 1, 31},
                "Follow-Up", 3,
                1, "High",
                new String[]{"201,Critical,Food contaminated or unfit for human consumption [s. 13],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat",
                        "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat"},
                new String[]{"306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2019, 1, 3},
                "Follow-Up", 1,
                4, "High",
                new String[]{"201,Critical,Food contaminated or unfit for human consumption [s. 13],Not Repeat"},
                new String[]{"210,Not Critical,Food not thawed in an acceptable manner [s. 14(2)],Not Repeat",
                        "304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));


        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 12, 7},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 10, 30},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 10, 30},
                "Follow-Up", 1,
                0, "High",
                new String[]{"201,Critical,Food contaminated or unfit for human consumption [s. 13],Not Repeat"},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 10, 29},
                "Routine", 5,
                6, "High",
                new String[]{"201,Critical,Food contaminated or unfit for human consumption [s. 13],Not Repeat",
                        "301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat",
                        "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "502,Not Critical,In operator鈥檚 absence no staff on duty has FOODSAFE Level 1 or equivalent [s. 10(2)],Not Repeat"
                }));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 6, 29},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 6, 26},
                "Routine", 3,
                4, "High",
                new String[]{"205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)],Not Repeat",
                        "301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 3, 20},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-AHZUMF", new int[]{2018, 3, 19},
                "Routine", 6,
                8, "High",
                new String[]{"201,Critical,Food contaminated or unfit for human consumption [s. 13],Not Repeat",
                        "203,Critical,Food not cooled in an acceptable manner [s. 12(a)],Not Repeat",
                        "205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)],Not Repeat",
                        "301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "210,Not Critical,Food not thawed in an acceptable manner [s. 14(2)],Not Repeat",
                        "211,Not Critical,Frozen potentially hazardous food stored/displayed above -18 °C. [s. 14(3)],Not Repeat",
                        "304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "309,Not Critical,Chemicals cleansers & similar agents stored or labeled improperly [s. 27],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2019, 10, 2},
                "Routine", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2019, 4, 10},
                "Routine", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2018, 11, 6},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2018, 11, 1},
                "Follow-Up", 0,
                1, "Low",
                new String[]{""},
                new String[]{"308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2018, 10, 24},
                "Follow-Up", 0,
                1, "Low",
                new String[]{""},
                new String[]{"308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2018, 10, 19},
                "Routine", 0,
                1, "Low",
                new String[]{""},
                new String[]{"308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2018, 5, 8},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SDFO-8HKP7E", new int[]{2018, 5, 2},
                "Routine", 0,
                2, "Low",
                new String[]{""},
                new String[]{"308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "315,Not Critical,Refrigeration units and hot holding equipment lack accurate thermometers [s. 19(2)],Not Repeat"}));


        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 11, 5},
                "Routine", 1,
                1, "Low",
                new String[]{"301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 10, 1},
                "Follow-Up", 0,
                1, "Low",
                new String[]{""},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 9, 24},
                "Follow-Up", 3,
                2, "High",
                new String[]{"301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat",
                        "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "210,Not Critical,Food not thawed in an acceptable manner [s. 14(2)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 8, 27},
                "Follow-Up", 1,
                1, "Low",
                new String[]{"301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat"},
                new String[]{"210,Not Critical,Food not thawed in an acceptable manner [s. 14(2)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 7, 3},
                "Routine", 3,
                2, "High",
                new String[]{"203,Critical,Food not cooled in an acceptable manner [s. 12(a)],Not Repeat",
                        "301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 3, 18},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 3, 12},
                "Follow-Up", 0,
                1, "Low",
                new String[]{""},
                new String[]{"306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 1, 30},
                "Follow-Up", 1,
                1, "Low",
                new String[]{"401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-B7BNSR", new int[]{2019, 1, 15},
                "Follow-Up", 2,
                3, "High",
                new String[]{"301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "210,Not Critical,Food not thawed in an acceptable manner [s. 14(2)],Not Repeat",
                        "315,Not Critical,Refrigeration units and hot holding equipment lack accurate thermometers [s. 19(2)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8GPUJX", new int[]{2019, 10, 1},
                "Follow-Up", 1,
                0, "Low",
                new String[]{"302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat"},
                new String[]{""}));

        inspectionManager.add(new Inspection("SDFO-8GPUJX", new int[]{2019, 9, 26},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SDFO-8GPUJX", new int[]{2019, 9, 24},
                "Routine", 2,
                4, "High",
                new String[]{"205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)],Not Repeat",
                        "302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat"},
                new String[]{"304,Not Critical,Premises not free of pests [s. 26(a)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "502,Not Critical,In operator鈥檚 absence no staff on duty has FOODSAFE Level 1 or equivalent [s. 10(2)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8GPUJX", new int[]{2019, 3, 20},
                "Routine", 2,
                4, "Low",
                new String[]{"302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat",
                        "401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "311,Not Critical,Premises not maintained as per approved plans [s. 6(1)(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8GPUJX", new int[]{2018, 7, 18},
                "Routine", 1,
                4, "Low",
                new String[]{"401,Critical,Adequate handwashing stations not available for employees [s. 21(4)],Not Repeat"},
                new String[]{"305,Not Critical,Conditions observed that may allow entrance/harbouring/breeding of pests [s. 26(b)(c)],Not Repeat",
                        "306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "307,Not Critical,Equipment/utensils/food contact surfaces are not of suitable design/material [s. 16; s. 19],Not Repeat",
                        "315,Not Critical,Refrigeration units and hot holding equipment lack accurate thermometers [s. 19(2)],Not Repeat"}));

        inspectionManager.add(new Inspection("SDFO-8GPUJX", new int[]{2018, 3, 12},
                "Routine", 0,
                2, "Low",
                new String[]{""},
                new String[]{"308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "309,Not Critical,Chemicals cleansers & similar agents stored or labeled improperly [s. 27],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-APSP3X", new int[]{2019, 8, 30},
                "Routine", 1,
                0, "Low",
                new String[]{"205,Critical,Cold potentially hazardous food stored/displayed above 4 °C. [s. 14(2)],Not Repeat"},
                new String[]{""}));

        inspectionManager.add(new Inspection("SWOD-APSP3X", new int[]{2019, 3, 5},
                "Routine", 0,
                2, "Low",
                new String[]{""},
                new String[]{"306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat",
                        "403,Not Critical,Employee lacks good personal hygiene clean clothing and hair control [s. 21(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SWOD-APSP3X", new int[]{2018, 6, 25},
                "Routine", 2,
                3, "Moderate",
                new String[]{"303,Critical,Equipment/facilities/hot & cold water for sanitary maintenance not adequate [s. 17(3); s. 4(1)(f)],Not Repeat",
                        "402,Critical,Employee does not wash hands properly or at adequate frequency [s. 21(3)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "308,Not Critical,Equipment/utensils/food contact surfaces are not in good working order [s. 16(b)],Not Repeat",
                        "311,Not Critical,Premises not maintained as per approved plans [s. 6(1)(b)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-ANSLB4", new int[]{2019,12,16},
                "Routine", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SHEN-ANSLB4", new int[]{2019, 6, 24},
                "Routine", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SHEN-ANSLB4", new int[]{2019, 2, 27},
                "Routine", 1,
                0, "Low",
                new String[]{"301,Critical,Equipment/utensils/food contact surfaces not maintained in sanitary condition [s. 17(1)],Not Repeat"},
                new String[]{""}));

        inspectionManager.add(new Inspection("SHEN-ANSLB4", new int[]{2018, 8, 31},
                "Follow-Up", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SHEN-ANSLB4", new int[]{2018, 8, 23},
                "Routine", 1,
                0, "Low",
                new String[]{"302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat"},
                new String[]{"306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SHEN-ANSLB4", new int[]{2018, 3, 5},
                "Routine", 0,
                0, "Low",
                new String[]{""},
                new String[]{""}));

        inspectionManager.add(new Inspection("SPLH-9NEUHG", new int[]{2019,10,1},
                "Routine", 0,
                1, "Low",
                new String[]{""},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat"}));

        inspectionManager.add(new Inspection("SPLH-9NEUHG", new int[]{2019,4,10},
                "Routine", 1,
                1, "Low",
                new String[]{"206,Critical,Hot potentially hazardous food stored/displayed below 60 °C. [s. 14(2)],Not Repeat"},
                new String[]{"501,Not Critical,Operator does not have FOODSAFE Level 1 or Equivalent [s. 10(1)],Not Repeat"}));

        inspectionManager.add(new Inspection("SPLH-9NEUHG", new int[]{2018,10,29},
                "Routine", 1,
                2, "Low",
                new String[]{"206,Critical,Hot potentially hazardous food stored/displayed below 60 °C. [s. 14(2)],Not Repeat"},
                new String[]{"209,Not Critical,Food not protected from contamination [s. 12(a)],Not Repeat",
                        "501,Not Critical,Operator does not have FOODSAFE Level 1 or Equivalent [s. 10(1)],Not Repeat"}));
        inspectionManager.add(new Inspection("SPLH-9NEUHG", new int[]{2018,5,30},
                "Routine", 1,
                1, "Low",
                new String[]{"302,Critical,Equipment/utensils/food contact surfaces not properly washed and sanitized [s. 17(2)],Not Repeat"},
                new String[]{"306,Not Critical,Food premises not maintained in a sanitary condition [s. 17(1)],Not Repeat"}));
        
        restaurantsManager.add(new Restaurant("104 Sushi & Co.", "10422 168 St",
                "SWOD-APSP3X", -122.75625586,
                49.19205936, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("Lee Yuen Seafood Restaurant", "14755 104 Ave",
                "SWOD-AHZUMF", -122.8668064,
                49.20610961, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("Lee Yuen Seafood Restaurant", "1812 152 St",
                "SHEN-B7BNSR", -122.80086843,
                49.03508252, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("Pattullo", "12808 King George Blvd",
                "SDFO-8HKP7E", -122.8136896,
                49.19166808, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("The Unfindable Bar", "12345 67 Ave",
                "NOSU-CHNUM", -122.86815856,
                49.14214908, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("Top in Town Pizza", "12788 76A Ave",
                "SDFO-8GPUJX", -122.86855856,
                49.14218908, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("Top In Town Pizza", "14330 64 Ave",
                "SHEN-ANSLB4", -122.82521495,
                49.11851305, "Surrey",
                "Restaurant", inspectionManager));

        restaurantsManager.add(new Restaurant("Zugba Flame Grilled Chicken", "14351 104 Ave",
                "SPLH-9NEUHG", -122.82418348,
                49.19172759, "Surrey",
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
            Restaurant currentrestaurant = restaurantsManager.get(position);


         for (Restaurant tracking : restaurantsManager){
                if (tracking.getTrackingNumber().equals(currentrestaurant.getTrackingNumber())){
                    int temp = -1;
                    for (int i = 0; i < 55; i++){
                        if (inspectionManager.get(i).getTrackingNum().equals(tracking.getTrackingNumber())) {
                            temp = i;
                            break;
                        }
                    }

                    int number;
                    // the number of issues
                    if(temp==-1)
                    {
                       number=0;
                    }
                    else {
                        number = currentrestaurant.getInspections().get(temp).getNumCritical()
                                + currentrestaurant.getInspections().get(temp).getNumNonCritical();
                    }

                    //fill the view
                    TextView t1 = itemview.findViewById(R.id.text11);
                    t1.setText(currentrestaurant.getRestaurantName());

                    TextView t2= itemview.findViewById(R.id.text2);
                    t2.setText("Issues Found: " + number);

                    TextView t3= itemview.findViewById(R.id.text3);
                  if(temp==-1)
                  {
                      t3.setText("N/A");}
                  else
                    {
                        t3.setText("" + timefunction(currentrestaurant.getInspections().get(temp).getInspectionDate()[0],
                                currentrestaurant.getInspections().get(temp).getInspectionDate()[1],
                                currentrestaurant.getInspections().get(temp).getInspectionDate()[2]));
                    }


                    TextView t4= itemview.findViewById(R.id.text4);
                    if(temp==-1)
                    {
                        t4.setText("Hazard Level: blue " );
                    }
                    else
                    {  t4.setText("Hazard Level: " + currentrestaurant.getInspections().get(temp).getColour());}


                    ImageView color = itemview.findViewById(R.id.hazard);
                    if(temp==-1)
                    {
                        color.setImageResource(R.drawable.blue);
                    }
                    else{
                    switch(currentrestaurant.getInspections().get(temp).getHazardRating()) {
                        case "Low":
                            color.setImageResource(R.drawable.blue);
                            break;
                        case "Moderate":
                            color.setImageResource(R.drawable.yellow);
                            break;
                        default:
                            color.setImageResource(R.drawable.red);
                    }
                }
                //break;
            }}


            return itemview;


        }


    }

    //a time function in an intelligent format so that it's easier to understand than dates
   /** private String timefunction(int Year, int Month , int Day){

        // SOURCE: https://stackoverflow.com/questions/36370895/getyear-getmonth-getday-are-deprecated-in-calendar-what-to-use-then

        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            String inspectiondate = Integer.toString(Year) + Integer.toString(Month) + Integer.toString(Day);
            Date inspection = simpleDateFormat.parse(inspectiondate);
            Date currentDate = new Date();

            long diffInMonth = Math.abs(currentDate.getTime() - inspection.getTime());
            long diffInDay = TimeUnit.DAYS.convert(diffInMonth, TimeUnit.MILLISECONDS);
            //this.diffInDay = (int) diffInDay;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inspection);

            String m ;
            {
                switch(Month) {
                    case 1:
                        m="January";
                        break;
                    case 2:
                        m="February";
                        break;
                    case 3:
                        m="March";
                        break;
                    case 4:
                        m="April";
                        break;
                    case 5:
                        m="May";
                        break;
                    case 6:
                        m="June";
                        break;
                    case 7:
                        m="July";
                        break;
                    case 8:
                        m="August";
                        break;
                    case 9:
                        m="September";
                        break;
                    case 10:
                        m="October";
                        break;
                    case 11:
                        m="November";
                        break;
                    default:
                        m="December";
                }}

            if(diffInDay<=30)
            {
                String T = " days";
                String t = Long.toString(diffInDay);
                return t + T ;

            }

            else if(diffInDay <=365) {
                String space = " ";
                String d = Integer.toString(Day);

                return m + space + d;
            }

            else
            {
                String space = " ";
                String y = Integer.toString(Year);
                return m + space + y ;
            }


        }
        catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }

    }
*/
   //a time function in an intelligent format so that it's easier to understand than dates
   private String timefunction(int Year, int Month , int Day){
       Calendar calendar = Calendar.getInstance();
       SimpleDateFormat simpleyearFormat = new SimpleDateFormat("yyyy");
       SimpleDateFormat simplemonthFormat = new SimpleDateFormat("MM");
       SimpleDateFormat simpledayFormat = new SimpleDateFormat("dd");

       //get year
       String yeartime = simpleyearFormat.format(calendar.getTime());
       int year = Integer.parseInt(yeartime);
       //get month
       String monthtime =  simplemonthFormat.format(calendar.getTime());
       int  month = Integer.parseInt(monthtime);
       //get day
       String daytime = simpledayFormat.format(calendar.getTime());
       int  day = Integer.parseInt(daytime);

       //calculate days
       int amount1 = (Year-2015)*365 + (Month-1)*30 + Day;   // inspection time
       int amount2 = (year-2015)*365 + (month-1)*30 + day;   // current time
       String m ;
       {
           switch(Month) {
               case 1:
                   m="January";
                   break;
               case 2:
                   m="February";
                   break;
               case 3:
                   m="March";
                   break;
               case 4:
                   m="April";
                   break;
               case 5:
                   m="May";
                   break;
               case 6:
                   m="June";
                   break;
               case 7:
                   m="July";
                   break;
               case 8:
                   m="August";
                   break;
               case 9:
                   m="September";
                   break;
               case 10:
                   m="October";
                   break;
               case 11:
                   m="November";
                   break;
               default:
                   m="December";
           }}



       if(amount2-amount1<=30)
       {
           String T = " days";
           String t = Integer.toString(amount2-amount1);
           return t + T ;

       }

       if(amount2-amount1>30&&amount2-amount1<=365) {
           String space = " ";
           String d = Integer.toString(Day);

           return m + space + d;
       }

       else
       {
           String space = " ";
           String y = Integer.toString(Year);
           return m + space + y ;

       }














   }






























}


