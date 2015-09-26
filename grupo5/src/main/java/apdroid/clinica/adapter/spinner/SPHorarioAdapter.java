package apdroid.clinica.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import apdroid.clinica.R;
import apdroid.clinica.entidades.Doctor;

/**
 * Created by AngeloPaulo on 24/septiembre/2015.
 */
public class SPHorarioAdapter extends ArrayAdapter<String> {

        public SPHorarioAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spn_item_horario_cn, parent, false);
            TextView tvMainItemSpex = (TextView) convertView.findViewById(R.id.tvHorItemSpcn);
            String dato = getItem(position);
            tvMainItemSpex.setText(dato);

            return convertView;

        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spn_item_horario_ex, parent, false);

            TextView tvMainItemSpcn = (TextView) convertView.findViewById(R.id.tvHorItemSpex);
            //Doctor dato = getItem(position);
            //tvMainItemSpcn.setText(dato.getHorario());
            return convertView;
        }

    }
