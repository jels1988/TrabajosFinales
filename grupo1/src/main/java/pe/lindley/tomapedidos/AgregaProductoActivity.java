package pe.lindley.tomapedidos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Pedido;
import pe.lindley.tomapedidos.entities.Producto;
import pe.lindley.tomapedidos.interfaces.Pasable;

public class AgregaProductoActivity extends AppCompatActivity implements Pasable {

    private EditText agregaproducto_etcantidad;
    private Cliente cliente;
    private Producto producto;
    private TextView agregaproducto_tvid;
    private TextView agregaproducto_tvnombre;
    private Pedido pedido;

    private boolean isNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregaproducto_activity);

        agregaproducto_etcantidad = (EditText) findViewById(R.id.agregaproducto_etcantidad);
        agregaproducto_tvid = (TextView) findViewById(R.id.agregaproducto_tvid);
        agregaproducto_tvnombre = (TextView) findViewById(R.id.agregaproducto_tvnombre);

        producto = getIntent().getExtras().getParcelable(ARG_PRODUCTO);
        cliente = getIntent().getExtras().getParcelable(ARG_CLIENTE);

        if (getIntent().getExtras().getInt(ARG_OPERACION) == OP_MODIFY) {
            isNuevo = false;
            pedido = getIntent().getExtras().getParcelable(ARG_PEDIDO);
            agregaproducto_etcantidad.setText(String.valueOf(pedido.getCantidad()));
        } else {
            isNuevo = true;
        }

        agregaproducto_tvid.setText(String.valueOf(producto.getProductoId()));
        agregaproducto_tvnombre.setText(producto.getProductoNombre());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agregaproducto_menu, menu);

        menu.findItem(R.id.agregaproducto_eliminar_item).setVisible(!isNuevo);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id) {
            case R.id.agregaproducto_guardar_item:
                if (isNuevo) {
                    pedido = new Pedido();
                    pedido.setCliente(cliente);
                    pedido.setProducto(producto);
                }

                try {
                    pedido.setCantidad(Integer.parseInt(agregaproducto_etcantidad.getText().toString()));
                } catch (Exception ex) {
                }

                intent = new Intent();
                intent.putExtra(ARG_PEDIDO, pedido);
                if(isNuevo) {
                    intent.putExtra(ARG_OPERACION, OP_ADD);
                } else {
                    intent.putExtra(ARG_OPERACION, OP_MODIFY);
                }
                setResult(RESULT_OK, intent);
                finish();
                return true;
            case R.id.agregaproducto_eliminar_item:
                new AlertDialog.Builder(AgregaProductoActivity.this).
                        setTitle("Confirmación").
                        setMessage("¿Está seguro de eliminar este producto del presente pedido?").
                        setNeutralButton("Aceptar", confirmacionOnClickListener).
                        setNegativeButton("Cancelar", null).
                        setCancelable(false).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    DialogInterface.OnClickListener confirmacionOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent();
            intent.putExtra(ARG_PEDIDO, pedido);
            intent.putExtra(ARG_OPERACION, OP_DELETE);
            setResult(RESULT_OK, intent);

            dialogInterface.dismiss();
            finish();
        }
    };
}
