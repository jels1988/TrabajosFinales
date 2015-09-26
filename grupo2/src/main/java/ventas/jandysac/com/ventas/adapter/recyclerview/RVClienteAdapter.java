package ventas.jandysac.com.ventas.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import ventas.jandysac.com.ventas.R;
import ventas.jandysac.com.ventas.dao.ClienteDAO;
import ventas.jandysac.com.ventas.entities.Cliente;

/**
 * Created by Rodolfo on 10/09/2015.
 */
public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.RVClienteAdapterViewHolder> implements Filterable {

    private String sFilter = "";
    private ArrayList<Cliente> mLstCliente, mLstClienteFilter;
    private RVClienteAdapterCallBack mRVClienteAdapterCallBack;
    private RVClienteAdapterFilter mRVClienteAdapterFilter;

    @Override
    public Filter getFilter() {
        if (mRVClienteAdapterFilter == null)
            mRVClienteAdapterFilter = new RVClienteAdapterFilter();
        return mRVClienteAdapterFilter;
    }

    public interface RVClienteAdapterCallBack {
        void onClienteClick(Cliente cliente);
        void onClienteLongClick(Cliente cliente);
    }

    public RVClienteAdapter(RVClienteAdapterCallBack mRVClienteAdapterCallBack) {
        this.mRVClienteAdapterCallBack = mRVClienteAdapterCallBack;
        mLstClienteFilter = new ArrayList<>();
        mLstCliente = new ArrayList<>();
        mLstCliente.addAll(new ClienteDAO().listCliente());
        mLstClienteFilter.addAll(mLstCliente);
    }

    @Override
    public RVClienteAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVClienteAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cliente_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RVClienteAdapterViewHolder rvClienteAdapterViewHolder, int i) {
        Cliente cliente = mLstClienteFilter.get(i);

        rvClienteAdapterViewHolder.itemView.setTag(i);
        rvClienteAdapterViewHolder.itemView.setOnClickListener(itemViewOnClickListener);


        rvClienteAdapterViewHolder.itemView.setOnLongClickListener(itemViewOnLongClickListener);
        //rvClienteAdapterViewHolder.tvCodigo.setText(cliente.getCodigo());
        rvClienteAdapterViewHolder.tvNombreCompleto.setText(cliente.getNombre_completo());
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mRVClienteAdapterCallBack != null)
                mRVClienteAdapterCallBack.onClienteClick(mLstClienteFilter.get((int) view.getTag()));
        }
    };

    View.OnLongClickListener itemViewOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mRVClienteAdapterCallBack != null) {
                mRVClienteAdapterCallBack.onClienteLongClick(mLstClienteFilter.get((int) v.getTag()));
                return true;
            } else {
                return false;
            }
        }
    };

    @Override
    public int getItemCount() {
        return mLstClienteFilter.size();
    }

    static class RVClienteAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvNombreCompleto;

        public RVClienteAdapterViewHolder(View itemView) {
            super(itemView);
            //tvCodigo = (TextView) itemView.findViewById(R.id.txtCodigo);
            tvNombreCompleto = (TextView) itemView.findViewById(R.id.txtNombre_Completo);
        }
    }

    class RVClienteAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            sFilter = charSequence == null ? "" : charSequence.toString();
            FilterResults filterResults = new FilterResults();

            if (sFilter.isEmpty()) {
                filterResults.values = mLstCliente;
                filterResults.count = mLstCliente.size();
            } else {
                String[] sFilters = sFilter.toUpperCase().split(" ");
                boolean isContains = false;

                ArrayList<Cliente> mLstClienteNew = new ArrayList<>();

                for (int i = 0; i < mLstCliente.size(); i++) {
                    isContains = false;
                    Cliente cliente = mLstCliente.get(i);

                    for (int j = 0; j < sFilters.length; j++) {
                        if (cliente.getCodigo().toUpperCase().contains(sFilters[j]) || cliente.getNombre_completo().toUpperCase().contains(sFilters[j])) {
                            isContains = true;
                            break;
                        }
                    }

                    if (isContains)
                        mLstClienteNew.add(cliente);
                }

                filterResults.values = mLstClienteNew;
                filterResults.count = mLstClienteNew.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null) {
                mLstClienteFilter.clear();
                mLstClienteFilter.addAll((ArrayList<Cliente>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    }
}
