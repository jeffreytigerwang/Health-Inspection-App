package com.example.restaurantlist.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;

/**
 *  this activity show all restaurants with some information
 */



public class ListActivity extends AppCompatActivity {

    private RestaurantsManager restaurantsManager;
    private InspectionManager inspectionManager;
    public static final String INDEX = "index";
    private static final String EXTRA_MESSAGE = "ExtraMessage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        restaurantsManager = RestaurantsManager.getInstance();
        inspectionManager = InspectionManager.getInstance();

        getSupportActionBar().setTitle("Restaurant List");
        ActionBar back = getSupportActionBar();
        back.setDisplayHomeAsUpEnabled(true);


        // add the restaurants to the RestaurantsManager
   /*     if(restaurantsManager.getcount()==0)
        {
            readCSVinspections();
            sortInspectionByName();
            readCSVrestaurant();
            sortRestaurantsByName();
        }*/

        populateListView();
        registerClickCallback();

    }

/*
    public void sortInspectionByName() {
        Comparator<Inspection> compareByTracking = new Comparator<Inspection>() { //Compares restaurant names
            @Override
            public int compare(Inspection i1, Inspection i2) {
                int c;
                c = i1.getTrackingNum().compareTo(i2.getTrackingNum());
                if (c==0){
                    c = i1.getTestdate().compareTo(i2.getTestdate());
                }
                return c;
            }

        };

        Collections.sort(inspectionManager.getList(), compareByTracking.reversed()); //Sort arraylist
    }


    public void sortRestaurantsByName() {
        Comparator<Restaurant> compareByName = new Comparator<Restaurant>() { //Compares restaurant names
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return r1.getRestaurantName().compareTo(r2.getRestaurantName());
            }
        };

        Collections.sort(restaurantsManager.get(), compareByName); //Sort arraylist
        restaurantsManager.setcount(5);
    }

    private void readCSVinspections() {
        InputStream is = getResources().openRawResource(R.raw.inspectionreports_itr1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));

        String line="";

        try {
            //step over headers
            reader.readLine();


            while (((line = reader.readLine()) != null)) {
                //Spilt by " , "
               
                String[] tokens = line.split(",",7);
                //read the data
                if(tokens[6].length()>0)
                { inspectionManager.add(new Inspection(tokens[0].replace("\"",""),
                        Integer.parseInt(tokens[1]),
                        tokens[2].replace("\"",""),
                        Integer.parseInt(tokens[3]),
                        Integer.parseInt(tokens[4]),
                        tokens[5].replace("\"",""),
                        tokens[6].replace("\"","") ));}
                else
                {   inspectionManager.add(new Inspection(tokens[0].replace("\"",""),
                        Integer.parseInt(tokens[1]),
                        tokens[2].replace("\"",""),Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]),
                        tokens[5].replace("\"",""),
                        ""));   }

            }
        } catch (IOException e) {
            Log.wtf("MyActivity","Error reading data file on line " + line,e);
            e.printStackTrace();
        }
    }

    private void readCSVrestaurant() {


        InputStream is = getResources().openRawResource(R.raw.restaurants_itr1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));

        String line="";

        try {
            //step over headers
            reader.readLine();


            while (((line = reader.readLine()) != null)) {
                //Spilt by " , "
                String[] tokens = line.split(",");
                //read the data
                restaurantsManager.add(new Restaurant(tokens[1].replace("\"",""),
                        tokens[2].replace("\"",""),
                        tokens[0].replace("\"",""),
                        Double.parseDouble(tokens[6].replace("\"","")),
                        Double.parseDouble(tokens[5].replace("\"","")),
                        tokens[3].replace("\"",""),
                        tokens[4].replace("\"",""),inspectionManager));


            }
        } catch (IOException e) {
            Log.wtf("MyActivity","Error reading data file on line " + line,e);
            e.printStackTrace();
        }



    }*/




    private void registerClickCallback(){
        //takes user to restaurant details when they click on a restaurant
        ListView list = findViewById(R.id.listvieww);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent intent = restaurantDetailsActivity.makeLaunchIntent(ListActivity.this, EXTRA_MESSAGE);
                //Sends index of which restaurant was click on in ViewList
                RestaurantsManager.getInstance().setCurrentRestaurant(position);
                startActivity(intent);
            }
        });

    }

    private void populateListView() {
        ArrayAdapter<RestaurantsManager> adapter = new MyListAdapter();
        ListView listView;
        listView = findViewById(R.id.listvieww);
        listView.setAdapter(adapter);

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

            inspectionManager = InspectionManager.getInstance();

            for (Restaurant tracking : restaurantsManager){
                if (tracking.getTrackingNumber().equals(currentrestaurant.getTrackingNumber())){
                    int temp = -1;
                    for (int i = 0; i < inspectionManager.getSize(); i++){
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
