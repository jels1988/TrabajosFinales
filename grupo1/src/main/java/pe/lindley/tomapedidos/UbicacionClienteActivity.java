package pe.lindley.tomapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.interfaces.Pasable;

public class UbicacionClienteActivity extends AppCompatActivity implements OnMapReadyCallback, Pasable {

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;

    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubicacioncliente_activity);

        cliente = getIntent().getExtras().getParcelable(ARG_CLIENTE);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(UbicacionClienteActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(cliente.getClienteLatitud()),
                        Double.parseDouble(cliente.getClienteLongitud())))
                .title(cliente.getClienteRazonSocial()).draggable(true));
        // valor entre 2 a 21
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(cliente.getClienteLatitud()),
                Double.parseDouble(cliente.getClienteLongitud())), 16));

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
