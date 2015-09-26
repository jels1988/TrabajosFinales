package com.example.angelica.apppizzahut.Adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.angelica.apppizzahut.Entity.Provincia;
import com.example.angelica.apppizzahut.R;

import java.util.List;

/**
 * Created by rudy on 24/09/15.
 */
public class SPDistritoAdapter extends ArrayAdapter<Provincia> {

    public SPDistritoAdapter(Context context, List<Provincia> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_distrito_spcn, parent, false);

        TextView tvItemDistritoSpcn = (TextView) convertView.findViewById(R.id.tvItemDistritoSpcn);

        Provincia provincia = getItem(position);

        tvItemDistritoSpcn.setText(provincia.getDistrito());

        return convertView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_distrito_spex, parent, false);

        TextView tvItemDistritoSpex = (TextView) convertView.findViewById(R.id.tvItemDistritoSpex);

        Provincia provincia = getItem(position);

        tvItemDistritoSpex.setText(provincia.getDistrito());

        return convertView;
    }
}
