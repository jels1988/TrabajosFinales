package com.cibertec.app.ferconsapedidos.Entidad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class Usuario implements Parcelable {

    private int IdUsuario;
    private String NombreUsuario;
    private String Usuario;
    private String Clave;

    public Usuario(int idUsuario, String nombreUsuario, String usuario, String clave) {
        IdUsuario = idUsuario;
        NombreUsuario = nombreUsuario;
        Usuario = usuario;
        Clave = clave;
    }


    public Usuario() {

    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    protected Usuario(Parcel in) {
        IdUsuario = in.readInt();
        NombreUsuario = in.readString();
        Usuario = in.readString();
        Clave = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdUsuario);
        dest.writeString(NombreUsuario);
        dest.writeString(Usuario);
        dest.writeString(Clave);
    }

    @SuppressWarnings("unused")
    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}