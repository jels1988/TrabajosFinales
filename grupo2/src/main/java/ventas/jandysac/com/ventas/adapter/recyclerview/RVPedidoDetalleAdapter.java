package ventas.jandysac.com.ventas.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ventas.jandysac.com.ventas.R;
import ventas.jandysac.com.ventas.dao.PedidoDAO;
import ventas.jandysac.com.ventas.entities.PedidoDetalle;

/**
 * Created by Rodolfo on 23/09/2015.
 */
public class RVPedidoDetalleAdapter extends RecyclerView.Adapter<RVPedidoDetalleAdapter.RVPedidoDetalleAdapterViewHolder> implements Filterable {
    private String sFilter = "";
    TextView tvClienteCodigo;
    private ArrayList<PedidoDetalle> mLstPedidoDetalle;
    private RVPedidoDetalleAdapterCallBack mRVPedidoDetalleAdapterCallBack;
    //private RVPedidoDetalleAdapterFilter mRVPedidoDetalleAdapterFilter;

    @Override
    public Filter getFilter() {
        return null;
    }


    public interface RVPedidoDetalleAdapterCallBack {
        void onPedidoDetalleClick(PedidoDetalle pedidodetalle);
    }

    public RVPedidoDetalleAdapter(RVPedidoDetalleAdapterCallBack mRVPedidoDetalleAdapterCallBack) {
        this.mRVPedidoDetalleAdapterCallBack = mRVPedidoDetalleAdapterCallBack;
        mLstPedidoDetalle = new ArrayList<>();
        String id_movimiento_venta = "1";
        mLstPedidoDetalle.addAll(new PedidoDAO().listPedidoDetalle(id_movimiento_venta));
    }

    @Override
    public RVPedidoDetalleAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVPedidoDetalleAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detalle_pedido_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RVPedidoDetalleAdapterViewHolder rvPedidoDetalleAdapterViewHolder, int i) {
        DecimalFormat formatDec = new DecimalFormat("#.00");
        DecimalFormat formatDec2 = new DecimalFormat("#");
        PedidoDetalle pedidodetalle = mLstPedidoDetalle.get(i);
        rvPedidoDetalleAdapterViewHolder.itemView.setTag(i);
        rvPedidoDetalleAdapterViewHolder.itemView.setOnClickListener(itemViewOnClickListener);
        //rvPedidoDetalleAdapterViewHolder.tvCodigo.setText(cliente.getCodigo());
        rvPedidoDetalleAdapterViewHolder.txtDetalleProducto.setText(pedidodetalle.getDescripcion());
        rvPedidoDetalleAdapterViewHolder.txtDetalleCantidad.setText(String.valueOf(formatDec2.format(pedidodetalle.getCantidad())));
        rvPedidoDetalleAdapterViewHolder.txtDetallePrecio.setText(String.valueOf(formatDec.format(pedidodetalle.getPrecio())));
        rvPedidoDetalleAdapterViewHolder.txtDetalleSubtotal.setText(String.valueOf(formatDec.format(pedidodetalle.getImporte_Neto())));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };



    static class RVPedidoDetalleAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtDetalleProducto, txtDetalleCantidad, txtDetallePrecio, txtDetalleSubtotal;
        TextView txtTotalPedido, txtItemsPedido;

        public RVPedidoDetalleAdapterViewHolder(View itemView) {
            super(itemView);
            //tvCodigo = (TextView) itemView.findViewById(R.id.txtCodigo);
            txtDetalleProducto = (TextView) itemView.findViewById(R.id.txtDetalleProducto);
            txtDetalleCantidad = (TextView) itemView.findViewById(R.id.txtDetalleCantidad);
            txtDetallePrecio = (TextView) itemView.findViewById(R.id.txtDetallePrecio);
            txtDetalleSubtotal = (TextView) itemView.findViewById(R.id.txtDetalleSubtotal);
            txtTotalPedido = (TextView) itemView.findViewById(R.id.txtTotalPedido);
            txtItemsPedido = (TextView) itemView.findViewById(R.id.txtItemsPedido);
        }
    }
}
