package com.example.angelica.apppizzahut.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bgeek05 on 17/09/2015.
 */
public class Usuario implements Parcelable {
    private int idusuario;
    private String nombre;
    private String dni;
    private String direccion;
    private String referencia;
    private String usuario;
    private String password;

    public Usuario() {
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected Usuario(Parcel in) {
        idusuario = in.readInt();
        nombre = in.readString();
        dni = in.readString();
        direccion = in.readString();
        referencia = in.readString();
        usuario = in.readString();
        password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idusuario);
        dest.writeString(nombre);
        dest.writeString(dni);
        dest.writeString(direccion);
        dest.writeString(referencia);
        dest.writeString(usuario);
        dest.writeString(password);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
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