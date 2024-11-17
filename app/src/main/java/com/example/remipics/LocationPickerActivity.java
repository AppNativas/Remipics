package com.example.remipics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.appcompat.app.AppCompatActivity;

public class LocationPickerActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Posicionamos el mapa en una ubicación predeterminada (por ejemplo, la ubicación actual del usuario o una ubicación por defecto)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.7128, -74.0060), 15));

        // Configuramos el listener para la selección de ubicación
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if (mMap != null) {
                    // Eliminamos el marcador anterior
                    mMap.clear();
                    // Colocamos un nuevo marcador en la ubicación seleccionada
                    mMap.addMarker(new MarkerOptions().position(point).title("Ubicación seleccionada"));
                    selectedLocation = point;
                }
            }
        });
    }

    // Método para confirmar la selección de ubicación
    public void confirmLocation(View view) {
        if (selectedLocation != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("LATITUDE", selectedLocation.latitude);
            returnIntent.putExtra("LONGITUDE", selectedLocation.longitude);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "Por favor, selecciona una ubicación en el mapa", Toast.LENGTH_SHORT).show();
        }
    }
}
