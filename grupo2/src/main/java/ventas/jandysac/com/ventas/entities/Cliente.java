package ventas.jandysac.com.ventas.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rodolfo on 09/09/2015.
 */
public class Cliente implements Parcelable {
    private int clienteID;
    private String codigo;
    private String apellido_paterno;
    private String apellido_materno;
    private String nombres;
    private String nombre_completo;
    private String direccion;
    private String tipo_doc;
    private String coodenadas;

    public Cliente() {
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getCoodenadas() {
        return coodenadas;
    }

    public void setCoodenadas(String coodenadas) {
        this.coodenadas = coodenadas;
    }



    protected Cliente(Parcel in) {
        clienteID = in.readInt();
        codigo = in.readString();
        apellido_paterno = in.readString();
        apellido_materno = in.readString();
        nombres = in.readString();
        nombre_completo = in.readString();
        direccion = in.readString();
        tipo_doc = in.readString();
        coodenadas = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clienteID);
        dest.writeString(codigo);
        dest.writeString(apellido_paterno);
        dest.writeString(apellido_materno);
        dest.writeString(nombres);
        dest.writeString(nombre_completo);
        dest.writeString(direccion);
        dest.writeString(tipo_doc);
        dest.writeString(coodenadas);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}