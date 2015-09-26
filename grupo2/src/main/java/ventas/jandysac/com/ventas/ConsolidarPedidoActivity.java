package ventas.jandysac.com.ventas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ventas.jandysac.com.ventas.adapter.recyclerview.RVConsolidarPedidoAdapter;
import ventas.jandysac.com.ventas.dao.DataBaseHelper;
import ventas.jandysac.com.ventas.entities.ConsolidarPedido;

/**
 * Created by Administrator on 19/09/2015.
 */
public class ConsolidarPedidoActivity extends AppCompatActivity implements RVConsolidarPedidoAdapter.RVConsolidarPedidoAdapterListener {


    private ArrayList<ConsolidarPedido> mLstConsolidarPedido;
    private RecyclerView rvListaPedidos;
    private RVConsolidarPedidoAdapter mRVConsolidarPedidoAdapter;
    private DataBaseHelper dataBaseHelper;

    private TextView tvTotalPedido, tvItemsPedido;
    private SharedPreferences sp;
    private ImageButton btnEnviarPedidos;
    private CheckBox ckbTodos;

    String cod_vendedor = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidar_pedido);

        tvTotalPedido = (TextView) findViewById(R.id.tvTotalPedido);
        tvItemsPedido = (TextView) findViewById(R.id.tvItemsPedido);
        rvListaPedidos = (RecyclerView) findViewById(R.id.rvListaPedidos);
        btnEnviarPedidos = (ImageButton)findViewById(R.id.btnEnviarPedidos);

        rvListaPedidos.setHasFixedSize(true);
        rvListaPedidos.setLayoutManager(new LinearLayoutManager(ConsolidarPedidoActivity.this));

        mLstConsolidarPedido = new ArrayList<>();

        try {
            dataBaseHelper = new DataBaseHelper(ConsolidarPedidoActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sp.contains(MainActivity.ARG_NOMAPE) &&
                !sp.getString(MainActivity.ARG_NOMAPE, "").isEmpty()) {
            cod_vendedor = sp.getString(MainActivity.ARG_CODIGO, "");
        }

        btnEnviarPedidos.setOnClickListener(btnEnviarPedidosOnClickListener);

        reloadPedidos();

    }

    View.OnClickListener btnEnviarPedidosOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            int countSel = 0;
            mLstConsolidarPedido =  mRVConsolidarPedidoAdapter.lstConsolidarPedido();

            for (int k = 0; k < mLstConsolidarPedido.size(); k++) {
                ConsolidarPedido pedSelec = mLstConsolidarPedido.get(k);
                if (pedSelec.isSelected() == true) {
                    countSel++;
                }
            }
            if(countSel>0)
            {
                new AlertDialog.Builder(ConsolidarPedidoActivity.this).setTitle("Consolidar Pedidos").setMessage("¿Desea Ud. Enviar los Pedidos Seleccionados?").
                        setNegativeButton("No", alertAcceptCancelCancelOnClickListener).
                        setPositiveButton("Si", alertAcceptCancelAcceptOnClickListener).
                        setCancelable(false).show();
            }else {
                Toast.makeText(ConsolidarPedidoActivity.this,"No hay Pedidos Seleccionados.", Toast.LENGTH_LONG)
                        .show();
            }

        }
    };


    DialogInterface.OnClickListener alertAcceptCancelCancelOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    };

    DialogInterface.OnClickListener alertAcceptCancelAcceptOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            new ProgressAsyncTask().execute();

            for (int k = 0; k < mLstConsolidarPedido.size(); k++) {
                ConsolidarPedido pedSelec = mLstConsolidarPedido.get(k);
                if (pedSelec.isSelected() == true) {
                    pedSelec.setEstado(1);
                    mRVConsolidarPedidoAdapter.updateEstadoPedido(pedSelec);
                }
            }

            reloadPedidos();
        }
    };

    @Override
    public void onSelectedItem(ConsolidarPedido cPerdido, int position) {

    }

    public void reloadPedidos(){

        mRVConsolidarPedidoAdapter = new RVConsolidarPedidoAdapter(ConsolidarPedidoActivity.this, cod_vendedor);
        rvListaPedidos.setAdapter(mRVConsolidarPedidoAdapter);

        tvItemsPedido.setText(String.valueOf(mRVConsolidarPedidoAdapter.getItemCount()));
        tvTotalPedido.setText(mRVConsolidarPedidoAdapter.getTotal());


    }

    class ProgressAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ConsolidarPedidoActivity.this);
            progressDialog.setMessage("Enviando Pedidos...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
