package com.mycompany.trabajopracticofinal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Inscripcion {

   
    Materia materia;
    Alumno alumno;
    Date fecha = new Date();

    
    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
    }

    
    public Inscripcion() {
    }

    
    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void aprobada(Alumno alumno, Materia materia) {
        if (alumno.getMateriasAprobadas().containsAll(materia.getCorrelativas())) {
            System.out.println("Aprobado. " + "El alumno: " + alumno.getNombre()
                    + ", con Legajo N°: " + alumno.getLegajo()
                    + ", usted puede inscribirse a la materia: " + materia.getNombre() + ".");
        } else {
            System.out.println("Desaprobado. anda pa ya.");

        }
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "materia=" + materia + ", alumno=" + alumno + ", fecha=" + fecha + '}';
    }

    public static void verificacion() throws SQLException, JsonProcessingException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el Legajo del alumno");
        int legajoAlumno = sc.nextInt();
        System.out.println("¿A que materia se quiere inscribir?");
        String nombreMateria = sc.next();

        Inscripcion insc = new Inscripcion();
        insc.aprobada(traerDatosAlumno(legajoAlumno), traerDatosMateria(nombreMateria));
    }

    public static Alumno traerDatosAlumno(int legajo) throws SQLException, JsonProcessingException {

        Conexion conexion = new Conexion();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        Alumno alumno = new Alumno();

        conexion.estableceConexion();
        Statement stmt = conexion.conectar.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM alumnos WHERE legajo=" + legajo + ";");
        rs.next();

        
        alumno.setNombre(rs.getString("nombre"));
        alumno.setLegajo(rs.getInt("legajo"));

        
        String jsonTextAprobadas = mapper.writeValueAsString(rs.getString("materias_aprobadas"));
        ArrayList<String> nombreCorrelativas = mapper.readValue(jsonTextAprobadas, ArrayList.class);
        alumno.setMateriasAprobadas(nombreCorrelativas);

        conexion.cerrarConnection();

        return alumno;
    }

    public static Materia traerDatosMateria(String nombre) throws SQLException, JsonProcessingException {

        Conexion conexion = new Conexion();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        conexion.estableceConexion();
        Statement stmt = conexion.conectar.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM materias_final WHERE nombre=\"" + nombre + "\";");
        rs.next();

        Materia materia = new Materia(rs.getString("nombre"));
        materia.setCorrelativas(mapper.readValue(mapper.writeValueAsString(rs.getString("correlativas")), ArrayList.class));

        conexion.cerrarConnection();

        return materia;
    }
}
