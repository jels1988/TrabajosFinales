package com.cibertec.app.ferconsapedidos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MenuPrincipalActivity extends AppCompatActivity {

    private Button btRegistraPedido;
    private Button btPedidos;
    private Button  btCliente;
    private Button btSynchronize;
    private DrawerLayout dlmenu;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public  static Integer ARG_OPCION = 0 ;
    public final static Integer ARG_OPCION_CLIENTES = 4;
    public final static Integer  ARG_OPCION_PEDIDOS = 2;
    public final static Integer  ARG_OPCION_NUEVOPEDIDO = 1;
    private SharedPreferences sp;
    private TextView tvNick;
    public final static String ARG_USER = "arg_user";
    private final String ARG_PASS = "arg_pass";
    public  static String ARG_USERID = "arg_ide";
    public  static String USERID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        btRegistraPedido = (Button)findViewById(R.id.btRegistraPedido);
        btRegistraPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIncome = new Intent(MenuPrincipalActivity.this,ClientesActivity.class);
                ARG_OPCION = ARG_OPCION_NUEVOPEDIDO;

                MenuPrincipalActivity.this.startActivity(newIncome);
                dlmenu.closeDrawer(Gravity.LEFT);
            }
        });
        btPedidos = (Button)findViewById(R.id.btPedidos);
        btPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPedido = new Intent( MenuPrincipalActivity.this, PedidoCabeceraActivity.class);
                ARG_OPCION = ARG_OPCION_PEDIDOS;

                MenuPrincipalActivity.this.startActivity(intentPedido);
                dlmenu.closeDrawer(Gravity.LEFT);
            }
        });

        btCliente = (Button)findViewById(R.id.btCliente);
        btCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIncome = new Intent(MenuPrincipalActivity.this,ClientesActivity.class);
                ARG_OPCION = ARG_OPCION_CLIENTES;
                MenuPrincipalActivity.this.startActivity(newIncome);
                dlmenu.closeDrawer(Gravity.LEFT);
            }
        });

        btSynchronize = (Button)findViewById(R.id.btSynchronize);
        btSynchronize .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSincroniza = new Intent(MenuPrincipalActivity.this, SincronizarActivity.class);
                MenuPrincipalActivity.this.startActivity(intentSincroniza);
                dlmenu.closeDrawer(Gravity.LEFT);
            }
        });

        dlmenu = (DrawerLayout) findViewById(R.id.dlMenu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MenuPrincipalActivity.this, dlmenu, R.string.app_name, R.string.app_name) {
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

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);


        tvNick = (TextView) findViewById(R.id.tvNick);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MenuPrincipalActivity.ARG_USER);
        USERID = intent.getStringExtra(MenuPrincipalActivity.ARG_USERID);

        tvNick.setText(username);
    }

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

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.idCerrarSesion:

                SharedPreferences.Editor spe = sp.edit();
                String UserV, PassV ;

                spe.putString(ARG_USER, "");
                spe.putString(ARG_PASS, "");
                spe.commit();

                Intent intent = new Intent(MenuPrincipalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

