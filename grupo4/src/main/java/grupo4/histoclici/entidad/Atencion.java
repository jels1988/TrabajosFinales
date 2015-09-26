package grupo4.histoclici.entidad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pedro_jx on 18/09/2015.
 */
public class Atencion implements Parcelable {

    private int IdAtencion;
    private int IdPaciente;
    private String fechaAtencion;
    private String motivo;
    private String tratamiento;

    public int getIdAtencion() {
        return IdAtencion;
    }

    public void setIdAtencion(int idAtencion) {
        this.IdAtencion = idAtencion;
    }

    public int getIdPaciente() {
        return IdPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        IdPaciente = idPaciente;
    }

    public String getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(String fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Atencion() {
    }

    public Atencion(int idAtencion, int idPaciente, String fechaAtencion, String motivo, String tratamiento) {
        this.IdAtencion = idAtencion;
        this.IdPaciente = idPaciente;
        this.fechaAtencion = fechaAtencion;
        this.motivo = motivo;
        this.tratamiento = tratamiento;
    }

    protected Atencion(Parcel in) {
        IdAtencion = in.readInt();
        IdPaciente = in.readInt();
        fechaAtencion = in.readString();
        motivo = in.readString();
        tratamiento = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdAtencion);
        dest.writeInt(IdPaciente);
        dest.writeString(fechaAtencion);
        dest.writeString(motivo);
        dest.writeString(tratamiento);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Atencion> CREATOR = new Parcelable.Creator<Atencion>() {
        @Override
        public Atencion createFromParcel(Parcel in) {
            return new Atencion(in);
        }

        @Override
        public Atencion[] newArray(int size) {
            return new Atencion[size];
        }
    };
}

