package grupo4.histoclici.entidad;

import android.os.Parcel;
import android.os.Parcelable;

public class Medico implements Parcelable{

    private int IdMedico;
    private String medico;
    private String usuario;
    private String clave;

    public int getIdMedico() {
        return IdMedico;
    }

    public void setIdMedico(int IdMedico) {
        this.IdMedico = IdMedico;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Medico() {
    }

    public Medico(int IdMedico, String medico, String usuario, String clave) {
        this.IdMedico = IdMedico;
        this.medico = medico;
        this.usuario = usuario;
        this.clave = clave;
    }

    protected Medico(Parcel in) {
        IdMedico = in.readInt();
        medico = in.readString();
        usuario = in.readString();
        clave = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdMedico);
        dest.writeString(medico);
        dest.writeString(usuario);
        dest.writeString(clave);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Medico> CREATOR = new Parcelable.Creator<Medico>() {
        @Override
        public Medico createFromParcel(Parcel in) {
            return new Medico(in);
        }

        @Override
        public Medico[] newArray(int size) {
            return new Medico[size];
        }
    };
}
