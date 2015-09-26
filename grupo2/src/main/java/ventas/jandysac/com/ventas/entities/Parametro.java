package ventas.jandysac.com.ventas.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JoseKoji on 16/09/2015.
 */
public class Parametro implements Parcelable {
    private String nombre;
    private String valor;

    public Parametro() {
    }

    public Parametro(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    protected Parametro(Parcel in) {
        nombre = in.readString();
        valor = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(valor);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Parametro> CREATOR = new Parcelable.Creator<Parametro>() {
        @Override
        public Parametro createFromParcel(Parcel in) {
            return new Parametro(in);
        }

        @Override
        public Parametro[] newArray(int size) {
            return new Parametro[size];
        }
    };
}
