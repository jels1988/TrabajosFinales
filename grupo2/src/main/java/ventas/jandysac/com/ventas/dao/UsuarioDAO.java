package ventas.jandysac.com.ventas.dao;

import android.database.Cursor;

import ventas.jandysac.com.ventas.entities.Parametro;
import ventas.jandysac.com.ventas.entities.Usuario;

/**
 * Created by JoseKoji on 16/09/2015.
 */
public class UsuarioDAO {
    public Usuario findUsuario(String nombre) {
        Usuario usuario = new Usuario();
        Cursor cursor = null;
        String[] args = new String[]{nombre};

        try {
            cursor = DataBaseHelper.myDataBase.query("Usuario", null, "usuario=?", args, null, null, null);

            if (cursor.moveToFirst()) {
                usuario.setUsuario(cursor.isNull(cursor.getColumnIndex("usuario")) ? "" : cursor.getString(cursor.getColumnIndex("usuario")));
                usuario.setContrasenia(cursor.isNull(cursor.getColumnIndex("contrasenia")) ? "" : cursor.getString(cursor.getColumnIndex("contrasenia")));
                usuario.setNombres(cursor.isNull(cursor.getColumnIndex("nombres")) ? "" : cursor.getString(cursor.getColumnIndex("nombres")));
                usuario.setApellidos(cursor.isNull(cursor.getColumnIndex("apellidos")) ? "" : cursor.getString(cursor.getColumnIndex("apellidos")));
                usuario.setCodigo(cursor.isNull(cursor.getColumnIndex("codigo")) ? "" : cursor.getString(cursor.getColumnIndex("codigo")));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return usuario;
    }
}
