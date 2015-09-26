package com.example.angelica.apppizzahut.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angelica.apppizzahut.Adapter.LVAdapterProductos;
import com.example.angelica.apppizzahut.Entity.Pedido;
import com.example.angelica.apppizzahut.Entity.Producto;
import com.example.angelica.apppizzahut.R;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 * Created by Administrator on 19/09/2015.
 */
public class PedidoActivity extends AppCompatActivity {

    ArrayList<Producto> productos = new ArrayList<>();
    LinearLayout llPedido;
    Button btPagar;
    ArrayList<Pedido> pedidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        productos = getIntent().getParcelableArrayListExtra(ProductoActivity.ARG_PRODUCTOS);
        llPedido = (LinearLayout) findViewById(R.id.llPedido);
        btPagar = (Button) findViewById(R.id.btPagar);

        TextView[] textViews = new TextView[productos.size()];
        EditText[] editTexts = new EditText[productos.size()];

        for (int i = 0; i < productos.size(); i++) {
            textViews[i] = new TextView(this);
            editTexts[i] = new EditText(this);
            editTexts[i].setEnabled(false);
            textViews[i].setText(productos.get(i).getNombre() + "\t\t\t S/." + String.valueOf(productos.get(i).getPrecio()) + "\n" +
                    productos.get(i).getDescripcion());
            llPedido.addView(textViews[i]);
            llPedido.addView(editTexts[i]);

        }

        btPagar.setOnClickListener(btPagarOnClickListener);

    }

    View.OnClickListener btPagarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            double total = 0;
            for(int i=0;i<productos.size();i++){
                total += productos.get(i).getPrecio();
            }

            new AlertDialog.Builder(PedidoActivity.this).setTitle("Confirmar Pedido").setMessage("El total a pagar es: S/." + String.valueOf(total)).setNegativeButton("Cancelar", null).setPositiveButton("Aceptar", alertAcceptCancelAcceptOnClickListener).setCancelable(false).show();
        }
    };

    DialogInterface.OnClickListener alertAcceptCancelAcceptOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            finish();
            Toast.makeText(getApplicationContext(), "Pedido Enviado", Toast.LENGTH_SHORT).show();
        }
    };
}
