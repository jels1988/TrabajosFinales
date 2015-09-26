package grupo4.histoclici.adaptadores.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import grupo4.histoclici.R;
import grupo4.histoclici.dao.AtencionDAO;
import grupo4.histoclici.entidad.Atencion;

/**
 * Created by pedro_jx on 18/09/2015.
 */
public class ARVHistoriaClinica extends RecyclerView.Adapter<ARVHistoriaClinica.ARVHistoriaClinicaHolder> {

    public ArrayList<Atencion> alAtencion;

    public ARVHistoriaClinica(int IdPaciente) {
        listarAtencionXPaciente(IdPaciente);
    }

    static class ARVHistoriaClinicaHolder extends RecyclerView.ViewHolder{
        TextView itvIdAtencion, itvFechaAtencion, itvMotivo, itvTratamiento;
            public ARVHistoriaClinicaHolder(View itemView) {
                super(itemView);
                itvIdAtencion = (TextView)itemView.findViewById(R.id.itvIdAtencion);
                itvFechaAtencion = (TextView)itemView.findViewById(R.id.itvFechaAtencion);
                itvMotivo = (TextView)itemView.findViewById(R.id.itvMotivo);
                itvTratamiento = (TextView)itemView.findViewById(R.id.itvTratamiento);
            }
    }

    @Override
    public ARVHistoriaClinica.ARVHistoriaClinicaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ARVHistoriaClinicaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_historia_clinica, parent, false));
    }

    @Override
    public void onBindViewHolder(ARVHistoriaClinica.ARVHistoriaClinicaHolder holder, int position) {
        Atencion atencion = alAtencion.get(position);
        holder.itvIdAtencion.setText(String.valueOf(atencion.getIdAtencion()));
        holder.itvFechaAtencion.setText(atencion.getFechaAtencion());
        holder.itvMotivo.setText(atencion.getMotivo());
        holder.itvTratamiento.setText(atencion.getTratamiento());
        holder.itemView.setTag(position);
        //setear OnClickListener o OnLongListener
    }

    @Override
    public int getItemCount() {
        return alAtencion.size();
    }

    private void listarAtencionXPaciente(int IdPaciente){
        alAtencion = new ArrayList<>();
        alAtencion.addAll(new AtencionDAO().listarAtencionXPaciente(IdPaciente));
        notifyDataSetChanged();
    }

}
