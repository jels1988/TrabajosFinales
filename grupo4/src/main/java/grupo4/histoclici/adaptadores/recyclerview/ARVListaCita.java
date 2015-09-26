package grupo4.histoclici.adaptadores.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import grupo4.histoclici.R;
import grupo4.histoclici.dao.CitaDAO;
import grupo4.histoclici.entidad.Cita;

public class ARVListaCita extends RecyclerView.Adapter<ARVListaCita.ARVListaCitaViewHolder>{

    private ArrayList<Cita> alCita;
    private ARVListaCitaListener iARVListaCitaListener;

    public interface ARVListaCitaListener{
        void ieliminarCita(Cita cita, int position);
        void ilistarAtencionesXPaciente(int IdPaciente, String Paciente);
    }

    //Método constructor
    public ARVListaCita(ARVListaCitaListener parametroARVListaCitaListener, String fechaRecibida){
        iARVListaCitaListener = parametroARVListaCitaListener;
        listarCitasXFecha(fechaRecibida);
    }

    //Patron Holder
    static class ARVListaCitaViewHolder extends  RecyclerView.ViewHolder{
        TextView itvrvIdCita, itvrvIdPacienteRV, itvrvPacienteRV, itvrvFechaCita, itvrvHoraInicio, itvrvHoraFin;
        CheckBox icbrvPregunta;

        public ARVListaCitaViewHolder(View itemView) {
            super(itemView);

            itvrvIdCita = (TextView)itemView.findViewById(R.id.itvrvIdCita);
            itvrvIdPacienteRV = (TextView)itemView.findViewById(R.id.itvrvIdPacienteRV);
            itvrvPacienteRV = (TextView)itemView.findViewById(R.id.itvrvPacienteRV);
            itvrvFechaCita = (TextView)itemView.findViewById(R.id.itvrvFechaCita);
            itvrvHoraInicio = (TextView)itemView.findViewById(R.id.itvrvHoraInicio);
            itvrvHoraFin = (TextView)itemView.findViewById(R.id.itvrvHoraFin);
            icbrvPregunta = (CheckBox)itemView.findViewById(R.id.icbrvPregunta);
        }
    }

    //Inflamos la vista
    @Override
    public ARVListaCitaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ARVListaCitaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_cita, parent, false));
    }

    //Llenamos datos de cada cita en una fecha
    @Override
    public void onBindViewHolder(ARVListaCitaViewHolder holder, int position) {
        Cita ocita = alCita.get(position);

        holder.itvrvIdCita.setText(String.valueOf(ocita.getIdCita()));
        holder.itvrvIdPacienteRV.setText(String.valueOf(ocita.getIdPaciente()));
        holder.itvrvPacienteRV.setText(ocita.getPaciente());
        holder.itvrvFechaCita.setText(ocita.getFechaCita());
        holder.itvrvHoraInicio.setText(ocita.getInicio());
        holder.itvrvHoraFin.setText(ocita.getFin());
        if(ocita.getPregunta().equals("N"))
            holder.icbrvPregunta.setChecked(false);
        else
            holder.icbrvPregunta.setChecked(true);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(itemViewOnClickListener); //listener para ver la historia clínica del paciente
        holder.itemView.setOnLongClickListener(itemViewOnLongListener); //listener para eliminar cita
    }

    @Override
    public int getItemCount() {
        return alCita.size();
    }

    //Para que ActivityListaCita en la implementación muestre la pantalla: Lista de atenciones al hacer click en alguna cita
    View.OnClickListener itemViewOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(iARVListaCitaListener != null)
                iARVListaCitaListener.ilistarAtencionesXPaciente(alCita.get((int)v.getTag()).getIdPaciente(), alCita.get((int)v.getTag()).getPaciente());
        }
    };

    //Para que ActivityListaCita en la implementación elimine la cita sobre la que se haga LongClick
    View.OnLongClickListener itemViewOnLongListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            if(iARVListaCitaListener != null)
                iARVListaCitaListener.ieliminarCita(alCita.get((int)v.getTag()), (int)v.getTag());
            return false;
        }
    };


    public void listarCitasXFecha(String fechaRecibida){
        alCita = new ArrayList<>();
        alCita.addAll(new CitaDAO().listarCitarXFecha(fechaRecibida));
        notifyDataSetChanged();
    }

    public  void eliminarCita(int IdCita, int position, String fecha){
        new CitaDAO().eliminarCita(IdCita);
        notifyItemRemoved(position);
        listarCitasXFecha(fecha);
    }





}
