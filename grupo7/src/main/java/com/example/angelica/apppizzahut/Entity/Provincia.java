package com.example.angelica.apppizzahut.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bgeek05 on 15/09/2015.
 */
public class Provincia implements Parcelable {
    private int idprovincia;
    private String provincia;
    private String distrito;
    private String ubigeo;

    public Provincia() {
    }

    public int getIdprovincia() {
        return idprovincia;
    }

    public void setIdprovincia(int idprovincia) {
        this.idprovincia = idprovincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    protected Provincia(Parcel in) {
        idprovincia = in.readInt();
        provincia = in.readString();
        distrito = in.readString();
        ubigeo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idprovincia);
        dest.writeString(provincia);
        dest.writeString(distrito);
        dest.writeString(ubigeo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Provincia> CREATOR = new Parcelable.Creator<Provincia>() {
        @Override
        public Provincia createFromParcel(Parcel in) {
            return new Provincia(in);
        }

        @Override
        public Provincia[] newArray(int size) {
            return new Provincia[size];
        }
    };
}