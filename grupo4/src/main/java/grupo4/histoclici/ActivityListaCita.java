package grupo4.histoclici;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import grupo4.histoclici.adaptadores.recyclerview.ARVListaCita;
import grupo4.histoclici.dao.DataBaseHelper;
import grupo4.histoclici.entidad.Cita;

public class ActivityListaCita extends AppCompatActivity implements ARVListaCita.ARVListaCitaListener {

    private DrawerLayout dlMenu;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tvNuevoPaciente, tvListarPacientes, tvNuevaCita, tvCerrar, tvDoctor;
    private ImageView ivDoctor;
    private DatePicker dpFechaCita;
    private RecyclerView rvCita;

    private ARVListaCita arvListaCita;

    private DataBaseHelper dataBaseHelper;

    public final static String ARG_FECHA = "ARG_FECHA", ARG_IDPACIENTE = "ARG_IDPACIENTE", ARG_PACIENTE = "ARG_PACIENTE";
    public final static int REQUEST_CODE_INSERTAR_CITA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lista_cita);

        tvDoctor = (TextView) findViewById(R.id.tvDoctor);
        ivDoctor = (ImageView)findViewById(R.id.ivDoctor);
        tvNuevoPaciente = (TextView) findViewById(R.id.tvNuevoPaciente);
        tvListarPacientes = (TextView) findViewById(R.id.tvListarPacientes);
        tvNuevaCita = (TextView) findViewById(R.id.tvNuevaCita);
        tvCerrar = (TextView) findViewById(R.id.tvCerrar);

        tvNuevoPaciente.setOnClickListener(tvOnClickListener);
        tvListarPacientes.setOnClickListener(tvOnClickListener);
        tvNuevaCita.setOnClickListener(tvOnClickListener);
        tvCerrar.setOnClickListener(tvOnClickListener);

        dlMenu = (DrawerLayout) findViewById(R.id.dlMenu);

        SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        tvDoctor.setText("Dr. " + sp.getString("ARG_MEDICO", ""));
        switch(sp.getInt("ARG_IDMEDICO", 0)){
            case 1: ivDoctor.setImageResource(R.drawable.asalazar);
                break;
            case 2: ivDoctor.setImageResource(R.drawable.mdipas);
                break;
            case 3: ivDoctor.setImageResource(R.drawable.pgarcia);
                break;
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(ActivityListaCita.this, dlMenu, R.string.inicio, R.string.inicio) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        dlMenu.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rvCita = (RecyclerView) findViewById(R.id.rvCita);
        rvCita.setHasFixedSize(true);
        rvCita.setLayoutManager(new LinearLayoutManager(ActivityListaCita.this));

        Calendar fecha = Calendar.getInstance();
        dpFechaCita = (DatePicker) findViewById(R.id.dpFechaCita);
        dpFechaCita.init(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DATE), seleccionarFecha);

        String fechaOnCreate = String.format("%02d/%02d/%s", fecha.get(Calendar.DATE), (fecha.get(Calendar.MONTH) + 1), fecha.get(Calendar.YEAR));
        arvListaCita = new ARVListaCita(ActivityListaCita.this, fechaOnCreate);
        rvCita.setAdapter(arvListaCita);
    }

    DatePicker.OnDateChangedListener seleccionarFecha = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String fechaOnDataChangeLister = String.format("%02d/%02d/%d", dayOfMonth, (monthOfYear + 1), year);
            arvListaCita.listarCitasXFecha(fechaOnDataChangeLister);
        }
    };

    View.OnClickListener tvOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String actividad = ((TextView) v).getText().toString();

            if (actividad.equals("Nueva Cita")) {
                Intent i = new Intent(ActivityListaCita.this, ActivityCita.class);
                startActivityForResult(i, REQUEST_CODE_INSERTAR_CITA);
                dlMenu.closeDrawer(GravityCompat.START);
            } else if (actividad.equals("Cerrar Sesión")) {
                Toast.makeText(ActivityListaCita.this, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(actividad.equals("Listar Pacientes")){
                Intent i = new Intent(ActivityListaCita.this, ActivityListaPaciente.class);
                dlMenu.closeDrawer(GravityCompat.START);
                startActivity(i);
            }
            else if(actividad.equals("Nuevo Paciente")){
                Intent i = new Intent(ActivityListaCita.this, ActivityPaciente.class);
                dlMenu.closeDrawer(GravityCompat.START);
                startActivity(i);
            }
        }
    };

    @Override
    public void ilistarAtencionesXPaciente(int IdPaciente, String Paciente) {
        //Manuel le pones el nombre de la actividad que vas a crear para Historia Clínica: sugiero ActivityHistoriaClinica
        Intent intent = new Intent(ActivityListaCita.this, ActivityHistoriaClinica.class);
        intent.putExtra(ARG_IDPACIENTE, IdPaciente);
        intent.putExtra(ARG_PACIENTE, Paciente);
        startActivity(intent);
    }

    @Override
    public void ieliminarCita(final Cita cita, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListaCita.this);
        builder.setCancelable(false);
        builder.setTitle("Eliminar");
        builder.setIcon(R.drawable.eliminar);
        builder.setMessage(String.format("¿Eliminar la cita de %s el día %s de %s a %s", cita.getPaciente(), cita.getFechaCita(), cita.getInicio(), cita.getFin()));
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arvListaCita.eliminarCita(cita.getIdCita(), position, cita.getFechaCita());
                Toast.makeText(ActivityListaCita.this, R.string.ok_eliminar_cita, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_INSERTAR_CITA && resultCode == RESULT_OK) {
            if (String.format("%02d/%02d/%d", dpFechaCita.getDayOfMonth(), (dpFechaCita.getMonth() + 1), dpFechaCita.getYear()).equals(data.getStringExtra(ARG_FECHA)))
                arvListaCita.listarCitasXFecha(data.getStringExtra(ARG_FECHA));
            Toast.makeText(ActivityListaCita.this, R.string.ok_insertar_cita, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
