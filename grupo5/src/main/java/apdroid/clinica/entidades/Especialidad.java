package apdroid.clinica.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ANTONIO on 08/09/2015.
 */
public class Especialidad implements Parcelable {
    private int idEspecialidad;
    private String nombre;

    public Especialidad() {
    }

    public Especialidad(int idEspecialidad, String nombre) {
        this.idEspecialidad = idEspecialidad;
        this.nombre = nombre;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected Especialidad(Parcel in) {
        idEspecialidad = in.readInt();
        nombre = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idEspecialidad);
        dest.writeString(nombre);
    }

    @SuppressWarnings("unused")
    public static final Creator<Especialidad> CREATOR = new Creator<Especialidad>() {
        @Override
        public Especialidad createFromParcel(Parcel in) {
            return new Especialidad(in);
        }

        @Override
        public Especialidad[] newArray(int size) {
            return new Especialidad[size];
        }
    };
}