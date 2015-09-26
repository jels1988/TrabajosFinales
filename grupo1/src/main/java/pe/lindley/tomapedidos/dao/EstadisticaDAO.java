package pe.lindley.tomapedidos.dao;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Estadistica;

/**
 * Created by MTancun on 22/09/2015.
 */
public class EstadisticaDAO {
    private static EstadisticaDAO INSTANCE = null;

    private EstadisticaDAO() {
    }

    public static EstadisticaDAO getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EstadisticaDAO();
        }
    }

    public Estadistica getEstadistica() {
        Estadistica estadistica = new Estadistica();
        Cursor cursor = null;
        //consulta de Clientes Totales
        try {
            cursor = DataBaseHelper.myDataBase.query("TotalCliente", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                estadistica.setTotalCliente(cursor.isNull(cursor.getColumnIndex("totalCliente")) ? 0 : cursor.getInt(cursor.getColumnIndex("totalCliente")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        //consulta de Clientes con Pedido
        try {
            cursor = DataBaseHelper.myDataBase.query("TotalClienteconPedido", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                estadistica.setTotalconPedido(cursor.isNull(cursor.getColumnIndex("TotalClienteconPedido")) ? 0 : cursor.getInt(cursor.getColumnIndex("TotalClienteconPedido")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        estadistica.setTotalsinPedido(estadistica.getTotalCliente()-estadistica.getTotalconPedido());
        try {
            estadistica.setCumplimientoplandeVisita((100.0 * estadistica.getTotalconPedido()) / estadistica.getTotalCliente());
        }catch ( Exception ex ) {}
        return estadistica;
    }
}
