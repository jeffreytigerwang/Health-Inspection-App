package Model;

import com.example.restaurantlist.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data model: Store a collection of restaurants.
 */

public class RestaurantsManager {
    private List<Restaurant> restaurants = new ArrayList<>();
    private static RestaurantsManager instance;


    public static RestaurantsManager getInstance() {
        if (instance == null) {
            instance = new RestaurantsManager();
        }
        return instance;
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

    public int getNumRestaurants() {
        return restaurants.size();
    }











}
