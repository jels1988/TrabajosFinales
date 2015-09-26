package pe.lindley.tomapedidos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import pe.lindley.tomapedidos.dao.EstadisticaDAO;
import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Estadistica;

public class EstadisticaActivity extends AppCompatActivity {

    private TextView estadistica_item_totalclientes;
    private TextView estadistica_item_clientesconpedido;
    private TextView estadistica_item_clientessinpedido;
    private TextView estadistica_item_plandevisita;

    private Estadistica estadistica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadistica_activity);

        estadistica_item_totalclientes = (TextView) findViewById(R.id.estadistica_item_totalclientes);
        estadistica_item_clientesconpedido = (TextView) findViewById(R.id.estadistica_item_clientesconpedido);
        estadistica_item_clientessinpedido = (TextView) findViewById(R.id.estadistica_item_clientessinpedido);
        estadistica_item_plandevisita = (TextView) findViewById(R.id.estadistica_item_plandevisita);

        estadistica= EstadisticaDAO.getInstance().getEstadistica();

        estadistica_item_totalclientes.setText(String.valueOf(estadistica.getTotalCliente()));
        estadistica_item_clientesconpedido.setText(String.valueOf(estadistica.getTotalconPedido()));
        estadistica_item_clientessinpedido.setText(String.valueOf(estadistica.getTotalsinPedido()));
        estadistica_item_plandevisita.setText(
                new java.text.DecimalFormat("##0.##").format(estadistica.getCumplimientoplandeVisita()) + " %");
    }
}
