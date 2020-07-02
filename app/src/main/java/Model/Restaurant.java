package Model;

public class Restaurant {
    private String restaurantName;
    private String address;
    private String trackingNumber;
    private double longitude;
    private double latitude;
    private String physicalCity;
    private String facType;
    private Inspection inspection;

    public Restaurant() {
        this.restaurantName="";
        this.address="";
        this.trackingNumber="";
        this.longitude=0;
        this.latitude=0;
        this.physicalCity="";
        this.facType="";
        this.inspection=new Inspection(this.trackingNumber);
    }

    public Restaurant(String restaurantName, String address, String trackingNumber, double longitude, double latitude, String physicalCity, String facType, Inspection inspection) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.trackingNumber = trackingNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.physicalCity = physicalCity;
        this.facType = facType;
        this.inspection = inspection;
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

    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }

    public Inspection getInspection() {
        return inspection;
    }
    public Restaurant getRestaurant(String trackingNumber){
        if(this.trackingNumber==trackingNumber)
        {
            return this;
        }

        return null;
    }

}