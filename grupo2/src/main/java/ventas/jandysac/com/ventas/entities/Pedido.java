package ventas.jandysac.com.ventas.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rodolfo on 09/09/2015.
 */
public class Pedido implements Parcelable {
    private String codigo_empresa;
    private String codigo_vendedor;
    private double precio;
    private double cantidad;

    public Pedido() {
    }

    public String getCodigo_Empresa() {
        return codigo_empresa;
    }

    public void setCodigo_Empresa(String codigo_empresa) {
        this.codigo_empresa = codigo_empresa;
    }

    public String getCodigo_Vendedor() {
        return codigo_vendedor;
    }

    public void setCodigo_Vendedor(String codigo_vendedor) {
        this.codigo_vendedor = codigo_vendedor;
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

    protected Pedido(Parcel in) {
        codigo_empresa = in.readString();
        codigo_vendedor = in.readString();
        precio = in.readDouble();
        cantidad = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo_empresa);
        dest.writeString(codigo_vendedor);
        dest.writeDouble(precio);
        dest.writeDouble(cantidad);
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