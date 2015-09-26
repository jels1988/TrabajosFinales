package com.cibertec.app.ferconsapedidos.Dao;

import android.database.Cursor;

import com.cibertec.app.ferconsapedidos.Entidad.Usuario;

import java.util.ArrayList;

/**
 * Created by jdiaz on 07/09/2015.
 */
public class UsuarioDAO {
    public ArrayList<Usuario> ListOneUser(Usuario usuario) {
        ArrayList<Usuario> ListOneUser = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("usuario", new String[]{"NombreUsuario","IdUsuario"}, "Usuario=? and Clave=?",
                    new String[]{String.valueOf(usuario.getUsuario()),String.valueOf(usuario.getClave())}, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Usuario user = new Usuario();
                    user.setNombreUsuario(cursor.isNull(cursor.getColumnIndex("NombreUsuario")) ? "" : cursor.getString(cursor.getColumnIndex("NombreUsuario")));
                    user.setIdUsuario(cursor.isNull(cursor.getColumnIndex("IdUsuario")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdUsuario")));
                    ListOneUser.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return ListOneUser;
    }
}
