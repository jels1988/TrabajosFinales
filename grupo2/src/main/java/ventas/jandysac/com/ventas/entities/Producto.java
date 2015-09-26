package ventas.jandysac.com.ventas.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rodolfo on 09/09/2015.
 */
public class Producto implements Parcelable {
    private String codigo;
    private String descripcion;
    private String unidad_medida_venta;
    private double precio;
    private double stock;

    public Producto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidad_Medida_Venta() {
        return unidad_medida_venta;
    }

    public void setUnidad_Medida_Venta(String unidad_medida_venta) {
        this.unidad_medida_venta = unidad_medida_venta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    protected Producto(Parcel in) {
        codigo = in.readString();
        descripcion = in.readString();
        unidad_medida_venta = in.readString();
        precio = in.readDouble();
        stock = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo);
        dest.writeString(descripcion);
        dest.writeString(unidad_medida_venta);
        dest.writeDouble(precio);
        dest.writeDouble(stock);
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