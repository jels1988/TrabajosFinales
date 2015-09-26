package com.cibertec.app.ferconsapedidos.Entidad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class PedidoDetalle implements Parcelable {
    private int IdPedidoDetalle	;
    private int IdPedidoCabecera;
    private int  IdProducto;
    private String CodigoProducto;
    private String DescripcionProducto;
    private String Unidad;
    private  Double Cantidad;
    private  Double Precio;
    private  Double Subtotal;



    //public PedidoDetalle(int idPedidoDetalle, int idPedidoCabecera, int idProducto,String CodigoProduccto ,String descripcionProducto, String unidad, Double cantidad) {
    public PedidoDetalle() {
        //IdPedidoDetalle = idPedidoDetalle;
        //IdPedidoCabecera = idPedidoCabecera;
        //IdProducto = idProducto;
        //CodigoProducto=CodigoProduccto;
        //DescripcionProducto = descripcionProducto;
        //Unidad = unidad;
        //Cantidad = cantidad;
    }
    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }
    public String getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        CodigoProducto = codigoProducto;
    }
    public int getIdPedidoDetalle() {
        return IdPedidoDetalle;
    }

    public void setIdPedidoDetalle(int idPedidoDetalle) {
        IdPedidoDetalle = idPedidoDetalle;
    }

    public Double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Double cantidad) {
        Cantidad = cantidad;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getDescripcionProducto() {
        return DescripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        DescripcionProducto = descripcionProducto;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public int getIdPedidoCabecera() {
        return IdPedidoCabecera;
    }

    public void setIdPedidoCabecera(int idPedidoPedidoCabecera) {
        IdPedidoCabecera = idPedidoPedidoCabecera;
    }
    public Double getSubtotal() {
        return Cantidad*Precio;
    }

    public void setSubtotal(Double subtotal) {
        Subtotal = subtotal;
    }
    protected PedidoDetalle(Parcel in) {
        IdPedidoDetalle = in.readInt();
        IdPedidoCabecera = in.readInt();
        IdProducto = in.readInt();
        CodigoProducto=in.readString();
        DescripcionProducto = in.readString();
        Unidad = in.readString();
        Cantidad = in.readByte() == 0x00 ? null : in.readDouble();
        Precio = in.readByte() == 0x00 ? null : in.readDouble();
        Subtotal= in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdPedidoDetalle);
        dest.writeInt(IdPedidoCabecera);
        dest.writeInt(IdProducto);
        dest.writeString(CodigoProducto);
        dest.writeString(DescripcionProducto);
        dest.writeString(Unidad);
        if (Cantidad == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(Cantidad);
        }
        if (Precio == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(Precio);
        }
        if (Subtotal == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(Subtotal);
        }

    }

    @SuppressWarnings("unused")
    public static final Creator<PedidoDetalle> CREATOR = new Creator<PedidoDetalle>() {
        @Override
        public PedidoDetalle createFromParcel(Parcel in) {
            return new PedidoDetalle(in);
        }

        @Override
        public PedidoDetalle[] newArray(int size) {
            return new PedidoDetalle[size];
        }
    };
}