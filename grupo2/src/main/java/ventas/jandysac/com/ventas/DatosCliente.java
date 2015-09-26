package ventas.jandysac.com.ventas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ventas.jandysac.com.ventas.adapter.recyclerview.RVPedidoDetalleAdapter;
import ventas.jandysac.com.ventas.dao.PedidoDAO;
import ventas.jandysac.com.ventas.entities.Cliente;
import ventas.jandysac.com.ventas.entities.PedidoDetalle;

public class DatosCliente extends AppCompatActivity {

    public final static String ARG_COD_CLIENTE = "arg_cod_cliente";
    public final static String ARG_COORDENADAS = "arg_coordenadas";
    public final static String ARG_NOMBRE_CLIENTE= "arg_nombre_cliente";
    public final static String ARG_CLIENTE = "cliente";
    TextView tvClienteCodigo, tvClienteNombre, tvClienteDireccion, tvClienteTipoDoc;
    Button btMapa, btPedido;
    String coordenadas;
    String codigoCliente;
    String nombreCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cliente);

        tvClienteCodigo = (TextView) findViewById(R.id.tvClienteCodigo);
        tvClienteNombre = (TextView) findViewById(R.id.tvClienteNombre);
        tvClienteDireccion = (TextView) findViewById(R.id.tvClienteDireccion);
        tvClienteTipoDoc = (TextView) findViewById(R.id.tvClienteTipoDoc);
        btMapa = (Button) findViewById(R.id.btMapa);
        btPedido = (Button) findViewById(R.id.btPedido);


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(BusquedaCliente.ARG_CLIENTE)) {
            Cliente cliente = getIntent().getParcelableExtra(BusquedaCliente.ARG_CLIENTE);

            getSupportActionBar().setTitle(String.valueOf(cliente.getNombre_completo()));
            tvClienteCodigo.setText(cliente.getCodigo());
            tvClienteNombre.setText(cliente.getNombre_completo());
            tvClienteDireccion.setText(cliente.getDireccion());
            tvClienteTipoDoc.setText(cliente.getTipo_doc());
            coordenadas = cliente.getCoodenadas();
            codigoCliente = cliente.getCodigo();
            nombreCliente = cliente.getNombre_completo();

            btMapa.setOnClickListener(btMapaOnClickListener);
            btPedido.setOnClickListener(btPedidoOnClickListener);
        }


    }

    View.OnClickListener btMapaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DatosCliente.this, MapaActivity.class);
            intent.putExtra(ARG_COORDENADAS, coordenadas);
            intent.putExtra(ARG_NOMBRE_CLIENTE, nombreCliente);
            startActivity(intent);
        }
    };


    View.OnClickListener btPedidoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO cambiar BusquedaProducto.class por la actividad de Pedido
            PedidoDetalle pedidodetalle = new PedidoDetalle();
            pedidodetalle.setCodigo_Cliente(codigoCliente);
            PedidoDetalle pedidocabecera = new PedidoDAO().listPedidoCabecera(codigoCliente);
            //Toast.makeText(DatosCliente.this, String.valueOf(pedidocabecera.getId_Movimiento_Venta()), Toast.LENGTH_SHORT).show();
            if(pedidocabecera.getId_Movimiento_Venta() != 0) {
                pedidodetalle.setId_Movimiento_Venta(pedidocabecera.getId_Movimiento_Venta());
                pedidodetalle.setCodigo_Cliente(pedidocabecera.getCodigo_Cliente());
                pedidodetalle.setImporte_Total(pedidocabecera.getImporte_Total());
                pedidodetalle.setItems(pedidocabecera.getItems());
                Intent intent = new Intent(DatosCliente.this, DetallePedido.class);
                intent.putExtra(ARG_COD_CLIENTE, codigoCliente);
                intent.putExtra(ARG_CLIENTE, pedidodetalle);
                startActivity(intent);
            }else{
                PedidoDAO cabeceraGuardar = new PedidoDAO();
                //if (IdPersona != -1) {
                //    dataGuardar.updatePersona(persona);
                //} else {
                String codigo = String.valueOf(cabeceraGuardar.addPedidoCabecera(pedidodetalle));
                pedidodetalle.setId_Movimiento_Venta(Integer.valueOf(codigo.toString()));
                pedidodetalle.setCodigo_Cliente(codigoCliente);
                Intent intent = new Intent(DatosCliente.this, BusquedaProducto.class);
                intent.putExtra(ARG_COD_CLIENTE, codigoCliente);
                intent.putExtra(ARG_CLIENTE, pedidodetalle);
                startActivity(intent);
            }

        }
    };

}
