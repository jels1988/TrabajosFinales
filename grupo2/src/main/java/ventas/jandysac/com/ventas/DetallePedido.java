package ventas.jandysac.com.ventas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ventas.jandysac.com.ventas.adapter.listview.LVPedidoDetalleAdapter;
import ventas.jandysac.com.ventas.adapter.recyclerview.RVPedidoDetalleAdapter;
import ventas.jandysac.com.ventas.dao.DataBaseHelper;
import ventas.jandysac.com.ventas.entities.ConsolidarPedido;
import ventas.jandysac.com.ventas.entities.PedidoDetalle;
import ventas.jandysac.com.ventas.dao.PedidoDAO;

public class DetallePedido extends AppCompatActivity implements RVPedidoDetalleAdapter.RVPedidoDetalleAdapterCallBack {

    DecimalFormat formato = new DecimalFormat("###.##");
    public final static String ARG_COD_CLIENTE = "arg_cod_cliente";
    public final static String ARG_CLIENTE = "cliente";
    TextView txtTotalPedido, txtItemsPedido;
    private ImageButton btnAgregarProducto, btnGuardarPedido;
    private ArrayList mLstPedidoCabecera;
    private ArrayList<PedidoDetalle> mLstPedidoDetalle;
    private RecyclerView rvPedidoDetalle;
    private ListView lvPedidoDetalle;
    private RVPedidoDetalleAdapter rvPedidoDetalleAdapter;
    private LVPedidoDetalleAdapter mLVPedidoDetalleAdapter;
    private DataBaseHelper dataBaseHelper;
    private SharedPreferences sp;
    int IdPedido = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        btnAgregarProducto = (ImageButton) findViewById(R.id.btnAgregarProducto);
        btnGuardarPedido = (ImageButton) findViewById(R.id.btnGuardarPedido);

        txtTotalPedido = (TextView) findViewById(R.id.txtTotalPedido);
        txtItemsPedido = (TextView) findViewById(R.id.txtItemsPedido);
        //rvPedidoDetalle = (RecyclerView) findViewById(R.id.rvPedidoDetalle);
        try {
            dataBaseHelper = new DataBaseHelper(DetallePedido.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(DatosCliente.ARG_COD_CLIENTE)) {
            String codigo_cliente = getIntent().getStringExtra(DatosCliente.ARG_COD_CLIENTE);
            PedidoDetalle pedidodetalle = getIntent().getParcelableExtra(DatosCliente.ARG_CLIENTE);
            pedidodetalle.setCodigo_Cliente(codigo_cliente);
            PedidoDAO pedido = new PedidoDAO();
            mLstPedidoCabecera= new ArrayList<>();
            //mLstPedidoCabecera = pedido.listPedidoCabecera(codigo_cliente);

            //PedidoDetalle pedidocabecera = new PedidoDAO().listPedidoCabecera(codigo_cliente);
            txtTotalPedido.setText(String.valueOf(formato.format(pedidodetalle.getImporte_Total())));
            txtItemsPedido.setText(String.valueOf(formato.format(pedidodetalle.getItems())));


            lvPedidoDetalle = (ListView) findViewById(R.id.lvPedidoDetalle);
            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            IdPedido = pedidodetalle.getId_Movimiento_Venta();
            //pedidoDetalle.setId_Movimiento_Venta(pedidodetalle.getId_Movimiento_Venta());
            PedidoDAO detalleGuardar = new PedidoDAO();
            mLstPedidoDetalle = detalleGuardar.listPedidoDetalle(String.valueOf(IdPedido));
            mLVPedidoDetalleAdapter = new LVPedidoDetalleAdapter(DetallePedido.this, 0, mLstPedidoDetalle);
            lvPedidoDetalle.setAdapter(mLVPedidoDetalleAdapter);
            registerForContextMenu(lvPedidoDetalle);
        }

        btnAgregarProducto.setOnClickListener(btnAgregarProductoOnClickListener);
        btnGuardarPedido.setOnClickListener(btnGuardarPedidoOnClickListener);
    }

    View.OnClickListener btnAgregarProductoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String codigo_cliente = getIntent().getStringExtra(DatosCliente.ARG_COD_CLIENTE);
            PedidoDetalle pedidodetalle = getIntent().getParcelableExtra(DatosCliente.ARG_CLIENTE);
            Intent intent = new Intent(DetallePedido.this, BusquedaProducto.class);
            intent.putExtra(ARG_COD_CLIENTE, codigo_cliente);
            intent.putExtra(ARG_CLIENTE, pedidodetalle);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener btnGuardarPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new ProgressAsyncTask().execute();
            PedidoDAO pedidoDao = new PedidoDAO();
            ConsolidarPedido consolida = new ConsolidarPedido();
            consolida.setIdPedido(IdPedido);
            consolida.setEstado(1);
            pedidoDao.updateEstadoPedido(consolida);
        }
    };

    class ProgressAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(DetallePedido.this, BusquedaCliente.class);
            startActivity(intent);
            progressDialog.dismiss();
            finishAffinity();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DetallePedido.this);
            progressDialog.setMessage("Enviando pedido al servidor...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        //int mPosition;
        // Se comprueba si se ha pulsado algún elemento del ListView
        if (v.getId() == R.id.lvPedidoDetalle){
            // Obtenemos la posición del elemento que ha sido pulsado.
            //mPosition = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
            // Establecemos como título del submenú la opción pulsado del ListView
            //menu.setHeaderTitle(lvPedidoDetalle.getAdapter().getItem(mPosition).toString());
            // Inflamos el submenú
            this.getMenuInflater().inflate(R.menu.menu_context, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        PedidoDetalle pedidocabecera = getIntent().getParcelableExtra(DatosCliente.ARG_CLIENTE);
        PedidoDetalle pedidodetalle = mLstPedidoDetalle.get(info.position);
        switch (item.getItemId()) {
//                case R.id.mcEditar:
//                    Toast.makeText(DetallePedido.this, pedidodetalle.getCodigo_Producto(), Toast.LENGTH_SHORT).show();
//                    return true;
            case R.id.mcEliminar:
                if (IdPedido != -1) {
                    //Toast.makeText(DetallePedido.this, String.valueOf(pedidodetalle.getImporte_Neto()), Toast.LENGTH_SHORT).show();
                    PedidoDAO pedidoDao = new PedidoDAO();
                    Integer stock = pedidoDao.stockProducto(pedidodetalle.getCodigo_Producto());
                    pedidoDao.deletePedidoDetalle(pedidodetalle.getCodigo_Producto(), IdPedido, stock, Double.valueOf(pedidodetalle.getCantidad()));
                    mLstPedidoDetalle.remove(info.position);
                    mLVPedidoDetalleAdapter.notifyDataSetChanged();
                    Toast.makeText(DetallePedido.this, "Registro Eliminado...", Toast.LENGTH_SHORT).show();
                    txtTotalPedido.setText(String.valueOf(formato.format(Double.valueOf(txtTotalPedido.getText().toString()) - pedidodetalle.getImporte_Neto())));
                    txtItemsPedido.setText(String.valueOf(Integer.valueOf(txtItemsPedido.getText().toString())-1));
                    return true;
                }
            case R.id.mcCancelar:
                return false;
        }
        return super.onContextItemSelected(item);
    }

    public void onPedidoDetalleClick(PedidoDetalle pedidodetalle) {

    }
}
