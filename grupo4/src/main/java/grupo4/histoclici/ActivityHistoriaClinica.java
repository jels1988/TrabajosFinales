package grupo4.histoclici;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import grupo4.histoclici.adaptadores.recyclerview.ARVHistoriaClinica;

public class ActivityHistoriaClinica extends AppCompatActivity {

    private ARVHistoriaClinica arvHistoriaClinica;

    public final static String ARG_IDPACIENTE = "ARG_IDPACIENTE";
    public final static String ARG_PACIENTE = "ARG_PACIENTE";
    private final static int REQUEST_CODE = 1;

    TextView tvPacienteHC, tvIdPacienteHC;
    RecyclerView rvHistoriaClinica;
    private int m_idPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historia_clinica);

        tvPacienteHC = (TextView)findViewById(R.id.tvPacienteHC);
        tvIdPacienteHC = (TextView)findViewById(R.id.tvIdPacienteHC);
        rvHistoriaClinica = (RecyclerView)findViewById(R.id.rvHistoriaClinica);

        tvIdPacienteHC.setText(String.valueOf(getIntent().getIntExtra(ActivityListaCita.ARG_IDPACIENTE, 0)));
        tvPacienteHC.setText(getIntent().getStringExtra(ActivityListaCita.ARG_PACIENTE));

        rvHistoriaClinica.setHasFixedSize(true);
        rvHistoriaClinica.setLayoutManager(new LinearLayoutManager(ActivityHistoriaClinica.this));

        arvHistoriaClinica = new ARVHistoriaClinica(getIntent().getIntExtra(ActivityListaCita.ARG_IDPACIENTE, 0));
        rvHistoriaClinica.setAdapter(arvHistoriaClinica);

        m_idPaciente = getIntent().getIntExtra( ActivityListaCita.ARG_IDPACIENTE, 0 );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_historia_clinica, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (  requestCode == REQUEST_CODE && resultCode == RESULT_OK ){
            arvHistoriaClinica = new ARVHistoriaClinica(getIntent().getIntExtra(ActivityListaCita.ARG_IDPACIENTE, 0));
            rvHistoriaClinica.setAdapter(arvHistoriaClinica);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.a_NuevaAtencion) {
            Intent intent = new Intent( ActivityHistoriaClinica.this, ActivityAtencion.class  );
            intent.putExtra( ARG_IDPACIENTE, m_idPaciente );
            intent.putExtra( ARG_PACIENTE, getIntent().getStringExtra(ActivityListaCita.ARG_PACIENTE) );
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
