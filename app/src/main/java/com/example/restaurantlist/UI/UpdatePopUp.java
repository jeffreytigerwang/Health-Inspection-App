package com.example.restaurantlist.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;

import static com.example.restaurantlist.UI.MenuActivity.CHECK;
import static com.example.restaurantlist.UI.MenuActivity.DATE;
import static com.example.restaurantlist.UI.MenuActivity.URLdata;


public class UpdatePopUp extends Activity {

    private RestaurantsManager restaurantsManager;
    private InspectionManager inspectionManager;

    private static final String FILE_NAME = "restaurants.txt";

    public static String dataURL;
    private RequestQueue mQueue;
    private String date;
    private String data;
    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, UpdatePopUp .class);
        return intent;
    }

    TextView test;

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
                //RestaurantsManager.getInstance().clearList();
                //RestaurantsManager.getInstance().setUpdate(date);

                saveData();
            }
        });


    }

    private void saveData() {

        //test.setText(date.toString());

        String url = dataURL;
        //test = findViewById(R.id.texttest);
        //test.setText(dataURL);
/*
        try {

            URL url = new URL(dataURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            reader.readLine();

            while (((line = reader.readLine()) != null)) {

                //Spilt by " , "
                String[] tokens = line.split(",");
                //read the data
                restaurantsManager.add(new Restaurant(tokens[1].replace("\"", ""),
                        tokens[2].replace("\"", ""),
                        tokens[0].replace("\"", ""),
                        Double.parseDouble(tokens[6].replace("\"", "")),
                        Double.parseDouble(tokens[5].replace("\"", "")),
                        tokens[3].replace("\"", ""),
                        tokens[4].replace("\"", ""), inspectionManager));


            }


            startActivity(new Intent(UpdatePopUp.this, ListActivity.class));
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
*/
/*
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(url.getBytes());

            loadData();
            startActivity(new Intent(UpdatePopUp.this, ListActivity.class));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
*/

/*
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            test.setText(response.toString());
                            //startActivity(new Intent(UpdatePopUp.this, MenuActivity.class));

                        } //catch (JSONException e) {
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        // e.printStackTrace();
                        //}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);

*/


    }


    private void loadData(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            String line="";
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis!= null){
                try{
                    fis.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

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

}
