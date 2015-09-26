package grupo4.histoclici.adaptadores.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import grupo4.histoclici.R;
import grupo4.histoclici.dao.PacienteDAO;
import grupo4.histoclici.entidad.Paciente;

public class ARVListaPaciente extends RecyclerView.Adapter<ARVListaPaciente.ARVListaPacienteHolder> implements Filterable {

    private String filtro = "";
    private ArrayList<Paciente> alPaciente, alPacienteFiltro;
    private ARVListaPacienteFilter arvListaPacienteFilter;
    ARVListaPacienteListener iARVListaPacienteListener;

    @Override
    public Filter getFilter() {
        if (arvListaPacienteFilter == null)
            arvListaPacienteFilter = new ARVListaPacienteFilter();
        return arvListaPacienteFilter;
    }

    public interface ARVListaPacienteListener{
        void ieditarPaciente(Paciente paciente);
        void imostrarmenu(Paciente paciente);
    }

    public ARVListaPaciente(ARVListaPacienteListener parametroARVListaCitaListener) {
        iARVListaPacienteListener = parametroARVListaCitaListener;
        listarPaciente();
    }

    static class ARVListaPacienteHolder extends RecyclerView.ViewHolder {
        TextView itvIdPaciente, itvPaciente, itvGenero,itvTelefono, itvCelular, itvDomicilio, itvLatitud, itvAltitud;

        public ARVListaPacienteHolder(View itemView) {
            super(itemView);
            itvIdPaciente = (TextView)itemView.findViewById(R.id.itvIdPaciente);
            itvPaciente = (TextView)itemView.findViewById(R.id.itvPaciente);
            itvGenero = (TextView)itemView.findViewById(R.id.itvGenero);
            itvTelefono = (TextView)itemView.findViewById(R.id.itvTelefono);
            itvCelular = (TextView)itemView.findViewById(R.id.itvCelular);
            itvDomicilio = (TextView)itemView.findViewById(R.id.itvDomicilio);
            itvLatitud = (TextView)itemView.findViewById(R.id.itvLatitud);
            itvAltitud = (TextView)itemView.findViewById(R.id.itvAltitud);
        }
    }

    @Override
    public ARVListaPacienteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ARVListaPacienteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_paciente, parent, false));
    }

    @Override
    public void onBindViewHolder(ARVListaPacienteHolder holder, int position) {
        //Paciente paciente = alPaciente.get(position);
        Paciente paciente = alPacienteFiltro.get(position);

        holder.itvIdPaciente.setText(String.valueOf(paciente.getidPaciente()));
        holder.itvPaciente.setText(paciente.getPaciente());
        holder.itvGenero.setText(paciente.getGenero());
        holder.itvTelefono.setText(paciente.getTelefono());
        holder.itvCelular.setText(paciente.getCelular());
        holder.itvDomicilio.setText(paciente.getDomicilio());
        holder.itvLatitud.setText(paciente.getLatitud());
        holder.itvAltitud.setText(paciente.getAltitud());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(editarPaciente);
        holder.itemView.setOnLongClickListener(itemPaciente );
    }

    @Override
    public int getItemCount() {
        //return alPaciente.size();
        return alPacienteFiltro.size();
    }

    View.OnLongClickListener itemPaciente = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if(iARVListaPacienteListener != null)
                //iARVListaPacienteListener.imostrarmenu(alPaciente.get((int)v.getTag()));
                iARVListaPacienteListener.imostrarmenu(alPacienteFiltro.get((int)v.getTag()));
            return false;
        }
    };

    View.OnClickListener editarPaciente = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(iARVListaPacienteListener != null)
                //iARVListaPacienteListener.ieditarPaciente(alPaciente.get((int)v.getTag()));
                iARVListaPacienteListener.ieditarPaciente(alPacienteFiltro.get((int)v.getTag()));
        }
    };

    public void listarPaciente(){
        alPaciente = new ArrayList<>();
        alPacienteFiltro = new ArrayList<>();
        alPaciente.addAll(new PacienteDAO().listarPacientes());
        alPacienteFiltro.addAll(alPaciente);
        notifyDataSetChanged();
    }

    class ARVListaPacienteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filtro = charSequence == null ? "" : charSequence.toString();
            FilterResults filterResults = new FilterResults();

            if (filtro.isEmpty()) {
                filterResults.values = alPaciente;
                filterResults.count = alPaciente.size();
            } else {
                String[] sFilters = filtro.toUpperCase().split(" ");
                boolean isContains = false;

                ArrayList<Paciente> alPacienteNueva = new ArrayList<>();

                for (int i = 0; i < alPaciente.size(); i++) {
                    isContains = false;
                    Paciente paciente = alPaciente.get(i);

                    for (int j = 0; j < sFilters.length; j++) {
                        if (paciente.getPaciente().toUpperCase().contains(sFilters[j])) {
                            isContains = true;
                            break;
                        }
                    }

                    if (isContains)
                        alPacienteNueva.add(paciente);
                }

                filterResults.values = alPacienteNueva;
                filterResults.count = alPacienteNueva.size();
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null) {
                alPacienteFiltro.clear();
                alPacienteFiltro.addAll((ArrayList<Paciente>) filterResults.values);
                notifyDataSetChanged();
            }
        }
    }

}
