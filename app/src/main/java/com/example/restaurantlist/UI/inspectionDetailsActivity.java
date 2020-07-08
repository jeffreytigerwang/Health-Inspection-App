package com.example.restaurantlist.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import com.example.restaurantlist.R;

public class inspectionDetailsActivity extends AppCompatActivity {
    private int index;
    private int test1 = 1;

    private static final String EXTRA_MESSAGE = "Extra-Message";
    int violationNumber = 0;

/***    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, inspectionDetailsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }***/

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
       // violations.addAll(Arrays.asList(InspectionManager.getInstance().get(index).getNonViolLump()));

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

            } else {

                violationIcon.setImageResource(R.drawable.blank);

            }

            //Setup severity icon
            if (currentViolation.contains("Not Critical")) {
                severityImage.setImageResource(R.drawable.noncritical_violation);
                severityText.setText("Severity: Not Critical");

            }
            else if (currentViolation.contains("Critical")) {
                severityImage.setImageResource(R.drawable.critical_violation);
                severityText.setText("Severity: Critical");
            }
            else {
                severityImage.setImageResource(R.drawable.blank);
            }

            TextView description = (TextView) itemView.findViewById(R.id.TextSetDescription);
            description.setText(currentViolation);

            return itemView;
        }

    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.ViewViolationDetails);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View itemView = view;
                TextView getDescription = (TextView) itemView.findViewById(R.id.TextSetDescription);
                String fullDescription = getDescription.getText().toString();

                Toast.makeText(inspectionDetailsActivity.this, fullDescription, Toast.LENGTH_SHORT).show();
            }
        });
    }


}