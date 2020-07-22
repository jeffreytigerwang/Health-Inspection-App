package com.example.restaurantlist.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
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
import static com.example.restaurantlist.UI.MenuActivity.IDATE;
import static com.example.restaurantlist.UI.MenuActivity.IURLdata;
import static com.example.restaurantlist.UI.MenuActivity.URLdata;


public class UpdatePopUp extends Activity {
    private ProgressBar progressBar;
    private Button cancel;

    private static final String FILE_NAME = "restaurants.txt";

    public static String dataURL;
    public static String InspectionURL;
    private RequestQueue mQueue;
    public static String date;
    public static String dateInspection;
    public int cancelData = 0;

    public static Intent makeLaunchIntent(Context c){
        Intent intent = new Intent(c, UpdatePopUp .class);
        return intent;
    }
    TextView cunt;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        progressBar = findViewById(R.id.progressBarUpdate);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*0.8),(int) (height*0.6));

        mQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        date = intent.getStringExtra(DATE);
        dataURL = intent.getStringExtra(URLdata);

        dateInspection = intent.getStringExtra(IDATE);
        InspectionURL = intent.getStringExtra(IURLdata);

        cunt = findViewById(R.id.fatcunt);
        //Toast.makeText(getApplicationContext(), dateInspection, Toast.LENGTH_SHORT).show();

        Button later = findViewById(R.id.later);
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuActivity.makeLaunchIntent(UpdatePopUp.this);
                intent.putExtra(CHECK, 1);
                startActivity(intent);
            }
        });

        cancel = findViewById(R.id.cancel);



    }

    public void startAsyncTask(View v){
        DataAsyncTask task = new DataAsyncTask(this);
        task.execute(10);
    }
    private static class DataAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<UpdatePopUp> activityWeakReference;

        DataAsyncTask(UpdatePopUp activity){
            activityWeakReference = new WeakReference<UpdatePopUp>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            UpdatePopUp activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
            activity.cancel.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            final UpdatePopUp activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            activity.progressBar.setProgress(values[0]);
            activity.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity.getApplicationContext(), "Cancelling...", Toast.LENGTH_LONG).show();
                    activity.cancelData = 1;
                }
            });
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            UpdatePopUp activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }

            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
            if(activity.cancelData != 1) {
                Toast.makeText(activity.getApplicationContext(),"Done!", LENGTH_LONG).show();
                activity.saveInspectionData();
                activity.saveData();
            }
        }

        @Override
        protected String doInBackground(Integer... integers) {

            UpdatePopUp activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return "Finished!";
            }

            if(RestaurantsManager.getInstance().getUpdate() != date){
                //restaurant needs to be changed

                activity.readRestaurantData();
            }

            if(InspectionManager.getInstance().getInspectionDate() != dateInspection){
                //inspection needs to be changed

                activity.readInspectionData();
            }

            for(int i =0; i<integers[0] ; i++){
                publishProgress((i*100)/integers[0]);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    private void readInspectionData(){
        //inspectionUrl
        final String url2 = InspectionURL;
        OkHttpClient client2 = new OkHttpClient();

        okhttp3.Request request2 = new Request.Builder()
            .url(url2)
            .build();
        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response2) throws IOException {
                if(response2.isSuccessful()){
                    final String myResponse2 = response2.body().string();

                    UpdatePopUp.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String[] lines = myResponse2.split(System.getProperty("line.separator"));
                                //Toast.makeText(getBaseContext(), lines[0], Toast.LENGTH_SHORT ).show();
                                for(int i = 1 ; i<1000; i++) {

                                    //Toast.makeText(getBaseContext(), lines[i], Toast.LENGTH_SHORT ).show();
                                    if(lines[i].indexOf('"') != -1) {

                                        int firstIndex = lines[i].indexOf('"');
                                        int lastIndex = lines[i].lastIndexOf('"');

                                        String start = lines[i].substring(0, firstIndex+1);
                                        //Toast.makeText(getBaseContext(), start, Toast.LENGTH_SHORT ).show();
                                        String middle = lines[i].substring(firstIndex+1,lastIndex);
                                        //Toast.makeText(getBaseContext(), middle, Toast.LENGTH_SHORT ).show();
                                        String end = lines[i].substring(lastIndex);
                                        //Toast.makeText(getBaseContext(), end, Toast.LENGTH_SHORT ).show();

                                        middle = middle.replaceAll(",", " ");
                                        //Toast.makeText(getBaseContext(), middle, Toast.LENGTH_SHORT).show();

                                        lines[i] = start + middle + end;
                                        //Toast.makeText(getBaseContext(),lines[i], Toast.LENGTH_LONG).show();

                                        lines[i] = lines[i].substring(0,firstIndex) + lines[i].substring(firstIndex+1);
                                        firstIndex = lines[i].indexOf('"');
                                        lines[i] = lines[i].substring(0,firstIndex) + lines[i].substring(firstIndex+1);
                                        //Toast.makeText(getBaseContext(), lines[i], Toast.LENGTH_SHORT ).show();

                                    }


                                    String[] tokens = lines[i].split(",");
                                    //Toast.makeText(getBaseContext(), tokens[5], Toast.LENGTH_SHORT ).show();

                                    if(tokens[5].length()>0) {
                                        InspectionManager.getInstance().add(new Inspection(tokens[0], Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[6], tokens[5]));
                                    }else{
                                        InspectionManager.getInstance().add(new Inspection(tokens[0], Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[6], ""));
                                    }


                                }

                                //InspectionManager.getInstance().add(new Inspection("SWOD-APSP3X", 20200220,"Routine", 1,1,"Low",""));
                                //obtainInspectionData(myResponse2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            sortInspectionByName();
                            //saveInspectionData();

                        }
                    });
                }
            }
        });



    }

    private void readRestaurantData() {


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
                            //saveData();
                        }
                    });


                }
            }
        });






    }
    private void saveInspectionData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(InspectionManager.getInstance());
        editor.putString("InspectionManger", json);
        editor.apply();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(RestaurantsManager.getInstance());
        editor.putString("RestaurantsManager", json);
        editor.apply();
    }

    private void obtainInspectionData(String result2) {
        String[] lines = result2.split(System.getProperty("line.separator"));
        Toast.makeText(getBaseContext(), lines.length, Toast.LENGTH_SHORT ).show();
        /*
        for(int i = 0 ; i<lines.length; i++){

            if(lines[i].indexOf('"') != -1) {

                int firstIndex = lines[i].indexOf('"');
                int lastIndex = lines[i].lastIndexOf('"');

                while(lines[i].indexOf(',') != -1 && lines[i].indexOf(',') < lines[i].lastIndexOf('"')) {
                    int commalocation = lines[i].indexOf(',', firstIndex);
                    lines[i] = lines[i].substring(0, commalocation) + lines[i].substring(commalocation+1);

                }

       //         lines[i] = lines[i].substring(0,firstIndex) + lines[i].substring(firstIndex+1);
        //        lines[i] = lines[i].substring(0,lastIndex) + lines[i].substring(lastIndex+1);



            }
            Toast.makeText(getBaseContext(), ""+"gu", Toast.LENGTH_SHORT ).show();
            String[] tokens = lines[i].split(",");
            if(tokens[5].length()>0) {
                InspectionManager.getInstance().add(new Inspection(tokens[0], Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[6], tokens[5]));
            }else{
                InspectionManager.getInstance().add(new Inspection(tokens[0], Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), tokens[6], ""));
            }

        }

*/

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

        Collections.sort(InspectionManager.getInstance().getList(), compareByTracking.reversed()); //Sort arraylist
    }
}
