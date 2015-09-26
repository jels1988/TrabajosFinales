package apdroid.clinica.dao;

import android.database.Cursor;

import java.util.ArrayList;

import apdroid.clinica.entidades.Doctor;

/**
 * Created by AngeloPaulo on 24/septiembre/2015.
 */
public class DoctorDao {

    private static DoctorDao singleton;



    private DoctorDao(){
    }

    public static DoctorDao getSingleton(){
        if(singleton == null){
            singleton = new DoctorDao();
        }

        return singleton;
    }

    public ArrayList<Doctor> listarDoctores() {
        ArrayList<Doctor> lstDoctor = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DB_Helper.getMyDataBase().query("Doctor", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Doctor doctor = new Doctor();

                    doctor.setIddoc(cursor.isNull(cursor.getColumnIndex("id_doc")) ? -1 : cursor.getInt(cursor.getColumnIndex("id_doc")));
                    doctor.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
                    doctor.setApellido(cursor.isNull(cursor.getColumnIndex("apellido")) ? "" : cursor.getString(cursor.getColumnIndex("apellido")));
                    doctor.setIdespec(cursor.isNull(cursor.getColumnIndex("id_especialidad")) ? -1 : cursor.getInt(cursor.getColumnIndex("id_especialidad")));
                    //doctor.setHorario(cursor.isNull(cursor.getColumnIndex("horario")) ? "" : cursor.getString(cursor.getColumnIndex("horario")));
                    doctor.setIdlocal(cursor.isNull(cursor.getColumnIndex("idlocal")) ? -1 : cursor.getInt(cursor.getColumnIndex("idlocal")));

                    lstDoctor.add(doctor);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstDoctor;
    }


    public ArrayList<String> consultar(int selec){
        if (selec==0){
            selec=1;
        }
        ArrayList<String> result= new ArrayList<>();
        Cursor cursor = DB_Helper.getMyDataBase().rawQuery("SELECT nombre, apellido FROM Doctor WHERE id_especialidad=" + selec + " ORDER BY nombre", null);
        while(cursor.moveToNext()){
           result.add(cursor.getString(0)+" "+cursor.getString(1));
        }
        cursor.close();
        return result;
    }


    public ArrayList<Doctor> listarDoctoresEsp(Integer esp) {
        ArrayList<Doctor> lstDoctor = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DB_Helper.getMyDataBase().query("Doctor", null, "id_especialidad = ?", new String[]{String.valueOf(esp)}, null, null, "nombre ASC");

            if (cursor.moveToFirst()) {
                do {
                    Doctor doctor = new Doctor();

                    doctor.setIddoc(cursor.isNull(cursor.getColumnIndex("id_doc")) ? -1 : cursor.getInt(cursor.getColumnIndex("id_doc")));
                    doctor.setNombre(cursor.isNull(cursor.getColumnIndex("nombre")) ? "" : cursor.getString(cursor.getColumnIndex("nombre")));
                    doctor.setApellido(cursor.isNull(cursor.getColumnIndex("apellido")) ? "" : cursor.getString(cursor.getColumnIndex("apellido")));
                    doctor.setIdespec(cursor.isNull(cursor.getColumnIndex("id_especialidad")) ? -1 : cursor.getInt(cursor.getColumnIndex("id_especialidad")));
                    //doctor.setHorario(cursor.isNull(cursor.getColumnIndex("horario")) ? "" : cursor.getString(cursor.getColumnIndex("horario")));
                    doctor.setIdlocal(cursor.isNull(cursor.getColumnIndex("idlocal")) ? -1 : cursor.getInt(cursor.getColumnIndex("idlocal")));

                    lstDoctor.add(doctor);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstDoctor;
    }




}
