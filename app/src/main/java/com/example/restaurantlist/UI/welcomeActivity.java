package com.example.restaurantlist.UI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.Model.requestHttp;
import com.example.restaurantlist.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.restaurantlist.UI.DownloadDataActivity.method;

public class welcomeActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int count=0;

    private RestaurantsManager restaurantsManager;
    private InspectionManager inspectionManager;
    private static final String updatedalready = "updated";

    private static final String TAG="welcomeActivity";
    private static final int ERROR_DIALOG_REQUEST=9001;

    public static Intent makeLaunchIntent(Context c, String mes) {
        Intent i = new Intent(c, ListActivity.class);
        i.putExtra(updatedalready, mes);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         restaurantsManager = RestaurantsManager.getInstance();
         inspectionManager = InspectionManager.getInstance();


        readCSVinspections();
        sortInspectionByName();
        readCSVrestaurant();
        sortRestaurantsByName();


        //hide hideNavigationBar, let it full screen.
        hideNavigationBar();



        if(isServicesOK()){
            init();
        }


    }
    private void init(){
        //set up the ProgressBar
        setupprog();

    }
    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking google services version");
        int availalve = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(welcomeActivity.this);
        if(availalve== ConnectionResult.SUCCESS){
            Log.d(TAG,"isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(availalve)){
            Log.d(TAG,"isServicesOK: an error occured but we can fix it");
            Dialog dialog =GoogleApiAvailability.getInstance().getErrorDialog(welcomeActivity.this,availalve,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else {
            Toast.makeText(this,"You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void setupprog() {
        progressBar=findViewById(R.id.pb);

        final Timer time = new Timer();
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {

                count++;
                progressBar.setProgress(count);

                if(count == 100)
                {

                    time.cancel();
                    Intent intent= new Intent(welcomeActivity.this, MapsActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

        };

        time.schedule(timerTask,0,15);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideNavigationBar() {
        //Code found at [https://www.youtube.com/watch?v=cMVbpbaDwTo]
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }

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



    }


}
