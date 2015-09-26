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
import pe.lindley.tomapedidos.dao.ClienteDAO;
import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.interfaces.Pasable;

public class ClienteActivity extends AppCompatActivity implements RVClienteAdapter.RVClienteAdapterListener, Pasable {

    private RecyclerView cliente_rvcliente;
    private RVClienteAdapter rvClienteAdapter;
    private EditText cliente_etbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_activity);

        cliente_rvcliente = (RecyclerView) findViewById(R.id.cliente_rvcliente);
        cliente_etbuscar = (EditText) findViewById(R.id.cliente_etbuscar);

        cliente_rvcliente.setHasFixedSize(true);
        cliente_rvcliente.setLayoutManager(new LinearLayoutManager(ClienteActivity.this));
        rvClienteAdapter = new RVClienteAdapter(ClienteActivity.this);
        cliente_rvcliente.setAdapter(rvClienteAdapter);

        cliente_etbuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rvClienteAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onSelectedItem(Cliente cliente, int position) {
        Intent intent = new Intent(ClienteActivity.this, PedidoActivity.class);
        intent.putExtra(ARG_CLIENTE, cliente);

        startActivity(intent);
    }
}
