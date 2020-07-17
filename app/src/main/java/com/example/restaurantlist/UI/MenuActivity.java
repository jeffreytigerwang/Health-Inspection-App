package com.example.restaurantlist.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.restaurantlist.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MenuActivity extends AppCompatActivity {

    public static TextView listTest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listTest = findViewById(R.id.test);
        Button updateList = findViewById(R.id.update);









        updateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://data.surrey.ca/api/3/action/package_show?id=fraser-health-restaurant-inspection-reports")
                        .method("GET", null)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            final String myR = response.body().string();

                            MenuActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listTest.setText(myR);
                                }
                            });


                        }
                    }
                });
            }
        });

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



}











