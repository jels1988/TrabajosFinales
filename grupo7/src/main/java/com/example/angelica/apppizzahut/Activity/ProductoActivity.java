package com.example.angelica.apppizzahut.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.angelica.apppizzahut.Adapter.LVAdapterProductos;
import com.example.angelica.apppizzahut.Dao.ProductosDao;
import com.example.angelica.apppizzahut.Entity.Pedido;
import com.example.angelica.apppizzahut.Entity.Producto;
import com.example.angelica.apppizzahut.R;

import java.util.ArrayList;
import java.util.List;

public class ProductoActivity extends AppCompatActivity {
    ListView list;
    LVAdapterProductos adapterProductos;
    List<Producto> productoList = new ArrayList<>();
    ProductosDao productosDao = new ProductosDao();
    public static final String ARG_PRODUCTOS = "arg_pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        productoList = productosDao.listProductos();

        list = (ListView) findViewById(R.id.lvProducto);

        adapterProductos = new LVAdapterProductos(this, R.layout.item_productos, productoList);
        list.setAdapter(adapterProductos);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.getMenu().findItem(R.id.guardarpedido).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                final int checkedCount = list.getCheckedItemCount();
                mode.setTitle(checkedCount + " Selected");
                adapterProductos.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_producto, menu);
                return true;
            }


            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.guardarpedido:
                        SparseBooleanArray selected = adapterProductos.getmSelectedItemsIds();
                        ArrayList<Producto> productos = new ArrayList<>();

                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Producto selectedItem = adapterProductos.getItem(selected.keyAt(i));
                                productos.add(selectedItem);
                                adapterProductos.remove(selectedItem);
                            }
                        }
                        mode.finish();
                        Intent intent = new Intent(ProductoActivity.this, PedidoActivity.class);
                        intent.putExtra(ARG_PRODUCTOS, productos);
                        startActivity(intent);
                        finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapterProductos.removeSelection();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_producto, menu);
        return true;
    }
}
