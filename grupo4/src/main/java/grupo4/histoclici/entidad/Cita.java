package grupo4.histoclici.entidad;

import android.os.Parcel;
import android.os.Parcelable;

public class Cita implements Parcelable {

    private int IdCita;
    private int IdPaciente;
    private String Paciente;
    private String fechaCita;
    private String inicio;
    private String fin;
    private String pregunta;

    public int getIdCita() {
        return IdCita;
    }

    public void setIdCita(int idCita) {
        IdCita = idCita;
    }

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        IdPaciente = idPaciente;
    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        Paciente = paciente;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Cita() {
    }

    public Cita(int idCita, int idPaciente, String paciente, String fechaCita, String inicio, String fin, String pregunta) {
        this.IdCita = idCita;
        this.IdPaciente = idPaciente;
        this.Paciente = paciente;
        this.fechaCita = fechaCita;
        this.inicio = inicio;
        this.fin = fin;
        this.pregunta = pregunta;
    }

    protected Cita(Parcel in) {
        IdCita = in.readInt();
        IdPaciente = in.readInt();
        Paciente = in.readString();
        fechaCita = in.readString();
        inicio = in.readString();
        fin = in.readString();
        pregunta = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdCita);
        dest.writeInt(IdPaciente);
        dest.writeString(Paciente);
        dest.writeString(fechaCita);
        dest.writeString(inicio);
        dest.writeString(fin);
        dest.writeString(pregunta);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cita> CREATOR = new Parcelable.Creator<Cita>() {
        @Override
        public Cita createFromParcel(Parcel in) {
            return new Cita(in);
        }

        @Override
        public Cita[] newArray(int size) {
            return new Cita[size];
        }
    };
}
