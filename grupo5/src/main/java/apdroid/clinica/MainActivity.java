package apdroid.clinica;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import apdroid.clinica.adapter.DrawerItem;
import apdroid.clinica.adapter.DrawerListAdapter;
import apdroid.clinica.adapter.recyclerview.RVDatosCitasAdapter;
import apdroid.clinica.adapter.spinner.SPEspecialidadAdapter;
import apdroid.clinica.dao.DB_Helper;
import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.entidades.Especialidad;
import apdroid.clinica.entidades.Paciente;
import apdroid.clinica.service.ClinicaService;
import apdroid.clinica.util.Constantes;
import apdroid.clinica.util.Idioma;
import apdroid.clinica.util.Utiles;

/**
 * Clase para la pantalla principal despues del Login
 */
public class MainActivity extends AppCompatActivity implements RVDatosCitasAdapter.RVDatosCitasAdapterListener  {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar; //Declarando toolbar

    private String[] tagTitles;



    private Spinner spEspecialidad;
    private SPEspecialidadAdapter spEspecialidadAdapter;
    private EditText tvFechaFiltro;
    private ImageButton btFechaFiltro;
    private ImageButton btFechaLimpiar;

    private RecyclerView lstDatosCitas;
    private RVDatosCitasAdapter rvDatosCitasAdapter;

    private ClinicaService clinicaService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DB_Helper manager= new DB_Helper(this);
        try {
            manager.createDataBase();
            manager.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clinicaService = ClinicaService.getSingleton();

        configurarMenu(savedInstanceState);
        configurarControles();



    }

    private void configurarControles(){


        spEspecialidad = (Spinner)findViewById(R.id.spEspecialidad);

        ArrayList<Especialidad> listEspec = clinicaService.listarEspecialidades();
        if(spEspecialidadAdapter == null){

            ArrayList<Especialidad> lstSpinner = new ArrayList<>(listEspec);
            lstSpinner.add(0, new Especialidad(-1, getResources().getString(R.string.text_selec_especialidad) ));
            spEspecialidadAdapter = new SPEspecialidadAdapter(this, lstSpinner);
            spEspecialidad.setAdapter(spEspecialidadAdapter);
        }



        spEspecialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filtrarCitas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        lstDatosCitas =(RecyclerView) findViewById(R.id.lstDatosCitas);
        lstDatosCitas.setHasFixedSize(true);
        lstDatosCitas.setLayoutManager(new LinearLayoutManager(this));
        rvDatosCitasAdapter = new RVDatosCitasAdapter(MainActivity.this);
        lstDatosCitas.setAdapter(rvDatosCitasAdapter);

        configurarFiltroFechas();
    }

