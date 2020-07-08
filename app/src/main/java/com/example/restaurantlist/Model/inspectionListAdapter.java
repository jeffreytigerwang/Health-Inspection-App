package com.example.restaurantlist.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.restaurantlist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


        TextHowLong.setText(timefunction(inspectionDate[0], inspectionDate[1], inspectionDate[2]));

        TextCrit.setText("# of critical issues found : " + crit);
        TextNonCrit.setText("# of non critical issues found : " + nonCrit);
        TextHazard.setText("Hazard Level : " + hazard);




        return convertView;
    }

    //a time function in an intelligent format so that it's easier to understand than dates
    private String timefunction(int Year, int Month , int Day){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleyearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat simplemonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat simpledayFormat = new SimpleDateFormat("dd");

        //get year
        String yeartime = simpleyearFormat.format(calendar.getTime());
        int year = Integer.parseInt(yeartime);
        //get month
        String monthtime =  simplemonthFormat.format(calendar.getTime());
        int  month = Integer.parseInt(monthtime);
        //get day
        String daytime = simpledayFormat.format(calendar.getTime());
        int  day = Integer.parseInt(daytime);

        //calculate days
        int amount1 = (Year-2015)*365 + (Month-1)*30 + Day;   // inspection time
        int amount2 = (year-2015)*365 + (month-1)*30 + day;   // current time
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



        if(amount2-amount1<=30)
        {
            String T = " days";
            String t = Integer.toString(amount2-amount1);
            return t + T ;

        }

        if(amount2-amount1>30&&amount2-amount1<=365) {
            String space = " ";
            String d = Integer.toString(Day);

            return m + space + d;
        }

        else
        {
            String space = " ";
            String y = Integer.toString(Year);
            return m + space + y ;

        }


    }

}

