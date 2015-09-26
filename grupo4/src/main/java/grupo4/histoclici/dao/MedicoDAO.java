package grupo4.histoclici.dao;

import android.database.Cursor;

import grupo4.histoclici.entidad.Medico;

public class MedicoDAO {

    public Medico ingresar(String usuario, String clave) {
        Medico medico = null;
        Cursor cursor = null;

        String[] argumentos = new String[2];
        argumentos[0] = usuario;
        argumentos[1] = clave;

        try {
            cursor = DataBaseHelper.myDataBase.query("Medico", new String[]{"IdMedico","Medico"},"Usuario = ? and Clave = ?", argumentos,null, null, null, null);
            if (cursor.moveToFirst()) {
                medico = new Medico();
                medico.setIdMedico(cursor.isNull(cursor.getColumnIndex("IdMedico")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdMedico")));
                medico.setMedico(cursor.isNull(cursor.getColumnIndex("Medico")) ? "" : cursor.getString(cursor.getColumnIndex("Medico")));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (cursor != null)
                cursor.close();
        }
        return medico;
    }

}