    private void configurarFiltroFechas(){

        tvFechaFiltro = (EditText) findViewById( R.id.tvFechaFiltro );
        btFechaFiltro = (ImageButton) findViewById( R.id.btFechaFiltro );
        btFechaLimpiar = (ImageButton) findViewById(R.id.btFechaLimpiar);

        tvFechaFiltro.setInputType(InputType.TYPE_NULL);
        tvFechaFiltro.setOnKeyListener(null);
        tvFechaFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    filtrarCitas();
                }
            }
        });
        tvFechaFiltro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    configurarDatePicker();
                }
            }
        });
        tvFechaFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarDatePicker();
            }
        });
        btFechaFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarDatePicker();
            }
        });
        btFechaLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFechaFiltro.setText("");
            }
        });

    }

    private void configurarDatePicker(){

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, dpetOnDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.setTitle(getResources().getString(R.string.text_titulo_dp_fecha));
        //datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dpetOnDateSetListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            tvFechaFiltro.setText(sdf.format(c.getTime()));
            //getWindow().getDecorView().clearFocus();
        }
    };



    private void configurarMenu(Bundle savedInstanceState){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);


        tagTitles = getResources().getStringArray(R.array.Tags);


        //setear toolbar
        toolbar.dismissPopupMenus();
        setSupportActionBar(toolbar);


        // Setear una sombra sobre el contenido principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerLayout.setDrawerListener(drawerToggle); //Seteamos la escucha

        //Creamos lista de items de Menu
        ArrayList<DrawerItem> items = new ArrayList<>();
        items.add(new DrawerItem(tagTitles[0], R.drawable.ic_nuevacita));
        items.add(new DrawerItem(tagTitles[1], R.drawable.ic_micuenta));
        items.add(new DrawerItem(tagTitles[2], R.drawable.ic_locales));
        items.add(new DrawerItem(tagTitles[3], R.drawable.ic_salir));

        //Creamos adaptador y seteamos al Drawerlist
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        //Seteamos escucha
        drawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        // Habilitar el icono de la app por si hay algún estilo que lo deshabilitó
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Crear ActionBarDrawerToggle para la apertura y cierre
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(getTitle());
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getTitle());
            }
        };
        //Seteamos la escucha al drawer layout
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void filtrarCitas(){
        int idPaciente = -1;
        idPaciente = Utiles.obtenerValorSharedPreferenceInt(this, Constantes.ARG_NUSER) ;
        String fecha = tvFechaFiltro.getText().toString();
        Especialidad especialidad = (Especialidad)spEspecialidad.getSelectedItem();

        ArrayList<DatosCita> listaCitas = null;
        DatosCita datosCita = new DatosCita();
        datosCita.setIdEspecialidad( especialidad.getIdEspecialidad() );
        datosCita.setFecha(fecha);
        datosCita.setIdPaciente(idPaciente);

        listaCitas = clinicaService.filtrarCitas(datosCita);
        rvDatosCitasAdapter.setNewSource(listaCitas);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }


    /**
     * Seleccion de menu
     * @param position
     */
    private void selectItem(int position){
        cargarPantalla(position);

        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
    }

    /**
     * Muestra la pantalla segun el menu seleccionado
     * @param menuSelected
     */
    private void cargarPantalla(int menuSelected){

        switch (menuSelected){
            case 0: //Nueva Cita
                cargarNuevaCita();
                break;
            case 1:
                cargarMiCuenta();
                break;
            case 2:
                cargarNuestrosLocales();
                break;
            case 3:
                salir();
                break;

        }
    }

    private void salir() {
        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().clear().commit();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * Carga la pantalla de nueva cita
     */

    //private int Req_Code=22;
    public static String ARG_USUARIO =  "npersona";

    private void cargarNuevaCita(){
        //int usuario = this.getIntent().getIntExtra("user",0);
        String nusuario=this.getIntent().getStringExtra( ARG_USUARIO );


        Intent intent = new Intent(MainActivity.this, NuevaCitaActivity.class);
        //intent.putExtra("user",usuario);
        intent.putExtra(ARG_USUARIO ,nusuario);

        startActivityForResult(intent, Constantes.REQUEST_NUEVACITA); // declarar ... private int Req_Code=22;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == Constantes.REQUEST_NUEVACITA ){
            if( resultCode == RESULT_OK ){

                filtrarCitas();

            }else if(resultCode==RESULT_CANCELED){
                //Toast.makeText(this,"Tu Reserva de Cita se ha Cancelado",Toast.LENGTH_LONG).show();
            }

        }else if( requestCode == Constantes.REQUEST_DETALLECITA ) {
            if( resultCode == RESULT_OK ){
                DatosCita datosCita = data.getParcelableExtra(ARG_DATOS_CITA);
                int position = data.getIntExtra(ARG_POSITION, -1);
                if (position != -1) {
                    DatosCita old =  rvDatosCitasAdapter.getItem(position);
                    old.setEstado(datosCita.getEstado());
                    old.setFecha(datosCita.getFecha());
                    old.setHora(datosCita.getHora());
                    rvDatosCitasAdapter.notifyDataSetChanged();

                }
            }
        }else if (requestCode ==Constantes.REQUEST_ACTUALIZARPACIENTE){
           if(resultCode == RESULT_OK){
               Paciente paciente = data.getParcelableExtra("data");
               Idioma idioma= new Idioma();
               idioma.cambiaIdioma(paciente.getIdioma(), getBaseContext());
               Intent refresh = new Intent(this, MainActivity.class);
               startActivity(refresh);
               this.finish(); //

           }
        }


    }



    /**
     * Carga la pantalla de mi cuenta
     */
    private void cargarMiCuenta(){
        int usuario = this.getIntent().getIntExtra("user",0);
        Intent intent = new Intent(MainActivity.this, PacienteActivity.class);
        intent.putExtra("user",usuario);
        String nusuario=this.getIntent().getStringExtra( ARG_USUARIO );

        intent.putExtra(ARG_USUARIO ,nusuario);

        startActivityForResult(intent, Constantes.REQUEST_ACTUALIZARPACIENTE);

    }

    /**
     * Carga la pantalla de nuestros locales
     */
    private void cargarNuestrosLocales(){
        int usuario = this.getIntent().getIntExtra("user",0);
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("user",usuario);
        String nusuario=this.getIntent().getStringExtra( ARG_USUARIO );

        intent.putExtra(ARG_USUARIO ,nusuario);

        startActivityForResult(intent, Constantes.REQUEST_ACTUALIZARPACIENTE);


    }

    public final static String ARG_DATOS_CITA = "datosCita", ARG_POSITION = "position";

    @Override
    public void onSelectedItem(DatosCita datosCita, int position) {
        if (!datosCita.getEstado().equals("ANULADA")){
            Intent intent = new Intent(MainActivity.this, DetalleCitaActivity.class);
            intent.putExtra(ARG_DATOS_CITA, datosCita);
            intent.putExtra(ARG_POSITION, position);
            String nusuario=this.getIntent().getStringExtra( ARG_USUARIO );

            intent.putExtra(ARG_USUARIO ,nusuario);

            startActivityForResult(intent, Constantes.REQUEST_DETALLECITA);
        }
    }
}
