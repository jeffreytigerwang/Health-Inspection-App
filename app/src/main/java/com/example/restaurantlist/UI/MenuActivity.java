package com.example.restaurantlist.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantlist.Model.FetchAPI;
import com.example.restaurantlist.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {

    String[] url = {"https://data.surrey.ca/dataset/3c8cb648-0e80-4659-9078-ef4917b90ffb/resource/0e5d04a2-be9b-40fe-8de2-e88362ea916b/download/restaurants.csv",
            "http://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports"};

    String[] fileList = {"restaurants_itr1.csv", "inspectionreports_itr1.csv"};

    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


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

        updateButton();



    }


    private void updateButton(){
        final TextView test1 = findViewById(R.id.textView1);
        final Button update1 = findViewById(R.id.buttonGet);
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFileFromURL(MenuActivity.this).execute(url[0],fileList[0]);
                test1.setText("test1");

                Toast.makeText(MenuActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class DownloadFileFromURL extends AsyncTask<String, Integer, String> {

        Context context;
        private DownloadFileFromURL(Context context) {
            this.context = context.getApplicationContext();
        }



        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                FetchAPI fetch = new FetchAPI(f_url[0]);
                String downLink = fetch.getUrl();
                File file = method(MenuActivity.this, f_url[1]);
                String time = fetch.getLastModified();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Date date = df.parse(time);
                long epoch = date.getTime();
                epoch = epoch / 1000;
                URL url = new URL(downLink);

                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthofFile = connection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(), 2048);


                OutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];

                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    if (lengthofFile > 0) // only if total length is known
                        publishProgress((int) (total * 100 / lengthofFile));
                    output.write(data, 0, count);

                }
                output.flush();
                output.close();
                input.close();

                file.setLastModified(epoch);

                return f_url[1];

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }


    }

    static File method(Context obj, String filename) {
        return new File (obj.getFilesDir(), filename);
    }





}
