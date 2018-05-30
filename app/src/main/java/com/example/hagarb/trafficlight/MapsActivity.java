package com.example.hagarb.trafficlight;

import android.Manifest;
import com.google.maps.android.*;
import com.google.maps.android.SphericalUtil;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.widget.Button;

import org.json.JSONArray;
///////////
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
///////////////

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.bind.TypeAdapters;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
//import com.pubnub.api.
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import android.app.Activity;
import android.os.Bundle;
//import android.widget;
import android.widget.Toast;
import android.content.Context;

//import static com.example.hagarb.trafficlight.R.id.textView;

import android.location.Location;
//import com.google.android.gms.location.LocationResul

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //    private boolean gpsEnabled = false;
    public static boolean Messageflag = false;
    private GoogleMap mMap;

    //    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 1000; // 1 second
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocationManager lm = (LocationManager)
                this.getSystemService(LOCATION_SERVICE);
//        gpsEnabled = lm
//                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button button = (Button) findViewById(R.id.buttonExit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, ageActivity.class);
                startActivity(intent);
            }
        });
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-57f7dbae-c9e6-11e7-8a2d-cad296c360f6");
        pnConfiguration.setPublishKey("pub-c-e31073c6-6513-4b2a-8d0b-99535bc49aff");
        pnConfiguration.setSecure(true);
        PubNub pubnub = new PubNub(pnConfiguration);
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {

                            /*
                                   if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                                       // This event happens when radio / connectivity is lost
                                   }

                                   else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {

                                       // Connect event. You can do stuff like publish, and know you'll get it.
                                       // Or just use the connected event to confirm you are subscribed for
                                       // UI / internal notifications, etc

                                       ///////////////***************Publish messages************ No Need!!!!!!!!!!!!!!!!!!

                                       if (status.getCategory() == PNStatusCategory.PNConnectedCategory){
                                           pubnub.publish().channel("guy").message("kori").async(new PNCallback<PNPublishResult>() {
                                               @Override
                                               public void onResponse(PNPublishResult result, PNStatus status) {
                                                   // Check whether request successfully completed or not.
                                                   if (!status.isError()) {

                                                       // Message successfully published to specified channel.
                                                   }
                                                   // Request processing failed.
                                                   else {

                                                       // Handle message publish error. Check 'category' property to find out possible issue
                                                       // because of which request did fail.
                                                       //
                                                       // Request can be resent using: [status retry];
                                                   }
                                               }
                                           });
                                       }
                                   }
                                   else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {

                                       // Happens as part of our regular operation. This event happens when
                                       // radio / connectivity is lost, then regained.
                                   }
                                   else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {

                                       // Handle messsage decryption error. Probably client configured to
                                       // encrypt messages and on live data feed it received plain text.
                                   }
                            */
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                // TextView textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);

                Context context = getApplicationContext();
                JsonPrimitive contacts = message.getMessage().getAsJsonPrimitive();
                String s = contacts.getAsString();
                int statusIndex = s.indexOf("conditions");
                int TlcCodeIndex = s.indexOf("id");
//                TrafficLightStatus tlc =new TrafficLightStatus( s.charAt(statusIndex+5)=='0' ? true:false,s.charAt(TlcCodeIndex+2));
                if (s.charAt(statusIndex + 1) == '0') {

                }
                Log.d(s, s);
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }

        });

        pubnub.subscribe().channels(Arrays.asList("Tzahi")).execute();

    }//onCreate

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
        //Initialize all the activity and markers on the google map

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng trafficLight1 = new LatLng(31.260955, 34.798259);
        mMap.addMarker(new MarkerOptions().position(trafficLight1).title("Rager_BenGurion_FaceOut"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight1));
        ///((instead of the last line/float zoomLevel = 16.0; //This goes up to 21
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trafficLight1, zoomLevel));))
        ////
        ///
        LatLng trafficLight2 = new LatLng(31.261089, 34.798114);
        mMap.addMarker(new MarkerOptions().position(trafficLight2).title("Rager_BenGurion_FaceIn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight2));

        LatLng trafficLight3 = new LatLng(31.275161, 34.796419);
        mMap.addMarker(new MarkerOptions().position(trafficLight3).title("Rager_AvitalHatzadik_FaceOut"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight3));

        LatLng trafficLight4 = new LatLng(31.275169, 34.795927);
        mMap.addMarker(new MarkerOptions().position(trafficLight4).title("Rager_AvitalHatzadik_FaceIn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight4));

        LatLng trafficLight5 = new LatLng(31.262401, 34.801107);
        mMap.addMarker(new MarkerOptions().position(trafficLight5).title("Aroma_University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight5));

        LatLng trafficLight6 = new LatLng(32.076551, 34.776788);
        mMap.addMarker(new MarkerOptions().position(trafficLight6).title("Tel Aviv"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight6));

        final LatLng trafficLight7 = new LatLng(31.258666, 34.797663);
        mMap.addMarker(new MarkerOptions().position(trafficLight7).title("Metzada"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(trafficLight7));

//        LocationRequest locationReq = new LocationRequest();
//        locationReq.setInterval(10000);
//        locationReq.setFastestInterval(5000);
//        locationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationReq);


        LocationListener ll=new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                Toast.makeText(
                        getBaseContext(),
                        "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                                + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                LatLng myLocation = new LatLng( loc.getLatitude(),loc.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(myLocation).title("MyLocation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                //Tzahi try
                double dist1 = distance(loc.getLatitude(),loc.getLongitude(),31.258666,34.797663);

                LatLng from = new LatLng(loc.getLatitude(),loc.getLongitude());
                LatLng to = new LatLng(31.258666,34.797663);

                //Calculating the distance in meters
                Double distance = SphericalUtil.computeDistanceBetween(from, to);

                //Displaying the distance
                Toast.makeText(getBaseContext(),String.valueOf(distance+" Meters"),Toast.LENGTH_SHORT).show();

                TextView textViewVelocity = (TextView) findViewById(R.id.textViewVelocity);
                textViewVelocity.setText((int) (loc.getSpeed()) + " km/h"); // is it in km/h?
                TextView textViewDistance = (TextView) findViewById(R.id.textViewDistance);
                double dist =distance(loc.getLatitude(),loc.getLongitude(),trafficLight7.latitude,trafficLight7.longitude);
                textViewDistance.setText((int)dist +" m ");  // is it in m?

                if (dist1<800) {
                    if (!Messageflag)
                    Toast.makeText(
                            getBaseContext(),
                            "you are need to stop", Toast.LENGTH_SHORT).show();
                        Toast.makeText(
                                getBaseContext(),
                                "Not", Toast.LENGTH_SHORT).show();
                    SetMessageFlag(true);
                }
                if (dist1>800){
                    SetMessageFlag(false);
                }
                //end of try
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationManager lm = (LocationManager)
                this.getSystemService(LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,10,ll);
        try {
            Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            l.getLatitude();
            l.getLongitude();

            LatLng myLocation = new LatLng(l.getLatitude(), l.getLongitude());

            CameraPosition BONDI =
                    new CameraPosition.Builder()
                            .target(new LatLng(l.getLatitude(), l.getLongitude()))
                            .zoom(15.5f)
                            .bearing(300)
                            .tilt(50)
                            .build();
            mMap. moveCamera(CameraUpdateFactory
                    .newCameraPosition(BONDI));

            //mMap.addMarker(new MarkerOptionsd().position(myLocation).title("MyLocationNOW!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            mMap.setMyLocationEnabled(true);
        }
        catch( Exception gps)
        {}
/**
 thread=  new Thread(){
@Override
public void run(){
try {
synchronized(this){
wait(3000);
}
}
catch(InterruptedException ex){
}

// TODO
}
 **/


    }//onMapReady

    private static void SetMessageFlag(Boolean flag){
        Messageflag= flag;
    }

    ///Hagar - distance
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist*1000); //return in meters


    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    ///Hagar - distance
//    private class MyLocationListener implements LocationListener {
//        // ContextWrapper wrapper = new ContextWrapper(context);
//
//        public void onLocationChanged(Location loc) {
//            //pb.setVisibility(View.INVISIBLE);
//            Toast.makeText(
//                    getBaseContext(),
//                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
//                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
//            // String longitude = "Longitude: " + loc.getLongitude();
//            // Log.v(TAG, longitude);
//            // String latitude = "Latitude: " + loc.getLatitude();
//            // Log.v(TAG, latitude);
//
//            //  String s = longitude + "-" + latitude ;
//            //  return s;LatLng trafficLight4 = new LatLng(31.275169, 34.795927);
//            LatLng myLocation = new LatLng( loc.getLatitude(),loc.getLongitude());
////            mMap.addMarker(new MarkerOptions().position(myLocation).title("MyLocation"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
//            //Tzahi try
//            double dist1 = distance(loc.getLatitude(),loc.getLongitude(),31.258666,34.797663);
//            if (dist1<1500) {
//                Toast.makeText(
//                        getBaseContext(),
//                        "you are need to stop", Toast.LENGTH_SHORT).show();
//            }
//            //end of try
//        }


        private void algorithm (int path ) {
            int z = 0;
            int i = 0;
            int imax = 0;

            int[] listOfTL={0};

            if (path == 1) {
                int[]  list = {1, 3, 5};
                imax=3;
                listOfTL=list;

            } else if (path == 2) {
                int[] list = {6, 4, 2};
                imax=3;
                listOfTL=list;

            } else  if (path==3){ //
                int[] list = {1, 3, 5, 6, 4, 2};
                imax = 6;
                listOfTL=list;
            }
            for ( i=1;i<=imax;i++) {
                z = listOfTL[i];
                //    TextView textViewDistance = (TextView) findViewById(R.id.textViewDistance);
                //double dist = distance(l.getLatitude(), l.getLongitude(), trafficLight2.latitude, trafficLight2.longitude);
                //textViewDistance.setText((int) dist + " m ");  // is it in m?
                /// the rest of the algorithm;
            }
            ///*** here anorther algorithm for computing is running///

        }


//
//        @Override
//        public void onProviderDisabled(String provider) {}
//
//        @Override
//        public void onProviderEnabled(String provider) {}
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {}
//    }

}//distance


