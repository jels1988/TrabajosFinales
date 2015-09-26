package apdroid.clinica.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import apdroid.clinica.R;
import apdroid.clinica.entidades.Especialidad;

/**
 * Created by ANTONIO on 08/09/2015.
 */
public class SPEspecialidadAdapter extends ArrayAdapter<Especialidad> {

    public SPEspecialidadAdapter(Context context, List<Especialidad> objects) {
        super(context, 0, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spn_item_especialidad_ex, parent, false);

        TextView tvMainItemSpcn = (TextView) convertView.findViewById(R.id.tvEspecItemSpex);
        Especialidad dato = getItem(position);
        tvMainItemSpcn.setText(dato.getNombre());
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spn_item_especialidad_cn, parent, false);

        TextView tvMainItemSpcn = (TextView) convertView.findViewById(R.id.tvEspecItemSpcn);
        Especialidad dato = getItem(position);
        tvMainItemSpcn.setText(dato.getNombre());
        return convertView;
    }
}
