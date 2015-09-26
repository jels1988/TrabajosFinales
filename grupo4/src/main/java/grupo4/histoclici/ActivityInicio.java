package grupo4.histoclici;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import grupo4.histoclici.dao.DataBaseHelper;
import grupo4.histoclici.dao.MedicoDAO;
import grupo4.histoclici.entidad.Medico;

public class ActivityInicio extends AppCompatActivity {

    private EditText etUsuario, etClave;
    private Button bIngresa;
    private DataBaseHelper dataBaseHelper;

    public final String ARG_IDMEDICO = "ARG_IDMEDICO";
    public final String ARG_MEDICO = "ARG_MEDICO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        etUsuario = (EditText)findViewById(R.id.etUsuario);
        etClave = (EditText)findViewById(R.id.etClave);
        bIngresa = (Button)findViewById(R.id.bIngresa);
        bIngresa.setOnClickListener(bIngresaOnClickListener);

        try {
            dataBaseHelper = new DataBaseHelper(ActivityInicio.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    View.OnClickListener bIngresaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (etUsuario.getText().toString().trim().length() == 0) {
                etUsuario.setHint(R.string.error_usuario);
                return;
            }
            else if(etClave.getText().toString().trim().length() == 0) {
                etClave.setHint(R.string.error_clave);
                return;
            }

            Medico medico = null;
            medico = new MedicoDAO().ingresar(etUsuario.getText().toString().trim(), etClave.getText().toString().trim());

            if(medico != null){
                SharedPreferences.Editor spe = getSharedPreferences(getPackageName(), MODE_PRIVATE).edit();
                spe.putInt(ARG_IDMEDICO, medico.getIdMedico());
                spe.putString(ARG_MEDICO, medico.getMedico());
                spe.commit();
                Intent iIngresa = new Intent(ActivityInicio.this, ActivityListaCita.class);
                startActivity(iIngresa);
                finish();
            }
            else{
                Toast.makeText(ActivityInicio.this, R.string.no_ingresa, Toast.LENGTH_SHORT).show();
                etUsuario.getText().clear();
                etClave.getText().clear();
            }
        }
    };

}
