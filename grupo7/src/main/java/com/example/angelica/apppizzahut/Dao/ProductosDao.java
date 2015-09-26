package com.example.angelica.apppizzahut.Dao;

import android.database.Cursor;

import com.example.angelica.apppizzahut.Entity.Local;
import com.example.angelica.apppizzahut.Entity.Producto;

import java.util.ArrayList;

/**
 * Created by bgeek05 on 19/09/2015.
 */
public class ProductosDao {
    public ArrayList<Producto> listProductos(){
        ArrayList<Producto> lstProductos = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("Productos", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto();
                    producto.setIdproducto(cursor.isNull(cursor.getColumnIndex("idproducto")) ? 0 : cursor.getInt(cursor.getColumnIndex("idproducto")));
                    producto.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
                    producto.setPrecio(cursor.isNull(cursor.getColumnIndex("precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("precio")));
                    producto.setDescripcion(cursor.isNull(cursor.getColumnIndex("descripcion")) ? "" : cursor.getString(cursor.getColumnIndex("descripcion")));
                    producto.setImagen(cursor.isNull(cursor.getColumnIndex("imagen")) ? "" : cursor.getString(cursor.getColumnIndex("imagen")));
                    lstProductos.add(producto);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstProductos;
    }
}
