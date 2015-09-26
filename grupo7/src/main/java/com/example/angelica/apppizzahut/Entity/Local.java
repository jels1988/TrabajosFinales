package com.example.angelica.apppizzahut.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bgeek05 on 15/09/2015.
 */
public class Local implements Parcelable {
    private int idlocal;
    private int idprovincia;
    private String nombre;
    private String latitud;
    private String longitud;
    private String direccion;

    public Local() {
    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public int getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(int idprovincia) {
        this.idprovincia = idprovincia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    protected Local(Parcel in) {
        idlocal = in.readInt();
        idprovincia = in.readInt();
        nombre = in.readString();
        latitud = in.readString();
        longitud = in.readString();
        direccion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idlocal);
        dest.writeInt(idprovincia);
        dest.writeString(nombre);
        dest.writeString(latitud);
        dest.writeString(longitud);
        dest.writeString(direccion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Local> CREATOR = new Parcelable.Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel in) {
            return new Local(in);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };
}