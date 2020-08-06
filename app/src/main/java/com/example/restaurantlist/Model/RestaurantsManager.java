package com.example.restaurantlist.Model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Manages all restaurants in an ArrayList
public class RestaurantsManager implements Iterable<Restaurant>{

    private List<Restaurant> restaurants = new ArrayList<>();
    private String searchTerm = "";
    private String hazardLevelFilter = "All";
    private String comparator = "All";
    private int violationLimit;
    private boolean searchFavourite=false;

    public void add(Restaurant restaurant) {
        restaurants.add(restaurant);
    }
    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
    public void setHazardLevelFilter(int index) {
        if (index == 0) this.hazardLevelFilter = "All";
        else if (index == 1) this.hazardLevelFilter = "Low";
        else if (index == 2) this.hazardLevelFilter = "Moderate";
        else if (index == 3) this.hazardLevelFilter = "High";
    }
    public void setComparator(int index) {
        if (index == 0) {

            this.comparator = "All";

        } else if (index == 1) {

            this.comparator = "Greater or Equal";

        } else if (index == 2) {

            this.comparator = "Lesser or Equal";

        }
    }

    public void setViolationLimit(int violationLimit) { this.violationLimit = violationLimit; }

    public Restaurant find(String tracking){
        for (Restaurant restaurant: restaurants) {
            if (restaurant.getTrackingNumber().equals(tracking)) {
                return restaurant;
            }
        }
        return null;
    }

    public List<Restaurant> getRestaurants() {
        searchTerm = searchTerm.trim();
        if (!searchFavourite&&searchTerm.isEmpty() &&
                hazardLevelFilter.equalsIgnoreCase("All") &&
                comparator.equalsIgnoreCase("All")) {

            return restaurants; // O(1) when search term is empty.
        }

        List<Restaurant> filteredRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (qualifies(restaurant)) {
                filteredRestaurants.add(restaurant);
            }
        }
        return filteredRestaurants;
    }

    private boolean qualifies(Restaurant restaurant) {
        String restaurantName = restaurant.getRestaurantName();
        restaurantName = restaurantName.toLowerCase();
        String hazardLevel = restaurant.getLastHazardLevel();
        int criticalViolationCount = 0;
        for(int i =0 ; i< restaurant.inspections.size(); i++) {

            criticalViolationCount = criticalViolationCount+ restaurant.inspections.get(i).getNumCritical();
        }
        if (restaurantName.toLowerCase().contains(searchTerm.toLowerCase()) &&
                ((hazardLevelFilter.equalsIgnoreCase("All")) ||
                        (hazardLevel.equalsIgnoreCase(hazardLevelFilter))) &&(restaurant.isCheckFavourite()||!searchFavourite)&&
                (inRange(criticalViolationCount))) {

            return true;

        } else {

            return false;
        }

    }

    boolean inRange(int count) {
        if ((comparator.equalsIgnoreCase("All")) ||
                ((comparator.equalsIgnoreCase("Greater or Equal")) && (count >= violationLimit)) ||
                ((comparator.equalsIgnoreCase("Lesser or Equal")) && (count <= violationLimit))) {

            return true;
        }
        return false;
    }

    /*
    Singleton Support
    */
    private static RestaurantsManager instance;

    private RestaurantsManager() {
        // prevent anyone else from instantiating object
    }

    public static RestaurantsManager getInstance() {
        if(instance == null) {
            instance = new RestaurantsManager();
        }
        return instance;
    }

    public Restaurant findRestaurantByLatLng(double latitude, double longitude) {
        for (Restaurant res: restaurants) {
            if (res.getLatitude() == latitude && res.getLongitude() == longitude) {
                return res;
            }
        }
        return null;
    }

    @Override
    public Iterator<Restaurant> iterator() {
        return getRestaurants().iterator();
    }

    public int getManagerSize() { return restaurants.size(); }


    public boolean isSearchFavourite() {
        return searchFavourite;
    }

    public void setSearchFavourite(boolean searchFavourite) {
        this.searchFavourite = searchFavourite;
    }
}
