package com.example.restaurantlist.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.restaurantlist.R;

public class MenuActivity extends AppCompatActivity {



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





    }











}
