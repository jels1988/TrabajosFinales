package ventas.jandysac.com.ventas.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rodolfo on 09/09/2015.
 */
public class PedidoDetalle implements Parcelable {
    private int id_movimiento_venta;
    private String codigo_cliente;
    private String codigo_producto;
    private String descripcion;
    private double precio;
    private double cantidad;
    private double importe_neto;
    private double importe_total;
    private double items;
    private double stock;

    public PedidoDetalle() {
    }
    public int getId_Movimiento_Venta() {
        return id_movimiento_venta;
    }

    public void setId_Movimiento_Venta(int id_movimiento_venta) {
        this.id_movimiento_venta = id_movimiento_venta;
    }

    public String getCodigo_Cliente() {
        return codigo_cliente;
    }

    public void setCodigo_Cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getCodigo_Producto() {
        return codigo_producto;
    }

    public void setCodigo_Producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte_Neto() {
        return importe_neto;
    }

    public void setImporte_Neto(double importe_neto) {
        this.importe_neto = importe_neto;
    }

    public double getImporte_Total() {
        return importe_total;
    }

    public void setImporte_Total(double importe_total) {
        this.importe_total = importe_total;
    }

    public double getItems() {
        return items;
    }

    public void setItems(double items) {
        this.items = items;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    protected PedidoDetalle(Parcel in) {
        id_movimiento_venta = in.readInt();
        codigo_cliente = in.readString();
        codigo_producto = in.readString();
        descripcion = in.readString();
        precio = in.readDouble();
        cantidad = in.readDouble();
        importe_neto = in.readDouble();
        importe_total = in.readDouble();
        items = in.readDouble();
        stock = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_movimiento_venta);
        dest.writeString(codigo_cliente);
        dest.writeString(codigo_producto);
        dest.writeString(descripcion);
        dest.writeDouble(precio);
        dest.writeDouble(cantidad);
        dest.writeDouble(importe_neto);
        dest.writeDouble(importe_total);
        dest.writeDouble(items);
        dest.writeDouble(stock);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PedidoDetalle> CREATOR = new Parcelable.Creator<PedidoDetalle>() {
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