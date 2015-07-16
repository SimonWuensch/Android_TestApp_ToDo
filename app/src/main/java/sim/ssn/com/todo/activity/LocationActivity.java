package sim.ssn.com.todo.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sim.ssn.com.todo.R;

public class LocationActivity extends Activity implements LocationListener {

    private LocationManager locationManager;

    private MapFragment mapFragment;
    private GoogleMap theMap;

    private Location location;

    String SHA1 = "50:BE:A8:46:F6:F5:F2:BC:FD:E4:22:3C:F1:F5:59:E4:2D:F8:B8:73";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        showMapFragment();
    }

    private void showMapFragment(){
        this.mapFragment = MapFragment.newInstance();
        getFragmentManager( ).beginTransaction( )
                .replace(R.id.activity_location_flContainer, this.mapFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initVariables();
    }

    private void initVariables(){
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }else{
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = "default";
            try{
                provider = locationManager.getBestProvider(criteria, true);
            }catch(Throwable t) {
                t.printStackTrace();
            }
            locationManager.requestLocationUpdates(provider, 0, 0, this);
        }


        this.theMap = this.mapFragment.getMap();
        this.theMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(LocationActivity.this, marker.getTitle() + " " + marker.getId(), Toast.LENGTH_SHORT).show( );
                return false;
            }
        });

        theMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng latLng) {
                addMarker(getCustomAddress(latLng.latitude, latLng.longitude, latLng.toString()));
            }
        });

        this.theMap.setOnMapLongClickListener( new GoogleMap.OnMapLongClickListener( ){
            @Override
            public void onMapLongClick( LatLng latLng ){
                updateCameraPosition(latLng);
            }
        });
    }

    private Address getCustomAddress(double latitude, double longitude, String... lines){
        Address address = new Address(Locale.GERMAN);
        address.setLatitude(latitude);
        address.setLongitude(longitude);
        for(int i = 0; i < lines.length; i++){
            address.setAddressLine(i, lines[i]);
        }
        return address;
    }

    public void updateCameraPosition(LatLng latLng){
        try{
            CameraUpdateFactory.newLatLng(latLng);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(6)
                    .bearing(270).tilt(30).build();
            theMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            theMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 10000, null);

        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager != null)
            locationManager.removeUpdates(this);
    }

    public void handleClick(View view){
        updateLocation();
        List<Address> addressList = getAddressList();
        StringBuilder locations = new StringBuilder();
        for(Address address : addressList){
            locations.append(addressList.indexOf(address)).append(":\n")//
                    .append("Longitude: ").append(address.getLongitude()).append("\n")//
                    .append("Latitude: ").append(address.getLatitude()).append("\n")//
                    .append(address.getAddressLine(0)).append("\n")//
                    .append(address.getAddressLine(1)).append("\n\n");

            addMarker(address);
        }

        TextView tvLocation = (TextView) findViewById(R.id.activity_location_tvLocation);
        tvLocation.setText(locations.toString());
    }

    private void updateLocation(){
        String locationProvider = LocationManager.GPS_PROVIDER;
        this.location = locationManager.getLastKnownLocation(locationProvider);

        if(location == null){
            Toast.makeText(this, "Location ist 'null'!", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Address> getAddressList() {
        Geocoder coder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        try{
            addressList = coder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
        }catch(Throwable t){
            t.printStackTrace();
            Toast.makeText(this, "Die Adressen konnten nicht geladen werden... ", Toast.LENGTH_SHORT).show();
        }

        if(addressList.isEmpty()) {
            addressList.add(getCustomAddress(49.77766, 9.96307, "Sanderheinrichsleitenweg", "20"));
        }

        Log.d(MainActivity.class.getSimpleName(), addressList.toString());
        return addressList;
    }

    private void addMarker(Address address){
        theMap.addMarker(new MarkerOptions()
                .position(new LatLng(address.getLatitude(), address.getLongitude()))
                .title(address.getAddressLine(0)));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        Log.d(MainActivity.class.getSimpleName(), "found new Location");
        Toast.makeText(this, "Found new Location... ", Toast.LENGTH_SHORT).show();
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
}