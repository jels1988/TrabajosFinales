package grupo4.histoclici.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import grupo4.histoclici.entidad.Cita;
import grupo4.histoclici.entidad.Paciente;

public class CitaDAO {

    String[] valores;

    public ArrayList<Cita> listarCitarXFecha(String fecha) {
        ArrayList<Cita> listaCita = new ArrayList<>();

        Cursor cursor = null;
        valores = new String[1];
        valores[0] = fecha;

        try{
            cursor = DataBaseHelper.myDataBase.rawQuery("select IdCita, c.IdPaciente, paciente, FechaCita, Inicio, Fin, fg_domicilio from Cita c join Paciente p on c.IdPaciente = p.IdPaciente where FechaCita = ? order by Inicio", valores);
            if(cursor.moveToFirst()){
                do{
                    Cita cita = new Cita();
                    cita.setIdCita(cursor.isNull(cursor.getColumnIndex("IdCita")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdCita")));
                    cita.setIdPaciente(cursor.isNull(cursor.getColumnIndex("IdPaciente")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPaciente")));
                    cita.setPaciente(cursor.isNull(cursor.getColumnIndex("Paciente")) ? "" : cursor.getString(cursor.getColumnIndex("Paciente")));
                    cita.setFechaCita(cursor.isNull(cursor.getColumnIndex("FechaCita")) ? "" : cursor.getString(cursor.getColumnIndex("FechaCita")));
                    cita.setInicio(cursor.isNull(cursor.getColumnIndex("Inicio")) ? "" : cursor.getString(cursor.getColumnIndex("Inicio")));
                    cita.setFin(cursor.isNull(cursor.getColumnIndex("Fin"))  ? "" : cursor.getString(cursor.getColumnIndex("Fin")));
                    cita.setPregunta(cursor.isNull(cursor.getColumnIndex("fg_domicilio"))  ? "" : cursor.getString(cursor.getColumnIndex("fg_domicilio")));
                    listaCita.add(cita);
                }
                while(cursor.moveToNext());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(cursor != null)
                cursor.close();
        }

        return listaCita;
    }

    public ArrayList<Paciente> listarPaciente() {
        ArrayList<Paciente> listaPaciente = new ArrayList<>();

        Cursor cursor = null;

        try {
            String[] campos = {"IdPaciente", "Paciente"};
            cursor = DataBaseHelper.myDataBase.query("Paciente", campos, null, null, null, null, "Paciente");

            if (cursor.moveToFirst()) {
                do {
                    Paciente paciente = new Paciente();
                    paciente.setidPaciente(cursor.isNull(cursor.getColumnIndex("IdPaciente")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPaciente")));
                    paciente.setPaciente(cursor.isNull(cursor.getColumnIndex("Paciente")) ? "" : cursor.getString(cursor.getColumnIndex("Paciente")));
                    listaPaciente.add(paciente);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return listaPaciente;
    }

    public void insertarCita(Cita cita) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("IdPaciente", cita.getIdPaciente());
            cv.put("FechaCita", cita.getFechaCita());
            cv.put("Inicio", cita.getInicio());
            cv.put("Fin", cita.getFin());
            cv.put("fg_domicilio", cita.getPregunta());
            DataBaseHelper.myDataBase.insert("Cita", null, cv);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarCita(int IdCita) {
        try {
            DataBaseHelper.myDataBase.delete("Cita", "IdCita = ?", new String[]{String.valueOf(IdCita)});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
