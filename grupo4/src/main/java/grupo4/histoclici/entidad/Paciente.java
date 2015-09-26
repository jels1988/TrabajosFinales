package grupo4.histoclici.entidad;

import android.os.Parcel;
import android.os.Parcelable;

public class Paciente implements Parcelable {
    private int idPaciente;
    private String paciente;
    private String genero;
    private String telefono;
    private String celular;
    private String domicilio;
    private String latitud;
    private String altitud;

    public Paciente() {
    }

    public int getidPaciente() {
        return idPaciente;
    }

    public void setidPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public Paciente(int idPaciente, String paciente, String genero, String telefono, String celular, String domicilio, String latitud, String altitud) {
        this.idPaciente = idPaciente;
        this.paciente = paciente;
        this.genero = genero;
        this.telefono = telefono;
        this.celular = celular;
        this.domicilio = domicilio;
        this.latitud = latitud;
        this.altitud = altitud;
    }



    protected Paciente(Parcel in) {
        idPaciente = in.readInt();
        paciente = in.readString();
        genero = in.readString();
        telefono = in.readString();
        celular = in.readString();
        domicilio = in.readString();
        latitud = in.readString();
        altitud = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPaciente);
        dest.writeString(paciente);
        dest.writeString(genero);
        dest.writeString(telefono);
        dest.writeString(celular);
        dest.writeString(domicilio);
        dest.writeString(latitud);
        dest.writeString(altitud);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Paciente> CREATOR = new Parcelable.Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };
}