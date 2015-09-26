package com.cibertec.app.ferconsapedidos.Entidad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class CondicionPago implements Parcelable {
    private int IdCondicionPago;
    private String CondicionPago;

    public CondicionPago() {

    }

    public int getIdCondicionPago() {
        return IdCondicionPago;
    }

    public void setIdCondicionPago(int idCondicionPago) {
        IdCondicionPago = idCondicionPago;
    }

    public String getCondicionPago() {
        return CondicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        CondicionPago = condicionPago;
    }

    protected CondicionPago(Parcel in) {
        IdCondicionPago = in.readInt();
        CondicionPago = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IdCondicionPago);
        dest.writeString(CondicionPago);
    }

    @SuppressWarnings("unused")
    public static final Creator<CondicionPago> CREATOR = new Creator<CondicionPago>() {
        @Override
        public CondicionPago createFromParcel(Parcel in) {
            return new CondicionPago(in);
        }

        @Override
        public CondicionPago[] newArray(int size) {
            return new CondicionPago[size];
        }
    };
}