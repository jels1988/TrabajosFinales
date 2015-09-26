package pe.lindley.tomapedidos.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jlama on 22/09/2015.
 */
public class Producto implements Parcelable {
    private int productoId;
    private String productoNombre;
    private String productoUnidadMedida;
    private int productoSubUnidad;

    public Producto() {
    }

    public Producto(int productoId, String productoNombre, String productoUnidadMedida, int productoSubUnidad) {
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.productoUnidadMedida = productoUnidadMedida;
        this.productoSubUnidad = productoSubUnidad;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getProductoUnidadMedida() {
        return productoUnidadMedida;
    }

    public void setProductoUnidadMedida(String productoUnidadMedida) {
        this.productoUnidadMedida = productoUnidadMedida;
    }

    public int getProductoSubUnidad() {
        return productoSubUnidad;
    }

    public void setProductoSubUnidad(int productoSubUnidad) {
        this.productoSubUnidad = productoSubUnidad;
    }

    protected Producto(Parcel in) {
        productoId = in.readInt();
        productoNombre = in.readString();
        productoUnidadMedida = in.readString();
        productoSubUnidad = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productoId);
        dest.writeString(productoNombre);
        dest.writeString(productoUnidadMedida);
        dest.writeInt(productoSubUnidad);
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

    @Override
    public boolean equals(Object o) {
        if( o != null && o instanceof Producto ) {
            return getProductoId() == ((Producto)o).getProductoId();
        } else {
            return false;
        }
    }
}
