package pe.lindley.tomapedidos.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jlama on 19/09/2015.
 */
public class Cliente implements Parcelable {
    private int clienteId;
    private String clienteRazonSocial;
    //NIT: Número de indentificador tritutario
    private String clienteNIT;
    private String clienteGiroNegocio;
    private String clienteDireccion;
    // para las coordenadas
    private String clienteLatitud;
    private String clienteLongitud;

    public Cliente() {
    }

    public Cliente(int clienteId, String clienteRazonSocial, String clienteDireccion, String clienteGiroNegocio, String clienteNIT, String clienteLatitud, String clienteLongitud) {
        this.clienteId = clienteId;
        this.clienteRazonSocial = clienteRazonSocial;
        this.clienteNIT = clienteNIT;
        this.clienteGiroNegocio = clienteGiroNegocio;
        this.clienteDireccion = clienteDireccion;
        this.clienteLatitud = clienteLatitud;
        this.clienteLongitud = clienteLongitud;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteRazonSocial() {
        return clienteRazonSocial;
    }

    public void setClienteRazonSocial(String clienteRazonSocial) {
        this.clienteRazonSocial = clienteRazonSocial;
    }

    public String getClienteNIT() {
        return clienteNIT;
    }

    public void setClienteNIT(String clienteNIT) {
        this.clienteNIT = clienteNIT;
    }

    public String getClienteLatitud() {
        return clienteLatitud;
    }

    public void setClienteLatitud(String clienteLatitud) {
        this.clienteLatitud = clienteLatitud;
    }

    public String getClienteLongitud() {
        return clienteLongitud;
    }

    public void setClienteLongitud(String clienteLongitud) {
        this.clienteLongitud = clienteLongitud;
    }

    protected Cliente(Parcel in) {
        clienteId = in.readInt();
        clienteRazonSocial = in.readString();
        clienteNIT = in.readString();
        clienteDireccion = in.readString();
        clienteGiroNegocio = in.readString();
        clienteLatitud = in.readString();
        clienteLongitud = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(clienteId);
        dest.writeString(clienteRazonSocial);
        dest.writeString(clienteNIT);
        dest.writeString(clienteDireccion);
        dest.writeString(clienteGiroNegocio);
        dest.writeString(clienteLatitud);
        dest.writeString(clienteLongitud);
    }

    @SuppressWarnings("unused")
    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public String getClienteGiroNegocio() {
        return clienteGiroNegocio;
    }

    public void setClienteGiroNegocio(String clienteGiroNegocio) {
        this.clienteGiroNegocio = clienteGiroNegocio;
    }

    public String getClienteDireccion() {
        return clienteDireccion;
    }

    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }

    @Override
    public boolean equals(Object o) {
        if( o != null && o instanceof Cliente ) {
            return getClienteId() == ((Cliente)o).getClienteId();
        } else
        {
            return false;
        }
    }
}
