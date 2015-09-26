package com.example.angelica.apppizzahut.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelica.apppizzahut.Dao.LocalesDao;
import com.example.angelica.apppizzahut.Dao.UsuarioDao;
import com.example.angelica.apppizzahut.Entity.Pedido;
import com.example.angelica.apppizzahut.Entity.Usuario;
import com.example.angelica.apppizzahut.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/09/2015.
 */
public class PrincipalActivity extends AppCompatActivity {

    private DrawerLayout dlmenu;
    private TextInputLayout tilDireccionP, tilReferenciaP, tilTelefonoP;
    private Button btnIniciaPedido;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tvNoticias, tvLocales;
    private UsuarioDao usuarioDao = new UsuarioDao();
    SharedPreferences sp;
    Usuario usuarios;
    Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tilDireccionP = (TextInputLayout) findViewById(R.id.tilDireccionP);
        tilReferenciaP = (TextInputLayout) findViewById(R.id.tilReferenciaP);
        tilTelefonoP = (TextInputLayout) findViewById(R.id.tilTelefonoP);

        btnIniciaPedido = (Button) findViewById(R.id.btnIniciaPedido);

        btnIniciaPedido.setOnClickListener(btnIniciaPedidoOnClickListener);

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sp.contains(InicioActivity.ARG_USER) && sp.contains(InicioActivity.ARG_PASS) && !sp.getString(InicioActivity.ARG_USER, "").isEmpty() && !sp.getString(InicioActivity.ARG_PASS, "").isEmpty()) {
            usuarios = usuarioDao.listarUsuario(sp.getString(InicioActivity.ARG_USER, "").toString());
            tilDireccionP.requestFocus();

            Toast.makeText(getApplicationContext(), "Bienvenido(a) " + sp.getString(InicioActivity.ARG_USER, ""), Toast.LENGTH_SHORT).show();
        } else {
            getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().clear().commit();
            Intent intent = new Intent(PrincipalActivity.this, InicioActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Problemas con las preferencias " + sp.getString(InicioActivity.ARG_USER, ""), Toast.LENGTH_SHORT).show();
        }

        tvNoticias = (TextView) findViewById(R.id.tvNoticias);
        tvLocales = (TextView) findViewById(R.id.tvLocales);

        tvNoticias.setOnClickListener(tvOnClickListener);
        tvLocales.setOnClickListener(tvOnClickListener);


        dlmenu = (DrawerLayout) findViewById(R.id.dlMenu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(PrincipalActivity.this, dlmenu, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        dlmenu.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    ;

    View.OnClickListener btnIniciaPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isCorrect = true;
            pedido = new Pedido();

            tilDireccionP.setErrorEnabled(false);
            tilReferenciaP.setErrorEnabled(false);
            tilTelefonoP.setErrorEnabled(false);

            if (tilDireccionP.getEditText().getText().toString().trim().length() <= 0) {
                tilDireccionP.setError("Ingrese una dirección");
                tilDireccionP.setErrorEnabled(true);
                isCorrect = false;
            } else
                pedido.setDireccion(tilDireccionP.getEditText().getText().toString().trim());

            if (tilReferenciaP.getEditText().getText().toString().trim().length() <= 0) {
                tilReferenciaP.setError("Ingrese una referencia");
                tilReferenciaP.setErrorEnabled(true);
                isCorrect = false;
            } else
                pedido.setReferencia(tilReferenciaP.getEditText().getText().toString().trim());

            if (tilTelefonoP.getEditText().getText().toString().trim().length() <= 0) {
                tilTelefonoP.setError("Ingrese un teléfono");
                tilTelefonoP.setErrorEnabled(true);
                isCorrect = false;
            } else
                pedido.setTelefono(tilTelefonoP.getEditText().getText().toString().trim());


            if (isCorrect) {
                tilDireccionP.getEditText().setText("");
                tilDireccionP.getEditText().setHint("Direccion");

                tilReferenciaP.getEditText().setText("");
                tilReferenciaP.getEditText().setHint("Referencia");

                tilTelefonoP.getEditText().setText("");
                tilTelefonoP.getEditText().setHint("Teléfono");

                new ProgressAsyncTask().execute();
            }
        }
    };

    class ProgressAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent intent = new Intent(PrincipalActivity.this, ProductoActivity.class);
            intent.putExtra("arg_pedido", pedido);
            startActivity(intent);

            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PrincipalActivity.this);
            progressDialog.setMessage("Obteniendo local más cercano");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    View.OnClickListener tvOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //Noticias
            if (view.getId() == R.id.tvNoticias) {

                Intent intent = new Intent(PrincipalActivity.this, NoticiasActivity.class);
                startActivity(intent);


            }

            //Locales
            if (view.getId() == R.id.tvLocales) {

                Intent intent = new Intent(PrincipalActivity.this, LocalActivity.class);
                startActivity(intent);


            }

            dlmenu.closeDrawer(GravityCompat.START);
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case R.id.action_editar_cuenta:
                Intent intent1 = new Intent(PrincipalActivity.this, CrearCuentaActivity.class);
                intent1.putExtra(InicioActivity.ARG_INSUPD, "upd");
                startActivity(intent1);
                return true;

            case R.id.action_cerrar_sesion:
                getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().clear().commit();
                Intent intent2 = new Intent(PrincipalActivity.this, InicioActivity.class);
                startActivity(intent2);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
