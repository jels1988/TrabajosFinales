package com.cibertec.app.ferconsapedidos.Dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.cibertec.app.ferconsapedidos.Entidad.PedidoCabecera;

import java.util.ArrayList;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class PedidoCabeceraDAO {



    public ArrayList<PedidoCabecera> listPedidoCabecera1() {
        ArrayList<PedidoCabecera> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("PedidoCabecera", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    PedidoCabecera PedidoCabecera = new PedidoCabecera();
                    PedidoCabecera.setFechaPedido(cursor.isNull(cursor.getColumnIndex("FechaPedido")) ? "" : cursor.getString(cursor.getColumnIndex("FechaPedido")));
                    PedidoCabecera.setIdCliente(cursor.isNull(cursor.getColumnIndex("IdCliente")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCliente")));
                    PedidoCabecera.setIdCondicionDePago(cursor.isNull(cursor.getColumnIndex("IdCondicionPago")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCondicionPago")));
                    PedidoCabecera.setIdUsuario(cursor.isNull(cursor.getColumnIndex("IdUsuario")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdUsuario")));
                    lstPersona.add(PedidoCabecera);
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
    public ArrayList<PedidoCabecera> listPedidoCabecera() {
        ArrayList<PedidoCabecera> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.rawQuery(" select  PedidoCabecera.IdPedidoCabecera,PedidoCabecera.IdCondicionPago,PedidoCabecera.IdCliente,PedidoCabecera.FechaPedido\n" +
                    "    ,PedidoCabecera.IdUsuario,Cliente.RUC,Cliente.NombreCliente ,p.DescripcionCondicionPago\n" +
                    "    from PedidoCabecera\n" +
                    "    inner join Condicionpago p on PedidoCabecera.IdCondicionpago=p.IdCondicionpago\n" +
                    "    inner join Cliente on Cliente.IdCliente=PedidoCabecera.IdCliente", null);

            if (cursor.moveToFirst()) {
                do {
                    PedidoCabecera PedidoCabecera = new PedidoCabecera();
                    PedidoCabecera.setIdPedidoCabecera(cursor.isNull(cursor.getColumnIndex("IdPedidoCabecera")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPedidoCabecera")));
                    PedidoCabecera.setFechaPedido(cursor.isNull(cursor.getColumnIndex("FechaPedido")) ? "" : cursor.getString(cursor.getColumnIndex("FechaPedido")));
                    PedidoCabecera.setIdCliente(cursor.isNull(cursor.getColumnIndex("IdCliente")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCliente")));
                    PedidoCabecera.setIdCondicionDePago(cursor.isNull(cursor.getColumnIndex("IdCondicionPago")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCondicionPago")));
                    PedidoCabecera.setIdUsuario(cursor.isNull(cursor.getColumnIndex("IdUsuario")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdUsuario")));
                    PedidoCabecera.setCondicionPago(cursor.isNull(cursor.getColumnIndex("DescripcionCondicionPago")) ? "" : cursor.getString(cursor.getColumnIndex("DescripcionCondicionPago")));
                    PedidoCabecera.setNombreCliente(cursor.isNull(cursor.getColumnIndex("NombreCliente")) ? "" : cursor.getString(cursor.getColumnIndex("NombreCliente")));
                    PedidoCabecera.setRuc(cursor.isNull(cursor.getColumnIndex("RUC")) ? "" : cursor.getString(cursor.getColumnIndex("RUC")));
                    lstPersona.add(PedidoCabecera);
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
    public int addPedidoCabecera(PedidoCabecera pedidoCabecera) {
        int IdCabecera=0;
        try {
            ContentValues cv = new ContentValues();
            cv.put("IdCondicionPago", pedidoCabecera.getIdCondicionDePago());
            cv.put("IdCliente", pedidoCabecera.getIdCliente());
            cv.put("FechaPedido", pedidoCabecera.getFechaPedido());
            cv.put("IdUsuario", pedidoCabecera.getIdUsuario());
            IdCabecera = (int) DataBaseHelper.myDataBase.insert("PedidoCabecera", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return IdCabecera;
    }

    public void updatePedidoCabecera(PedidoCabecera pedidoCabecera) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("IdCondicionPago", pedidoCabecera.getIdCondicionDePago());
            cv.put("IdCliente", pedidoCabecera.getIdCliente());
            cv.put("FechaPedido", pedidoCabecera.getFechaPedido());
            cv.put("IdUsuario", pedidoCabecera.getIdUsuario());
            DataBaseHelper.myDataBase.update("PedidoCabecera", cv, "IdPedidoCabecera = ?", new String[]{String.valueOf(pedidoCabecera.getIdPedidoCabecera())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deletePedidoCabecera(PedidoCabecera pedidoCabecera) {
        try {
            DataBaseHelper.myDataBase.delete("PedidoCabecera", "IdPedidoCabecera = ?", new String[]{String.valueOf(pedidoCabecera.getIdPedidoCabecera())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
