package com.example.angelica.apppizzahut.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.angelica.apppizzahut.Dao.DataBaseHelper;
import com.example.angelica.apppizzahut.Dao.UsuarioDao;
import com.example.angelica.apppizzahut.Entity.Usuario;
import com.example.angelica.apppizzahut.R;

/**
 * Created by Angelica on 08/09/2015.
 */
public class CrearCuentaActivity extends AppCompatActivity {

    UsuarioDao usuarioDao = new UsuarioDao();
    private TextInputLayout tilNombres, tilDNI, tilUsuario, tilPassword;
    private EditText etNombres, etDNI, etEmail;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        tilNombres = (TextInputLayout) findViewById(R.id.tilNombres);
        tilDNI = (TextInputLayout) findViewById(R.id.tilDNI);
        tilUsuario = (TextInputLayout) findViewById(R.id.tilUsuario);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        etNombres = (EditText) findViewById(R.id.etNombres);
        etDNI = (EditText) findViewById(R.id.etDNI);
        etEmail = (EditText) findViewById(R.id.etEmail);

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        String flag = getIntent().getStringExtra(InicioActivity.ARG_INSUPD);
        if (flag.equals("upd")) {
            //tilNombres.setHint("Nombres");
            //tilDNI.setHint("DNI");
            String usuario = sp.getString(InicioActivity.ARG_USER, "");
            Usuario usuario1 = usuarioDao.listarUsuario(usuario);
            etNombres.setText(usuario1.getNombre().toString());
            etDNI.setText(usuario1.getDni().toString());
            etEmail.setText(usuario1.getUsuario().toString());
            //tilNombres.addView(etNombres);
            //tilDNI.addView(etDNI);
            etNombres.setFocusable(false);
            etDNI.setFocusable(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear_cuenta, menu);
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
            boolean isCorrect = true;
            Usuario usuario = new Usuario();

            tilNombres.setErrorEnabled(false);
            tilDNI.setErrorEnabled(false);
            tilUsuario.setErrorEnabled(false);
            tilPassword.setErrorEnabled(false);

            if (tilNombres.getEditText().getText().toString().trim().length() <= 0) {
                tilNombres.setError("Ingrese un nombre");
                tilNombres.setErrorEnabled(true);
                isCorrect = false;
            } else
                usuario.setNombre(tilNombres.getEditText().getText().toString().trim());

            if (tilDNI.getEditText().getText().toString().trim().length() != 8) {
                tilDNI.setError("Ingrese un DNI correcto");
                tilDNI.setErrorEnabled(true);
                isCorrect = false;
            } else
                usuario.setDni(tilDNI.getEditText().getText().toString().trim());

            if (tilUsuario.getEditText().getText().toString().trim().length() <= 0) {
                tilUsuario.setError("Ingrese un usuario");
                tilUsuario.setErrorEnabled(true);
                isCorrect = false;
            } else
                usuario.setUsuario(tilUsuario.getEditText().getText().toString().trim());

            if (tilPassword.getEditText().getText().toString().trim().length() < 6) {
                tilPassword.setError("Mínimo 6 caracteres");
                tilPassword.setErrorEnabled(true);
                isCorrect = false;
            } else
                usuario.setPassword(tilPassword.getEditText().getText().toString().trim());

            if (isCorrect) {
                int i = usuarioDao.validarUsuario(usuario.getUsuario().toString());
                if (i == 0) {
                    Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    String flag = getIntent().getStringExtra(InicioActivity.ARG_INSUPD);

                    if (flag.equals("upd")) {
                        String usuarioStr = sp.getString(InicioActivity.ARG_USER, "");
                        usuarioDao.updUsuario(usuario, usuarioStr);
                        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().clear().commit();

                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(InicioActivity.ARG_USER, tilUsuario.getEditText().getText().toString().trim());
                        spe.putString(InicioActivity.ARG_PASS, tilPassword.getEditText().getText().toString().trim());

                        spe.commit();
                    } else {
                        usuarioDao.addUsuario(usuario);
                    }

                    finish();

                    if (flag.equals("upd")) {
                        Toast.makeText(getApplicationContext(), "Usuario Modificado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }

        return super.onOptionsItemSelected(item);
    }


}
