package com.cibertec.app.ferconsapedidos.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cibertec.app.ferconsapedidos.Entidad.CondicionPago;
import com.cibertec.app.ferconsapedidos.R;

import java.util.List;

/**
 * Created by jdiaz on 13/09/2015.
 */
public class AdaptadorCondicionPago extends ArrayAdapter<CondicionPago> {

    public AdaptadorCondicionPago(Context context, int resource, List<CondicionPago> objects) {
        super(context, 0, objects);
    }

    static class CondicionPagoAdapterViewHolder{

        TextView CondicionPago;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position,convertView,parent);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CondicionPagoAdapterViewHolder condicionPagoAdapterViewHolder;

        if (convertView==null ||  ! (convertView.getTag() instanceof  CondicionPagoAdapterViewHolder)  ){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_condicionpago_item,parent,false);
            condicionPagoAdapterViewHolder=new CondicionPagoAdapterViewHolder();
            condicionPagoAdapterViewHolder.CondicionPago=(TextView)convertView.findViewById(R.id.tvCondicionPago);


            convertView.setTag(condicionPagoAdapterViewHolder);


        }else{

            condicionPagoAdapterViewHolder = (CondicionPagoAdapterViewHolder)convertView.getTag();

        }

        CondicionPago condicionPago = getItem(position);



        condicionPagoAdapterViewHolder.CondicionPago.setText(condicionPago.getCondicionPago());



        return convertView;

    }

}
