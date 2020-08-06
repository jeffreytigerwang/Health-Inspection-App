package com.example.restaurantlist.Model;


import com.example.restaurantlist.R;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;



public class Inspection {


    private String formattedDate;
    private String trackingNum;
    private String testDate;
    private String InspType;
    private String HazardRating;
    private int diffInDay;

    private int NumCritical;
    private int NumNonCritical;

    private String[] CViolLump;

    //Inspections for the restaurants
    public Inspection(String trackingNum, String fullDate, String inspType, int numCritical, int numNonCritical, String hazardRating, String wholecviolLump) {
        this.trackingNum = trackingNum;
        this.testDate = fullDate;
        this.InspType = inspType;
        this.NumCritical = numCritical;
        this.NumNonCritical = numNonCritical;
        this.HazardRating = hazardRating;
        this.CViolLump = parseViolations(wholecviolLump);
        initDate();
    }

    //With reference to: https://www.baeldung.com/java-date-difference
    public void initDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            String rawInspectionDate = getTestDate();
            Date inspectionDate = sdf.parse(rawInspectionDate);
            Date currentDate = new Date();

            long diffInMS = Math.abs(currentDate.getTime() - inspectionDate.getTime());
            long diffInDay = TimeUnit.DAYS.convert(diffInMS, TimeUnit.MILLISECONDS);
            this.diffInDay = (int) diffInDay;

            //With reference to: https://stackoverflow.com/questions/36370895/getyear-getmonth-getday-are-deprecated-in-calendar-what-to-use-then
            String[] indexToMonth = new DateFormatSymbols().getMonths();
            int testMonth;
            Calendar inspectionCalendar = Calendar.getInstance();
            inspectionCalendar.setTime(inspectionDate);

            if (diffInDay <= 1) {

                this.formattedDate = diffInDay + "Day";

            } else if (diffInDay <= 30) {

                this.formattedDate = diffInDay + " Days";

            } else if (diffInDay <= 365) {
                this.formattedDate = indexToMonth[inspectionCalendar.get(Calendar.MONTH)]
                    + " " + inspectionCalendar.get(Calendar.DAY_OF_MONTH);

            } else {

                this.formattedDate = indexToMonth[inspectionCalendar.get(Calendar.MONTH)]
                        + " " + inspectionCalendar.get(Calendar.YEAR);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.formattedDate = "N/A";
        }
    }

    public String dateFormatter() { return this.formattedDate; }
    public int getDiffInDay() { return this.diffInDay; }

    private String[] parseViolations(String rawViolations) {
        return rawViolations.replace(",", ", ").split("\\|");
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public String getTestDate() {
        return this.testDate;
    }

    public String getInspType() {
        return InspType;
    }


    public int getNumCritical() {
        return NumCritical;
    }

    public int getNumNonCritical() {
        return NumNonCritical;
    }

    public String getHazardRating() {
        return HazardRating;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String[] getCViolLump() {
        return this.CViolLump;
    }


    public int getHazardIcon() {

        if (HazardRating.equals("Low")) {

            return R.drawable.blue;

        } else if (HazardRating.equals("Moderate")) {

            return R.drawable.yellow;

        } else {

            return R.drawable.red;

        }

    }

    @Override
    public String toString() {

        return  NumCritical + ", " +
                NumNonCritical + ", " +
                this.dateFormatter() + ", " +
                InspType + ", " +
                HazardRating;

    }

    public String getShortViolation(int position) {

        if (CViolLump.length == 0) {

            return "";

        }

        String[] shortViolations = new String[CViolLump.length];

        for (int i = 0; i < CViolLump.length; i++) {

            if (CViolLump[i].length() > 10) {

                if (CViolLump[i].length() < 40) {

                    shortViolations[i] = CViolLump[i];

                } else {

                    shortViolations[i] = CViolLump[i].substring(0, 40) + "...";

                }
            }

            else {

                shortViolations[i] = CViolLump[i];

            }
        }

        return shortViolations[position];
    }
}
