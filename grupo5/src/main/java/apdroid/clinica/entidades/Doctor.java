package apdroid.clinica.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AngeloPaulo on 24/septiembre/2015.
 */
public class Doctor implements Parcelable {
    private int iddoc;
    private String nombre;
    private String apellido;
    private int idespec;
    private String horario;
    private int idlocal;

    public Doctor() {
    }

    public int getIddoc() {
        return iddoc;
    }

    public void setIddoc(int iddoc) {
        this.iddoc = iddoc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIdespec() {
        return idespec;
    }

    public void setIdespec(int idespec) {
        this.idespec = idespec;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    protected Doctor(Parcel in) {
        iddoc = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        idespec = in.readInt();
        horario = in.readString();
        idlocal = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iddoc);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeInt(idespec);
        dest.writeString(horario);
        dest.writeInt(idlocal);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}
