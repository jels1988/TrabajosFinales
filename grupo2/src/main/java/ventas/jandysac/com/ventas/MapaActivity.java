package ventas.jandysac.com.ventas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import ventas.jandysac.com.ventas.entities.Cliente;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private LatLng coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);

        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(MapaActivity.this);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(DatosCliente.ARG_COORDENADAS)) {
            String[] sCoordenadas = getIntent().getStringExtra(DatosCliente.ARG_COORDENADAS).toString().split(",");
            String nombre_cliente = getIntent().getStringExtra(DatosCliente.ARG_NOMBRE_CLIENTE);
            Double l = Double.valueOf(sCoordenadas[0]);
            Double a = Double.valueOf(sCoordenadas[1]);

            coordenadas = new LatLng(l, a);
            getSupportActionBar().setTitle(String.valueOf(nombre_cliente.toString()));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
        );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 17.0f));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
