package pe.lindley.tomapedidos.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jlama on 22/09/2015.
 */
public class Pedido implements Parcelable {

    private Cliente cliente;
    private Producto producto;
    private int cantidad;

    public Pedido() {
    }

    protected Pedido(Parcel in) {
        cliente = (Cliente) in.readValue(Cliente.class.getClassLoader());
        producto = (Producto) in.readValue(Producto.class.getClassLoader());
        cantidad = in.readInt();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cliente);
        dest.writeValue(producto);
        dest.writeInt(cantidad);
    }

    @SuppressWarnings("unused")
    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if( o != null && o instanceof Pedido ) {
            Pedido pedido = (Pedido)o;
            return getProducto().getProductoId() == pedido.getProducto().getProductoId()
                    && getCliente().getClienteId() == pedido.getCliente().getClienteId();
        } else {
            return false;
        }
    }
}
