package apdroid.clinica;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;


public class MapActivity extends Activity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng PERTH = new LatLng(-12.101045, -76.994194);
        Marker punto1=  googleMap.addMarker(new MarkerOptions()
                .position(PERTH).title("Sede San Borja").snippet("Area de Influencia")
                .visible(true));

        punto1.showInfoWindow();
        googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(-12.110261, -76.978386), new LatLng(-12.112456, -77.012422), new LatLng(-12.106413, -77.016714), new LatLng(-12.088873, -77.007616), new LatLng(-12.084995, -76.982389), new LatLng(-12.110261, -76.978386))
                .fillColor(0x7F00FF00).strokeColor(Color.BLUE).strokeWidth(2).zIndex(55));

        LatLng PERTH2 = new LatLng(-12.127973, -77.002176);
        Marker punto2=  googleMap.addMarker(new MarkerOptions()
                .position(PERTH2).title("Sede Santiago de Surco").snippet("Area de Influencia")
                .visible(true));
        punto2.showInfoWindow();

        googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(-12.113288, -77.018483  ), new LatLng(-12.111190, -76.993764  ), new LatLng(-12.127554, -76.981490  ), new LatLng(-12.147692, -76.986211   ), new LatLng(-12.141819, -77.017883   ), new LatLng(-12.113288, -77.018483 ) )
                .fillColor(0x7F00FF00).strokeColor(Color.BLUE).strokeWidth(2).zIndex(55));


    }
}
