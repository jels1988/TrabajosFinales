package com.example.angelica.apppizzahut.Entity;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Angelica on 11/09/2015.
 */

public class Producto implements Parcelable {

    private int idproducto;
    private String nombre;
    private double precio;
    private String descripcion;
    private String imagen;

    public Producto() {
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    protected Producto(Parcel in) {
        idproducto = in.readInt();
        nombre = in.readString();
        precio = in.readDouble();
        descripcion = in.readString();
        imagen = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idproducto);
        dest.writeString(nombre);
        dest.writeDouble(precio);
        dest.writeString(descripcion);
        dest.writeString(imagen);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };
}