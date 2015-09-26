package apdroid.clinica.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edinson on 20/09/2015.
 */
public class Paciente implements Parcelable {
    private int    id_paciente;
    private String num_dni;
    private String usuario;
    private String password ;
    private String nombres;
    private String apellidos ;
    private String correo;
    private String estilo;
    private String idioma ;

    public Paciente() {
    }

    public Paciente(int id_paciente, String num_dni, String usuario, String password, String nombres, String apellidos, String correo, String estilo, String idioma) {
        this.id_paciente = id_paciente;
        this.num_dni = num_dni;
        this.usuario = usuario;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.estilo = estilo;
        this.idioma = idioma;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNum_dni() {
        return num_dni;
    }

    public void setNum_dni(String num_dni) {
        this.num_dni = num_dni;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    protected Paciente(Parcel in) {
        id_paciente = in.readInt();
        num_dni = in.readString();
        usuario = in.readString();
        password = in.readString();
        nombres = in.readString();
        apellidos = in.readString();
        correo = in.readString();
        estilo = in.readString();
        idioma = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_paciente);
        dest.writeString(num_dni);
        dest.writeString(usuario);
        dest.writeString(password);
        dest.writeString(nombres);
        dest.writeString(apellidos);
        dest.writeString(correo);
        dest.writeString(estilo);
        dest.writeString(idioma);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Paciente> CREATOR = new Parcelable.Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };
}