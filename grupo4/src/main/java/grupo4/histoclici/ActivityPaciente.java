package grupo4.histoclici;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import grupo4.histoclici.Utils.GPSTracker;
import grupo4.histoclici.dao.PacienteDAO;
import grupo4.histoclici.entidad.Paciente;

public class ActivityPaciente extends AppCompatActivity implements OnMapReadyCallback {
    TextView tvIdPaciente;
    EditText etPaciente, etTelefono, etCelular, etDomicilio;
    RadioButton rbF, rbM;
    Button btGps;
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMap;
    private int m_idPaciente;
    private double m_latitud = 0;
    private double m_longitud = 0;
    private double m_latitud_v = 0;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente);
        tvIdPaciente = (TextView)findViewById(R.id.tvIdPaciente);
        etPaciente = (EditText)findViewById(R.id.etPaciente);
        rbF = (RadioButton)findViewById(R.id.rbF);
        rbM = (RadioButton)findViewById(R.id.rbM);
        etTelefono = (EditText)findViewById(R.id.etTelefono);
        etCelular = (EditText)findViewById(R.id.etCelular);
        etDomicilio = (EditText)findViewById(R.id.etDomicilio);

        btGps = (Button)findViewById(R.id.bt_Gps);
        btGps.setOnClickListener(btGpsOnClickListener);

        if(getIntent().getExtras() != null){
            Paciente paciente = getIntent().getParcelableExtra(ActivityListaPaciente.ARG_PACIENTE);
            tvIdPaciente.setText(String.valueOf(paciente.getidPaciente()));
            m_idPaciente = Integer.parseInt( String.valueOf(paciente.getidPaciente()) );
            etPaciente.setText(paciente.getPaciente());
            if(paciente.getGenero().equals("F"))
                rbF.setChecked(true);
            else if(paciente.getGenero().equals("M"))
                rbM.setChecked(true);
            etTelefono.setText(paciente.getTelefono());
            etCelular.setText(paciente.getCelular());
            etDomicilio.setText(paciente.getDomicilio());
            m_latitud = Double.parseDouble( paciente.getLatitud() );
            m_longitud = Double.parseDouble( paciente.getAltitud() );
        }
        else
            m_idPaciente = 0;

        m_latitud_v = m_latitud;
        mSupportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mSupportMapFragment.getMapAsync(ActivityPaciente.this);

    }

    View.OnClickListener btGpsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            gps = new GPSTracker( ActivityPaciente.this );
            // check if GPS enabled
            if(gps.canGetLocation()){
                m_latitud = gps.getLatitude();
                m_longitud = gps.getLongitude();
                m_latitud_v = gps.getLatitude();
            }
            else{
                m_latitud_v = 0;
                gps.showSettingsAlert();
            }

            if ( m_latitud_v != 0 ){
                Toast.makeText(ActivityPaciente.this,R.string.ubicaciongps, Toast.LENGTH_LONG).show();
                mSupportMapFragment.getMapAsync(ActivityPaciente.this);
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_paciente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.a_GuardarPaciente) {

            if(etPaciente.getText().toString().length() == 0) {
                etPaciente.getText().clear();
                etPaciente.setHint(R.string.error_paciente);
                return false;
            }
            if(!rbF.isChecked() && !rbM.isChecked()){
                Toast.makeText(ActivityPaciente.this, R.string.error_genero, Toast.LENGTH_SHORT).show();
                return false;
            }

            if ( etTelefono.getText().toString().length() == 0 || etTelefono.getText().toString().length() < 7 ){
                Toast.makeText(ActivityPaciente.this, R.string.error_telefono, Toast.LENGTH_SHORT).show();
                return false;
            }

            if ( etCelular.getText().toString().length() == 0 || etCelular.getText().toString().length() < 9 ){
                Toast.makeText(ActivityPaciente.this, R.string.error_celular, Toast.LENGTH_SHORT).show();
                return false;
            }

            if ( etDomicilio.getText().toString().length() < 5 ){
                Toast.makeText(ActivityPaciente.this, R.string.error_domicilio, Toast.LENGTH_SHORT).show();
                return false;
            }

            if ( m_latitud_v == 0 ){
                Toast.makeText(ActivityPaciente.this, R.string.error_ubicaciongps, Toast.LENGTH_SHORT).show();
                return false;
            }

            Paciente paciente = new Paciente();
            paciente.setPaciente(etPaciente.getText().toString().trim());
            if(rbF.isChecked())
                paciente.setGenero("F");
            else if(rbM.isChecked())
                paciente.setGenero("M");
            paciente.setTelefono(etTelefono.getText().toString().trim());
            paciente.setCelular(etCelular.getText().toString().trim());
            paciente.setDomicilio(etDomicilio.getText().toString().trim());
            paciente.setLatitud( String.valueOf( m_latitud ) );
            paciente.setAltitud( String.valueOf( m_longitud ) );

            if(getIntent().getExtras() == null) {
                new PacienteDAO().insertarPaciente(paciente);
                Toast.makeText(ActivityPaciente.this, R.string.ok_insertar_paciente, Toast.LENGTH_SHORT).show();
            }
            else{
                paciente.setidPaciente(Integer.parseInt(tvIdPaciente.getText().toString().trim()));
                new PacienteDAO().actualizarPaciente(paciente);
                Intent intent = new Intent(ActivityPaciente.this, ActivityListaCita.class);
                setResult(RESULT_OK, intent);
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.clear();
        if (m_latitud_v != 0){
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(m_latitud, m_longitud)).title("Ubicación").draggable(true));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(m_latitud, m_longitud)));
        }
        else
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-12.050809, -77.034886))); //LIMA,PERU

        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
    }




}
