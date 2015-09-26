package ventas.jandysac.com.ventas.dao;

import android.database.Cursor;

import ventas.jandysac.com.ventas.entities.Parametro;

/**
 * Created by JoseKoji on 16/09/2015.
 */
public class ParametroDAO {
    public Parametro findPametro(String nombre) {
        Parametro parametro = new Parametro();
        Cursor cursor = null;
        String[] args = new String[]{nombre};

        try {
            cursor = DataBaseHelper.myDataBase.query("Parametro", null, "nombre=?", args, null, null, null);

            if (cursor.moveToFirst()) {
                parametro.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
                parametro.setValor(cursor.isNull(cursor.getColumnIndex("valor")) ? "" : cursor.getString(cursor.getColumnIndex("valor")));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return parametro;
    }
}
