package com.example.angelica.apppizzahut.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import com.example.angelica.apppizzahut.Entity.Local;
import com.example.angelica.apppizzahut.Entity.Provincia;

import java.util.ArrayList;

/**
 * Created by bgeek05 on 11/09/2015.
 */
public class LocalesDao {

    public ArrayList<Local> listLocales(int provinciaId) {
    ArrayList<Local> lstLocales = new ArrayList<>();
    Cursor cursor = null;

    try {
        cursor = DataBaseHelper.myDataBase.query("Locales", null, "idprovincia = ?", new String[]{String.valueOf(provinciaId)}, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                Local locales = new Local();
                locales.setIdlocal(cursor.isNull(cursor.getColumnIndex("idlocal")) ? 0 : cursor.getInt(cursor.getColumnIndex("idlocal")));
                locales.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
                locales.setLatitud(cursor.isNull(cursor.getColumnIndex("latitud")) ? "" : cursor.getString(cursor.getColumnIndex("latitud")));
                locales.setLongitud(cursor.isNull(cursor.getColumnIndex("longitud")) ? "" : cursor.getString(cursor.getColumnIndex("longitud")));
                locales.setDireccion(cursor.isNull(cursor.getColumnIndex("direccion")) ? "" : cursor.getString(cursor.getColumnIndex("direccion")));
                lstLocales.add(locales);
            } while (cursor.moveToNext());
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        if (cursor != null)
            cursor.close();
    }

    return lstLocales;
}

    public void addLocales(Local local) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("nombre", local.getNombre());
            cv.put("latitud", local.getLatitud());
            cv.put("longitud", local.getLongitud());
            cv.put("direccion", local.getDireccion());
            DataBaseHelper.myDataBase.insert("Locales", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateLocal(Local local) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("nombre", local.getNombre());
            cv.put("latitud", local.getLatitud());
            cv.put("longitud", local.getLongitud());
            cv.put("direccion", local.getDireccion());
            DataBaseHelper.myDataBase.update("Locales", cv, "idlocal = ?", new String[]{String.valueOf(local.getIdlocal())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteLocal(Local local) {
        try {
            DataBaseHelper.myDataBase.delete("Locales", "idlocal = ?", new String[]{String.valueOf(local.getIdlocal())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
