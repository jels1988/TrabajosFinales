package com.cibertec.app.ferconsapedidos.Dao;

import android.content.ContentValues;
import android.database.Cursor;


import com.cibertec.app.ferconsapedidos.Entidad.Producto;

import java.util.ArrayList;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class ProductoDAO {
    public ArrayList<Producto> listProducto() {
        ArrayList<Producto> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("Producto", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto();
                    producto.setDescripcionProducto(cursor.isNull(cursor.getColumnIndex("DescripcionProducto")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionProducto")));
                    producto.setUnidad(cursor.isNull(cursor.getColumnIndex("Unidad")) ? "" : cursor.getString(cursor.getColumnIndex("Unidad")));
                    producto.setIdProducto(cursor.isNull(cursor.getColumnIndex("IdProducto")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdProducto")));
                    producto.setPrecio(cursor.isNull(cursor.getColumnIndex("Precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Precio")));
                    producto.setCodigoProducto(cursor.isNull(cursor.getColumnIndex("CodigoProducto")) ? "" : cursor.getString(cursor.getColumnIndex("CodigoProducto")));
                    producto.setCantidad(cursor.isNull(cursor.getColumnIndex("Cantidad")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Cantidad")));

                    lstPersona.add(producto);
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

    public ArrayList<Producto> lisMaestratProducto() {
        ArrayList<Producto> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("MaestraProducto", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto();
                    producto.setDescripcionProducto(cursor.isNull(cursor.getColumnIndex("DescripcionProducto")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionProducto")));
                    producto.setUnidad(cursor.isNull(cursor.getColumnIndex("Unidad")) ? "" : cursor.getString(cursor.getColumnIndex("Unidad")));
                    producto.setPrecio(cursor.isNull(cursor.getColumnIndex("Precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Precio")));
                    producto.setCodigoProducto(cursor.isNull(cursor.getColumnIndex("CodigoProducto")) ? "" : cursor.getString(cursor.getColumnIndex("CodigoProducto")));

                    lstPersona.add(producto);
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

    public int addProducto(Producto producto) {
        long idProducto =-1;
        try {
            ContentValues cv = new ContentValues();
            cv.put("CodigoProducto", producto.getCodigoProducto());
            cv.put("DescripcionProducto", producto.getDescripcionProducto());
            cv.put("Unidad", producto.getUnidad());
            cv.put("Precio", producto.getPrecio());

            idProducto =    DataBaseHelper.myDataBase.insert("Producto", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ((int) idProducto);
    }

    public ArrayList<Producto> ActualizarProducto() {
        ArrayList<Producto> lstProducto = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("MaestraProducto", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto();
                    producto.setDescripcionProducto(cursor.isNull(cursor.getColumnIndex("DescripcionProducto")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionProducto")));
                    producto.setUnidad(cursor.isNull(cursor.getColumnIndex("Unidad")) ? "" : cursor.getString(cursor.getColumnIndex("Unidad")));
                    producto.setPrecio(cursor.isNull(cursor.getColumnIndex("Precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Precio")));
                    producto.setCodigoProducto(cursor.isNull(cursor.getColumnIndex("CodigoProducto")) ? "" : cursor.getString(cursor.getColumnIndex("CodigoProducto")));

                    Cursor cursorProducto = DataBaseHelper.myDataBase.rawQuery(" SELECT CodigoProducto FROM Producto WHERE CodigoProducto='"+producto.getCodigoProducto()+"' ", null);
                    if (cursorProducto.getCount()==0){

                        new ProductoDAO().addProducto(producto);
                        break;
                    }


                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstProducto;
    }

}
