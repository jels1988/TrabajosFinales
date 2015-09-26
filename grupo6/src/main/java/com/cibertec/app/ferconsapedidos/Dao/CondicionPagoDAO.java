package com.cibertec.app.ferconsapedidos.Dao;

import android.database.Cursor;

import com.cibertec.app.ferconsapedidos.Entidad.CondicionPago;

import java.util.ArrayList;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class CondicionPagoDAO {
    public ArrayList<CondicionPago> listCondicionPago() {
        ArrayList<CondicionPago> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("CondicionPago", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    CondicionPago condicionPago = new CondicionPago();
                    condicionPago.setIdCondicionPago(cursor.isNull(cursor.getColumnIndex("IdCondicionPago")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCondicionPago")));
                    condicionPago.setCondicionPago(cursor.isNull(cursor.getColumnIndex("DescripcionCondicionPago")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionCondicionPago")));

                    lstPersona.add(condicionPago);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstPersona;
    }
}
