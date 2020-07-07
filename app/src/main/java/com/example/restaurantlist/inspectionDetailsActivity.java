package com.example.restaurantlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Inspection;
import Model.InspectionManager;
import Model.RestaurantsManager;
import Model.inspectionListAdapter;

public class inspectionDetailsActivity extends AppCompatActivity {
    private int index;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        index = intent.getIntExtra(restaurantDetailsActivity.INDEX, -1);

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

        //violationNumber = InspectionManager.getInstance().get(index).getNonViolLump().length + InspectionManager.getInstance().get(index).getCViolLump().length;

        //violations.addAll(Arrays.asList(InspectionManager.getInstance().get(index).getNonViolLump()));
        //violations.addAll(Arrays.asList(InspectionManager.getInstance().get(index).getCViolLump()));
        //violations.add(InspectionManager.getInstance().get(index).getNonViolLump()[0]);

        violations.add("303,Not Critical,Equipment/facilities/hot & cold water for sanitary maintenance not adequate [s. 17(3); s. 4(1)(f)],Not Repeat");

        populateListView();


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

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.violation_details, parent, false);
            }

            String currentViolation = violations.get(position);

            //ImageView imageView = (ImageView) itemView.findViewById(R.id.ImageViolation);
            ImageView severityImage = (ImageView) itemView.findViewById(R.id.ImageSeverity);

            //Setup severity icon
            if (currentViolation.contains("Not Critical")) {
                severityImage.setImageResource(R.drawable.noncritical_violation);
            }
            else if (currentViolation.contains("Critical")) {
                severityImage.setImageResource(R.drawable.critical_violation);
            }
            else {
                severityImage.setImageResource(R.drawable.critical_violation);
            }


            return itemView;
        }

    }


}