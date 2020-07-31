package com.example.restaurantlist.Model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data model: Store a collection of restaurants.
 */

public class RestaurantsManager implements Iterable<Restaurant> {
    private List<Restaurant> restaurants = new ArrayList<>();
    private static RestaurantsManager instance;
    private int count=0;
    private int searchViolationNumberEquality = 0;
    private String searchIntent = "";
    private String searchHazardLevel= "";
    private int currentRestaurant;
    private int searchViolationBound = -1;
    private boolean searchFavourites = false;
    public void setCurrentRestaurant(int index){
        currentRestaurant = index;
    }
    public int getCurrentRestaurant(){
        return currentRestaurant;
    }


    public void setcount(int i){
        count=i;
    }

    public int getcount()
    {
        return count;
    }
    public void setSearchFavourites(boolean searchFavourites){
        this.searchFavourites = searchFavourites;
    }
    public static RestaurantsManager getInstance() {
        if (instance == null) {
            instance = new RestaurantsManager();
        }
        return instance;
    }
    public void setSearchViolationBound(int searchViolationBound) {
        this.searchViolationBound = searchViolationBound;
    }
    private RestaurantsManager() {
        // Nothing: ensure this is a singleton.
    }

    public void add(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurants.remove(restaurant);
    }
    public Restaurant get(int i) {
        return restaurants.get(i);
    }

    public  List get(){
        return restaurants;
    }
    public int getNumRestaurants() {
        return restaurants.size();
    }
    public void setSearchHazardLevel(int index) {
        if (index == 0) this.searchHazardLevel = "";
        else if (index == 1) this.searchHazardLevel = "Low";
        else if (index == 2) this.searchHazardLevel = "Moderate";
        else if (index == 3) this.searchHazardLevel = "High";
    }
    public void setSearchViolationNumberEquality(int index) {
        this.searchViolationNumberEquality = index;
    }
    public Restaurant findRestaurantByLatLng(double latitude, double longitude) {
        for (Restaurant res: restaurants) {
            if (res.getLatitude() == latitude && res.getLongitude() == longitude) {
                return res;
            }
        }
        return null;
    }
    public void setSearchIntent(String searchIntent) { this.searchIntent = searchIntent; }

    @NonNull
    @Override
    public Iterator<Restaurant> iterator() {
        return restaurants.iterator();
    }


}
