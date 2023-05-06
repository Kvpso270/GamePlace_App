package fr.android.gameplace_app;

import android.app.FragmentManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalisationManager extends AppCompatActivity implements LocationListener {

    private LocationManager lm;
    private MapFragment mapFragment;
    private GoogleMap googleMap;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);

    }

    @Override
    @SuppressWarnings("MissingPermission" )
    protected void onResume() {
        super.onResume();
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (lm.isProviderEnabled( LocationManager.GPS_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0,this);
        }
        if (lm.isProviderEnabled( LocationManager.PASSIVE_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0,this);
        }
        if (lm.isProviderEnabled( LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0,this);
        }
        loadMap();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //if (requestCode == PE)
    }

    @SuppressWarnings("MissingPermission")
    private void loadMap(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LocalisationManager.this.googleMap = googleMap;
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(15));
                googleMap.setMyLocationEnabled(true);
                googleMap.addMarker(new MarkerOptions().position(new LatLng(43.7999945, 6.725426)).title("Infini Software"));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(lm != null){
            lm.removeUpdates(this );
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Toast.makeText( this, "Location: "+ latitude + "/" + longitude, Toast.LENGTH_LONG ).show();
        if (googleMap != null){
            LatLng googleLocation = new LatLng( latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation ));
        }
    }
}
