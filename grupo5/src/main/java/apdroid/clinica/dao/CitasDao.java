package apdroid.clinica.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.util.Utiles;

/**
 * Created by ANTONIO on 12/09/2015.
 */
public class CitasDao {

    private static CitasDao singleton = null;

    private final String sql_listAll = "select id_cita, id_doctor, id_paciente, (select nombre_espec from especialidad where id_especialidad = d.id_especialidad) nom_especialidad, d.nombre || ' ' || d.apellido nom_doctor, fecha, hora , estado , detalleConsulta , nombrelocal from cita c inner join doctor d on c.id_doctor=d.id_doc inner join local l on l.idlocal = d.idlocal ";

    private String updateCita = "update cita set fecha = ?, hora = ? where id_cita = ?";

    private String nuevaCita = "INSERT INTO cita(id_doctor,id_paciente,fecha,hora,estado,detalleConsulta) VALUES (?,?,?,?,?,?)";

    private final String orderBy = "order by date(fecha) desc";



    private CitasDao(){

    }

    public static CitasDao getSingleton(){
        if(singleton == null){
            singleton = new CitasDao();
        }
        return singleton;
    }

    private ArrayList<DatosCita> ejecutarQuery(String query, String[] args){
        Cursor cursor = null;
        DatosCita cita = null;
        ArrayList<DatosCita> lstPersona = new ArrayList<>();
        String fechaTmp;
        try {
            cursor = DB_Helper.getMyDataBase().rawQuery(query, args);

            if (cursor.moveToFirst()) {
                do {
                    cita = new DatosCita();
                    cita.setIdCita(cursor.isNull(cursor.getColumnIndex("id_cita")) ? 0 : cursor.getInt(cursor.getColumnIndex("id_cita")));
                    cita.setIdDoctor(cursor.isNull(cursor.getColumnIndex("id_doctor")) ? 0 : cursor.getInt(cursor.getColumnIndex("id_doctor")));
                    cita.setIdPaciente(cursor.isNull(cursor.getColumnIndex("id_paciente")) ? 0 : cursor.getInt(cursor.getColumnIndex("id_paciente")));

                    cita.setEspecialidad(cursor.isNull(cursor.getColumnIndex("nom_especialidad")) ? "" : cursor.getString(cursor.getColumnIndex("nom_especialidad")));
                    cita.setDoctor(cursor.isNull(cursor.getColumnIndex("nom_doctor")) ? "" : cursor.getString(cursor.getColumnIndex("nom_doctor")));
                    fechaTmp = cursor.isNull(cursor.getColumnIndex("fecha")) ? "" : cursor.getString(cursor.getColumnIndex("fecha"));
                    cita.setFecha( Utiles.cambiarFormatoFecha(fechaTmp, "yyyy-MM-dd", "dd/MM/yyyy") );

                    cita.setHora(cursor.isNull(cursor.getColumnIndex("hora")) ? "" : cursor.getString(cursor.getColumnIndex("hora")));
                    cita.setEstado(cursor.isNull(cursor.getColumnIndex("estado")) ? "" : cursor.getString(cursor.getColumnIndex("estado")));
                    cita.setDetalleConsulta(cursor.isNull(cursor.getColumnIndex("detalleConsulta")) ? "" : cursor.getString(cursor.getColumnIndex("detalleConsulta")));
                    cita.setLocal(cursor.isNull(cursor.getColumnIndex("nombrelocal")) ? "" : cursor.getString(cursor.getColumnIndex("nombrelocal")));


                    lstPersona.add(cita);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstPersona;

    }

    public ArrayList<DatosCita> buscarCitas(DatosCita datosCita) {
        ArrayList<DatosCita> lstPersona = new ArrayList<>();
        String finalQuery = null;
        StringBuilder whereQuery = new StringBuilder();
        List<String> params = new ArrayList<>();

        if(datosCita != null){

            whereQuery.append("where 1 = 1 ");

            if( datosCita.getIdPaciente()!=null && datosCita.getIdPaciente() > 0 ){
                whereQuery.append("and c.id_paciente = ? ");
                params.add(String.valueOf(datosCita.getIdPaciente()));
            }

            if( datosCita.getIdEspecialidad() != null && datosCita.getIdEspecialidad() != -1){
                whereQuery.append("and d.id_especialidad = ? ");
                params.add(String.valueOf(datosCita.getIdEspecialidad()));
            }

            if(datosCita.getFecha()!=null && !"".equals(datosCita.getFecha())){
                Date date = null;
                String fecha = null;

                fecha = Utiles.cambiarFormatoFecha(datosCita.getFecha(), "dd/MM/yyyy", "yyyy-MM-dd");

                if ( fecha!=null && !"".equals(fecha)){
                    whereQuery.append("and c.fecha = ? ");
                    params.add(fecha);
                }

            }
        }

        finalQuery = sql_listAll + whereQuery + orderBy;
        Log.d("", finalQuery);
        Log.d("", params.toString());
        lstPersona = ejecutarQuery(finalQuery, params.size() > 0 ? params.toArray(new String[]{}) : null);


        return lstPersona;
    }

    public boolean anularCita(DatosCita datosCita){
        try {
            ContentValues cv = new ContentValues();
            cv.put("estado", datosCita.getEstado());

            DB_Helper.getMyDataBase().update("cita", cv, "id_cita = ?", new String[]{String.valueOf(datosCita.getIdCita())});
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true ;
    }


    public void actualizarCita(DatosCita datosCita) {

        Object []args = new Object[3];

        String fecha = Utiles.cambiarFormatoFecha(datosCita.getFecha(), "dd/MM/yyyy", "yyyy-MM-dd");
        args[0] = fecha;
        args[1] = datosCita.getHora();
        args[2] = datosCita.getIdCita();

        DB_Helper.getMyDataBase().execSQL(updateCita, args);
    }

    public void nueaCita(DatosCita datosCita) {

        //Object []args = new Object[6];
        List<Object> params = new ArrayList<>();

        String fecha = Utiles.cambiarFormatoFecha(datosCita.getFecha(), "dd/MM/yyyy", "yyyy-MM-dd");
        params.add(datosCita.getIdDoctor());
        params.add(datosCita.getIdPaciente());
        params.add(fecha);
        params.add(datosCita.getHora());
        params.add(datosCita.getEstado());
        params.add(datosCita.getDetalleConsulta());


        DB_Helper.getMyDataBase().execSQL(nuevaCita, params.toArray(new Object[]{}));
    }


}
