package com.cibertec.app.ferconsapedidos;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.cibertec.app.ferconsapedidos.Adaptador.AdaptadorCondicionPago;
import com.cibertec.app.ferconsapedidos.Adaptador.AdaptadorPedidoDetalle;
import com.cibertec.app.ferconsapedidos.Dao.CondicionPagoDAO;
import com.cibertec.app.ferconsapedidos.Dao.PedidoCabeceraDAO;
import com.cibertec.app.ferconsapedidos.Dao.PedidoDetalleDAO;
import com.cibertec.app.ferconsapedidos.Entidad.Cliente;
import com.cibertec.app.ferconsapedidos.Entidad.CondicionPago;
import com.cibertec.app.ferconsapedidos.Entidad.PedidoCabecera;
import com.cibertec.app.ferconsapedidos.Entidad.PedidoDetalle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PedidoActivity extends AppCompatActivity {
    private TextView tvNombreCliente;
    private TextView tvRuc;
    private TextView tvFechaPedido;
    private TextView tvTotal;
    private FloatingActionButton btAgregarProducto;
    private Button btGrabarPedido;
    private Spinner spCondicionPago;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private int IdCliente;
    private int IdCondicionPago;
    private int IdPedidoCabecera;
    private int positionPedidoCabecera;
    private Integer proceso;
    private String MensajeTransaccion;
    private String condicionPago;

    ListView List;
    AdaptadorPedidoDetalle adapatadorPedidoDetalle;
    ArrayList<PedidoDetalle> ArrayPedidoDetalle;
    AdaptadorCondicionPago adpatadorCondicionPago;

    private final static int REQUEST_CODE_NUEVO_PRODUCTO = 11;
    private final static int REQUEST_CODE_EDITAR_PRODUCTO = 4;
    private final static int REQUEST_CODE_EDITAR_PRODUCTO_MODIFICA_ITEM = 1;
    private final static int REQUEST_CODE_EDITAR_PRODUCTO_REMUEVE_ITEM = 2;
    public static String REQUEST_CODE_EDITAR_PRODUCTO_REMOVER = "REQUEST_CODE_EDITAR_PRODUCTO_REMOVER";

    public final static String ARG_PRODUCTO_PEDIDODETALLE = "ARG_PRODUCTO_PEDIDODETALLE";
    public final static String ARG_POSITION_PEDIDODETALLE = "ARG_POSITION_PEDIDODETALLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pedido);
        proceso = MenuPrincipalActivity.ARG_OPCION;
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);


        spCondicionPago = (Spinner)findViewById(R.id.spCondicionPago);
        adpatadorCondicionPago = new AdaptadorCondicionPago(this, 0,new CondicionPagoDAO().listCondicionPago());
        spCondicionPago.setAdapter(adpatadorCondicionPago);

        List = (ListView) findViewById(R.id.lvDetallePedido);
        if (proceso == MenuPrincipalActivity.ARG_OPCION_NUEVOPEDIDO){//1
            Cliente cliente = getIntent().getParcelableExtra(ClientesActivity.ARG_CLIENTE);

            tvNombreCliente = (TextView)findViewById(R.id.tvNombreClientePedido);
            tvRuc=(TextView)findViewById(R.id.tvRucPedido);
            tvFechaPedido = (TextView)findViewById(R.id.tvFechaPedido);
            tvNombreCliente.setText(cliente.getNombreCliente());
            tvRuc.setText(cliente.getRUC().toString());
            IdCliente =cliente.getIdCliente();

            ArrayPedidoDetalle= new ArrayList<PedidoDetalle>();
            tvFechaPedido.setText(String.format("%02d", day) + " / " + String.format("%02d", (month + 1)) + " / " + year);

        }

        if (proceso == MenuPrincipalActivity.ARG_OPCION_PEDIDOS){ //2
            this.setTitle("Editar Pedido");
            PedidoCabecera pedidoCabecera = getIntent().getParcelableExtra(PedidoCabeceraActivity.ARG_PEDIDOCABECERA);
            positionPedidoCabecera = getIntent().getIntExtra( PedidoCabeceraActivity.ARG_POSITION_PEDIDOCABECERA,-1);
            tvNombreCliente = (TextView)findViewById(R.id.tvNombreClientePedido);
            tvRuc=(TextView)findViewById(R.id.tvRucPedido);
            tvFechaPedido = (TextView)findViewById(R.id.tvFechaPedido);
            tvNombreCliente.setText(pedidoCabecera.getNombreCliente());
            IdPedidoCabecera=pedidoCabecera.getIdPedidoCabecera();
            tvRuc.setText(pedidoCabecera.getRuc()+"     NºPedido :"+String.valueOf(IdPedidoCabecera));
            IdCliente = pedidoCabecera.getIdCliente(); ;
            tvFechaPedido.setText(pedidoCabecera.getFechaPedido());
            ArrayPedidoDetalle= new PedidoDetalleDAO().listPedidoDetalleBuscar(String.valueOf(IdPedidoCabecera));
            condicionPago = pedidoCabecera.getCondicionPago();
            int count = spCondicionPago.getAdapter().getCount();
            for (int i = 0; i < count; i++)
            {
                CondicionPago pedidoDetalle = (CondicionPago) spCondicionPago.getAdapter().getItem(i);
                if (condicionPago.trim().toString().equals(pedidoDetalle.getCondicionPago().trim().toString())){
                    spCondicionPago.setSelection( i);
                    break;
                }
            }
        }

        adapatadorPedidoDetalle = new AdaptadorPedidoDetalle(PedidoActivity.this, 0,ArrayPedidoDetalle );
        List.setAdapter(adapatadorPedidoDetalle);
        List.setOnItemClickListener(lvPedidoDetalleOnItemClickListener);



        tvFechaPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PedidoActivity.this, dpetOnDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.setTitle("Fecha Pedido");

                datePickerDialog.show();
            }
        });

        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvTotal.setText(String.valueOf(ObtenerTotalPedido(List)));

        btAgregarProducto = (FloatingActionButton)findViewById(R.id.btagregar);
        btAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), ProductoActivity.class);

                startActivityForResult(intent, REQUEST_CODE_NUEVO_PRODUCTO);
            }
        });
        btGrabarPedido = (Button)findViewById(R.id.btGrabarPedido);
        btGrabarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (List.getAdapter().getCount()==0){

                    Toast.makeText(getApplicationContext(),"Debe ingresar un producto ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String MensajeTituloDialogPedido = proceso == MenuPrincipalActivity.ARG_OPCION_NUEVOPEDIDO ? "Registrar Nuevo Pedido" : "Editar Pedido";
                String MensajeContenidoDialogPedido = proceso == MenuPrincipalActivity.ARG_OPCION_NUEVOPEDIDO ? "Se enviará el pedido del cliente : "+tvNombreCliente.getText(): "Se actualizará el pedido Nº"+String.valueOf(IdPedidoCabecera);
                new AlertDialog.Builder(PedidoActivity.this).setTitle(MensajeTituloDialogPedido).setMessage(MensajeContenidoDialogPedido ).setNegativeButton("Cancelar", alertAcceptCancelCancelOnClickListener).setPositiveButton("Aceptar", alertAcceptCancelAcceptOnClickListener).setCancelable(false).show();


            }
        });


        spCondicionPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CondicionPago condicionPago = adpatadorCondicionPago.getItem(position);
                IdCondicionPago =condicionPago.getIdCondicionPago();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    DialogInterface.OnClickListener alertAcceptCancelAcceptOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {


            PedidoCabecera pedidoCabecera = new PedidoCabecera();
            pedidoCabecera.setIdCliente(IdCliente);
            pedidoCabecera.setIdUsuario(Integer.parseInt(MenuPrincipalActivity.USERID));
            pedidoCabecera.setFechaPedido(tvFechaPedido.getText().toString());
            pedidoCabecera.setIdCondicionDePago(IdCondicionPago);
            TextView textView = (TextView)spCondicionPago.getSelectedView().findViewById(R.id.tvCondicionPago);
            String result = textView.getText().toString();
            pedidoCabecera.setCondicionPago(result);
            PedidoCabeceraDAO pedidoCabeceraDAO = new PedidoCabeceraDAO();

            if (proceso== MenuPrincipalActivity.ARG_OPCION_NUEVOPEDIDO){ //1 Nuevo pedido
                IdPedidoCabecera =  pedidoCabeceraDAO.addPedidoCabecera(pedidoCabecera);
                MensajeTransaccion ="Se creó el pedido Nº ";
            }
            if (proceso== MenuPrincipalActivity.ARG_OPCION_PEDIDOS){ //Modif pedido
                pedidoCabecera.setIdPedidoCabecera(IdPedidoCabecera);
                pedidoCabeceraDAO.updatePedidoCabecera(pedidoCabecera);
                new PedidoDetalleDAO().deletePedidoCabeceraAll(IdPedidoCabecera);
                MensajeTransaccion ="Se actualizó el pedido Nº ";
            }

            ListView lv = (ListView)findViewById(R.id.lvDetallePedido);
            int count = lv.getAdapter().getCount();
            PedidoDetalleDAO pedidoDetalleDAO = new PedidoDetalleDAO();

            for (int j = 0; j < count; j++)
            {
                PedidoDetalle itempedidoDetalle =   adapatadorPedidoDetalle.getItem(j);
                PedidoDetalle pedidoDetalle = new PedidoDetalle();
                pedidoDetalle.setUnidad(itempedidoDetalle.getUnidad().toString());
                pedidoDetalle.setDescripcionProducto(itempedidoDetalle.getDescripcionProducto().toString());
                pedidoDetalle.setCantidad(Double.valueOf(itempedidoDetalle.getCantidad().toString()));
                pedidoDetalle.setCodigoProducto(itempedidoDetalle.getCodigoProducto().toString());
                pedidoDetalle.setPrecio(Double.valueOf(itempedidoDetalle.getPrecio().toString()));
                pedidoDetalle.setIdPedidoCabecera(IdPedidoCabecera);
                pedidoDetalle.setIdProducto(Integer.valueOf(itempedidoDetalle.getIdProducto()));
                pedidoDetalleDAO.addPedidoDetalle(pedidoDetalle);
            }


            Toast.makeText(PedidoActivity.this, MensajeTransaccion + IdPedidoCabecera, Toast.LENGTH_SHORT).show();
            if (proceso== MenuPrincipalActivity.ARG_OPCION_PEDIDOS) { //Modifica pedido
                Intent intent = getIntent();
                intent.putExtra(PedidoCabeceraActivity.ARG_PEDIDOCABECERA, pedidoCabecera);
                intent.putExtra(PedidoCabeceraActivity.ARG_POSITION_PEDIDOCABECERA, positionPedidoCabecera);
                setResult(RESULT_OK, intent);

            }

            finish();


        }
    };

    DialogInterface.OnClickListener alertAcceptCancelCancelOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            dialogInterface.dismiss();


        }
    };
    AdapterView.OnItemClickListener lvPedidoDetalleOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getBaseContext(), PedidoDetalleEditarItemActivity.class);
            PedidoDetalle pedidoDetalle = adapatadorPedidoDetalle.getItem(position);
            intent.putExtra(ARG_PRODUCTO_PEDIDODETALLE, pedidoDetalle);
            intent.putExtra(ARG_POSITION_PEDIDODETALLE, position);
            startActivityForResult(intent, REQUEST_CODE_EDITAR_PRODUCTO);

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK){
            return;
        }

        if (requestCode==REQUEST_CODE_EDITAR_PRODUCTO){

            PedidoDetalle pedidoDetalle = data.getParcelableExtra(ARG_PRODUCTO_PEDIDODETALLE);
            int position = data.getIntExtra(ARG_POSITION_PEDIDODETALLE, -1);
            int Remover = data.getIntExtra(PedidoActivity.REQUEST_CODE_EDITAR_PRODUCTO_REMOVER, -1);
            if (position != -1) {

                if (Remover==REQUEST_CODE_EDITAR_PRODUCTO_MODIFICA_ITEM){ //1
                    PedidoDetalle old = adapatadorPedidoDetalle.getItem(position);
                    old.setCantidad(Double.valueOf(pedidoDetalle.getCantidad()));
                    adapatadorPedidoDetalle.notifyDataSetChanged();
                }
                if (Remover== REQUEST_CODE_EDITAR_PRODUCTO_REMUEVE_ITEM){ //Remover

                    adapatadorPedidoDetalle.remove(adapatadorPedidoDetalle.getItem(position));
                    adapatadorPedidoDetalle.notifyDataSetChanged();
                }


            }
            tvTotal = (TextView)findViewById(R.id.tvTotal);
            tvTotal.setText(String.valueOf(ObtenerTotalPedido(List)));
        }
        if (requestCode==REQUEST_CODE_NUEVO_PRODUCTO) {

            PedidoDetalle pedidoDetalleAux = data.getParcelableExtra(ProductoActivity.ARG_PRODUCTO);

            if (ExisteProductoPedido(List,pedidoDetalleAux.getCodigoProducto())){
                Toast.makeText(getApplicationContext(),"Código "+pedidoDetalleAux.getCodigoProducto() +" ya fue ingresado.", Toast.LENGTH_SHORT).show();
                return;

            }

            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            pedidoDetalle.setUnidad(pedidoDetalleAux.getUnidad().toString());
            pedidoDetalle.setDescripcionProducto(pedidoDetalleAux.getDescripcionProducto().toString());
            pedidoDetalle.setCodigoProducto(pedidoDetalleAux.getCodigoProducto().toString());
            pedidoDetalle.setIdProducto(Integer.valueOf(pedidoDetalleAux.getIdProducto()));
            pedidoDetalle.setPrecio(Double.valueOf(pedidoDetalleAux.getPrecio().toString()));
            pedidoDetalle.setCantidad(Double.valueOf(pedidoDetalleAux.getCantidad().toString()));

            adapatadorPedidoDetalle.add(pedidoDetalle);
            adapatadorPedidoDetalle.notifyDataSetChanged();
            tvTotal = (TextView)findViewById(R.id.tvTotal);
            tvTotal.setText(String.valueOf(ObtenerTotalPedido(List)));

        }


    }



    private Double ObtenerTotalPedido (ListView lv){

        int count = lv.getAdapter().getCount();
        Double total = 0.00;
        for (int i = 0; i < count; i++)
        {
            PedidoDetalle pedidoDetalle = (PedidoDetalle) lv.getAdapter().getItem(i);
            total = total + pedidoDetalle.getCantidad() * pedidoDetalle.getPrecio();

        }
        return total;
    };

    private Boolean ExisteProductoPedido (ListView lv ,String codigoproducto){

        int count = lv.getAdapter().getCount();
        Boolean resultadoBusqueda= false;

        for (int i = 0; i < count; i++)
        {
            PedidoDetalle pedidoDetalle = (PedidoDetalle) lv.getAdapter().getItem(i);
            if (codigoproducto.trim().toString().equals(pedidoDetalle.getCodigoProducto().trim().toString())){
                resultadoBusqueda=true;
                break;
            }
        }
        return resultadoBusqueda;
    };
    DatePickerDialog.OnDateSetListener dpetOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar c = Calendar.getInstance();
            c.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            tvFechaPedido.setText(sdf.format(c.getTime()));
            getWindow().getDecorView().clearFocus();
        }
    };
}

