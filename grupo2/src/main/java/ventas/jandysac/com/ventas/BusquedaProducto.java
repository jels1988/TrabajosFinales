package ventas.jandysac.com.ventas;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ventas.jandysac.com.ventas.adapter.recyclerview.RVProductoAdapter;
import ventas.jandysac.com.ventas.dao.DataBaseHelper;
import ventas.jandysac.com.ventas.dao.PedidoDAO;
import ventas.jandysac.com.ventas.dao.ProductoDAO;
import ventas.jandysac.com.ventas.entities.Pedido;
import ventas.jandysac.com.ventas.entities.PedidoDetalle;
import ventas.jandysac.com.ventas.entities.Producto;
import ventas.jandysac.com.ventas.ui.UiProductoPrecio;

/**
 * Created by Rodolfo on 09/09/2015.
 */
public class BusquedaProducto extends AppCompatActivity implements RVProductoAdapter.RVProductoAdapterCallBack {
    private EditText txtBusquedaProducto;
    private RecyclerView rvProducto;
    private RVProductoAdapter rvProductoAdapter;
    private DataBaseHelper dataBaseHelper;
    private final static int REQUEST_CODE = 1;
    public final static String CLAVE = "CLAVE", POS = "POS";
    public final static int CODE1 = 1;
    DecimalFormat formatDec = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_producto);
        //getSupportActionBar().setIcon(R.drawable.ic_lock_black_24dp);
        txtBusquedaProducto = (EditText) findViewById(R.id.txtBusquedaProducto);
        rvProducto = (RecyclerView) findViewById(R.id.rvProducto);

        try {
            dataBaseHelper = new DataBaseHelper(BusquedaProducto.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        rvProductoAdapter = new RVProductoAdapter(BusquedaProducto.this);
        rvProducto.setHasFixedSize(true);
        rvProducto.setLayoutManager(new LinearLayoutManager(BusquedaProducto.this));
        rvProducto.setAdapter(rvProductoAdapter);
        getSupportActionBar().setTitle("Productos (" + String.valueOf(rvProductoAdapter.getItemCount()) + ")");
        txtBusquedaProducto.addTextChangedListener(txtBusquedaProductoTextWatcher);
    }

    TextWatcher txtBusquedaProductoTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //getSupportActionBar().setTitle("Productos (" + String.valueOf(rvProductoAdapter.getItemCount()) + ")");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            rvProductoAdapter.getFilter().filter(txtBusquedaProducto.getText().toString().trim());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    public void onProductoClick(final Producto producto) {

        PedidoDetalle pedidodetalle = getIntent().getParcelableExtra(DatosCliente.ARG_CLIENTE);
        PedidoDetalle pedido = new PedidoDetalle();
        PedidoDAO pedidoDao = new PedidoDAO();
        Integer cantidad = pedidoDao.buscarProducto(producto.getCodigo(), pedidodetalle.getId_Movimiento_Venta());
        if (cantidad != 0) {
            new AlertDialog.Builder(BusquedaProducto.this).setMessage("El producto ya esta añadido en el pedido actual......").setNeutralButton("ACEPTAR", alertOnClickListener).setCancelable(false).show();
            return;
        }


        final Dialog dialog = new Dialog(BusquedaProducto.this);
        dialog.setContentView(R.layout.ui_producto_precio);
        dialog.setTitle(producto.getDescripcion());
        TextView txtUiProductoPrecio = (TextView) dialog.findViewById(R.id.txtUiProductoPrecio);
        txtUiProductoPrecio.setText(String.valueOf(formatDec.format(producto.getPrecio())));

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(BusquedaProducto.this, String.valueOf(producto.getStock()), Toast.LENGTH_SHORT).show();
                TextView txtUiProductoCantidad = (TextView) dialog.findViewById(R.id.txtUiProductoCantidad);
                if (txtUiProductoCantidad.getText().toString().trim().length() <= 0) {
                    Toast.makeText(BusquedaProducto.this, "Ingrese la cantidad...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.valueOf(txtUiProductoCantidad.getText().toString().trim()) == 0) {
                    Toast.makeText(BusquedaProducto.this, "Ingrese la cantidad...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.valueOf(txtUiProductoCantidad.getText().toString().trim()) > producto.getStock()) {
                    Toast.makeText(BusquedaProducto.this, "No hay suficiente Stock...", Toast.LENGTH_SHORT).show();
                    return;
                }
                PedidoDetalle pedidodetalle = getIntent().getParcelableExtra(DatosCliente.ARG_CLIENTE);
                PedidoDetalle pedido = new PedidoDetalle();
                pedido.setId_Movimiento_Venta(pedidodetalle.getId_Movimiento_Venta());
                pedido.setCodigo_Producto(producto.getCodigo());
                pedido.setCantidad(Double.valueOf(txtUiProductoCantidad.getText().toString()));
                pedido.setPrecio(Double.valueOf(producto.getPrecio()));
                pedido.setStock(producto.getStock());
                PedidoDAO dataGuardar = new PedidoDAO();
                dataGuardar.addPedidoDetalle(pedido);
                Toast.makeText(BusquedaProducto.this, "Producto "+producto.getCodigo()+" fue añadido...", Toast.LENGTH_SHORT).show();
                rvProductoAdapter = new RVProductoAdapter(BusquedaProducto.this);
                rvProducto.setHasFixedSize(true);
                rvProducto.setLayoutManager(new LinearLayoutManager(BusquedaProducto.this));
                rvProducto.setAdapter(rvProductoAdapter);
                txtBusquedaProducto.setText("");
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    DialogInterface.OnClickListener alertOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    };
}
