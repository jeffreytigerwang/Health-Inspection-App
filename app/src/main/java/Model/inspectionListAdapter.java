package Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.restaurantlist.ListActivity;
import com.example.restaurantlist.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class inspectionListAdapter extends ArrayAdapter<Inspection> {

    private List<Inspection> inspectionsStorage;
    private Calendar calendar;
    private Context mContext;
    int mResource;


    public inspectionListAdapter(Context context, int resource, ArrayList<Inspection> objects ) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        inspectionsStorage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {





        String color = inspectionsStorage.get(position).getColour();
        int crit = inspectionsStorage.get(position).getNumCritical();
        int nonCrit = inspectionsStorage.get(position).getNumNonCritical();
        int[] inspectionDate = inspectionsStorage.get(position).getInspectionDate();
        //year month day
        String hazard = inspectionsStorage.get(position).getHazardRating();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);


        ImageView imageHazard = (ImageView) convertView.findViewById(R.id.hazard_level);
        TextView TextHowLong = (TextView) convertView.findViewById(R.id.details_howLong);
        TextView TextCrit = (TextView) convertView.findViewById(R.id.details_Crit);
        TextView TextNonCrit = (TextView) convertView.findViewById(R.id.details_NonCrit);
        TextView TextHazard = (TextView) convertView.findViewById(R.id.hazard_detail);

        //figuring out which color
        if(color.equals("red")){
            imageHazard.setImageResource(R.drawable.red);
        }
        else if(color.equals("blue")){
            imageHazard.setImageResource(R.drawable.blue);
        }
        else if(color.equals("yellow")){
            imageHazard.setImageResource(R.drawable.yellow);
        }

        //figuring out which month
        int finalDay = 0;
        String writtenMonth = "";
        if(inspectionDate[1] == 1){
            writtenMonth = "January";
        }
        if(inspectionDate[1] == 2){
            writtenMonth = "February";
        }
        if(inspectionDate[1] == 3){
            writtenMonth = "March";
        }
        if(inspectionDate[1] == 4){
            writtenMonth = "April";
        }
        if(inspectionDate[1] == 5){
            writtenMonth = "May";
        }
        if(inspectionDate[1] == 6){
            writtenMonth = "June";
        }
        if(inspectionDate[1] == 7){
            writtenMonth = "July";
        }
        if(inspectionDate[1] == 8){
            writtenMonth = "August";
        }
        if(inspectionDate[1] == 9){
            writtenMonth = "September";
        }
        if(inspectionDate[1] == 10){
            writtenMonth = "October";
        }
        if(inspectionDate[1] == 11){
            writtenMonth = "November";
        }
        if(inspectionDate[1] == 12){
            writtenMonth = "December";
        }


        calendar = Calendar.getInstance();
        //day month year
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DATE);
        int year = calendar.get(Calendar.YEAR);

        
        if(inspectionDate[0] == year){
            if(inspectionDate[1] == month){
                finalDay = day-inspectionDate[2];
                TextHowLong.setText("" + finalDay + " Days ago");
            }else{
                TextHowLong.setText(writtenMonth + " " + inspectionDate[2]);
            }
        }else{
            TextHowLong.setText(writtenMonth + " " + inspectionDate[0]);
        }


        TextCrit.setText("# of critical issues found : " + crit);
        TextNonCrit.setText("# of non critical issues found : " + nonCrit);
        TextHazard.setText("Hazard Level : " + hazard);




        return convertView;
    }
}

