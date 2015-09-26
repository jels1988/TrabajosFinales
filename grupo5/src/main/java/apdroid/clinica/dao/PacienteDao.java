package apdroid.clinica.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import apdroid.clinica.entidades.Paciente;

/**
 * Created by Edinson on 20/09/2015.
 */
public class PacienteDao {

    private static PacienteDao singleton = null;

    private final String sql_listxid = "SELECT id_paciente,num_dni,usuario,password,nombres,apellidos, correo, estilo, idioma FROM paciente where id_paciente =?";

    public static PacienteDao getSingleton(){
        if(singleton == null){
            singleton = new PacienteDao();
        }
        return singleton;
    }

    public Paciente ejecutarQueryxid(int idpaciente){
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(idpaciente));


        Cursor cursor = null;
        Paciente paciente = null;

        try {
            cursor = DB_Helper.getMyDataBase().rawQuery(sql_listxid, params.size() > 0 ? params.toArray(new String[]{}) : null);

            if (cursor.moveToFirst()) {
                do {
                    paciente = new Paciente();
                    paciente.setId_paciente(cursor.isNull(cursor.getColumnIndex("id_paciente")) ? 0 : cursor.getInt(cursor.getColumnIndex("id_paciente")));
                    paciente.setNum_dni(cursor.isNull(cursor.getColumnIndex("num_dni")) ? "" : cursor.getString(cursor.getColumnIndex("num_dni")));
                    paciente.setUsuario(cursor.isNull(cursor.getColumnIndex("usuario")) ? "" : cursor.getString(cursor.getColumnIndex("usuario")));
                    paciente.setPassword(cursor.isNull(cursor.getColumnIndex("password")) ? "" : cursor.getString(cursor.getColumnIndex("password")));
                    paciente.setNombres(cursor.isNull(cursor.getColumnIndex("nombres")) ? "" : cursor.getString(cursor.getColumnIndex("nombres")));
                    paciente.setApellidos(cursor.isNull(cursor.getColumnIndex("apellidos")) ? "" : cursor.getString(cursor.getColumnIndex("apellidos")));
                    paciente.setCorreo(cursor.isNull(cursor.getColumnIndex("correo")) ? "" : cursor.getString(cursor.getColumnIndex("correo")));
                    paciente.setEstilo(cursor.isNull(cursor.getColumnIndex("estilo")) ? "" : cursor.getString(cursor.getColumnIndex("estilo")));
                    paciente.setIdioma(cursor.isNull(cursor.getColumnIndex("idioma")) ? "" : cursor.getString(cursor.getColumnIndex("idioma")));

                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return paciente;

    }

    public boolean actualizarPaciente(Paciente paciente){
        try {
            ContentValues cv = new ContentValues();
            cv.put("num_dni", paciente.getNum_dni());
            cv.put("nombres", paciente.getNombres());
            cv.put("apellidos", paciente.getApellidos());
            cv.put("correo", paciente.getCorreo());
            cv.put("estilo", paciente.getEstilo());
            cv.put("idioma", paciente.getIdioma());

            DB_Helper.getMyDataBase().update("paciente", cv, "id_paciente = ?", new String[]{String.valueOf(paciente.getId_paciente())});
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true ;
    }


}
