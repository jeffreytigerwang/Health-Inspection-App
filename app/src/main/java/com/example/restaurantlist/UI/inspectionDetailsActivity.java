package com.example.restaurantlist.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;

/**
 * Detail of a specific inspection
 */


public class inspectionDetailsActivity extends AppCompatActivity {
    private int index;


    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, inspectionDetailsActivity.class);
        return intent;
    }


    private ArrayList<String> violations = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        getSupportActionBar().setTitle("Inspection Details");
        ActionBar back = getSupportActionBar();
        back.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        index = intent.getIntExtra(restaurantDetailsActivity.INDEX, -1);

        TextView date = findViewById(R.id.textSetDate);
        int Month = InspectionManager.getInstance().get(index).getInspectionDate()[1];
        String m ;
        {
            switch(Month) {
                case 1:
                    m="January";
                    break;
                case 2:
                    m="February";
                    break;
                case 3:
                    m="March";
                    break;
                case 4:
                    m="April";
                    break;
                case 5:
                    m="May";
                    break;
                case 6:
                    m="June";
                    break;
                case 7:
                    m="July";
                    break;
                case 8:
                    m="August";
                    break;
                case 9:
                    m="September";
                    break;
                case 10:
                    m="October";
                    break;
                case 11:
                    m="November";
                    break;
                default:
                    m="December";
            }}
        date.setText("" + m + " " + InspectionManager.getInstance().get(index).getInspectionDate()[2] + ", " + InspectionManager.getInstance().get(index).getInspectionDate()[0]);


        TextView type = findViewById(R.id.textSetType);
        type.setText(InspectionManager.getInstance().get(index).getInspType());

        TextView nonCritical = findViewById(R.id.textSetNonCritical);
        nonCritical.setText("" + InspectionManager.getInstance().get(index).getNumNonCritical());

        TextView Critical = findViewById(R.id.textSetCritical);
        Critical.setText("" + InspectionManager.getInstance().get(index).getNumCritical());

        TextView hazardLevel = findViewById(R.id.textSetHazardLevel);
        hazardLevel.setText("" + InspectionManager.getInstance().get(index).getHazardRating());

        TextView color = findViewById(R.id.textSetColor);
        color.setText("" + InspectionManager.getInstance().get(index).getColour());

        ImageView icon = findViewById(R.id.ImageHazard);
        if (InspectionManager.getInstance().get(index).getHazardRating().equals("Low")){
            icon.setImageResource(R.drawable.blue);
        }
        else if (InspectionManager.getInstance().get(index).getHazardRating().equals("High")){
            icon.setImageResource(R.drawable.red);
        }
        else{
            icon.setImageResource(R.drawable.yellow);
        }

        violations.addAll(Arrays.asList(InspectionManager.getInstance().get(index).getCViolLump()));

        populateListView();
        registerClickCallback();

    }

    private void populateListView() {

        ArrayAdapter<String> adapter = new violationAdapter();
        ListView violationsList = findViewById(R.id.ViewViolationDetails);
        violationsList.setAdapter(adapter);

    }


    private class violationAdapter extends ArrayAdapter<String> {

        public violationAdapter() {

            super(inspectionDetailsActivity.this, R.layout.violation_details, violations);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView (int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.violation_details, parent, false);
            }

            String currentViolation = violations.get(position);

            ImageView violationIcon = (ImageView) itemView.findViewById(R.id.ImageViolation);
            ImageView severityImage = (ImageView) itemView.findViewById(R.id.ImageSeverity);
            TextView severityText = (TextView) itemView.findViewById(R.id.TextSetSeverity);


            //Setup violation type icon
            if (violations.get(position).contains("pests") || violations.get(position).contains("Pests")) {

                violationIcon.setImageResource(R.drawable.pest);

            } else if (violations.get(position).contains("Equipment") || violations.get(position).contains("equipment")) {

                violationIcon.setImageResource(R.drawable.equipment);

            } else if (violations.get(position).contains("food") || violations.get(position).contains("Food")) {

                violationIcon.setImageResource(R.drawable.food);

            } else if (violations.get(position).contains("Sanitized") || violations.get(position).contains("sanitized") || violations.get(position).contains("washed") || violations.get(position).contains("Washed")) {

                violationIcon.setImageResource(R.drawable.sanitized);

            } else if (violations.get(position).contains("handwashing") || violations.get(position).contains("Handwashing") || violations.get(position).contains("wash hands") || violations.get(position).contains("Wash hands")) {

                violationIcon.setImageResource(R.drawable.wash);

            } else if (violations.get(position).contains("FOODSAFE")) {

                violationIcon.setImageResource(R.drawable.foodsafe);
            }

            else{

                violationIcon.setImageResource(R.drawable.blank);

            }

            //Setup severity icon
            if (currentViolation.contains("Not Critical")) {
                severityImage.setImageResource(R.drawable.noncritical_violation);
                severityText.setText(R.string.not_critical);

            }
            else if (currentViolation.contains("Critical")) {
                severityImage.setImageResource(R.drawable.critical_violation);
                severityText.setText(R.string.critical);
            }
            else {
                severityImage.setImageResource(R.drawable.blank);
            }

            String[] short_description = getResources().getStringArray(R.array.my_array);

            TextView description = (TextView) itemView.findViewById(R.id.TextSetDescription);
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
        ListView list = findViewById(R.id.ViewViolationDetails);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View itemView = view;
                String currentViolation = violations.get(position);
                String fullDescription = currentViolation;

                Toast.makeText(inspectionDetailsActivity.this, fullDescription, Toast.LENGTH_SHORT).show();
            }
        });
    }


}