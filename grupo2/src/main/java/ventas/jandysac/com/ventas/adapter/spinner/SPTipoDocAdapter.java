package ventas.jandysac.com.ventas.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ventas.jandysac.com.ventas.R;

import java.util.List;


public class SPTipoDocAdapter extends ArrayAdapter<String> {

    public SPTipoDocAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.tipo_doc_spcn, parent, false);
        TextView tvSpinnerCn;
        tvSpinnerCn = (TextView) convertView.findViewById(R.id.tvTipoDocSpcn);

        String dato = getItem(position);
        tvSpinnerCn.setText(dato);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.tipo_doc_spex, parent, false);
        TextView tvSpinnerEx;
        tvSpinnerEx = (TextView) convertView.findViewById(R.id.tvTipoDocSpex);

        String dato = getItem(position);
        tvSpinnerEx.setText(dato);

        return convertView;
    }

}
