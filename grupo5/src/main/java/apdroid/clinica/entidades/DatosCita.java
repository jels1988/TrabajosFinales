package apdroid.clinica.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AngeloPaulo on 03/septiembre/2015.
 */
public class DatosCita implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DatosCita> CREATOR = new Parcelable.Creator<DatosCita>() {
        @Override
        public DatosCita createFromParcel(Parcel in) {
            return new DatosCita(in);
        }

        @Override
        public DatosCita[] newArray(int size) {
            return new DatosCita[size];
        }
    };

    private Integer idCita;
    private Integer idDoctor;
    private Integer idPaciente;

    private String especialidad;
    private String doctor;
    private String hora;
    private String fecha;


    private String detalleConsulta ;
    private String estado;
    private String local ;

    private Integer idEspecialidad;



    public DatosCita() {
        idCita = -1;
    }

    public DatosCita(String nombre, String especialidad, String doctor, String hora, String fecha, String detalleconsulta, String estado, String local) {
        //this.nombre = nombre;
        this.especialidad = especialidad;
        this.doctor = doctor;
        this.hora = hora;
        this.fecha = fecha;
        this.detalleConsulta =  detalleconsulta ;
        this.estado =  estado ;
        this.local = local ;
    }

    protected DatosCita(Parcel in) {
        //nombre = in.readString();
        especialidad = in.readString();
        doctor = in.readString();
        hora = in.readString();
        fecha = in.readString();
        detalleConsulta = in.readString();
        estado  =in.readString();
        local = in.readString() ;
        idCita = in.readInt()  ;

    }

    //<editor-fold desc="Getters and Setters">



    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    //</editor-fold>

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDetalleConsulta() {
        return detalleConsulta;
    }

    public void setDetalleConsulta(String detalleConsulta) {
        this.detalleConsulta = detalleConsulta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString(nombre);
        dest.writeString(especialidad);
        dest.writeString(doctor);
        dest.writeString(hora);
        dest.writeString(fecha);
        dest.writeString(detalleConsulta);
        dest.writeString(estado);
        dest.writeString(local);
        dest.writeInt(idCita);
    }

}