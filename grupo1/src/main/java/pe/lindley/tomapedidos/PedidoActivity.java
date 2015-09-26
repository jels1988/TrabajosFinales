package pe.lindley.tomapedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import pe.lindley.tomapedidos.adapter.recyclerview.RVPedidoAdapter;
import pe.lindley.tomapedidos.dao.PedidoDAO;
import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Pedido;
import pe.lindley.tomapedidos.entities.Producto;
import pe.lindley.tomapedidos.interfaces.Pasable;

public class PedidoActivity extends AppCompatActivity implements RVPedidoAdapter.RVPedidoAdapterListener, Pasable {

    private TextView pedido_tvclienteid;
    private TextView pedido_tvclientenit;
    private TextView pedido_tvclientenombre;
    private TextView pedido_tvclientedireccion;

    private Cliente cliente;

    private RecyclerView pedido_rvpedido;
    private RVPedidoAdapter rvPedidoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_activity);

        pedido_tvclienteid = (TextView) findViewById(R.id.pedido_tvclienteid);
        pedido_tvclientenit = (TextView) findViewById(R.id.pedido_tvclientenit);
        pedido_tvclientenombre = (TextView) findViewById(R.id.pedido_tvclientenombre);
        pedido_tvclientedireccion = (TextView) findViewById(R.id.pedido_tvclientedireccion);

        pedido_rvpedido = (RecyclerView) findViewById(R.id.pedido_rvpedido);

        pedido_rvpedido.setHasFixedSize(true);
        pedido_rvpedido.setLayoutManager(new LinearLayoutManager(PedidoActivity.this));
        rvPedidoAdapter = new RVPedidoAdapter(PedidoActivity.this);
        pedido_rvpedido.setAdapter(rvPedidoAdapter);

        Intent intent = getIntent();
        cliente = intent.getExtras().getParcelable(ARG_CLIENTE);

        rvPedidoAdapter.setListaPedido(PedidoDAO.getInstance().getListaPedido(cliente));

        pedido_tvclienteid.setText(String.valueOf(cliente.getClienteId()));
        pedido_tvclientenit.setText(cliente.getClienteNIT());
        pedido_tvclientenombre.setText(cliente.getClienteRazonSocial());
        pedido_tvclientedireccion.setText(cliente.getClienteDireccion());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pedido_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        switch (id) {
            case R.id.pedidomenu_nuevoarticulo_item:
                intent = new Intent(PedidoActivity.this, ProductoActivity.class);
                startActivityForResult(intent, ARG_PRODUCTOPEDIDO);

                return true;
            case R.id.pedidomenu_eliminararticulos_item:
                new AlertDialog.Builder(PedidoActivity.this).
                        setTitle("Confirmación").
                        setMessage("¿Está seguro de eliminar todos los productos del presente pedido?").
                        setNeutralButton("Aceptar", confirmacionOnClickListener).
                        setNegativeButton("Cancelar", null).
                        setCancelable(false).show();
                return true;
            case R.id.pedidomenu_ubicacioncliente_item:
                intent = new Intent(PedidoActivity.this, UbicacionClienteActivity.class);
                intent.putExtra(ARG_CLIENTE, cliente);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    DialogInterface.OnClickListener confirmacionOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            rvPedidoAdapter.deleteAllPedido(cliente);
            dialogInterface.dismiss();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Pedido pedido;
        Producto producto;
        switch ( requestCode ) {
            case ARG_PRODUCTOPEDIDO:
                if( resultCode == RESULT_OK ) {
                    Intent intent = new Intent(PedidoActivity.this, AgregaProductoActivity.class);
                    // ver si ya existe ese producto
                    producto = data.getExtras().getParcelable(ARG_PRODUCTO);
                    pedido = new Pedido();
                    pedido.setProducto(producto);
                    pedido.setCliente(cliente);
                    intent.putExtra(ARG_CLIENTE, cliente);
                    intent.putExtra(ARG_PRODUCTO, producto);
                    if( rvPedidoAdapter.contains(pedido) ) {
                        // enviar información actualizada
                        intent.putExtra(ARG_PEDIDO, rvPedidoAdapter.get(rvPedidoAdapter.indexOf(pedido)));
                        intent.putExtra(ARG_OPERACION, OP_MODIFY);
                    } else {
                        intent.putExtra(ARG_OPERACION, OP_ADD);
                    }
                    startActivityForResult(intent, ARG_CLIENTEPRODUCTOPEDIDO);
                }
                break;
            case ARG_CLIENTEPRODUCTOPEDIDO:
                if( resultCode == RESULT_OK ) {
                    pedido = data.getExtras().getParcelable(ARG_PEDIDO);
                    switch (data.getExtras().getInt(ARG_OPERACION)) {
                        case OP_ADD:
                            rvPedidoAdapter.addPedido(pedido);
                            break;
                        case OP_MODIFY:
                            rvPedidoAdapter.updatePedido(pedido);
                            break;
                        case OP_DELETE:
                            rvPedidoAdapter.deletePedido(pedido);
                            break;
                    }
                }
                break;
        }
    }

    @Override
    public void onSelectedItem(Pedido pedido, int position) {
        Intent intent = new Intent(PedidoActivity.this, AgregaProductoActivity.class);
        intent.putExtra(ARG_CLIENTE, cliente);
        intent.putExtra(ARG_PRODUCTO, pedido.getProducto());
        intent.putExtra(ARG_PEDIDO, pedido);
        intent.putExtra(ARG_OPERACION, OP_MODIFY);
        startActivityForResult(intent, ARG_CLIENTEPRODUCTOPEDIDO);
    }
}
