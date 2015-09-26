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
import pe.lindley.tomapedidos.dao.ClienteDAO;
import pe.lindley.tomapedidos.entities.Cliente;

/**
 * Created by jlama on 21/09/2015.
 */
public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.RVClienteAdapterViewHolder> implements Filterable {

    private String sFilter = "";
    private List<Cliente> listaCliente;
    private List<Cliente> listaClienteFilter;
    private RVClienteAdapterListener rvClienteAdapterListener;
    private RVClienteAdapterFilter rvClienteAdapterFilter;

    @Override
    public Filter getFilter() {
        if (rvClienteAdapterFilter == null)
            rvClienteAdapterFilter = new RVClienteAdapterFilter();
        return rvClienteAdapterFilter;
    }

    public interface RVClienteAdapterListener {
        void onSelectedItem(Cliente cliente, int position);
    }

    public RVClienteAdapter(RVClienteAdapterListener rvClienteAdapterListener) {
        listaCliente = new ArrayList<>();
        listaClienteFilter = new ArrayList<>();
        listaCliente.addAll(ClienteDAO.getInstance().getListaCliente());
        listaClienteFilter.addAll(listaCliente);
        this.rvClienteAdapterListener = rvClienteAdapterListener;
    }

    // Los tres métodos a implementar
    @Override
    public RVClienteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RVClienteAdapterViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cliente_item_activity, parent, false));
    }

    @Override
    public int getItemCount() {
        return listaClienteFilter.size();
    }

    @Override
    public void onBindViewHolder(RVClienteAdapterViewHolder holder, int position) {
        Cliente cliente = listaClienteFilter.get(position);

        holder.cliente_item_tvid.setText(String.valueOf(cliente.getClienteId()));
        holder.cliente_item_tvdireccion.setText(cliente.getClienteDireccion());
        holder.cliente_item_tvnombre.setText(cliente.getClienteRazonSocial());
        holder.cliente_item_tvnit.setText(cliente.getClienteNIT());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener);
    }

    //Manejar la selección del item
    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (rvClienteAdapterListener != null)
                rvClienteAdapterListener.onSelectedItem(listaClienteFilter.get((int) v.getTag()), (int) v.getTag());
        }
    };

    // métodos a implementar para el manejo

    public void setNewSource(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
        listaClienteFilter.clear();
        listaClienteFilter.addAll(this.listaCliente);
        notifyDataSetChanged();
    }
    /*
    public void update(Persona persona, int position) {
        Persona personaOld = mLstPersona.get(position);
        personaOld.setNombre(persona.getNombre());
        personaOld.setApellido(persona.getApellido());
        personaOld.setEdad(persona.getEdad());
        personaOld.setDni(persona.getDni());
        notifyItemChanged(position);
    }*/

    // Para el patrón Holder
    static class RVClienteAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView cliente_item_tvid;
        TextView cliente_item_tvnombre;
        TextView cliente_item_tvdireccion;
        TextView cliente_item_tvnit;

        public RVClienteAdapterViewHolder(View itemView) {
            super(itemView);
            cliente_item_tvid = (TextView) itemView.findViewById(R.id.cliente_item_tvid);
            cliente_item_tvnombre = (TextView) itemView.findViewById(R.id.cliente_item_tvnombre);
            cliente_item_tvdireccion = (TextView) itemView.findViewById(R.id.cliente_item_tvdireccion);
            cliente_item_tvnit = (TextView) itemView.findViewById(R.id.cliente_item_tvnit);
        }
    }

    class RVClienteAdapterFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            sFilter = charSequence == null ? "" : charSequence.toString();
            FilterResults filterResults = new FilterResults();

            if (sFilter.isEmpty()) {
                filterResults.values = listaCliente;
                filterResults.count = listaCliente.size();
            } else {
                String[] sFilters = sFilter.toUpperCase().split(" ");
                boolean isContains = false;

                ArrayList<Cliente> listaClienteNew = new ArrayList<>();

                for (Cliente cliente : listaCliente) {
                    isContains = false;

                    for (int j = 0; j < sFilters.length; j++) {
                        if (cliente.getClienteRazonSocial().toUpperCase().contains(sFilters[j])
                                || cliente.getClienteDireccion().toUpperCase().contains(sFilters[j])
                                || String.valueOf(cliente.getClienteId()).contains(sFilters[j])) {
                            isContains = true;
                            break;
                        }
                    }

                    if (isContains)
                        listaClienteNew.add(cliente);
                }

                filterResults.values = listaClienteNew;
                filterResults.count = listaClienteNew.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null && filterResults.values != null) {
                listaClienteFilter.clear();
                listaClienteFilter.addAll((ArrayList<Cliente>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    }
}
