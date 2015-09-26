package grupo4.histoclici.adaptadores.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import grupo4.histoclici.R;
import grupo4.histoclici.entidad.Paciente;

public class ASPaciente extends ArrayAdapter<Paciente> {

    public ASPaciente(Context context, List<Paciente> objects) {
        super(context, 0, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PacienteHolder oPH;

        if(convertView == null || !(convertView.getTag() instanceof PacienteHolder)){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner_paciente, parent, false);
            oPH = new PacienteHolder();
            oPH.tvidPaciente = (TextView)convertView.findViewById(R.id.itvspPaciente);
            oPH.tvPaciente = (TextView)convertView.findViewById(R.id.itvspPaciente);
            convertView.setTag(oPH);
        }else{
            oPH = (PacienteHolder)convertView.getTag();
        }

        Paciente opaciente = getItem(position);

        oPH.tvidPaciente.setText(String.valueOf(opaciente.getidPaciente()));
        oPH.tvPaciente.setText(opaciente.getPaciente().toString());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        PacienteHolder oPH;

        if(convertView == null || !(convertView.getTag() instanceof PacienteHolder)){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_dropdown_paciente, parent, false);
            oPH = new PacienteHolder();
            oPH.tvidPaciente = (TextView)convertView.findViewById(R.id.itvddpidPaciente);
            oPH.tvPaciente = (TextView)convertView.findViewById(R.id.itvddpPaciente);
            convertView.setTag(oPH);
        }else{
            oPH = (PacienteHolder)convertView.getTag();
        }

        Paciente opaciente = getItem(position);

        oPH.tvidPaciente.setText(String.valueOf(opaciente.getidPaciente()));
        oPH.tvPaciente.setText(opaciente.getPaciente().toString());
        return convertView;
    }

    private static class PacienteHolder{
        TextView tvidPaciente, tvPaciente;
    }

}
