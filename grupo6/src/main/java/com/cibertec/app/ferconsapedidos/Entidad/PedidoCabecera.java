package com.cibertec.app.ferconsapedidos.Entidad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class PedidoCabecera implements Parcelable {
    private int IdPedidoCabecera;
    private int IdCondicionPago;
    private int IdCliente;
    private String FechaPedido;
    private int IdUsuario;
    private String NombreCliente;
    private String CondicionPago;
    private String Ruc;



    public String getNombreCliente() { return NombreCliente; }
    public void setNombreCliente(String nombreCliente) {
        NombreCliente = nombreCliente;
    }
    public String getCondicionPago() {
        return CondicionPago;
    }
    public void setCondicionPago(String condicionPago) {
        CondicionPago = condicionPago;
    }
    public int getIdUsuario() {
        return IdUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public PedidoCabecera() {

    }

    @Override
    public String toString() {
        return  NombreCliente + " " +String.valueOf(IdPedidoCabecera) ;

    }
    public int getIdPedidoCabecera() {
        return IdPedidoCabecera;
    }

    public void setIdPedidoCabecera(int idPedidoCabecera) {
        IdPedidoCabecera = idPedidoCabecera;
    }

    public int getIdCondicionDePago() {
        return IdCondicionPago;
    }

    public void setIdCondicionDePago(int idCondicionDePago) {
        IdCondicionPago = idCondicionDePago;
    }

    public int getIdCliente() {
        return IdCliente;
    }

    public void setIdCliente(int idCliente) {
        IdCliente = idCliente;
    }

    public String getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        FechaPedido = fechaPedido;
    }
    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String ruc) {
        Ruc = ruc;
    }

    protected PedidoCabecera(Parcel in) {
        IdPedidoCabecera = in.readInt();
        IdCondicionPago = in.readInt();
        IdCliente = in.readInt();
        FechaPedido = in.readString();
        NombreCliente = in.readString();
        CondicionPago = in.readString();
        Ruc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdPedidoCabecera);
        dest.writeInt(IdCondicionPago);
        dest.writeInt(IdCliente);
        dest.writeString(FechaPedido);
        dest.writeString(NombreCliente);
        dest.writeString(CondicionPago);
        dest.writeString(Ruc);
    }

    @SuppressWarnings("unused")
    public static final Creator<PedidoCabecera> CREATOR = new Creator<PedidoCabecera>() {
        @Override
        public PedidoCabecera createFromParcel(Parcel in) {
            return new PedidoCabecera(in);
        }

        @Override
        public PedidoCabecera[] newArray(int size) {
            return new PedidoCabecera[size];
        }
    };
}