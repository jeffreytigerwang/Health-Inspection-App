package com.example.restaurantlist.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurantlist.R;

public class searchActivity extends AppCompatActivity {

    private Button searchBtn;
    private Button clearBtn;
    private EditText searchField;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBtn = findViewById(R.id.search_search);
        clearBtn = findViewById(R.id.search_clear);
        searchField = findViewById(R.id.search_userEnter);


        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button searchBtn = findViewById(R.id.search_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });


    }


    private void search(){

    }
}
