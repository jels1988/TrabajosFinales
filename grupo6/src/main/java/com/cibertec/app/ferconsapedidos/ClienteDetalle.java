package com.cibertec.app.ferconsapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cibertec.app.ferconsapedidos.Entidad.Cliente;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class ClienteDetalle extends AppCompatActivity implements OnMapReadyCallback {
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;

    private TextView tvNombreCliente;
    private TextView tvRuc;
    private TextView tvDireccion;
    private TextView tvTelefono;
    private double latitud;
    private double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);
        Cliente cliente = getIntent().getParcelableExtra(ClientesActivity.ARG_CLIENTE);
        tvNombreCliente = (TextView)findViewById(R.id.tvNombreClente);
        tvDireccion = (TextView)findViewById(R.id.tvDireccion);
        tvRuc = (TextView)findViewById(R.id.tvRUC);
        tvTelefono=(TextView)findViewById(R.id.tvTelefono);


        tvNombreCliente.setText(cliente.getNombreCliente().toString());
        tvRuc.setText(cliente.getRUC().toString());
        tvDireccion.setText(cliente.getDireccion());
        tvTelefono.setText(cliente.getTelefono());
        latitud =cliente.getLatitud();
        longitud=cliente.getLongitud();

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(ClienteDetalle.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title("Marker").draggable(true));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitud, longitud)));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }
}
