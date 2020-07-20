package com.example.restaurantlist.UI;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantlist.Model.Inspection;
import com.example.restaurantlist.Model.InspectionManager;
import com.example.restaurantlist.Model.MyClusterItem;
import com.example.restaurantlist.Model.Restaurant;
import com.example.restaurantlist.Model.RestaurantsManager;
import com.example.restaurantlist.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
      GoogleMap.OnInfoWindowClickListener

{

    private GoogleMap mMap;
    private Marker mMarker;
    private RestaurantsManager manager = RestaurantsManager.getInstance();


    private FusedLocationProviderClient mfusedLocationProviderClient;
    private RestaurantsManager restaurants;
    private InspectionManager inspections;
    private ClusterManager<MyClusterItem> mClusterManager;


    private static final String TAG = "MapsActivity";
    private static final float DEFAULT_ZOOM = 15f;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    
    //widgets
    private EditText searchText;
    private ImageButton deviceGPS;
    private ImageButton changelist;



    
    //vars
    private Boolean mLocationPermissionsGrandted = false;
    private static final String EXTRA_MESSAGE = "ExtraMessage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchText = (EditText)findViewById(R.id.input_search);
        deviceGPS  = (ImageButton) findViewById(R.id.ic_device);
        changelist = (ImageButton)  findViewById(R.id.ic_change);
        restaurants= RestaurantsManager.getInstance();
        inspections= InspectionManager.getInstance();

        getLocationPremission();
        changelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MapsActivity.this, ListActivity.class);
                finish();
                startActivity(intent);

            }
        });


    }

    private void initsearch(){
        Log.d(TAG,"initsearch: initializing");

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                ||actionId == EditorInfo.IME_ACTION_DONE
                ||event.getAction() == event.ACTION_DOWN
                ||event.getAction() == event.KEYCODE_ENTER)   {
                    
                    //execute our method for searching
                    geolocate();
                }
                
                return false;
            }

        });
        deviceGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked device icon");
                getDeviceLocation();
            }
        });

        hideSoftKeyboard(this);
}

    private void geolocate() {
        Log.d(TAG,"geoLocate: geolocating");
        String searchstring =searchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchstring,1);
        }catch (IOException e){
            Log.e(TAG,"geoLocate: IOException:  " + e.getMessage());
        }
        if (list.size()>0){
            Address address = list.get(0);
            Log.d(TAG,"geoLocate: found a location: " + address.toString());
            //Toast.makeText(this,address.toString(),Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, " getDeviceLocation: getting the current devices location");
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGrandted) {

             Task location = mfusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()&&task.getResult() != null) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            assert currentLocation != null;
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
                                   , DEFAULT_ZOOM
                            ,"My Location");
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }


    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera : moving the camera to: lat " + latLng.latitude + ", lng:" + latLng.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if(!title.equals("My Location")) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);
        }
        hideSoftKeyboard(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGrandted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGrandted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission granted");
                    mLocationPermissionsGrandted = true;
                    //initalize our map
                    initMap();

                }
            }
        }
    }

    private void getLocationPremission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGrandted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }


    }
    
    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        mClusterManager = new ClusterManager<>(this, mMap);

        //Clustering
        setUpClusterer();

        //Set Custom InfoWindow Adapter
        CustomInfoAdapter adapter = new CustomInfoAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);

        if (mLocationPermissionsGrandted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               return;
            }

            mMap.setMyLocationEnabled(true);
            initsearch();
        }

/***
        Intent i_receive = getIntent();
        String resID = i_receive.getStringExtra(EXTRA_MESSAGE);

        if (resID != null) {
            HandleReceivingCoordinates(resID);
        }

 ***/

