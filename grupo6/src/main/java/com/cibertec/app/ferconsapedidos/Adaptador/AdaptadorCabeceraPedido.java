package com.cibertec.app.ferconsapedidos.Adaptador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.cibertec.app.ferconsapedidos.Entidad.PedidoCabecera;
import com.cibertec.app.ferconsapedidos.R;


import java.util.ArrayList;

/**
 * Created by jdiaz on 09/09/2015.
 */
public class AdaptadorCabeceraPedido extends ArrayAdapter<PedidoCabecera> {




    private ArrayList<PedidoCabecera> original;
    private ArrayList<PedidoCabecera> fitems;
    private Filter filter;


    public AdaptadorCabeceraPedido(Context context, int resource, ArrayList<PedidoCabecera> fitems) {
        super(context, resource,  fitems);

        this.original = new ArrayList<PedidoCabecera>();
        this.fitems = new ArrayList<PedidoCabecera>();
        this.fitems.addAll(fitems);
        this.original.addAll(fitems);

    }


    private class ViewHolder {
        TextView IdPedidoCabecera;
        TextView FechaPedido;
        TextView NombreCliente;
        TextView CondicionPago;


    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;



        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate( R.layout.activity_pedido_cabecera_item, parent, false);

            holder = new ViewHolder();
            holder.NombreCliente = (TextView) convertView.findViewById(R.id.tvNombreClentePedidoCabecera);
            holder.CondicionPago = (TextView) convertView.findViewById(R.id.tvCondicionPagoPedidoCabecera);
            holder.FechaPedido = (TextView) convertView.findViewById(R.id.tvFechaPedidoCabecera);
            holder.IdPedidoCabecera = (TextView) convertView.findViewById(R.id.tvIdPedidoCabecera);


            convertView.setTag(holder);


        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        PedidoCabecera country = fitems.get(position);
        holder.NombreCliente.setText(country.getNombreCliente().toString());
        holder.CondicionPago.setText(country.getCondicionPago().toString());
        holder.FechaPedido.setText(country.getFechaPedido().toString());
        holder.IdPedidoCabecera.setText( String.valueOf(country.getIdPedidoCabecera()));


        return convertView;
    }

    @Override
    public Filter getFilter() {


        filter = new PkmnNameFilter();

        return filter;

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
                ArrayList<PedidoCabecera> list = new ArrayList<PedidoCabecera>(original);
                results.values = list;
                results.count = list.size();

            }
            else
            {
                final ArrayList<PedidoCabecera> list = new ArrayList<PedidoCabecera>(original);
                ArrayList<PedidoCabecera> nlist = new ArrayList<PedidoCabecera>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final PedidoCabecera pkmn = original.get(i);

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
            Log.println(Log.INFO, "Results", Integer.valueOf(results.count).toString());

            fitems = (ArrayList<PedidoCabecera>) results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = fitems.size(); i < l; i++)
                add(fitems.get(i));
            notifyDataSetInvalidated();




        }

    }


}


