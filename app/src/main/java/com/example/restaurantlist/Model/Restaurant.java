package com.example.restaurantlist.Model;

import com.example.restaurantlist.R;

import java.util.ArrayList;

//Holds details for restaurant
public class Restaurant {

    private String restaurantName;
    private String address;
    private String physicalCity;
    private String trackingNumber;

    private double latitude;
    private double longitude;
    private boolean checkFavourite;
    private int icon;
    private int criticalViolationCount;


    public ArrayList<Inspection> inspections;

    public Restaurant(String restaurantName, String address, String physicalCity, double latitude, double longitude, String trackingNumber) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.physicalCity = physicalCity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trackingNumber = trackingNumber;
        this.icon = matchLogo();
        this.inspections = new ArrayList<>();
        this.criticalViolationCount = countCriticalViolation();
        this.checkFavourite=false;

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String name) {
        this.restaurantName = name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public int getIcon() {
        return icon;
    }

    public int getCriticalViolationCount() { return this.criticalViolationCount; }

    public String getLastHazardLevel() {
        if (inspections.isEmpty()) return "None";
        return inspections.get(0).getHazardRating();
    }

    private int countCriticalViolation() {
        int count = 0;
        for (Inspection inspection : inspections) {
            if (inspection.getNumCritical()>=0) {
                count = count + inspection.getNumCritical();
            }
        }
        return count;
    }

    private int matchLogo(){
        restaurantName = this.getRestaurantName().toLowerCase();

        if (restaurantName.contains("a&w") || restaurantName.contains("a & w")){
            return R.drawable.aw_canada_logo;
        }
        else if (restaurantName.contains("lee yuen seafood")){
            return R.drawable.lee_yuen_logo;
        }
        else if (restaurantName.contains("unfindable")){
            return R.drawable.icon;
        }
        else if (restaurantName.contains("top in town pizza")){
            return R.drawable.top_in_town_pizza;
        }
        else if (restaurantName.contains("104 sushi")){
            return R.drawable.sushi_104;
        }
        else if (restaurantName.contains("zugba flame")){
            return R.drawable.zugba_flame;
        }
        else if (restaurantName.contains("5 star catering")){
            return R.drawable.logo5;
        }
        else if (restaurantName.contains("mcdonald")){
            return R.drawable.mcdonald;
        }
        else if (restaurantName.contains("7-eleven")){
            return R.drawable.seven_eleven;
        }
        else if (restaurantName.contains("pizza hut")){
            return R.drawable.pizza_hut;
        }
        else if (restaurantName.contains("kfc")){
            return R.drawable.kfc;
        }
        else if (restaurantName.contains("subway")){
            return R.drawable.subway;
        }
        else if (restaurantName.contains("burger king")){
            return R.drawable.burger_king;
        }
        else if (restaurantName.contains("boston pizza")){
            return R.drawable.bp_pizza;
        }
        else{
            return R.drawable.icon1;
        }
    }

    public ArrayList<Inspection> getInspections() {
        return inspections;
    }

    public Inspection getInspection(int inspection) {
        if (inspections.size() <= inspection || inspection < 0){
            return null;
        }

        return inspections.get(inspection);
    }

    public int getInspectionSize() {
        return inspections.size();
    }



    @Override
    public String toString() {
        boolean empty = false;
        Inspection first = new Inspection("", "", "", 0, 0, "", "");

        if (inspections.isEmpty()) {

            empty = true;

        } else {

            first = inspections.get(0);

        }


        if (!empty) {

            return trackingNumber + ", "
                    + restaurantName + ", "
                    + (first.getNumCritical() + first.getNumNonCritical())
                    + ", "
                    + first.getHazardRating() + ", "
                    + first.dateFormatter();

        } else {

            return trackingNumber + " "
                    + restaurantName + "\nNo inspections";

        }

    }

    public boolean isCheckFavourite() {
        return checkFavourite;
    }

    public void setCheckFavourite(boolean checkFavourite) {
        this.checkFavourite = checkFavourite;
    }
}
