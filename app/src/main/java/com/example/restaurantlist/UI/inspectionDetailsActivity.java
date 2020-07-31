package com.example.restaurantlist.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class inspectionDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "Extra";
    private static final String RESTAURANT_MESSAGE = "Restaurant";

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, inspectionDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    private Inspection mInspection;
    private Restaurant restaurant;
    private ArrayList<String> violations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getInspection();
        displayDetails();
        registerClickCallback();

    }

    private void displayDetails(){
        violationListView();

        TextView trackingNumberText= findViewById(R.id.trackingNumber);
        TextView inspectionDateText= findViewById(R.id.inspectionDate);
        TextView inspectionTypeText= findViewById(R.id.inspectionType);
        TextView numCriticalText= findViewById(R.id.numCritical);
        TextView numNonCriticalText= findViewById(R.id.numNonCritical);
        TextView hazardRatingText= findViewById(R.id.hazardRating);
        ImageView hazardImage = findViewById(R.id.hazardImage);


        trackingNumberText.setText(mInspection.getTrackingNum());
        inspectionDateText.setText(getFormatDate());
        inspectionTypeText.setText(mInspection.getInspType());
        numCriticalText.setText(Integer.toString(mInspection.getNumCritical()));
        numNonCriticalText.setText(Integer.toString(mInspection.getNumNonCritical()));
        hazardRatingText.setText(mInspection.getHazardRating());

        if (mInspection.getHazardRating().equals("Low")) {

            hazardRatingText.setTextColor(Color.BLUE);

        } else if (mInspection.getHazardRating().equals("Moderate")) {

            hazardRatingText.setTextColor(Color.rgb(205, 205, 0));

        } else if (mInspection.getHazardRating().equals("High")) {

            hazardRatingText.setTextColor(Color.RED);

        }

        hazardImage.setImageResource(mInspection.getHazardIcon());

    }

    // Return a date formatted in "May 12, 2019"
    private String getFormatDate() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            Date inspectionDate = sdf.parse(mInspection.getTestDate());
            Calendar inspectionCalendar = Calendar.getInstance();

            inspectionCalendar.setTime(inspectionDate);
            String[] indexToMonth = new DateFormatSymbols().getMonths();

            return indexToMonth[inspectionCalendar.get(Calendar.MONTH)]
                    + " " + inspectionCalendar.get(Calendar.DAY_OF_MONTH)
                    + ", " + inspectionCalendar.get(Calendar.YEAR);

        }

        catch (Exception e) {

            // Handle it.
        }

        return "N/A";
    }

    private void getInspection() {

        RestaurantsManager manager = RestaurantsManager.getInstance();
        Intent intent = getIntent();
        String messageRestaurant = intent.getStringExtra(RESTAURANT_MESSAGE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        for (Restaurant temp : manager) {
            if (messageRestaurant.equals(temp.getTrackingNumber())) {
                restaurant = temp;
            }
        }

        for (Inspection temp : restaurant.inspections) {
            if (temp.toString().equals(message)) {
                mInspection = temp;
            }
        }

        for (int i = 0; i < mInspection.getCViolLump().length; i++) {
            violations.add(mInspection.getShortViolation(i));
        }
    }

    private void violationListView() {

        ArrayAdapter<String> adapter = new CustomAdapter();
        ListView violationsList = findViewById(R.id.violationsList);
        violationsList.setAdapter(adapter);

    }

    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter() {
            super(inspectionDetailsActivity.this, R.layout.layout_violation_details, violations);
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.layout_violation_details, parent, false);
            }

            String currentViolation = violations.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.violationimage);
            ImageView severityImage = (ImageView) itemView.findViewById(R.id.violationSeverity);

            //Setup severity icon
            if (violations.get(position).contains("Not Critical")) {
                severityImage.setImageResource(R.drawable.noncritical_violation);
                TextView textView2 = (TextView) itemView.findViewById(R.id.inspec_severity);
                textView2.setText(R.string.not_critical);
            }
            else if (violations.get(position).contains("Critical")) {
                severityImage.setImageResource(R.drawable.critical_violation);
                TextView textView2 = (TextView) itemView.findViewById(R.id.inspec_severity);
                textView2.setText(R.string.critical);
            }
            else {
                imageView.setImageResource(R.drawable.blank);
                TextView textView2 = (TextView) itemView.findViewById(R.id.inspec_severity);
                textView2.setText("");
            }

            //Setup violation type icon
            if (violations.get(position).contains("pests") || violations.get(position).contains("Pests")) {

                imageView.setImageResource(R.drawable.pest);

            } else if (violations.get(position).contains("Equipment") || violations.get(position).contains("equipment")) {

                imageView.setImageResource(R.drawable.equipment);

            } else if (violations.get(position).contains("food") || violations.get(position).contains("Food") || violations.get(position).contains("Cold")) {

                imageView.setImageResource(R.drawable.food);

            } else if (violations.get(position).contains("Sanitized") || violations.get(position).contains("sanitized") || violations.get(position).contains("310") ) {

                imageView.setImageResource(R.drawable.sanitized);

            } else if ((violations.get(position).contains("handwashing")) || (violations.get(position).contains("wash hands"))) {

                imageView.setImageResource(R.drawable.wash);

            } else if (violations.get(position).contains("FOODSAFE") ||(violations.get(position).contains("402"))) {

                imageView.setImageResource(R.drawable.foodsafe);

            } else if (violations.get(position).contains("Premises") ||(violations.get(position).contains("311"))) {

                imageView.setImageResource(R.drawable.premises);

            } else {

                imageView.setImageResource(R.drawable.blank);

            }



            TextView textView = (TextView) itemView.findViewById(R.id.violationtext);
            textView.setText(currentViolation);


            String[] short_description = getResources().getStringArray(R.array.my_array);

            TextView description = (TextView) itemView.findViewById(R.id.inspec_Description);
            if (currentViolation.contains("101") || currentViolation.contains("102") ||
                    currentViolation.contains("103") || currentViolation.contains("104")) {
                description.setText(short_description[0]);

            }
            else if (currentViolation.contains("201") || currentViolation.contains("202") ||
                    currentViolation.contains("203") || currentViolation.contains("204") ||
                    currentViolation.contains("205") || currentViolation.contains("206") ||
                    currentViolation.contains("208") || currentViolation.contains("209") ||
                    currentViolation.contains("210") || currentViolation.contains("211") ||
                    currentViolation.contains("212")) {
                description.setText(short_description[1]);
            }

            else if (currentViolation.contains("301") || currentViolation.contains("302") ||
                    currentViolation.contains("303") || currentViolation.contains("304") ||
                    currentViolation.contains("305") || currentViolation.contains("306") ||
                    currentViolation.contains("307") || currentViolation.contains("308") ||
                    currentViolation.contains("309") || currentViolation.contains("310") ||
                    currentViolation.contains("311") || currentViolation.contains("312") ||
                    currentViolation.contains("313") || currentViolation.contains("314") ||
                    currentViolation.contains("315")) {
                description.setText(short_description[2]);
            }

            else if (currentViolation.contains("401") || currentViolation.contains("402") ||
                    currentViolation.contains("403") || currentViolation.contains("404")) {
                description.setText(short_description[3]);
            }

            else if (currentViolation.contains("501") || currentViolation.contains("502")) {
                description.setText(short_description[4]);
            }

            else {
                description.setText("");

            }






            return itemView;
        }

    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.violationsList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View itemView = view;
                TextView textView = (TextView) itemView.findViewById(R.id.violationtext);
                String message = textView.getText().toString();

                for (String temp : mInspection.getCViolLump()) {
                    if (temp.length() > 10) {
                        if (temp.length() < 40) {
                            if (temp.equals(message)) {
                                message = temp;
                            }
                        } else {

                            if ((temp.substring(0, 40) + "...").equals(message)) {
                                message = temp;
                            }

                        }
                    }
                }
                if(message.length()>10) {
                    showToast(message);
                }
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(inspectionDetailsActivity.this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_inspections, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        return true;
    }
}
