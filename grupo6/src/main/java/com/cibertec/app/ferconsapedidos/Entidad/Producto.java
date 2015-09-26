package com.cibertec.app.ferconsapedidos.Entidad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class Producto extends PedidoDetalle implements Parcelable {
    private int IdProducto;
    private String CodigoProducto;
    private String DescripcionProducto;
    private Double Precio;
    private String Unidad;

    public Producto() {

    }
    @Override
    public String toString() {
        return   CodigoProducto + " " + DescripcionProducto ;

    }
    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public String getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        CodigoProducto = codigoProducto;
    }

    public String getDescripcionProducto() {
        return DescripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        DescripcionProducto = descripcionProducto;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    protected Producto(Parcel in) {
        IdProducto = in.readInt();
        CodigoProducto = in.readString();
        DescripcionProducto = in.readString();
        Precio = in.readByte() == 0x00 ? null : in.readDouble();
        Unidad = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdProducto);
        dest.writeString(CodigoProducto);
        dest.writeString(DescripcionProducto);
        if (Precio == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(Precio);
        }
        dest.writeString(Unidad);
    }

    @SuppressWarnings("unused")
    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
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