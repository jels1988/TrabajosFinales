package com.example.angelica.apppizzahut.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.angelica.apppizzahut.Entity.Local;
import com.example.angelica.apppizzahut.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocalMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_mapa);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        mSupportMapFragment.getMapAsync(LocalMapaActivity.this);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(LocalActivity.ARG_LOCAL) ){
            local = (Local)getIntent().getParcelableExtra(LocalActivity.ARG_LOCAL);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicion = new LatLng(Double.parseDouble(local.getLatitud()), Double.parseDouble(local.getLongitud()));

        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.addMarker(new MarkerOptions()
                        .position(posicion)
                        .title(local.getNombre())
                        .snippet(local.getDireccion())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pizza_marker))
        );
        mGoogleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(posicion, 15)
        );
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

    }
}
