package pe.lindley.tomapedidos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import pe.lindley.tomapedidos.dao.ClienteDAO;
import pe.lindley.tomapedidos.dao.EstadisticaDAO;
import pe.lindley.tomapedidos.dao.PedidoDAO;
import pe.lindley.tomapedidos.dao.ProductoDAO;
import pe.lindley.tomapedidos.entities.Estadistica;
import pe.lindley.tomapedidos.entities.Producto;
import pe.lindley.tomapedidos.interfaces.Logeable;

public class MainActivity extends AppCompatActivity implements Logeable {

    private DrawerLayout main_drawerlayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Button main_btiniciodia;
    private Button main_btpreventa;
    private Button main_btestadistica;
    private Button main_btcierredia;
    private Button main_btcerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        /* encontrar cada componente */
        main_btiniciodia = (Button) findViewById(R.id.main_btiniciodia);
        main_btpreventa = (Button) findViewById(R.id.main_btpreventa);
        main_btestadistica = (Button) findViewById(R.id.main_btestadistica);
        main_btcierredia = (Button) findViewById(R.id.main_btcierredia);
        main_btcerrarsesion = (Button) findViewById(R.id.main_btcerrarsesion);

        /* Para el drawerlayout */
        main_drawerlayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, main_drawerlayout,
                R.string.app_name, R.string.app_name) {
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
        main_drawerlayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /* acciones */
        main_btiniciodia.setOnClickListener(main_btiniciodiaOnClickListener);
        main_btpreventa.setOnClickListener(main_btpreventaOnClickListener);
        main_btestadistica.setOnClickListener(main_btestadisticaOnClickListener);
        main_btcierredia.setOnClickListener(main_btcierrediaOnClickListener);
        main_btcerrarsesion.setOnClickListener(main_btcerrarsesionOnClickListener);
    }

    private View.OnClickListener main_btiniciodiaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Estadistica estadistica = EstadisticaDAO.getInstance().getEstadistica();
            if( estadistica.getTotalCliente() == 0 ) {
                ClienteDAO.getInstance().poblarBaseDatos();
                ProductoDAO.getInstance().poblarBaseDatos();
                new AlertDialog.Builder(MainActivity.this).
                        setTitle("Inicio del día").
                        setMessage("Inicio concluído").
                        setNeutralButton("Aceptar", null).setCancelable(false).show();
            } else {
                new AlertDialog.Builder(MainActivity.this).
                        setTitle("Inicio del día").
                        setMessage("Debe de haber cerrado el día").
                        setNeutralButton("Aceptar", null).setCancelable(false).show();
            }
        }
    };

    private View.OnClickListener main_btpreventaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
            startActivity(intent);
            main_drawerlayout.closeDrawer(GravityCompat.START);
        }
    };

    private View.OnClickListener main_btestadisticaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, EstadisticaActivity.class);
            startActivity(intent);
            main_drawerlayout.closeDrawer(GravityCompat.START);
        }
    };

    private View.OnClickListener main_btcierrediaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Estadistica estadistica = EstadisticaDAO.getInstance().getEstadistica();
            if( estadistica.getTotalCliente() > 0 ) {
                ClienteDAO.getInstance().deleteAll();
                ProductoDAO.getInstance().deleteAll();
                PedidoDAO.getInstance().deleteAll();
                new AlertDialog.Builder(MainActivity.this).
                        setTitle("Cierre del día").
                        setMessage("Cierre concluído").
                        setNeutralButton("Aceptar", null).setCancelable(false).show();

            } else {
                new AlertDialog.Builder(MainActivity.this).
                        setTitle("Cierre del día").
                        setMessage("Debe de haber iniciado el día").
                        setNeutralButton("Aceptar", null).setCancelable(false).show();
            }
        }
    };

    private View.OnClickListener main_btcerrarsesionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs =
                    getSharedPreferences(ARG_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor spe = prefs.edit();
            spe.clear();
            spe.commit();

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