/***
        //mark restaurants on the map
        for(int i=0;i< restaurants.getNumRestaurants();i++) {
            LatLng sydney = new LatLng(restaurants.get(i).getLatitude(), restaurants.get(i).getLongitude());
           // mMap.addMarker(new MarkerOptions().position(sydney).title("marker in " + restaurants.get(i).getRestaurantName()));
           // MarkerOptions markerOptions;
           // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            String color ="blue";
            for(int j=0;j<inspections.getSize();j++) {
                if(restaurants.get(i).getTrackingNumber().equals(inspections.get(j).getTrackingNum()))
                { color = inspections.get(j).getColour();
                    break;}
            }
              String detail="Address: "
                      +restaurants.get(i).getAddress()
                      +"\r"
                      +". Hazard level: "
                      +color;
              mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,DEFAULT_ZOOM));
              MarkerOptions markerOptions = new MarkerOptions().position(sydney).title(restaurants.get(i).getRestaurantName()).snippet(detail);



            switch (color){
                case "blue" :
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    break;
                case "red" :
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    break;
                default:
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    break;
            }
            mMap.addMarker(markerOptions);
        }
***/
          // if user click the info window, they can see all inspections
          mMap.setOnInfoWindowClickListener(this);

    }

    private class CustomInfoAdapter implements GoogleMap.InfoWindowAdapter {

        private Activity context;

        public CustomInfoAdapter(Activity context){
            this.context = context;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View itemView = context.getLayoutInflater().inflate(R.layout.map_info, null);

            LatLng latLng0 = marker.getPosition();
            double lat = latLng0.latitude;
            double lng = latLng0.longitude;
            Restaurant restaurant = manager.findRestaurantByLatLng(lat, lng);

            ImageView logo = itemView.findViewById(R.id.info_item_restaurantLogo);
            //logo.setImageResource(restaurant.getIcon());

            TextView restaurantNameText = itemView.findViewById(R.id.info_item_restaurantName);
            String temp = restaurant.getRestaurantName();

            if (temp.length() > 25) {
                temp = temp.substring(0, 25) + "...";
            }
            restaurantNameText.setText(temp);


            Inspection mostRecentInspection = restaurant.getInspections().get(0);

            for (int j = 0; j < inspections.getSize(); j++){
                if (restaurant.getTrackingNumber().equals(inspections.get(j).getTrackingNum())){
                    mostRecentInspection = inspections.get(j);
                    break;
                }


            }
            if (mostRecentInspection != null) {
                TextView numNonCriticalText = itemView.findViewById(R.id.textSetMapHazard);
                numNonCriticalText.setText(mostRecentInspection.getHazardRating());

                TextView numCriticalText = itemView.findViewById(R.id.textSetMapAddress);
                numCriticalText.setText(restaurant.getAddress());



                ImageView hazard = itemView.findViewById(R.id.info_item_hazardImage);
                //hazard.setImageResource(mostRecentInspection.getHazardIcon());

            }

            return itemView;
        }
    }



    private void setUpClusterer() {
        mMap.setOnCameraIdleListener(mClusterManager);
        addItems();
        mClusterManager.cluster();
        mClusterManager.setRenderer(new MarkerClusterRenderer(getApplicationContext(), mMap, mClusterManager));
    }


//just for testing purpose
    private void addItems() {
        RestaurantsManager manager = RestaurantsManager.getInstance();
        //List<Restaurant> restaurants = manager.getRestaurants();
        for (Restaurant restaurant : manager) {

            String temp = restaurant.getRestaurantName();

            String color ="blue";
            for(int j=0;j<inspections.getSize();j++) {
                if(restaurant.getTrackingNumber().equals(inspections.get(j).getTrackingNum()))
                { color = inspections.get(j).getColour();
                    break;}
            }
            MyClusterItem newItem;
            switch (color){
                case "blue" :
                     newItem = new MyClusterItem(restaurant.getLatitude(),
                            restaurant.getLongitude(),
                            temp, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    break;
                case "red" :
                     newItem = new MyClusterItem(restaurant.getLatitude(),
                            restaurant.getLongitude(),
                            temp, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    break;
                default:
                     newItem = new MyClusterItem(restaurant.getLatitude(),
                            restaurant.getLongitude(),
                            temp, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                    break;
            }
          /*  MyClusterItem newItem = new MyClusterItem(restaurant.getLatitude(),
                    restaurant.getLongitude(),
                    temp, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));*/

            mClusterManager.addItem(newItem);
        }

    }

   // code found from  https://juejin.im/post/58d8ccb45c497d005702dae6
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        int count=0;
        for (int i = 0; i < restaurants.getNumRestaurants(); i++) {
            if (marker.getPosition().longitude == restaurants.get(i).getLongitude()){
                count=i;
                break;
            }
        }
        Intent intent = restaurantDetailsActivity.makeLaunchIntent(MapsActivity.this, EXTRA_MESSAGE);
        //Sends index of which restaurant was click on in ViewList
        RestaurantsManager.getInstance().setCurrentRestaurant(count);
        startActivity(intent);

    }

    /***
    private void HandleReceivingCoordinates(String resID) {
        RestaurantsManager manager = RestaurantsManager.getInstance();

        Restaurant goToRes = null;
        boolean found = false;
        int i = 0;
        for (Restaurant temp : manager) {
            if (resID.equals(temp.getTrackingNumber())) {
                goToRes = temp;
                found = true;
                break;
            }
            i++;
        }

        if (found) {
            mClusterManager.clearItems();
            moveCamera(new LatLng(goToRes.getLatitude(),
                    goToRes.getLongitude()), DEFAULT_ZOOM, null);
            String temp = goToRes.getRestaurantName();

            MarkerOptions options = new MarkerOptions().
                    position(new LatLng(goToRes.getLatitude(),
                            goToRes.getLongitude())).
                    title(temp);

            mMarker = mMap.addMarker(options);
            mMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            mMarker.showInfoWindow();
            moveCamera(new LatLng(goToRes.getLatitude(),
                    goToRes.getLongitude()), DEFAULT_ZOOM, null);
        }
    }
    ***/


    private class MarkerClusterRenderer extends DefaultClusterRenderer<MyClusterItem> {

        public MarkerClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<MyClusterItem> clusterManager) {
            super(context, map, mClusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyClusterItem item, MarkerOptions markerOptions) {
            // use this to make your change to the marker option
            // for the marker before it gets render on the map
            markerOptions.icon(item.getHazard());
            markerOptions.title(item.getTitle());
            super.onBeforeClusterItemRendered(item, markerOptions);
        }
    }
}