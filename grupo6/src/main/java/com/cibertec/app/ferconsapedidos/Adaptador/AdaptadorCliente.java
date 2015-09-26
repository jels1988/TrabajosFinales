package com.cibertec.app.ferconsapedidos.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cibertec.app.ferconsapedidos.Entidad.Cliente;
import com.cibertec.app.ferconsapedidos.R;

import java.util.ArrayList;

/**
 * Created by jdiaz on 30/08/2015.
 */
public class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.ClienteViewHolder> implements  Filterable,View.OnClickListener  {


    private ArrayList<Cliente> datos;
    public View view;
    private View.OnClickListener listener;

    private Filter filter;
    private ArrayList<Cliente> original;
    private ArrayList<Cliente> fitems;

    //...
    public AdaptadorCliente(ArrayList<Cliente> datos) {
        this.datos = datos;
        this.original = new ArrayList<Cliente>();
        this.fitems = new ArrayList<Cliente>();
        this.fitems.addAll(datos);
        this.original.addAll(datos);

    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) listener.onClick(view);
    }



    public class ClienteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreCliente;
        private TextView tvRUC;


        public ClienteViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvNombreCliente = (TextView) itemView.findViewById(R.id.tvNombreClente);
            tvRUC= (TextView) itemView.findViewById(R.id.tvRUC);
        }


    }

    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_clientes_item, viewGroup, false);
        ClienteViewHolder tvh = new ClienteViewHolder(itemView);
        tvh.tvNombreCliente = (TextView) itemView.findViewById(R.id.tvNombreClente);
        tvh.tvRUC = (TextView) itemView.findViewById(R.id.tvRUC);

        itemView.setOnClickListener(this);

        return tvh;

    }

    @Override

    public void onBindViewHolder(ClienteViewHolder viewHolder, int pos) {
        Cliente item = datos.get(pos);

        viewHolder.tvNombreCliente.setText(item.getNombreCliente());
        viewHolder.tvRUC.setText(item.getRUC());

    }


    @Override
    public int getItemCount() {
        return datos.size();
    }

    @Override
    public Filter getFilter() {


        filter = new PkmnNameFilter();

        return filter;

    }

    public void clear() {
        int count = getItemCount();
        datos.clear();
        notifyItemRangeRemoved(0, count);
    }

    public void setNewSource(ArrayList<Cliente> lstCliente) {
        datos = lstCliente;
        notifyDataSetChanged();
    }

    public void add(Cliente cliente) {
        datos.add(cliente);
        fitems.add(cliente);
        original.add(cliente);
        notifyItemInserted(datos.size());
    }
    public void remove(int i) {
        datos.remove(i);
        notifyItemRemoved(i);
    }


    public void addAll(ArrayList<Cliente> lstCliente) {
        int count = getItemCount();
        datos.addAll(lstCliente);
        notifyItemRangeInserted(count, lstCliente.size());
    }

    public void update(Cliente cliente, int position) {
        Cliente clienteold = datos.get(position);
        clienteold.setNombreCliente(cliente.getNombreCliente());
        clienteold.setDireccion(cliente.getDireccion());
        clienteold.setRUC(cliente.getRUC());
        clienteold.setTelefono(cliente.getTelefono());
        clienteold.setLongitud(cliente.getLongitud());
        clienteold.setLatitud(cliente.getLatitud());
        notifyItemChanged(position);
    }



    private class PkmnNameFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0)
            {
                ArrayList<Cliente> list = new ArrayList<Cliente>(original);
                results.values = list;
                results.count = list.size();



            }
            else
            {
                final ArrayList<Cliente> list = new ArrayList<Cliente>(original);
                ArrayList<Cliente> nlist = new ArrayList<Cliente>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final Cliente pkmn = original.get(i);

                    if (pkmn.toString().toLowerCase().contains(prefix))
                    {
                        nlist.add(pkmn);

                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {


            fitems = (ArrayList<Cliente>) results.values;
            notifyDataSetChanged();

            datos.clear();
            for(int i = 0, l = fitems.size(); i < l; i++) {

               datos.add(fitems.get(i));
            }





        }

    }

}
