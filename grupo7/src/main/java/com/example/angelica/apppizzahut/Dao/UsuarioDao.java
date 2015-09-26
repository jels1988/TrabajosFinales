package com.example.angelica.apppizzahut.Dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.angelica.apppizzahut.Entity.Provincia;
import com.example.angelica.apppizzahut.Entity.Usuario;

import java.util.ArrayList;

/**
 * Created by bgeek05 on 17/09/2015.
 */
public class UsuarioDao {

    public Usuario listarUsuario(String usuario) {
        Cursor cursor = null;
        Usuario usuarioResult = new Usuario();
        try {
            cursor = DataBaseHelper.myDataBase.rawQuery("Select nombre,dni,usuario From Usuarios where usuario = ?", new String[]{usuario});

            if (cursor.moveToFirst()) {
                do {
                    usuarioResult.setNombre(cursor.isNull(cursor.getColumnIndex("nombre"))?"":cursor.getString(cursor.getColumnIndex("nombre")));
                    usuarioResult.setDni(cursor.isNull(cursor.getColumnIndex("dni")) ? "" : cursor.getString(cursor.getColumnIndex("dni")));
                    usuarioResult.setUsuario(cursor.isNull(cursor.getColumnIndex("usuario")) ? "" : cursor.getString(cursor.getColumnIndex("usuario")));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return usuarioResult;
    }

    public int validarUsuario(String usuario) {
        int iFlagValido = -1;
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.rawQuery("Select Count(*) as Usuario From Usuarios where usuario = ?", new String[]{usuario});

            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getInt(cursor.getColumnIndex("Usuario")) > 0){
                        iFlagValido = 0;
                    }else{
                        iFlagValido = 1;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return iFlagValido;
    }

    public int validarPassword(String usuario, String password) {
        int iFlagValido = -1;
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.rawQuery("Select Count(*) as Pass From Usuarios Where usuario = ? And password = ?",
                    new String[]{usuario,password});

            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getInt(cursor.getColumnIndex("Pass")) > 0){
                        iFlagValido = 0;
                    }else{
                        iFlagValido = 1;
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return iFlagValido;
    }

    public void addUsuario(Usuario usuario) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("nombre", usuario.getNombre());
            cv.put("dni", usuario.getDni());
            cv.put("usuario", usuario.getUsuario());
            cv.put("password", usuario.getPassword());
            DataBaseHelper.myDataBase.insert("Usuarios", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updUsuario(Usuario usuario, String usuarioStr) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("usuario", usuario.getUsuario());
            cv.put("password", usuario.getPassword());
            DataBaseHelper.myDataBase.update("Usuarios", cv, "usuario = ?", new String[]{usuarioStr});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
