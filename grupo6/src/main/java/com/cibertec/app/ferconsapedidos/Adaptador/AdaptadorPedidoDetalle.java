package com.cibertec.app.ferconsapedidos.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cibertec.app.ferconsapedidos.Entidad.PedidoDetalle;
import com.cibertec.app.ferconsapedidos.R;

import java.util.List;

/**
 * Created by jdiaz on 13/09/2015.
 */
public class AdaptadorPedidoDetalle extends ArrayAdapter<PedidoDetalle> {

    public AdaptadorPedidoDetalle(Context context, int resource, List<PedidoDetalle> objects) {
        super(context, 0, objects);
    }

    static class LVPrincipalAdapterViewHolder{

        TextView CodigoProducto;
        TextView DesCripcionProducto;
        TextView Unidad;
        TextView cantidad;
        TextView Precio;
        TextView SubTotal;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LVPrincipalAdapterViewHolder lvPrincipalAdapterViewHolder;

        if (convertView==null ||  ! (convertView.getTag() instanceof  LVPrincipalAdapterViewHolder)  ){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_pedido_item,parent,false);
            lvPrincipalAdapterViewHolder=new LVPrincipalAdapterViewHolder();
            lvPrincipalAdapterViewHolder.CodigoProducto=(TextView)convertView.findViewById(R.id.tvCodigoProductoPedido);
            lvPrincipalAdapterViewHolder.DesCripcionProducto=(TextView)convertView.findViewById(R.id.tvDescripcionProductoPedido);
            lvPrincipalAdapterViewHolder.Unidad=(TextView)convertView.findViewById(R.id.tvUnidad);
            lvPrincipalAdapterViewHolder.cantidad=(TextView)convertView.findViewById(R.id.tvCantidad);
            lvPrincipalAdapterViewHolder.Precio=(TextView)convertView.findViewById(R.id.tvPrecio);
            lvPrincipalAdapterViewHolder.SubTotal=(TextView)convertView.findViewById(R.id.tvSubTotal);

            convertView.setTag(lvPrincipalAdapterViewHolder);


        }else{

            lvPrincipalAdapterViewHolder = (LVPrincipalAdapterViewHolder)convertView.getTag();

        }

        PedidoDetalle persona = getItem(position);



        lvPrincipalAdapterViewHolder.CodigoProducto.setText(persona.getCodigoProducto());
        lvPrincipalAdapterViewHolder.DesCripcionProducto.setText(persona.getDescripcionProducto());
        lvPrincipalAdapterViewHolder.Unidad.setText(persona.getUnidad());
        lvPrincipalAdapterViewHolder.cantidad.setText(String.valueOf(persona.getCantidad()));
        lvPrincipalAdapterViewHolder.Precio.setText(String.valueOf(persona.getPrecio()));
        lvPrincipalAdapterViewHolder.SubTotal.setText(String.valueOf(persona.getSubtotal()));


        return convertView;

    }

}

