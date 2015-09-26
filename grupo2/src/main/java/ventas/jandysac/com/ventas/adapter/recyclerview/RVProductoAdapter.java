package ventas.jandysac.com.ventas.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ventas.jandysac.com.ventas.R;
import ventas.jandysac.com.ventas.dao.ProductoDAO;
import ventas.jandysac.com.ventas.entities.Producto;

/**
 * Created by Rodolfo on 10/09/2015.
 */
public class RVProductoAdapter extends RecyclerView.Adapter<RVProductoAdapter.RVProductoAdapterViewHolder> implements Filterable {

    private String sFilter = "";
    private ArrayList<Producto> mLstProducto, mLstProductoFilter;
    private RVProductoAdapterCallBack mRVProductoAdapterCallBack;
    private RVProductoAdapterFilter mRVProductoAdapterFilter;

    @Override
    public Filter getFilter() {
        if (mRVProductoAdapterFilter == null)
            mRVProductoAdapterFilter = new RVProductoAdapterFilter();
        return mRVProductoAdapterFilter;
    }

    public interface RVProductoAdapterCallBack {
        void onProductoClick(Producto producto);
    }

    public RVProductoAdapter(RVProductoAdapterCallBack mRVProductoAdapterCallBack) {
        this.mRVProductoAdapterCallBack = mRVProductoAdapterCallBack;
        mLstProductoFilter = new ArrayList<>();
        mLstProducto = new ArrayList<>();
        mLstProducto.addAll(new ProductoDAO().listProducto());
        mLstProductoFilter.addAll(mLstProducto);
    }

    @Override
    public RVProductoAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVProductoAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.producto_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RVProductoAdapterViewHolder rvProductoAdapterViewHolder, int i) {
        DecimalFormat formatDec = new DecimalFormat("#.00");
        DecimalFormat formatDec2 = new DecimalFormat("#");
        Producto producto = mLstProductoFilter.get(i);

        rvProductoAdapterViewHolder.itemView.setTag(i);
        rvProductoAdapterViewHolder.itemView.setOnClickListener(itemViewOnClickListener);
        //rvProductoAdapterViewHolder.tvCodigo.setText(producto.getCodigo());
        rvProductoAdapterViewHolder.tvDescripcionProducto.setText(producto.getDescripcion());
        rvProductoAdapterViewHolder.tvPrecioProducto.setText("Pre: "+String.valueOf(formatDec.format(producto.getPrecio())));
        rvProductoAdapterViewHolder.tvStockProducto.setText("Sto: "+String.valueOf(formatDec2.format(producto.getStock())));
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mRVProductoAdapterCallBack != null)
                mRVProductoAdapterCallBack.onProductoClick(mLstProductoFilter.get((int) view.getTag()));
        }
    };

    @Override
    public int getItemCount() {
        return mLstProductoFilter.size();
    }

    static class RVProductoAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvDescripcionProducto, tvPrecioProducto, tvStockProducto;

        public RVProductoAdapterViewHolder(View itemView) {
            super(itemView);
            //tvCodigo = (TextView) itemView.findViewById(R.id.txtCodigo);
            tvDescripcionProducto = (TextView) itemView.findViewById(R.id.txtDescripcionProducto);
            tvPrecioProducto = (TextView) itemView.findViewById(R.id.txtPrecioProducto);
            tvStockProducto = (TextView) itemView.findViewById(R.id.txtStockProducto);
        }
    }

    class RVProductoAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            sFilter = charSequence == null ? "" : charSequence.toString();
            FilterResults filterResults = new FilterResults();

            if (sFilter.isEmpty()) {
                filterResults.values = mLstProducto;
                filterResults.count = mLstProducto.size();
            } else {
                String[] sFilters = sFilter.toUpperCase().split(" ");
                boolean isContains = false;

                ArrayList<Producto> mLstProductoNew = new ArrayList<>();

                for (int i = 0; i < mLstProducto.size(); i++) {
                    isContains = false;
                    Producto producto = mLstProducto.get(i);

                    for (int j = 0; j < sFilters.length; j++) {
                        if (producto.getCodigo().toUpperCase().contains(sFilters[j]) || producto.getDescripcion().toUpperCase().contains(sFilters[j])) {
                            isContains = true;
                            break;
                        }
                    }

                    if (isContains)
                        mLstProductoNew.add(producto);
                }

                filterResults.values = mLstProductoNew;
                filterResults.count = mLstProductoNew.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null) {
                mLstProductoFilter.clear();
                mLstProductoFilter.addAll((ArrayList<Producto>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    }
}
