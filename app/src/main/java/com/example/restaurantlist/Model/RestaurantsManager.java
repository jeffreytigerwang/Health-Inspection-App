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
    private String updateRestaurant = "";
    private int currentRestaurant;

    public void clearList(){
        restaurants.clear();
    }

    public void setCurrentRestaurant(int index){
        currentRestaurant = index;
    }
    public int getCurrentRestaurant(){
        return currentRestaurant;
    }

    public String getUpdate(){
        return updateRestaurant;
    }
    public void setUpdate(String d){
        updateRestaurant = d;
    }

    public void setcount(int i){
        count=i;
    }

    public int getcount()
    {
        return count;
    }

    public static RestaurantsManager getInstance() {
        if (instance == null) {
            instance = new RestaurantsManager();
        }
        return instance;
    }

    private RestaurantsManager() {
        // Nothing: ensure this is a singleton.
    }

    public void setRestaurantsManager(RestaurantsManager save){
        instance = save;
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
    //test

    @NonNull
    @Override
    public Iterator<Restaurant> iterator() {
        return restaurants.iterator();
    }


}
