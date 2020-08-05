package com.example.restaurantlist.Model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;


//With reference to: https://developers.google.com/maps/documentation/android-sdk/utility/marker-clustering
//Detail behind cluster of markers on the map
public class MyClusterItem implements ClusterItem {

    private LatLng mPosition;
    private String mTitle;
    private BitmapDescriptor mHazard;

    public MyClusterItem(double lat, double lng, String Title, BitmapDescriptor mHazard) {
        this.mPosition = new LatLng(lat, lng);
        this.mTitle = Title;
        this.mHazard = mHazard;
    }

    public MyClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public BitmapDescriptor getHazard() {
        return mHazard;
    }

    @Override
    public String getSnippet() {
        return "";
    }
}
