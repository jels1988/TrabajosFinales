package com.cibertec.app.ferconsapedidos.Dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.cibertec.app.ferconsapedidos.Entidad.PedidoDetalle;

import java.util.ArrayList;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class PedidoDetalleDAO {
    public ArrayList<PedidoDetalle> listPedidoDetalle() {
        ArrayList<PedidoDetalle> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("PedidoDetalle", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    PedidoDetalle pedidoDetalle = new PedidoDetalle();
                    pedidoDetalle.setCantidad(cursor.isNull(cursor.getColumnIndex("Cantidad")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Cantidad")));
                    pedidoDetalle.setCodigoProducto(cursor.isNull(cursor.getColumnIndex("CodigoProducto")) ? "" : cursor.getString(cursor.getColumnIndex("CodigoProducto")));
                    pedidoDetalle.setDescripcionProducto(cursor.isNull(cursor.getColumnIndex("DescripcionProducto")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionProducto")));
                    pedidoDetalle.setIdPedidoCabecera(cursor.isNull(cursor.getColumnIndex("IdPedidoCabecera")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPedidoCabecera")));
                    pedidoDetalle.setIdProducto(cursor.isNull(cursor.getColumnIndex("IdProducto")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdProducto")));
                    pedidoDetalle.setUnidad(cursor.isNull(cursor.getColumnIndex("Unidad")) ? "" : cursor.getString(cursor.getColumnIndex("Unidad")));
                    pedidoDetalle.setPrecio(cursor.isNull(cursor.getColumnIndex("Precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Precio")));
                    pedidoDetalle.setIdPedidoDetalle(cursor.isNull(cursor.getColumnIndex("IdPedidoDetalle")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPedidoDetalle")));
                    lstPersona.add(pedidoDetalle);
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
    public ArrayList<PedidoDetalle> listPedidoDetalleBuscar(String IdPedidoCabecera) {
        ArrayList<PedidoDetalle> lstPedidoDetalle = new ArrayList<>();
        Cursor cursor = null;

        try {
            String[] args = new String[] {IdPedidoCabecera};
            cursor =  DataBaseHelper.myDataBase.rawQuery(" select IdPedidoDetalle,IdPedidoCabecera,IdProducto,CodigoProducto,DescripcionProducto,Unidad,Cantidad,Precio \n" +
                    "from PedidoDetalle Where IdPedidoCabecera=? ", args);
                    //DataBaseHelper.myDataBase.query("PedidoDetalle", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    PedidoDetalle pedidoDetalle = new PedidoDetalle();
                    pedidoDetalle.setCantidad(cursor.isNull(cursor.getColumnIndex("Cantidad")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Cantidad")));
                    pedidoDetalle.setCodigoProducto(cursor.isNull(cursor.getColumnIndex("CodigoProducto")) ? "" : cursor.getString(cursor.getColumnIndex("CodigoProducto")));
                    pedidoDetalle.setDescripcionProducto(cursor.isNull(cursor.getColumnIndex("DescripcionProducto")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionProducto")));
                    pedidoDetalle.setIdPedidoCabecera(cursor.isNull(cursor.getColumnIndex("IdPedidoCabecera")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPedidoCabecera")));
                    pedidoDetalle.setIdProducto(cursor.isNull(cursor.getColumnIndex("IdProducto")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdProducto")));
                    pedidoDetalle.setUnidad(cursor.isNull(cursor.getColumnIndex("Unidad")) ? "" : cursor.getString(cursor.getColumnIndex("Unidad")));
                    pedidoDetalle.setPrecio(cursor.isNull(cursor.getColumnIndex("Precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("Precio")));
                    pedidoDetalle.setIdPedidoDetalle(cursor.isNull(cursor.getColumnIndex("IdPedidoDetalle")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPedidoDetalle")));
                    lstPedidoDetalle.add(pedidoDetalle);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstPedidoDetalle;
    }
    public void addPedidoDetalle(PedidoDetalle pedidoDetalle) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("IdPedidoCabecera", pedidoDetalle.getIdPedidoCabecera());
            cv.put("IdProducto", pedidoDetalle.getIdProducto());
            cv.put("CodigoProducto", pedidoDetalle.getCodigoProducto());
            cv.put("DescripcionProducto", pedidoDetalle.getDescripcionProducto());
            cv.put("Unidad", pedidoDetalle.getUnidad());
            cv.put("Cantidad", pedidoDetalle.getCantidad());
            cv.put("Precio", pedidoDetalle.getPrecio());

            DataBaseHelper.myDataBase.insert("PedidoDetalle", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updatePedidoCabecera(PedidoDetalle pedidoDetalle) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("IdPedidoCabecera", pedidoDetalle.getIdPedidoCabecera());
            cv.put("IdProducto", pedidoDetalle.getIdProducto());
            cv.put("CodigoProducto", pedidoDetalle.getCodigoProducto());
            cv.put("DescripcionProducto", pedidoDetalle.getDescripcionProducto());
            cv.put("Unidad", pedidoDetalle.getUnidad());
            cv.put("Cantidad", pedidoDetalle.getCantidad());

            DataBaseHelper.myDataBase.update("PedidoDetalle", cv, "IdPedidoDetalle = ?", new String[]{String.valueOf(pedidoDetalle.getIdPedidoDetalle())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deletePedidoCabecera(PedidoDetalle pedidoDetalle) {
        try {
            DataBaseHelper.myDataBase.delete("PedidoDetalle", "IdPedidoDetalle = ?", new String[]{String.valueOf(pedidoDetalle.getIdPedidoDetalle())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deletePedidoCabeceraAll(Integer IdPedidoCabecera) {
        try {
            DataBaseHelper.myDataBase.delete("PedidoDetalle", "IdPedidoCabecera = ?", new String[]{String.valueOf(IdPedidoCabecera)});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

