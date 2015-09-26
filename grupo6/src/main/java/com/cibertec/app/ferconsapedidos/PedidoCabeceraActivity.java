package com.cibertec.app.ferconsapedidos;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.app.ferconsapedidos.Adaptador.AdaptadorCabeceraPedido;
import com.cibertec.app.ferconsapedidos.Dao.PedidoCabeceraDAO;
import com.cibertec.app.ferconsapedidos.Entidad.PedidoCabecera;
import com.cibertec.app.ferconsapedidos.Entidad.PedidoDetalle;

import java.util.ArrayList;

public class PedidoCabeceraActivity extends AppCompatActivity {

    ListView ListPedidoCabecera;
    AdaptadorCabeceraPedido adpatadorPedidoCabecera;
    ArrayList<PedidoCabecera>  arrayPedidoCabecera;
    public final static String ARG_PEDIDOCABECERA = "ARG_PEDIDOCABECERA";
    public final static String ARG_POSITION_PEDIDOCABECERA = "ARG_POSITION_PEDIDOCABECERA";
    public final static String ARG_EDICION = "ARG_EDICION";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_cabecera);

        ListPedidoCabecera = (ListView) findViewById(R.id.lvPedidoCabecera);
        arrayPedidoCabecera=new PedidoCabeceraDAO().listPedidoCabecera();

        adpatadorPedidoCabecera = new  AdaptadorCabeceraPedido(this, R.layout.activity_pedido_cabecera_item, arrayPedidoCabecera );
        ListPedidoCabecera.setAdapter(adpatadorPedidoCabecera);
        ListPedidoCabecera.setTextFilterEnabled(true);
        ListPedidoCabecera.setOnItemClickListener(lvPedidoCabeceraOnItemClickListener);


    }

    AdapterView.OnItemClickListener lvPedidoCabeceraOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            PedidoCabecera pedidoCabecera =  adpatadorPedidoCabecera.getItem(position);
            Intent intent = new Intent(getBaseContext(),PedidoActivity.class);
            intent.putExtra(ARG_PEDIDOCABECERA ,  pedidoCabecera);
            intent.putExtra(ARG_POSITION_PEDIDOCABECERA , position);
            intent.putExtra(ARG_EDICION, "EDITAR");

            startActivityForResult(intent,12);
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clientes, menu);
        final MenuItem searchItem = menu.findItem(R.id.iSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.Search_Pedido));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(PedidoCabeceraActivity.this, R.string.Search_Pedido, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //textView.setText(newText);
                adpatadorPedidoCabecera.getFilter().filter(newText.toString());
                return true;
            }

        });
        //check http://stackoverflow.com/questions/11085308/changing-the-background-drawable-of-the-searchview-widget
        //View searchPlate = (View) searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        //searchPlate.setBackgroundResource(R.color.Red);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK){
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
        PedidoCabecera pedidoCabecera = data.getParcelableExtra("ARG_PEDIDOCABECERA");
        int position = data.getIntExtra("ARG_POSITION_PEDIDOCABECERA", -1);
        if (resultCode != RESULT_OK){
            return;
        }

        if (position != -1) {
                PedidoCabecera old = adpatadorPedidoCabecera.getItem(position);
                old.setCondicionPago(pedidoCabecera.getCondicionPago());
                old.setFechaPedido(pedidoCabecera.getFechaPedido());
                adpatadorPedidoCabecera.notifyDataSetChanged();
        }
    }


}
