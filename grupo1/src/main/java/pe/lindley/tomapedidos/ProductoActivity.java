package pe.lindley.tomapedidos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import pe.lindley.tomapedidos.adapter.recyclerview.RVClienteAdapter;
import pe.lindley.tomapedidos.adapter.recyclerview.RVProductoAdapter;
import pe.lindley.tomapedidos.entities.Producto;
import pe.lindley.tomapedidos.interfaces.Pasable;

public class ProductoActivity extends AppCompatActivity implements RVProductoAdapter.RVProductoAdapterListener, Pasable {

    private RecyclerView producto_rvproducto;
    private RVProductoAdapter rvProductoAdapter;
    private EditText producto_etbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_activity);

        producto_rvproducto = (RecyclerView) findViewById(R.id.producto_rvproducto);
        producto_etbuscar = (EditText) findViewById(R.id.producto_etbuscar);

        producto_rvproducto.setHasFixedSize(true);
        producto_rvproducto.setLayoutManager(new LinearLayoutManager(ProductoActivity.this));
        rvProductoAdapter = new RVProductoAdapter(ProductoActivity.this);
        producto_rvproducto.setAdapter(rvProductoAdapter);

        producto_etbuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rvProductoAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onSelectedItem(Producto producto, int position) {
        Intent intent = new Intent();
        intent.putExtra(ARG_PRODUCTO, producto);

        setResult(RESULT_OK, intent);
        finish();
    }
}
