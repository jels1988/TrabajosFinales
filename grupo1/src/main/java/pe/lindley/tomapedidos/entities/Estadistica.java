package pe.lindley.tomapedidos.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MTancun on 22/09/2015.
 */
public class Estadistica implements Parcelable {
    private int totalCliente;
    private int totalconPedido;
    private int totalsinPedido;
    private double cumplimientoplandeVisita;

    protected Estadistica(Parcel in) {
        totalCliente = in.readInt();
        totalconPedido = in.readInt();
        totalsinPedido = in.readInt();
        cumplimientoplandeVisita = in.readDouble();
    }

    public Estadistica() {
    }

    public int getTotalCliente() {
        return totalCliente;
    }

    public void setTotalCliente(int totalCliente) {
        this.totalCliente = totalCliente;
    }

    public int getTotalconPedido() {
        return totalconPedido;
    }

    public void setTotalconPedido(int totalconPedido) {
        this.totalconPedido = totalconPedido;
    }

    public int getTotalsinPedido() {
        return totalsinPedido;
    }

    public void setTotalsinPedido(int totalsinPedido) {
        this.totalsinPedido = totalsinPedido;
    }

    public double getCumplimientoplandeVisita() {
        return cumplimientoplandeVisita;
    }

    public void setCumplimientoplandeVisita(double cumplimientoplandeVisita) {
        this.cumplimientoplandeVisita = cumplimientoplandeVisita;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalCliente);
        dest.writeInt(totalconPedido);
        dest.writeInt(totalsinPedido);
        dest.writeDouble(cumplimientoplandeVisita);
    }

    @SuppressWarnings("unused")
    public static final Creator<Estadistica> CREATOR = new Creator<Estadistica>() {
        @Override
        public Estadistica createFromParcel(Parcel in) {
            return new Estadistica(in);
        }

        @Override
        public Estadistica[] newArray(int size) {
            return new Estadistica[size];
        }
    };
}