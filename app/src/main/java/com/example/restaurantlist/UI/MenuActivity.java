package com.example.restaurantlist.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class MenuActivity extends AppCompatActivity {


    private RequestQueue mQueue;
    private String recentDate;
    public static final String CHECK = "check";
    public static final String DATE = "date";
    public static final String IDATE = "Idate";
    public static final String IURLdata = "Iurl";
    private int check = 0;
    public static final String URLdata = "url";
    private String recentUpdateDate2 = "";
    private String updateInspData2 = "";

    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, MenuActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        check = intent.getIntExtra(CHECK, 0);

        //loadData();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            checkInspectionUpdate();

            if(check != 1) {
                RestaurantsManager.getInstance().clearList();
                InspectionManager.getInstance().clearInspectionList();
                checkUpdate();

            }

        }






        // create a btn that user can intend to ListActivity for the list of restaurant.
        final LinearLayout linearLayout;
        linearLayout=findViewById(R.id.menu);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackground(getDrawable(R.drawable.btn_list_item));
                Intent intent= new Intent(MenuActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });


    }


    private void checkUpdate(){
        String url = "http://data.surrey.ca/api/3/action/package_show?id=restaurants";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject result = response.getJSONObject("result");
                            JSONArray resources = result.getJSONArray("resources");

                            JSONObject resource = resources.getJSONObject(0);
                            String recentUpdateDate = resource.getString("last_modified");
                            String updateData = resource.getString("url");
                            recentDate = RestaurantsManager.getInstance().getUpdate();

                            String insp = InspectionManager.getInstance().getInspectionDate();


                            if(recentDate.equals("") || !recentDate.equals(recentUpdateDate) || !insp.equals(recentUpdateDate2)){


                                Intent intentDATE = UpdatePopUp.makeLaunchIntent(MenuActivity.this);
                                intentDATE.putExtra(IDATE, recentUpdateDate2);
                                intentDATE.putExtra(IURLdata, updateInspData2);
                                intentDATE.putExtra(DATE, recentUpdateDate);
                                intentDATE.putExtra(URLdata, updateData);
                                startActivity(intentDATE);

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

    private void checkInspectionUpdate(){

        final String urlInspection = "http://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlInspection, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {



                            JSONObject result = response.getJSONObject("result");
                            JSONArray resources = result.getJSONArray("resources");

                            JSONObject resource = resources.getJSONObject(0);
                            recentUpdateDate2 = resource.getString("last_modified");
                            updateInspData2 = resource.getString("url");


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("RestaurantsManager", "");
        String pson = sharedPreferences.getString("InspectionManager", "");

        RestaurantsManager obj  = gson.fromJson(json, RestaurantsManager.class);
        RestaurantsManager.getInstance().setRestaurantsManager(obj);

        InspectionManager pojb = gson.fromJson(pson,InspectionManager.class);
        InspectionManager.getInstance().setInspectionManager(pojb);

    }


}











