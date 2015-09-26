package com.cibertec.app.ferconsapedidos;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cibertec.app.ferconsapedidos.Adaptador.AdaptadorCliente;
import com.cibertec.app.ferconsapedidos.Adaptador.AdaptadorProducto;
import com.cibertec.app.ferconsapedidos.Dao.ClienteDAO;
import com.cibertec.app.ferconsapedidos.Dao.ProductoDAO;
import com.cibertec.app.ferconsapedidos.Entidad.Cliente;
import com.cibertec.app.ferconsapedidos.Entidad.PedidoDetalle;
import com.cibertec.app.ferconsapedidos.Entidad.Producto;

import java.util.ArrayList;

public class ProductoActivity extends AppCompatActivity {

    private AdaptadorProducto adaptadorProducto;
    private ArrayList<Producto> arrayProducto;
    private RecyclerView recViewProducto;

    public final static String ARG_PRODUCTO = "ARG_PRODUCTO";
    public final static String ARG_POSITION_PRODUCTO = "ARG_POSITION_PRODUCTO";
    public final static String ARG_PRODUCTO_PEDIDODETALLE= "ARG_PRODUCTO_PEDIDODETALLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        recViewProducto = (RecyclerView) findViewById(R.id.rvProducto);
        recViewProducto.setHasFixedSize(true);
        arrayProducto=new ProductoDAO().listProducto();
        adaptadorProducto = new AdaptadorProducto(arrayProducto);
        recViewProducto.setAdapter(adaptadorProducto);

        recViewProducto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        adaptadorProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recViewProducto.getChildAdapterPosition(v);

                Intent intent = new Intent(getBaseContext(), PedidoDetalleNuevoItemActivity.class);
                Producto producto = arrayProducto.get(itemPosition);
                intent.putExtra(ARG_PRODUCTO, producto);
                intent.putExtra(ARG_POSITION_PRODUCTO, itemPosition);

                startActivityForResult(intent, 22);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // si es nuevo
        if (resultCode == RESULT_OK){

            PedidoDetalle pedidoDetalle = data.getParcelableExtra(ARG_PRODUCTO_PEDIDODETALLE);
            Intent i = getIntent();
            i.putExtra(ARG_PRODUCTO, pedidoDetalle);
            setResult(RESULT_OK, i);
            finish();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clientes, menu);
        final MenuItem searchItem = menu.findItem(R.id.iSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.Search_product));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(ProductoActivity.this, R.string.Search_product, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //textView.setText(newText);
                adaptadorProducto.getFilter().filter(newText.toString());
                return true;
            }

        });
        //check http://stackoverflow.com/questions/11085308/changing-the-background-drawable-of-the-searchview-widget
        //View searchPlate = (View) searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        //searchPlate.setBackgroundResource(R.color.Red);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
