package grupo4.histoclici;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import grupo4.histoclici.adaptadores.recyclerview.ARVListaPaciente;
import grupo4.histoclici.entidad.Paciente;

public class ActivityListaPaciente extends AppCompatActivity implements ARVListaPaciente.ARVListaPacienteListener{
    static final String ARG_PACIENTE = "ARG_PACIENTE";
    static final int REQUEST_CODE_EDITAR_PACIENTE = 1;
    private EditText etPacienteFiltro;
    private RecyclerView rvPaciente;
    private ARVListaPaciente arvListaPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_paciente);
        etPacienteFiltro = (EditText)findViewById(R.id.etPacienteFiltro);
        rvPaciente = (RecyclerView)findViewById(R.id.rvPaciente);
        rvPaciente.setHasFixedSize(true);
        rvPaciente.setLayoutManager(new LinearLayoutManager(ActivityListaPaciente.this));
        arvListaPaciente = new ARVListaPaciente(ActivityListaPaciente.this);
        rvPaciente.setAdapter(arvListaPaciente);
        etPacienteFiltro.addTextChangedListener(filtra);
    }

    TextWatcher filtra = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            arvListaPaciente.getFilter().filter(etPacienteFiltro.getText().toString().trim());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void ieditarPaciente(Paciente paciente) {

        Intent intent = new Intent(ActivityListaPaciente.this, ActivityPaciente.class);
        intent.putExtra(ARG_PACIENTE, paciente);
        startActivityForResult(intent, REQUEST_CODE_EDITAR_PACIENTE);

    }

    @Override
    public void imostrarmenu(Paciente paciente) {
        //Toast.makeText(ActivityListaPaciente.this, "LONGGGGGGGGGGGGG", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if ( item.getItemId() == R.id.s_Editar ){
            Intent intent = new Intent(ActivityListaPaciente.this, ActivityPaciente.class);
            intent.putExtra("MODO ACTU",2);
            startActivityForResult(intent, REQUEST_CODE_EDITAR_PACIENTE);
        }
        if ( item.getItemId() == R.id.s_Ver ){
            Intent intent = new Intent(ActivityListaPaciente.this, ActivityPaciente.class);
            intent.putExtra("MODO VER",1);
            startActivityForResult(intent, REQUEST_CODE_EDITAR_PACIENTE);
        }
        if ( item.getItemId() == R.id.s_Cancelar ){

        }


        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EDITAR_PACIENTE && resultCode == RESULT_OK){
            arvListaPaciente.listarPaciente();
            Toast.makeText(ActivityListaPaciente.this, R.string.ok_actualizar_paciente, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityListaPaciente.this, R.string.calcel_actualizar_paciente, Toast.LENGTH_SHORT).show();
        }
        etPacienteFiltro.setText("");
    }
}
