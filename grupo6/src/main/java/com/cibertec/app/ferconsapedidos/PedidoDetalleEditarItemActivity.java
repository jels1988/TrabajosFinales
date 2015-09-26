package com.cibertec.app.ferconsapedidos;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cibertec.app.ferconsapedidos.Entidad.PedidoDetalle;

public class PedidoDetalleEditarItemActivity extends AppCompatActivity {


    private TextView tvCodigoProducto ;
    private TextView tvDescripcionProducto;
    private TextView tvUnidadProducto;
    private TextView tvPrecioProducto;
    private EditText etCantidadProducto;
    private Button btEditarProducto;
    private Button btRemoverProducto;
    private int position = -1;
    private TextInputLayout tilCantidadProductoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_detalle_editar_item);
        this.setTitle("Pedido - Editar Item");
        final PedidoDetalle producto = getIntent().getParcelableExtra(PedidoActivity.ARG_PRODUCTO_PEDIDODETALLE);
        tvCodigoProducto = (TextView)findViewById(R.id.tvCodigoProductoEditar);
        tvDescripcionProducto =(TextView)findViewById(R.id.tvDescripcionProductoEditar);
        tvPrecioProducto=(TextView)findViewById(R.id.tvPrecioEditar);
        tvUnidadProducto=(TextView)findViewById(R.id.tvUnidadEditar);
        etCantidadProducto=(EditText)findViewById(R.id.etCantidadProducto);
        etCantidadProducto.setText(producto.getCantidad().toString());
        tvCodigoProducto.setText(producto.getCodigoProducto());
        tvDescripcionProducto.setText(producto.getDescripcionProducto());
        tvUnidadProducto.setText(producto.getUnidad());
        tvPrecioProducto.setText(producto.getPrecio().toString());
        position = getIntent().getIntExtra(PedidoActivity.ARG_POSITION_PEDIDODETALLE, -1);


        btEditarProducto = (Button)findViewById(R.id.btEditarProducto);
        btEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCorrect = true;
                tilCantidadProductoEditar = (TextInputLayout)findViewById(R.id.tilCantidadProductoEditar);
                tilCantidadProductoEditar.setErrorEnabled(false);
                if (tilCantidadProductoEditar.getEditText().getText().toString().trim().length() <= 0) {
                    tilCantidadProductoEditar.setError("Ingrese una cantidad");
                    tilCantidadProductoEditar.setErrorEnabled(true);
                    isCorrect = false;
                    return;
                }
                if (tilCantidadProductoEditar.getEditText().getText().toString().trim().equals(".")) {
                    tilCantidadProductoEditar.setError("Ingrese una cantidad");
                    tilCantidadProductoEditar.setErrorEnabled(true);
                    isCorrect = false;
                    return;

                }
                if ( Double.valueOf(tilCantidadProductoEditar.getEditText().getText().toString().trim()) <= 0 ) {
                    tilCantidadProductoEditar.setError("Ingrese una cantidad");
                    tilCantidadProductoEditar.setErrorEnabled(true);
                    isCorrect = false;
                    return;
                }
                if (!isCorrect) {
                    return;
                }

                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                pedidoDetalle.setCantidad(Double.valueOf(etCantidadProducto.getText().toString()));
                pedidoDetalle.setCodigoProducto(tvCodigoProducto.getText().toString());
                pedidoDetalle.setDescripcionProducto(tvDescripcionProducto.getText().toString());
                pedidoDetalle.setUnidad(tvUnidadProducto.getText().toString());
                pedidoDetalle.setPrecio(Double.valueOf(tvPrecioProducto.getText().toString()));
                pedidoDetalle.setIdProducto(Integer.valueOf(producto.getIdProducto()));

                Intent i = getIntent();
                i.putExtra(PedidoActivity.ARG_PRODUCTO_PEDIDODETALLE, pedidoDetalle);
                i.putExtra(PedidoActivity.ARG_POSITION_PEDIDODETALLE,position);
                i.putExtra(PedidoActivity.REQUEST_CODE_EDITAR_PRODUCTO_REMOVER,1);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btRemoverProducto = (Button)findViewById(R.id.btRemoverProducto);
        btRemoverProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                i.putExtra(PedidoActivity.ARG_POSITION_PEDIDODETALLE,position);
                i.putExtra(PedidoActivity.REQUEST_CODE_EDITAR_PRODUCTO_REMOVER,2);
                setResult(RESULT_OK, i);
                finish();
            }
        });


    }


}
