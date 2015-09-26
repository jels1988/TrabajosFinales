package apdroid.clinica.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apdroid.clinica.R;
import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.entidades.Doctor;
import apdroid.clinica.entidades.Especialidad;

/**
 * Created by AngeloPaulo on 24/septiembre/2015.
 */
public class SPDoctorAdapter extends ArrayAdapter<Doctor> {

    public SPDoctorAdapter(Context context, int resource, List<Doctor> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spn_item_doctor_ex, parent, false);
        TextView tvMainItemSpex = (TextView) convertView.findViewById(R.id.tvDocItemSpex);
        Doctor dato = getItem(position);
        tvMainItemSpex.setText("Dr. " + dato.getNombre() + " " + dato.getApellido());

        return convertView;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.spn_item_doctor_cn, parent, false);

        TextView tvMainItemSpcn = (TextView) convertView.findViewById(R.id.tvDocItemSpcn);
        Doctor dato = getItem(position);
        tvMainItemSpcn.setText("Dr. " + dato.getNombre() + " " + dato.getApellido());
        return convertView;
    }

    public void setNewSource(ArrayList<Doctor> newLstDatosCitas) {
        clear();
        addAll(newLstDatosCitas);
        notifyDataSetChanged();
    }



}