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

    // La méthode onCreate est appelée lors de la création de l'activité
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragrment_localisation);

        // On récupère le fragment de la carte depuis le layout
        FragmentManager fragmentManager = getFragmentManager();
        mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);

    }

    // La méthode onResume est appelée lorsque l'activité reprend le focus
    @Override
    @SuppressWarnings("MissingPermission" )
    protected void onResume() {
        super.onResume();

        // On récupère le service de localisation
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        // On vérifie si les différents fournisseurs de localisation sont activés et on demande des mises à jour de position
        if (lm.isProviderEnabled( LocationManager.GPS_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0,this);
        }
        if (lm.isProviderEnabled( LocationManager.PASSIVE_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0,this);
        }
        if (lm.isProviderEnabled( LocationManager.NETWORK_PROVIDER)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000,0,this);
        }

        // On charge la carte
        loadMap();
    }

    // La méthode onRequestPermissionsResult est appelée lorsque l'utilisateur a répondu à la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //if (requestCode == PE)
    }

    // La méthode loadMap charge la carte une fois qu'elle est prête
    @SuppressWarnings("MissingPermission")
    private void loadMap(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // On récupère la carte
                LocalisationManager.this.googleMap = googleMap;
                // On zoome sur la carte
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(10));
                // On affiche la position de l'utilisateur sur la carte
                googleMap.setMyLocationEnabled(true);
            }
        });
    }

    // La méthode onPause est appelée lorsque l'activité perd le focus
    @Override
    protected void onPause() {
        super.onPause();

        // On arrête les mises à jour de position
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
        // Récupération des coordonnées de la nouvelle position
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Affichage d'un toast avec les coordonnées
        Toast.makeText( this, "Location: "+ latitude + "/" + longitude, Toast.LENGTH_LONG ).show();
        // Mise à jour de la position sur la carte si elle est disponible
        if (googleMap != null){
            LatLng googleLocation = new LatLng( latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(googleLocation ));
        }
    }
}
