package com.example.angelica.apppizzahut.Dao;

import android.database.Cursor;
import com.example.angelica.apppizzahut.Entity.Provincia;

import java.util.ArrayList;

/**
 * Created by bgeek05 on 11/09/2015.
 */
public class ProvinciasDao {

    public ArrayList<Provincia> listProvinciasSpn() {
        ArrayList<Provincia> lstProvincias = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.rawQuery("Select Distinct provincia From Provincias", null);

            if (cursor.moveToFirst()) {
                do {
                    Provincia provincia = new Provincia();
                    provincia.setProvincia(cursor.isNull(cursor.getColumnIndex("provincia")) ? "" : cursor.getString(cursor.getColumnIndex("provincia")));
                    lstProvincias.add(provincia);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstProvincias;
    }

    public ArrayList<Provincia> listDistritosSpn(Provincia provincias) {
        ArrayList<Provincia> lstProvincias = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.rawQuery("Select Distinct idprovincia, distrito From Provincias Where provincia = ?", new String[]{provincias.getProvincia()});

            if (cursor.moveToFirst()) {
                do {
                    Provincia provincia = new Provincia();
                    provincia.setIdprovincia(cursor.isNull(cursor.getColumnIndex("idprovincia")) ? 0 : cursor.getInt(cursor.getColumnIndex("idprovincia")));
                    provincia.setDistrito(cursor.isNull(cursor.getColumnIndex("distrito")) ? "" : cursor.getString(cursor.getColumnIndex("distrito")));
                    lstProvincias.add(provincia);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstProvincias;
    }
}
