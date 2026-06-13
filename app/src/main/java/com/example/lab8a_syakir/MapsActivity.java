package com.example.lab8a_syakir;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle BundleSavedInstanceState) {
        super.onCreate(BundleSavedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set Map view option to Hybrid (combination of normal & satellite)
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Define multiple LatLng coordinates (Sydney regional landmarks)
        LatLng sydney = new LatLng(-34, 151);
        LatLng tamWorth = new LatLng(-31.083332, 150.916672);
        LatLng newCastle = new LatLng(-32.916668, 151.750000);
        LatLng brisbane = new LatLng(-27.470125, 153.021072);

        // Map marking adjustments
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(tamWorth).title("Marker in Tamworth"));
        mMap.addMarker(new MarkerOptions().position(newCastle).title("Marker in Newcastle"));
        mMap.addMarker(new MarkerOptions().position(brisbane).title("Marker in Brisbane"));

        // Position camera focus centered on Sydney with an experimental zoom depth of 6
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 6f));
    }

    // Custom method bound via layout XML to search queried locations
    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null && !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                // Fetch the coordinates matching the name string
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                mMap.addMarker(new MarkerOptions().position(latLng).title("Search Result: " + location));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}