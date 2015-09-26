package pe.lindley.tomapedidos.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.lindley.tomapedidos.R;
import pe.lindley.tomapedidos.dao.ClienteDAO;
import pe.lindley.tomapedidos.dao.PedidoDAO;
import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Pedido;

/**
 * Created by jlama on 22/09/2015.
 */
public class RVPedidoAdapter extends RecyclerView.Adapter<RVPedidoAdapter.RVPedidoAdapterViewHolder> {

    private List<Pedido> listaPedido;
    private RVPedidoAdapterListener rvPedidoAdapterListener;

    public interface RVPedidoAdapterListener {
        void onSelectedItem(Pedido pedido, int position);
    }

    public RVPedidoAdapter(RVPedidoAdapterListener rvPedidoAdapterListener) {
        listaPedido = new ArrayList<>();
        this.rvPedidoAdapterListener = rvPedidoAdapterListener;
    }

    @Override
    public RVPedidoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVPedidoAdapterViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pedido_item_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(RVPedidoAdapterViewHolder holder, int position) {
        Pedido pedido = listaPedido.get(position);

        holder.pedido_item_tvid.setText(String.valueOf(pedido.getProducto().getProductoId()));
        holder.pedido_item_tvnombre.setText(pedido.getProducto().getProductoNombre());
        holder.pedido_item_tvcantidad.setText(String.valueOf(pedido.getCantidad()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    //Manejar la selección del item
    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (rvPedidoAdapterListener != null)
                rvPedidoAdapterListener.onSelectedItem(listaPedido.get((int) v.getTag()), (int) v.getTag());
        }
    };

    public boolean contains( Pedido pedido ) {
        return listaPedido.contains(pedido);
    }

    public int indexOf ( Pedido pedido ) {
        return listaPedido.indexOf(pedido);
    }

    public Pedido get ( int position ) {
        return listaPedido.get(position);
    }

    public void setListaPedido( List<Pedido> listaPedido ) {
        this.listaPedido = listaPedido;
        notifyDataSetChanged();
    }

    public void addPedido( Pedido pedido ) {
        PedidoDAO.getInstance().add(pedido);

        listaPedido.add(pedido);
        notifyItemInserted(listaPedido.indexOf(pedido));
    }

    public void updatePedido( Pedido pedido ) {
        PedidoDAO.getInstance().update(pedido);

        int position = listaPedido.indexOf(pedido);
        listaPedido.get(position).setCantidad(pedido.getCantidad());
        notifyItemChanged(position);
    }

    public void deletePedido( Pedido pedido ) {
        PedidoDAO.getInstance().delete(pedido);

        int position = listaPedido.indexOf(pedido);
        listaPedido.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAllPedido( Cliente cliente) {
        PedidoDAO.getInstance().delete(cliente);

        listaPedido = new ArrayList<>();
        notifyDataSetChanged();
    }

    static class RVPedidoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView pedido_item_tvid;
        TextView pedido_item_tvnombre;
        TextView pedido_item_tvcantidad;

        public RVPedidoAdapterViewHolder(View itemView) {
            super(itemView);
            pedido_item_tvid = (TextView) itemView.findViewById(R.id.pedido_item_tvid);
            pedido_item_tvnombre = (TextView) itemView.findViewById(R.id.pedido_item_tvnombre);
            pedido_item_tvcantidad = (TextView) itemView.findViewById(R.id.pedido_item_tvcantidad);
        }
    }
}
