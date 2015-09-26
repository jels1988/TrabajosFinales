package com.example.angelica.apppizzahut.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.angelica.apppizzahut.Dao.DataBaseHelper;
import com.example.angelica.apppizzahut.Dao.UsuarioDao;
import com.example.angelica.apppizzahut.R;

public class InicioActivity extends AppCompatActivity {

    private Button btnEntrar;
    private TextInputLayout tilEmail, tilPassword;
    private EditText etEmail, etPassword;
    UsuarioDao usuarioDao = new UsuarioDao();
    private DataBaseHelper dataBaseHelper;
    private SharedPreferences sp;
    public static final String ARG_USER = "arg_user";
    public static final String ARG_PASS = "arg_pass";
    public static final String ARG_INSUPD = "arg_insupd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sp.contains(ARG_USER) && sp.contains(ARG_PASS) && !sp.getString(ARG_USER, "").isEmpty() && !sp.getString(ARG_PASS, "").isEmpty()) {
            Intent intent = new Intent(InicioActivity.this, PrincipalActivity.class);
            intent.putExtra(ARG_INSUPD,"upd");
            startActivity(intent);
            finish();
        }

        LinearLayout layoutInicio = (LinearLayout) findViewById(R.id.layoutInicio);
        layoutInicio.setBackgroundColor(Color.WHITE);

        etEmail = (EditText) findViewById(R.id.etEmail);

        etPassword = (EditText) findViewById(R.id.etPassword);


        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(btnEntrarOnClickListener);

        try {
            dataBaseHelper = new DataBaseHelper(InicioActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    View.OnClickListener btnEntrarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {


            boolean isComplete = true;

            tilEmail.setErrorEnabled(false);
            tilPassword.setErrorEnabled(false);

            int validaUsuario;
            int validaPassword;

            if (etEmail.getText().toString().trim().length() <= 0) {
                tilEmail.setErrorEnabled(true);
                tilEmail.setError("Ingrese su Usuario");
                tilEmail.requestFocus();
                isComplete = false;
            } else {
                validaUsuario = usuarioDao.validarUsuario(etEmail.getText().toString().trim());
                if (validaUsuario > 0) {
                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError("El Usuario no existe");
                    tilEmail.requestFocus();
                    isComplete = false;
                } else {
                    if (etPassword.getText().toString().trim().length() <= 0) {
                        tilPassword.setErrorEnabled(true);
                        tilPassword.setError("Ingrese su Password");
                        tilPassword.requestFocus();
                        isComplete = false;
                    } else {
                        validaPassword = usuarioDao.validarPassword(etEmail.getText().toString().trim(),
                                etPassword.getText().toString().trim());
                        if (validaPassword > 0) {
                            tilPassword.setErrorEnabled(true);
                            tilPassword.setError("Password incorrecto");
                            tilPassword.requestFocus();
                            isComplete = false;
                        }
                    }
                }
            }

            if (isComplete) {
                SharedPreferences.Editor spe = sp.edit();
                spe.putString(ARG_USER, etEmail.getText().toString().trim());
                spe.putString(ARG_PASS, etPassword.getText().toString().trim());

                spe.commit();

                Intent intent = new Intent(InicioActivity.this, PrincipalActivity.class);
                startActivity(intent);
                finish();
            }

        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.action_crear_cuenta) {
            Intent intent = new Intent(InicioActivity.this, CrearCuentaActivity.class);
            intent.putExtra(InicioActivity.ARG_INSUPD, "ins");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
