package com.example.angelica.apppizzahut.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 19/09/2015.
 */
public class Pedido implements Parcelable {
    private int idpedido;
    private String numeropedido;
    private int idusuario;
    private String telefono;
    private String direccion;
    private String referencia;
    private int idlocal;
    private double total;
    private String estado;
    private String nota;

    public Pedido() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNumeropedido() {
        return numeropedido;
    }

    public void setNumeropedido(String numeropedido) {
        this.numeropedido = numeropedido;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    protected Pedido(Parcel in) {
        idpedido = in.readInt();
        numeropedido = in.readString();
        idusuario = in.readInt();
        telefono = in.readString();
        direccion = in.readString();
        referencia = in.readString();
        idlocal = in.readInt();
        total = in.readDouble();
        estado = in.readString();
        nota = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idpedido);
        dest.writeString(numeropedido);
        dest.writeInt(idusuario);
        dest.writeString(telefono);
        dest.writeString(direccion);
        dest.writeString(referencia);
        dest.writeInt(idlocal);
        dest.writeDouble(total);
        dest.writeString(estado);
        dest.writeString(nota);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pedido> CREATOR = new Parcelable.Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };
}