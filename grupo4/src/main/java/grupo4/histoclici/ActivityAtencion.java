package grupo4.histoclici;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import grupo4.histoclici.dao.AtencionDAO;
import grupo4.histoclici.entidad.Atencion;

public class ActivityAtencion extends AppCompatActivity {

    TextView m_tvPaciente;
    EditText m_etFecha;
    EditText m_etMotivo;
    EditText m_etTratamiento;
    private int m_idPaciente;

    public final static String ARG_IDPACIENTE = "ARG_IDPACIENTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atencion);

        m_tvPaciente = (TextView)findViewById( R.id.tv_Paciente );
        m_etFecha = (EditText)findViewById( R.id.et_Fecha );
        m_etMotivo = (EditText)findViewById( R.id.et_Motivo );
        m_etTratamiento = (EditText)findViewById( R.id.et_Tratamiento );

        m_tvPaciente.setText(getIntent().getStringExtra(  ActivityHistoriaClinica.ARG_PACIENTE ) );
        m_idPaciente = getIntent().getIntExtra( ActivityHistoriaClinica.ARG_IDPACIENTE, 0 );


        m_etFecha.setInputType(InputType.TYPE_NULL);
        m_etFecha.setOnKeyListener(null);
        m_etFecha.setOnFocusChangeListener(m_etFechaOnFocusChangeListener);

    }

    View.OnFocusChangeListener m_etFechaOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAtencion.this, dpm_etFechaOnDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.setTitle("Fecha");
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis() - 1000);
                calendar.set(2015,8,26);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener dpm_etFechaOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            m_etFecha.setText(sdf.format(c.getTime()));
            getWindow().getDecorView().clearFocus();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_atencion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        // }
        if (id == R.id.action_save) {

            if (m_etFecha.getText().toString().trim().length() < 10) {
                m_etFecha.getText().clear();
                m_etFecha.setHint(R.string.error_fecha_atencion);
                return false;
            }
            else if ( m_etMotivo.getText().toString().trim().length() <= 0 ){
                m_etMotivo.getText().clear();
                m_etMotivo.setHint(R.string.error_motivo);
                return false;
            }
            else if ( m_etTratamiento.getText().toString().trim().length() <= 0 ){
                m_etTratamiento.getText().clear();
                m_etTratamiento.setHint(R.string.error_tratamiento);
                return false;
            }

            Intent intent = new Intent();
            //intent.putExtra(ARG_IDPACIENTE ,m_idPaciente);
            setResult(RESULT_OK, intent);

            Atencion atencion = new Atencion();
            atencion.setIdPaciente(m_idPaciente);
            atencion.setFechaAtencion(m_etFecha.getText().toString());
            atencion.setMotivo(m_etMotivo.getText().toString());
            atencion.setTratamiento(m_etTratamiento.getText().toString());
            new AtencionDAO().insertarAtencion(atencion);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
