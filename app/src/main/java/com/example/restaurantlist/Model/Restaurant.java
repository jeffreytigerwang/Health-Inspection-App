package com.example.restaurantlist.Model;

import com.example.restaurantlist.R;

public class Restaurant {
    private String restaurantName;
    private String restaurantNameL;

    private String address;
    private String trackingNumber;
    private double longitude;
    private double latitude;
    private String physicalCity;
    private String facType;
    private InspectionManager inspections;
    private int icon;



    public Restaurant() {
        this.restaurantName="";
        this.address="";
        this.trackingNumber="";
        this.longitude=0;
        this.latitude=0;
        this.physicalCity="";
        this.facType="";

    }

    public Restaurant(String restaurantName, String address, String trackingNumber, double longitude, double latitude, String physicalCity, String facType, InspectionManager inspections) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.trackingNumber = trackingNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.physicalCity = physicalCity;
        this.facType = facType;
        this.inspections = inspections;
        this.icon = matchLogo();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhysicalCity() {
        return physicalCity;
    }

    public void setPhysicalCity(String physicalCity) {
        this.physicalCity = physicalCity;
    }

    public String getFacType() {
        return facType;
    }

    public void setFacType(String facType) {
        this.facType = facType;
    }

    public void setInspections(InspectionManager inspections) {
        this.inspections = inspections;
    }

    public InspectionManager getInspections() {
        return inspections;
    }

    public Restaurant getRestaurant(String trackingNumber){
        if(this.trackingNumber==trackingNumber)
        {
            return this;
        }

        return null;
    }

    private int matchLogo(){
        restaurantNameL = this.getRestaurantName().toLowerCase();
        if (restaurantNameL.contains("a&w")){
            return R.drawable.aw_canada_logo;
        }
        else if (restaurantNameL.contains("lee yuen seafood")){
            return R.drawable.lee_yuen_logo;
        }
        else if (restaurantNameL.contains("unfindable")){
            return R.drawable.icon;
        }
        else if (restaurantNameL.contains("top in town pizza")){
            return R.drawable.top_in_town_pizza;
        }
        else if (restaurantNameL.contains("104 sushi")){
            return R.drawable.sushi_104;
        }
        else if (restaurantNameL.contains("zugba flame")){
            return R.drawable.zugba_flame;
        }
        else{
            return R.drawable.red;
        }
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
