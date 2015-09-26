package apdroid.clinica;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.service.ClinicaService;
import apdroid.clinica.util.Constantes;
import apdroid.clinica.util.Utiles;

public class ReprogramarCitaActivity extends AppCompatActivity {

    private DatosCita datosCita;

    private TextView tvEspecialidad;
    private TextView tvDoctor;
    private TextView etFecha;
    private ImageButton btFecha;
    private TextView tv_nombre_local;
    private Spinner spHorarioReprog;

    private ArrayAdapter<String> aaHorario;

    private Button btReservCita;
    private TextView tvUser;
    private ClinicaService clinicaService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprogramar_cita);

        clinicaService = ClinicaService.getSingleton();

        configurarControles();

        cargarDatosCita();

    }

    private void configurarControles(){
        tvEspecialidad = (TextView)findViewById(R.id.tvEspecialidad);
        tvDoctor = (TextView)findViewById(R.id.tvDoctor);
        etFecha = (TextView)findViewById(R.id.etFecha);

        spHorarioReprog = (Spinner)findViewById(R.id.spHorarioReprog);
        String []opHorario = getResources().getStringArray(R.array.ListaHorarios);
        aaHorario = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, opHorario);
        spHorarioReprog.setAdapter(aaHorario);

        etFecha = (EditText) findViewById(R.id.etFecha);
        btFecha = (ImageButton) findViewById(R.id.btFecha);
        etFecha.setInputType(InputType.TYPE_NULL);
        etFecha.setOnKeyListener(null);
        etFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    configurarDatePicker();
                }
            }
        });
        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarDatePicker();
            }
        });
        btFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarDatePicker();
            }
        });

        btReservCita = (Button)findViewById(R.id.btReservCita);
        btReservCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reprogramarCita();
            }
        });

        tvUser = (TextView)findViewById(R.id.tvUser);
        tvUser.setText(Utiles.obtenerValorSharedPreference(ReprogramarCitaActivity.this, Constantes.ARG_NOMBRE));
        tv_nombre_local = (TextView) findViewById(R.id.tv_nombre_local);
    }

    private void configurarDatePicker(){

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ReprogramarCitaActivity.this, dpetOnDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.setTitle("Fecha de Cita");
        //datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }
    private DatePickerDialog.OnDateSetListener dpetOnDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etFecha.setText(sdf.format(c.getTime()));
            //getWindow().getDecorView().clearFocus();
        }
    };


    private void cargarDatosCita(){
        Intent intent = getIntent();
        if( intent != null ){
            datosCita = intent.getParcelableExtra(MainActivity.ARG_DATOS_CITA);
            tvEspecialidad.setText(datosCita.getEspecialidad());
            tvDoctor.setText(datosCita.getDoctor());
            etFecha.setText(datosCita.getFecha());
            spHorarioReprog.setSelection(aaHorario.getPosition(datosCita.getHora()));
            tv_nombre_local.setText(datosCita.getLocal());

        }


    }

    private void reprogramarCita(){

        if(validarReprogramar()){
            Intent intent= new Intent();

            datosCita.setFecha( etFecha.getText().toString() );
            datosCita.setHora((String) spHorarioReprog.getSelectedItem());
            intent.putExtra(MainActivity.ARG_DATOS_CITA, datosCita);

            clinicaService.actualizarCita(datosCita);

            setResult(RESULT_OK, intent);
            finish();

        }
    }

//    private void nuevaCita(){
//
//            Intent intent= new Intent();
//
//            datosCita.setFecha( etFecha.getText().toString() );
//            datosCita.setHora((String) sp.getSelectedItem());
//
//            intent.putExtra(MainActivity.ARG_DATOS_CITA, datosCita);
//
//            clinicaService.actualizarCita(datosCita);
//
//            setResult(RESULT_OK, intent);
//            finish();
//
//
//    }








    private boolean validarReprogramar(){
        boolean resp = true;

        if( "".equals(spHorarioReprog.getSelectedItem())  ){
            Toast.makeText(getApplicationContext(), "Debe ingresar Horario !!!", Toast.LENGTH_LONG).show();
            return false;
        }

        if( "".equals(etFecha.getText().toString().toString())  ){
            Toast.makeText(getApplicationContext(), "Debe ingresar Fecha !!!", Toast.LENGTH_LONG).show();
            return false;
        }

        return resp;
    }



}
