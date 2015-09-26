package grupo4.histoclici.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import grupo4.histoclici.entidad.Paciente;

/**
 * Created by pedro_jx on 18/09/2015.
 */
public class PacienteDAO {

    public ArrayList<Paciente> listarPacientes(){
        ArrayList<Paciente> alPaciente = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = DataBaseHelper.myDataBase.query("Paciente",new String[]{"IdPaciente","Paciente","Genero","Telefono","Celular","Domicilio","Latitud","Altitud"},null,null,null,null,"Paciente");
            if(cursor.moveToFirst()){
                do{
                    Paciente paciente = new Paciente();
                    paciente.setidPaciente(cursor.isNull(cursor.getInt(cursor.getColumnIndex("IdPaciente"))) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPaciente")));
                    paciente.setPaciente(cursor.isNull(cursor.getInt(cursor.getColumnIndex("Paciente"))) ? "" : cursor.getString(cursor.getColumnIndex("Paciente")));
                    paciente.setGenero(cursor.isNull(cursor.getInt(cursor.getColumnIndex("Genero"))) ? "" : cursor.getString(cursor.getColumnIndex("Genero")));
                    paciente.setTelefono(cursor.getString(cursor.getColumnIndex("Telefono")));
                    paciente.setCelular(cursor.getString(cursor.getColumnIndex("Celular")));
                    paciente.setDomicilio(cursor.getString(cursor.getColumnIndex("Domicilio")));
                    paciente.setLatitud(cursor.getString(cursor.getColumnIndex("Latitud")));
                    //paciente.setLatitud(cursor.isNull(cursor.getString(cursor.getColumnIndex("Latitud"))) ? "" : cursor.getString(cursor.getColumnIndex("Latitud")));
                    paciente.setAltitud(cursor.getString(cursor.getColumnIndex("Altitud")));
                    //paciente.setAltitud(cursor.isNull(cursor.getInt(cursor.getColumnIndex("Altitud"))) ? "" : cursor.getString(cursor.getColumnIndex("Altitud")));
                    alPaciente.add(paciente);
                }
                while (cursor.moveToNext());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(cursor != null)
                cursor.close();
        }
        return alPaciente;
    }

    public void insertarPaciente(Paciente paciente){
        try{
            ContentValues cv = new ContentValues();
            cv.put("Paciente",paciente.getPaciente());
            cv.put("Genero",paciente.getGenero());
            cv.put("Telefono",paciente.getTelefono());
            cv.put("Celular",paciente.getCelular());
            cv.put("Domicilio",paciente.getDomicilio());
            cv.put("Latitud",paciente.getLatitud());
            cv.put("Altitud",paciente.getAltitud());
            DataBaseHelper.myDataBase.insert("Paciente",null,cv);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void actualizarPaciente(Paciente paciente){
        try{
            ContentValues cv = new ContentValues();
            cv.put("Paciente",paciente.getPaciente());
            cv.put("Genero",paciente.getGenero());
            cv.put("Telefono",paciente.getTelefono());
            cv.put("Celular",paciente.getCelular());
            cv.put("Domicilio",paciente.getDomicilio());
            cv.put("Latitud",paciente.getLatitud());
            cv.put("Altitud",paciente.getAltitud());
            DataBaseHelper.myDataBase.update("Paciente", cv, "IdPaciente = ?", new String[]{String.valueOf(paciente.getidPaciente())});
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}