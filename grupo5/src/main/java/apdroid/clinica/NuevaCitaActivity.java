package apdroid.clinica;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import apdroid.clinica.adapter.spinner.SPDoctorAdapter;
import apdroid.clinica.adapter.spinner.SPEspecialidadAdapter;
import apdroid.clinica.adapter.spinner.SPHorarioAdapter;
import apdroid.clinica.dao.DB_Helper;
import apdroid.clinica.dao.DB_Manager;
import apdroid.clinica.dao.DoctorDao;
import apdroid.clinica.dao.EspecialidadDao;
import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.entidades.Doctor;
import apdroid.clinica.entidades.Especialidad;
import apdroid.clinica.service.ClinicaService;
import apdroid.clinica.util.Constantes;
import apdroid.clinica.util.Utiles;

/**
 * Created by AngeloPaulo on 01/septiembre/2015.
 */
public class NuevaCitaActivity extends AppCompatActivity {

    private ClinicaService clinicaService;
    private Spinner spHorario;
    private ArrayAdapter<String> aaHorario;

    private Spinner spEspecialidad;
    private SPEspecialidadAdapter spEspecialidadAdapter;

    //<editor-fold desc="Config Spinner Doctor">
    private Spinner spDoctor;
    private SPDoctorAdapter spDoctorAdapter;

    //<editor-fold desc="Config Calendar">
    private SimpleDateFormat formatoFecha;
    private ImageButton btCalendar;
    private TextView tvFecha;
    Calendar calendario = Calendar.getInstance();

    private DB_Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevacita);

        configSpEspecialidad();
        configCalendar();
        configSpDoctor();
        configSpHorario();
        configBtReservarCita();
        configUser();
    }



    private void configSpEspecialidad(){
        clinicaService = ClinicaService.getSingleton();
        spEspecialidad = (Spinner)findViewById(R.id.spEspecialidad);
        spEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrarDoctores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<Especialidad> listEspec = clinicaService.listarEspecialidades();
        ArrayList<Especialidad> lstSpinner = new ArrayList<>(listEspec);
        lstSpinner.add(0, new Especialidad(-1, getResources().getString(R.string.text_selec_especialidad) ) );
        spEspecialidadAdapter = new SPEspecialidadAdapter(this, lstSpinner);
        spEspecialidad.setAdapter(spEspecialidadAdapter);

    }
    //</editor-fold>

    private void filtrarDoctores(){
        Especialidad especialidad = (Especialidad)spEspecialidad.getSelectedItem();
        ArrayList<Doctor> lstDoctores = null;
        if( especialidad==null || especialidad.getIdEspecialidad()== -1 ){
            lstDoctores = clinicaService.listarDoctores();
        }else{
            lstDoctores = clinicaService.listarDoctoresEsp((Integer)especialidad.getIdEspecialidad());
        }


        spDoctorAdapter.setNewSource(lstDoctores);
    }



    private void configSpDoctor(){

        spDoctor= (Spinner)findViewById(R.id.spDoctor);
        ArrayList<Doctor> listDoc = clinicaService.listarDoctores();
        spDoctorAdapter= new SPDoctorAdapter(this,0,listDoc);
        spDoctor.setAdapter(spDoctorAdapter);

    }
    //</editor-fold>



    private void configSpHorario(){

        spHorario= (Spinner)findViewById(R.id.spHorario);
        //spHorario.setOnItemSelectedListener(spHoraOnItemSelectedListener);
        //ArrayList<Doctor> listDoc = clinicaService.listarDoctores();

        //ArrayList<Doctor> lstSpinnerDoc = new ArrayList<>(listDoc);
        String []opHorario = getResources().getStringArray(R.array.ListaHorarios);
        aaHorario = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, opHorario);

        //spHorarioAdapter= new SPHorarioAdapter(this, 0, lstHorario);
        spHorario.setAdapter(aaHorario);

    }
    //</editor-fold>



    private void configCalendar(){

        tvFecha = (TextView) findViewById(R.id.etFecha);
        btCalendar = (ImageButton) findViewById(R.id.btCal);

        btCalendar.setOnClickListener(btCalendarOnClickListener);
        tvFecha.setOnClickListener(tvFechaOnClickListener);

        formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    DatePickerDialog.OnDateSetListener dpFechaOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFecha();

        }
    };

    View.OnClickListener tvFechaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setFecha();
        }
    };


    View.OnClickListener btCalendarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setFecha();

        }
    };

    public void updateFecha() {
        tvFecha.setText(formatoFecha.format(calendario.getTime()));

    }

    public void setFecha() {
        DatePickerDialog datePickerDialog =  new DatePickerDialog(NuevaCitaActivity.this, dpFechaOnDateSetListener,
                calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.setTitle(getResources().getString(R.string.text_fecha) );
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()+86400000);//86400000 = 24 horas
        datePickerDialog.show();
    }
    //</editor-fold>

    //<editor-fold desc="Config - USER">
    private TextView tvUser;

    private void configUser(){

        tvUser= (TextView) findViewById(R.id.tvUser);

        String nuser = this.getIntent().getStringExtra(MainActivity.ARG_USUARIO);
        tvUser.setText(nuser);

    }
    //</editor-fold>

    //<editor-fold desc="Config Button Reservar Cita">
    private Button btReservCita;
    private DatosCita datosCita;

    private void configBtReservarCita() {
        btReservCita = (Button) findViewById(R.id.btReservCita);
        btReservCita.setOnClickListener(btReservCitaOnClickListener);
    }



    View.OnClickListener btReservCitaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            grabarCita();
        }
    };
    //</editor-fold>


    private void grabarCita(){
        Especialidad especialidad = (Especialidad)spEspecialidad.getSelectedItem();
        String hora = spHorario.getSelectedItem().toString();
        Doctor doctor = (Doctor)spDoctor.getSelectedItem();

        if( especialidad.getIdEspecialidad() == -1 || "".equals(tvFecha.getText().toString().trim()) || "".equals(hora)
                || doctor==null){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.text_msg_ingresardatos), Toast.LENGTH_LONG).show();
        }else{
            Intent resultIntent=new Intent();
            DatosCita data=new DatosCita();



            int idPaciente =  Utiles.obtenerValorSharedPreferenceInt(this, Constantes.ARG_NUSER) ;

            //data.setDoctor(docSel);
            data.setFecha(tvFecha.getText().toString());
            //data.setDetalleConsulta("");
            data.setHora(hora);
            data.setIdDoctor(doctor.getIddoc());
            data.setIdEspecialidad(especialidad.getIdEspecialidad());
            data.setIdPaciente(idPaciente);

            clinicaService.nuevaCita(data);

            resultIntent.putExtra("data", data);
            setResult(RESULT_OK, resultIntent);
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.text_msg_gracias), Toast.LENGTH_LONG).show();
            finish();

        }



    }



}
