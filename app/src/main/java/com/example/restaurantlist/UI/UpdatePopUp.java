package com.example.restaurantlist.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.restaurantlist.UI.MenuActivity.CHECK;
import static com.example.restaurantlist.UI.MenuActivity.DATE;
import static com.example.restaurantlist.UI.MenuActivity.URLdata;


public class UpdatePopUp extends Activity {



    private static final String FILE_NAME = "restaurants.txt";

    public static String dataURL;
    private RequestQueue mQueue;
    private String date;

    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, UpdatePopUp .class);
        return intent;
    }
    TextView cunt;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8),(int) (height*0.6));

        mQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        date = intent.getStringExtra(DATE);
        dataURL = intent.getStringExtra(URLdata);

        cunt = findViewById(R.id.fatcunt);

        Button later = findViewById(R.id.later);
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuActivity.makeLaunchIntent(UpdatePopUp.this);
                intent.putExtra(CHECK, 1);
                startActivity(intent);
            }
        });

        Button now = findViewById(R.id.now);
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });


    }

    private void readData() {


        final String url = dataURL;
        //Toast toast = Toast.makeText(getApplicationContext() ,url ,Toast.LENGTH_SHORT);
        //toast.show();

        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();

                    UpdatePopUp.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast toast = Toast.makeText(getApplicationContext() , myResponse, LENGTH_LONG);
                            //toast.show();
                             try{
                                 //cunt.setText(myResponse);
                                 //String[] lines = myResponse.split(System.getProperty("line.separator"));
                                 //FileOutputStream fOut = openFileOutput(FILE_NAME,MODE_PRIVATE);
                                 //fOut.write(myResponse.getBytes());
                                 //fOut.close();
                                 //File fileDir =new File(getFilesDir(),FILE_NAME);
                                 //Toast.makeText(getBaseContext(), ""+fileDir, LENGTH_LONG ).show();

                                 //Intent intent = MenuActivity.makeLaunchIntent(UpdatePopUp.this);
                                 //intent.putExtra(CHECK, 1);
                                 //startActivity(intent);
                                 //(lines);
                                 //Toast.makeText(getBaseContext(), ""+ lines.length, LENGTH_LONG ).show();
/*
                                 for(int i = 0 ; i<lines.length; i++){
                                     if(lines[i].indexOf('"') != -1) {
                                         //Toast.makeText(getBaseContext(), ""+ "EOFHWOEF" , Toast.LENGTH_SHORT ).show();

                                         int firstIndex = lines[i].indexOf('"');
                                         int commalocation = lines[i].indexOf(',', firstIndex);

                                         lines[i] = lines[i].substring(0, commalocation) + lines[i].substring(commalocation+1);
                                         lines[i] = lines[i].substring(0,firstIndex) + lines[i].substring(firstIndex+1);
                                         firstIndex = lines[i].indexOf('"');
                                         lines[i] = lines[i].substring(0,firstIndex) + lines[i].substring(firstIndex+1);
                                         //Toast.makeText(getBaseContext(), ""+ lines[i], Toast.LENGTH_SHORT ).show();


                                     }

                                     String[] tokens = lines[i].split(",");
                                     Toast.makeText(getBaseContext(), ""+ lines[i] , Toast.LENGTH_SHORT ).show();
                                     //Toast.makeText(getBaseContext(), ""+ tokens[1] , LENGTH_LONG ).show();
                                     RestaurantsManager.getInstance().add(new Restaurant(tokens[1], tokens[2], tokens[0], Double.parseDouble(tokens[6]),
                                             Double.parseDouble(tokens[5]), tokens[3], tokens[4], InspectionManager.getInstance()));

                                 }
*/
                                obtainData(myResponse);
                             }catch(Exception e){
                                 e.printStackTrace();
                             }


                            RestaurantsManager.getInstance().setcount(1);
                            //RestaurantsManager.getInstance().setUpdate(date);
                            sortRestaurantsByName();
                            saveData();
                        }
                    });


                }
            }
        });






    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(RestaurantsManager.getInstance());
        editor.putString("RestaurantsManager", json);
        editor.apply();
    }

    private void obtainData(String result) {


        try {

            InputStream inputStream = new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8")));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );


            String line = "";
            reader.readLine();


            while (((line = reader.readLine()) != null)) {

                    if(line.indexOf('"') != -1) {
                        //Toast.makeText(getBaseContext(), ""+ "EOFHWOEF" , Toast.LENGTH_SHORT ).show();

                        int firstIndex = line.indexOf('"');
                        int commalocation = line.indexOf(',', firstIndex);

                        line = line.substring(0, commalocation) + line.substring(commalocation+1);
                        line = line.substring(0,firstIndex) + line.substring(firstIndex+1);
                        firstIndex = line.indexOf('"');
                        line = line.substring(0,firstIndex) + line.substring(firstIndex+1);
                        //Toast.makeText(getBaseContext(), ""+ lines[i], Toast.LENGTH_SHORT ).show();


                    }

                    String[] tokens = line.split(",");
                    //Toast.makeText(getBaseContext(), ""+ tokens[0] , LENGTH_LONG ).show();
                    //Toast.makeText(getBaseContext(), ""+ tokens[1] , LENGTH_LONG ).show();
                    RestaurantsManager.getInstance().add(new Restaurant(tokens[1], tokens[2], tokens[0], Double.parseDouble(tokens[6]),
                            Double.parseDouble(tokens[5]), tokens[3], tokens[4], InspectionManager.getInstance()));




            }
        }
        catch(Exception e){
                Log.wtf("myactivity", "error reading data file on line", e);
                e.printStackTrace();
            }


    }


    public void sortRestaurantsByName(){
        Comparator<Restaurant> compareByName = new Comparator<Restaurant>() { //Compares restaurant names
            @Override
            public int compare(Restaurant r1, Restaurant r2) {
                return r1.getRestaurantName().compareTo(r2.getRestaurantName());
            }
        };

        Collections.sort(RestaurantsManager.getInstance().get(), compareByName); //Sort arraylist
        RestaurantsManager.getInstance().setcount(5);
    }

}
