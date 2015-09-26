package ventas.jandysac.com.ventas;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ventas.jandysac.com.ventas.adapter.recyclerview.RVClienteAdapter;
import ventas.jandysac.com.ventas.dao.DataBaseHelper;
import ventas.jandysac.com.ventas.entities.Cliente;

public class BusquedaCliente extends AppCompatActivity implements RVClienteAdapter.RVClienteAdapterCallBack {

    public final static String ARG_CLIENTE = "arg_cliente";

    /* DrawerLayout Section */
    private DrawerLayout dlmenu;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView tvOpcionUsuario, tvOpcion1, tvOpcion2, tvOpcion3, tvOpcion4, tvOpcionAyuda, tvOpcionCerrarSes;
    /* End DrawerLayout Section */

    private EditText txtBusqueda;
    private RecyclerView rvCliente;
    private RVClienteAdapter rvClienteAdapter;
    private DataBaseHelper dataBaseHelper;
    private final static int REQUEST_CODE = 1;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cliente);

        /* DrawerLayout Section */
        tvOpcionUsuario = (TextView) findViewById(R.id.tvOpcionUsuario);
        tvOpcion1 = (TextView) findViewById(R.id.tvOpcion1);
        tvOpcion2 = (TextView) findViewById(R.id.tvOpcion2);
        tvOpcion3 = (TextView) findViewById(R.id.tvOpcion3);
        tvOpcion4 = (TextView) findViewById(R.id.tvOpcion4);

        tvOpcionAyuda = (TextView) findViewById(R.id.tvOpcionAyuda);
        tvOpcionCerrarSes = (TextView) findViewById(R.id.tvOpcionCerrarSes);

        tvOpcion1.setOnClickListener(tvOnClickListener);
        tvOpcion2.setOnClickListener(tvOnClickListener);
        tvOpcion3.setOnClickListener(tvOnClickListener);
        tvOpcion4.setOnClickListener(tvOnClickListener);
        tvOpcionAyuda.setOnClickListener(tvOnClickListener);
        tvOpcionCerrarSes.setOnClickListener(tvOnClickListener);

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sp.contains(MainActivity.ARG_NOMAPE) &&
                !sp.getString(MainActivity.ARG_NOMAPE, "").isEmpty()) {
            tvOpcionUsuario.setText(sp.getString(MainActivity.ARG_NOMAPE, ""));
        } else {
            tvOpcionUsuario.setText("Usuario");
        }


        dlmenu = (DrawerLayout) findViewById(R.id.dlMenu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(BusquedaCliente.this, dlmenu, R.string.app_name, R.string.app_name) {
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

        /* End DrawerLayout Section */

        //getSupportActionBar().setIcon(R.drawable.ic_lock_black_24dp);
        txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
        rvCliente = (RecyclerView) findViewById(R.id.rvCliente);

        try {
            dataBaseHelper = new DataBaseHelper(BusquedaCliente.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        rvClienteAdapter = new RVClienteAdapter(BusquedaCliente.this);
        rvCliente.setHasFixedSize(true);
        rvCliente.setLayoutManager(new LinearLayoutManager(BusquedaCliente.this));
        rvCliente.setAdapter(rvClienteAdapter);
        getSupportActionBar().setTitle("Clientes (" + String.valueOf(rvClienteAdapter.getItemCount()) + ")");
        txtBusqueda.addTextChangedListener(txtBusquedaTextWatcher);
    }

    /* DrawerLayout Section */
    View.OnClickListener tvOnClickListener = new View.OnClickListener() {

        @Override

        public void onClick(View view) {

            String opcActual = ((TextView) view).getText().toString();

            if(opcActual.equals(getResources().getString(R.string.drawer_item_agregar_Pedido))){
                    Intent intent = new Intent(BusquedaCliente.this, MainActivity.class);
                    startActivity(intent);
            }else if(opcActual.equals(getResources().getString(R.string.drawer_item_consolidar_pedido))){
                Intent intent = new Intent(BusquedaCliente.this, ConsolidarPedidoActivity.class);
                startActivity(intent);
            } else if (opcActual.equals(getResources().getString(R.string.drawer_item_cerrar_session))) {
                getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().clear().commit();

                Intent intent = new Intent(BusquedaCliente.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            Toast.makeText(BusquedaCliente.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            dlmenu.closeDrawer(GravityCompat.START);
        }
    };

    TextWatcher txtBusquedaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //getSupportActionBar().setTitle("Clientes (" + String.valueOf(rvClienteAdapter.getItemCount()) + ")");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            rvClienteAdapter.getFilter().filter(txtBusqueda.getText().toString().trim());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClienteClick(Cliente cliente) {
        Intent intent = new Intent(BusquedaCliente.this, DatosCliente.class);
        intent.putExtra(ARG_CLIENTE, cliente);
        startActivity(intent);
    }

    @Override
    public void onClienteLongClick(Cliente cliente) {
        Intent intent = new Intent(BusquedaCliente.this, ClienteFormActivity.class);
        intent.putExtra(ClienteFormActivity.ARG_OPERACION, ClienteFormActivity.REQUEST_CODE_UPDATE_DELETE);
        intent.putExtra(ARG_CLIENTE, cliente);
        startActivityForResult(intent, ClienteFormActivity.REQUEST_CODE_UPDATE_DELETE);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* DrawerLayout Section */
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /* End DrawerLayout Section */

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.itAgregar:
                Intent intent = new Intent(BusquedaCliente.this, ClienteFormActivity.class);
                intent.putExtra(ClienteFormActivity.ARG_OPERACION, ClienteFormActivity.REQUEST_CODE_INSERT);
                startActivityForResult(intent, ClienteFormActivity.REQUEST_CODE_INSERT);
                return true;
            case R.id.itLogout:
                getSharedPreferences(getPackageName(), MODE_PRIVATE).edit().clear().commit();
                /*Intent intent = new Intent(BusquedaCliente.this, MainActivity.class);
                startActivity(intent);*/
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ClienteFormActivity.REQUEST_CODE_INSERT) {
                rvClienteAdapter = new RVClienteAdapter(BusquedaCliente.this);
                rvCliente.setHasFixedSize(true);
                rvCliente.setLayoutManager(new LinearLayoutManager(BusquedaCliente.this));
                rvCliente.setAdapter(rvClienteAdapter);
//                rvClienteAdapter.notifyDataSetChanged();
            }
            if (requestCode == ClienteFormActivity.REQUEST_CODE_UPDATE_DELETE) {
                txtBusqueda.setText("");
                rvClienteAdapter = new RVClienteAdapter(BusquedaCliente.this);
                rvCliente.setHasFixedSize(true);
                rvCliente.setLayoutManager(new LinearLayoutManager(BusquedaCliente.this));
                rvCliente.setAdapter(rvClienteAdapter);
//                rvClienteAdapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(BusquedaCliente.this, "El usuario canceló operación", Toast.LENGTH_SHORT).show();
        }
    }
}
