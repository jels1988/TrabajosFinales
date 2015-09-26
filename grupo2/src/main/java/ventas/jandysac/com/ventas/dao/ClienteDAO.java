package ventas.jandysac.com.ventas.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import ventas.jandysac.com.ventas.entities.Cliente;

/**
 * Created by Rodolfo on 10/09/2015.
 */
public class ClienteDAO {
    public ArrayList<Cliente> listCliente() {
        ArrayList<Cliente> listCliente = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("cliente", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setClienteID(cursor.isNull(cursor.getColumnIndex("clienteID")) ? 0 : cursor.getInt(cursor.getColumnIndex("clienteID")));
                    cliente.setCodigo(cursor.isNull(cursor.getColumnIndex("codigo")) ? "" : cursor.getString(cursor.getColumnIndex("codigo")));
                    cliente.setNombres(cursor.isNull(cursor.getColumnIndex("nombres")) ? "" : cursor.getString(cursor.getColumnIndex("nombres")));
                    cliente.setApellido_materno(cursor.isNull(cursor.getColumnIndex("apellido_paterno")) ? "" : cursor.getString(cursor.getColumnIndex("apellido_paterno")));
                    cliente.setApellido_paterno(cursor.isNull(cursor.getColumnIndex("apellido_materno")) ? "" : cursor.getString(cursor.getColumnIndex("apellido_materno")));
                    cliente.setNombre_completo(cursor.isNull(cursor.getColumnIndex("nombre_completo")) ? "" : cursor.getString(cursor.getColumnIndex("nombre_completo")));
                    cliente.setDireccion(cursor.isNull(cursor.getColumnIndex("direccion")) ? "" : cursor.getString(cursor.getColumnIndex("direccion")));
                    cliente.setTipo_doc(cursor.isNull(cursor.getColumnIndex("tipo_doc")) ? "" : cursor.getString(cursor.getColumnIndex("tipo_doc")));
                    cliente.setCoodenadas(cursor.isNull(cursor.getColumnIndex("coordenadas")) ? "-12.0731275,-77.054646" : cursor.getString(cursor.getColumnIndex("coordenadas")));

                    listCliente.add(cliente);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return listCliente;
    }

    public void addCliente(Cliente cliente) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("codigo", cliente.getCodigo());
            cv.put("apellido_paterno", cliente.getApellido_paterno());
            cv.put("apellido_materno", cliente.getApellido_materno());
            cv.put("nombres", cliente.getNombres());
            cv.put("nombre_completo", cliente.getNombre_completo());
            cv.put("direccion", cliente.getDireccion());
            cv.put("tipo_doc", cliente.getTipo_doc());
            cv.put("coordenadas", cliente.getCoodenadas());
            DataBaseHelper.myDataBase.insert("Cliente", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateCliente(Cliente cliente) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("codigo", cliente.getCodigo());
            cv.put("apellido_paterno", cliente.getApellido_paterno());
            cv.put("apellido_materno", cliente.getApellido_materno());
            cv.put("nombres", cliente.getNombres());
            cv.put("nombre_completo", cliente.getNombre_completo());
            cv.put("direccion", cliente.getDireccion());
            cv.put("tipo_doc", cliente.getTipo_doc());
            cv.put("coordenadas", cliente.getCoodenadas());
            DataBaseHelper.myDataBase.update("Cliente", cv, "clienteID = ?", new String[]{String.valueOf(cliente.getClienteID())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
