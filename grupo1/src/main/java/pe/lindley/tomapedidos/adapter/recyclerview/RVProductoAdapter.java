package pe.lindley.tomapedidos.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.lindley.tomapedidos.R;
import pe.lindley.tomapedidos.dao.ProductoDAO;
import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Producto;

/**
 * Created by jlama on 22/09/2015.
 */
public class RVProductoAdapter extends RecyclerView.Adapter<RVProductoAdapter.RVProductoAdapterViewHolder> implements Filterable {

    private List<Producto> listaProducto;
    private List<Producto> listaProductoFilter;
    private String sFilter = "";
    private RVProductoAdapterListener rvProductoAdapterListener;
    private RVProductoAdapterFilter rvProductoAdapterFilter;

    public interface RVProductoAdapterListener {
        void onSelectedItem(Producto producto, int position);
    }

    public RVProductoAdapter( RVProductoAdapterListener rvProductoAdapterListener ) {
        listaProducto = new ArrayList<>();
        listaProductoFilter = new ArrayList<>();
        listaProducto.addAll(ProductoDAO.getInstance().getListaCliente());
        listaProductoFilter.addAll(listaProducto);
        this.rvProductoAdapterListener = rvProductoAdapterListener;
    }

    @Override
    public Filter getFilter() {
        if (rvProductoAdapterFilter == null)
            rvProductoAdapterFilter = new RVProductoAdapterFilter();
        return rvProductoAdapterFilter;
    }

    @Override
    public RVProductoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVProductoAdapterViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.producto_item_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(RVProductoAdapterViewHolder holder, int position) {
        Producto producto = listaProductoFilter.get(position);

        holder.producto_tvid.setText(String.valueOf(producto.getProductoId()));
        holder.producto_tvnombre.setText(producto.getProductoNombre());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    //Manejar la selección del item
    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (rvProductoAdapterListener != null)
                rvProductoAdapterListener.onSelectedItem(listaProductoFilter.get((int) v.getTag()), (int) v.getTag());
        }
    };

    @Override
    public int getItemCount() {
        return listaProductoFilter.size();
    }

    static class RVProductoAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView producto_tvid;
        TextView producto_tvnombre;

        public RVProductoAdapterViewHolder(View itemView) {
            super(itemView);
            producto_tvid = (TextView) itemView.findViewById(R.id.producto_tvid);
            producto_tvnombre = (TextView) itemView.findViewById(R.id.producto_tvnombre);
        }
    }

    class RVProductoAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            sFilter = charSequence == null ? "" : charSequence.toString();
            FilterResults filterResults = new FilterResults();

            if (sFilter.isEmpty()) {
                filterResults.values = listaProducto;
                filterResults.count = listaProducto.size();
            } else {
                String[] sFilters = sFilter.toUpperCase().split(" ");
                boolean isContains = false;

                ArrayList<Producto> listaProductoNew = new ArrayList<>();

                for (Producto producto : listaProducto) {
                    isContains = false;

                    for (int j = 0; j < sFilters.length; j++) {
                        if (producto.getProductoNombre().toUpperCase().contains(sFilters[j]) || String.valueOf(producto.getProductoId()).contains(sFilters[j]) ) {
                            isContains = true;
                            break;
                        }
                    }

                    if (isContains)
                        listaProductoNew.add(producto);
                }

                filterResults.values = listaProductoNew;
                filterResults.count = listaProductoNew.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null && filterResults.values != null) {
                listaProductoFilter.clear();
                listaProductoFilter.addAll((ArrayList<Producto>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    }
}
