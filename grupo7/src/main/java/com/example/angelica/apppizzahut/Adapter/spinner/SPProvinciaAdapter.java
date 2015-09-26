package com.example.angelica.apppizzahut.Adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.angelica.apppizzahut.Entity.Local;
import com.example.angelica.apppizzahut.Entity.Provincia;
import com.example.angelica.apppizzahut.R;

import java.util.List;

/**
 * Created by rudy on 24/09/15.
 */
public class SPProvinciaAdapter extends ArrayAdapter<Provincia> {

    public SPProvinciaAdapter(Context context, List<Provincia> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_provincia_spcn, parent, false);

        TextView tvItemProvinciaSpcn = (TextView) convertView.findViewById(R.id.tvItemProvinciaSpcn);

        Provincia provincia = getItem(position);

        tvItemProvinciaSpcn.setText(provincia.getProvincia());

        return convertView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_provincia_spex, parent, false);

        TextView tvItemProvinciaSpex = (TextView) convertView.findViewById(R.id.tvItemProvinciaSpex);

        Provincia provincia = getItem(position);

        tvItemProvinciaSpex.setText(provincia.getProvincia());

        return convertView;
    }
}
