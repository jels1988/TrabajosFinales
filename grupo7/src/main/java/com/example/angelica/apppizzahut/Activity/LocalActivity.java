package com.example.angelica.apppizzahut.Activity;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import com.example.angelica.apppizzahut.Adapter.RVAdapterLocales;
import com.example.angelica.apppizzahut.Adapter.spinner.SPDistritoAdapter;
import com.example.angelica.apppizzahut.Adapter.spinner.SPProvinciaAdapter;
import com.example.angelica.apppizzahut.Dao.LocalesDao;
import com.example.angelica.apppizzahut.Dao.ProvinciasDao;
import com.example.angelica.apppizzahut.Entity.Local;
import com.example.angelica.apppizzahut.Entity.Provincia;
import com.example.angelica.apppizzahut.R;

import java.util.ArrayList;

/**
 * Created by Angelica on 13/09/2015.
 */
public class LocalActivity extends AppCompatActivity implements RVAdapterLocales.RVLocalesAdapterCallBack{

    private DrawerLayout dlmenu;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView rvLocales;
    private RVAdapterLocales AdapterLocales;
    private RecyclerView.LayoutManager LMLocales;
    private Spinner spProvinciaFiltro;
    private SPProvinciaAdapter spProvinciaAdapter;
    private Spinner spDistritoFiltro;
    private SPDistritoAdapter spDistritoAdapter;
    private TextView tvNoticias;
    private TextView tvPrincipal;

    private ArrayList<Provincia> lstProvincia;
    private ArrayList<Provincia> lstDistrito;

    private ProvinciasDao provinciasDao;
    private LocalesDao localesDao;

    ArrayList<Local> locals;

    public static String ARG_LOCAL = "local";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locales);

        rvLocales = (RecyclerView) findViewById(R.id.rvLocales);
        spProvinciaFiltro = (Spinner)findViewById(R.id.spProvinciaFiltro);
        spDistritoFiltro = (Spinner)findViewById(R.id.spDistritoFiltro);
        tvNoticias = (TextView)findViewById(R.id.tvNoticias);
        dlmenu = (DrawerLayout)findViewById(R.id.dlMenuLocal);
        tvPrincipal = (TextView)findViewById(R.id.tvPrincipal);
        tvNoticias.setOnClickListener(tvOnClickListener);
        tvPrincipal.setOnClickListener(tvOnClickListener);


        provinciasDao = new ProvinciasDao();
        localesDao = new LocalesDao();

        lstProvincia = provinciasDao.listProvinciasSpn();
        spProvinciaAdapter = new SPProvinciaAdapter(LocalActivity.this,lstProvincia);
        spProvinciaFiltro.setOnItemSelectedListener(spProvinciaFiltroOnItemSelectedListener);
        spProvinciaFiltro.setAdapter(spProvinciaAdapter);

        spDistritoAdapter = new SPDistritoAdapter(LocalActivity.this,new ArrayList<Provincia>());
        spDistritoFiltro.setOnItemSelectedListener(spDistritoOnItemSelectedListener);
        spDistritoFiltro.setAdapter(spDistritoAdapter);

        rvLocales.setHasFixedSize(true);
        LMLocales = new LinearLayoutManager(this);
        rvLocales.setLayoutManager(LMLocales);
        AdapterLocales = new RVAdapterLocales(LocalActivity.this);
        rvLocales.setAdapter(AdapterLocales);


        actionBarDrawerToggle = new ActionBarDrawerToggle(LocalActivity.this, dlmenu, R.string.app_name, R.string.app_name) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener tvOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //Noticias
            if (view.getId() == R.id.tvNoticias) {
                Intent intent = new Intent(LocalActivity.this, NoticiasActivity.class);
                startActivity(intent);
            }

            //Delivery
            if (view.getId() == R.id.tvPrincipal) {
                Intent intent = new Intent(LocalActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
            dlmenu.closeDrawer(GravityCompat.START);
        }
    };

    AdapterView.OnItemSelectedListener  spProvinciaFiltroOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Provincia provincia = lstProvincia.get(position);
            spDistritoAdapter.clear();
            lstDistrito = provinciasDao.listDistritosSpn(provincia);
            spDistritoAdapter.addAll(lstDistrito);
            spDistritoAdapter.notifyDataSetChanged();
            spDistritoFiltro.getOnItemSelectedListener().onItemSelected(parent, view ,0, id);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    AdapterView.OnItemSelectedListener  spDistritoOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Provincia distrito = lstDistrito.get(position);
            locals = localesDao.listLocales(distrito.getIdprovincia());
            AdapterLocales.setNewSource(locals);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public void onLocalClick(Local local, int position) {
        Intent intent = new Intent(LocalActivity.this, LocalMapaActivity.class);
        intent.putExtra(ARG_LOCAL, local);
        startActivity(intent);
    }
}
