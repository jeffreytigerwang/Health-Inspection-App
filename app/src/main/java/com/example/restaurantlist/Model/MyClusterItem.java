package com.example.restaurantlist.Model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyClusterItem implements ClusterItem {

    private LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private BitmapDescriptor mHazard;


    public MyClusterItem(double lat, double lng, String mTitle, BitmapDescriptor mHazard) {
        this.mPosition = new LatLng(lat, lng);
        this.mTitle = mTitle;
        this.mHazard = mHazard;
    }

    public MyClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }


    @Override
    public LatLng getPosition() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return "";
    }

    public BitmapDescriptor getHazard() {
        return mHazard;
    }
}
