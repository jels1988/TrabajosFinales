package ventas.jandysac.com.ventas.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GaryV on 24/09/2015.
 */

public class ConsolidarPedido implements Parcelable {
    private  int idPedido;
    private String nombre;
    private int items;
    private double total;
    private  int estado;
    private boolean isSelected;


    public ConsolidarPedido() {
    }

    public ConsolidarPedido(int idPedido, String nombre, int items, double total, int estado, boolean isSelected) {
        this.idPedido = idPedido;
        this.nombre = nombre;
        this.items = items;
        this.total = total;
        this.estado = estado;
        this.isSelected = isSelected;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    protected ConsolidarPedido(Parcel in) {
        idPedido = in.readInt();
        nombre = in.readString();
        items = in.readInt();
        total = in.readDouble();
        estado = in.readInt();
        isSelected = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPedido);
        dest.writeString(nombre);
        dest.writeInt(items);
        dest.writeDouble(total);
        dest.writeInt(estado);
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ConsolidarPedido> CREATOR = new Parcelable.Creator<ConsolidarPedido>() {
        @Override
        public ConsolidarPedido createFromParcel(Parcel in) {
            return new ConsolidarPedido(in);
        }

        @Override
        public ConsolidarPedido[] newArray(int size) {
            return new ConsolidarPedido[size];
        }
    };
}