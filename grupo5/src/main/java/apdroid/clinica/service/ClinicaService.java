package apdroid.clinica.service;

import android.util.Log;

import java.util.ArrayList;

import apdroid.clinica.dao.CitasDao;
import apdroid.clinica.dao.DoctorDao;
import apdroid.clinica.dao.EspecialidadDao;
import apdroid.clinica.dao.PacienteDao;
import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.entidades.Doctor;
import apdroid.clinica.entidades.Especialidad;
import apdroid.clinica.entidades.Paciente;

/**
 * Created by ANTONIO on 09/09/2015.
 */
public class ClinicaService {

    private static ClinicaService singleton;

    private EspecialidadDao especialidadDao;
    private CitasDao citasDao;
    private PacienteDao pacienteDao;
    private DoctorDao doctorDao;


    private ClinicaService(){
        especialidadDao = EspecialidadDao.getSingleton();
        doctorDao =DoctorDao.getSingleton();
        citasDao = CitasDao.getSingleton();
        pacienteDao =PacienteDao.getSingleton();

    }

    public static ClinicaService getSingleton(){
        if(singleton == null){
            singleton = new ClinicaService();
        }

        return singleton;
    }


    public ArrayList<Especialidad> listarEspecialidades(){
        ArrayList<Especialidad> lstEspecialidadCache = null;
        lstEspecialidadCache = especialidadDao.listarEspecialidades();

        return lstEspecialidadCache;
    }

    public ArrayList<Doctor> listarDoctores(){
        ArrayList<Doctor> lstDoctorCache = null;
        lstDoctorCache = doctorDao.listarDoctores();

        return lstDoctorCache;
    }

    public ArrayList<Doctor> listarDoctoresEsp(Integer idEspecialidad){
        ArrayList<Doctor> lstDoctorCache = null;
        lstDoctorCache = doctorDao.listarDoctoresEsp(idEspecialidad);

        return lstDoctorCache;
    }







    public ArrayList<DatosCita> filtrarCitas(DatosCita datosCita){
        ArrayList<DatosCita> lstCitas = null;

        lstCitas = citasDao.buscarCitas(datosCita);


        return lstCitas;
    }

    public boolean anularCita(DatosCita datosCita){
        return citasDao.anularCita(datosCita) ;

    }
    public Paciente consultaPacientexid(int idpaciente){
        return pacienteDao.ejecutarQueryxid(idpaciente);
    }
    public boolean actualizarPaciente(Paciente paciente){
        return pacienteDao.actualizarPaciente(paciente) ;
    }

    public void actualizarCita(DatosCita datosCita) {
        citasDao.actualizarCita(datosCita);

    }

    public void nuevaCita(DatosCita datosCita) {
        datosCita.setDetalleConsulta("");
        datosCita.setEstado("PROGRAMADO");
        citasDao.nueaCita(datosCita);

    }


}
