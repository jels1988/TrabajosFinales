package com.cibertec.app.ferconsapedidos.Adaptador;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.cibertec.app.ferconsapedidos.Entidad.Producto;
import com.cibertec.app.ferconsapedidos.R;

import java.util.ArrayList;

/**
 * Created by jdiaz on 13/09/2015.
 */
public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ProductoViewHolder> implements Filterable,View.OnClickListener {


    private ArrayList<Producto> datos;
    public View view;
    private View.OnClickListener listener;
    private Filter filter;
    private ArrayList<Producto> original;
    private ArrayList<Producto> fitems;

    //...
    public AdaptadorProducto(ArrayList<Producto> datos) {
        this.datos = datos;
        this.original = new ArrayList<Producto>();
        this.fitems = new ArrayList<Producto>();
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


    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescripcionProducto;
        private TextView tvCodigoProducto;


        public ProductoViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvDescripcionProducto = (TextView) itemView.findViewById(R.id.tvDescripcionProducto);
            tvCodigoProducto = (TextView) itemView.findViewById(R.id.tvCodigoProducto);
        }


    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_producto_item, viewGroup, false);
        ProductoViewHolder tvh = new ProductoViewHolder(itemView);
        tvh.tvDescripcionProducto = (TextView) itemView.findViewById(R.id.tvDescripcionProducto);
        tvh.tvCodigoProducto = (TextView) itemView.findViewById(R.id.tvCodigoProducto);

        itemView.setOnClickListener(this);
        return tvh;

    }

    @Override

    public void onBindViewHolder(ProductoViewHolder viewHolder, int pos) {
        Producto item = datos.get(pos);

        viewHolder.tvDescripcionProducto.setText(item.getDescripcionProducto());
        viewHolder.tvCodigoProducto.setText(item.getCodigoProducto());

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

    private class PkmnNameFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0)
            {
                ArrayList<Producto> list = new ArrayList<Producto>(original);
                results.values = list;
                results.count = list.size();



            }
            else
            {
                final ArrayList<Producto> list = new ArrayList<Producto>(original);
                ArrayList<Producto> nlist = new ArrayList<Producto>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final Producto pkmn = original.get(i);

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


            fitems = (ArrayList<Producto>) results.values;
            notifyDataSetChanged();

            datos.clear();
            for(int i = 0, l = fitems.size(); i < l; i++) {

                datos.add(fitems.get(i));
            }
            //notifyDataSetInvalidated();




        }

    }

}

