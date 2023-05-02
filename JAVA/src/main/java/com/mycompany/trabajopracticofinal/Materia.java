package com.mycompany.trabajopracticofinal;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Materia {

    
    String nombre;
    List<String> correlativas = new ArrayList<>();

    
    public Materia(String nombre) {
        this.nombre = nombre;
    }

   
    public Materia() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<String> correlativas) {
        this.correlativas = correlativas;
    }

    
    public static void crearMateria() throws SQLException {

        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        Conexion conexion = new Conexion();

        Materia materia = new Materia();

        System.out.println("Que nombre quiere que tenga la materia?");
        String nombre = sc.next();
        materia.setNombre(nombre);

        System.out.println("Cuantas correlativas tiene?");

        int numero = sc.nextInt();

        System.out.println("Que materias desea agregar a las correlativas?");
        ArrayList<String> correlativas = new ArrayList<>();

        String input;

        for (int i = 0; i < numero; i++) {
            input = sc.next();
            correlativas.add(input);
        }

        String correlativasJson = new Gson().toJson(correlativas);

        conexion.estableceConexion();
        Statement stmt = conexion.conectar.createStatement();
        stmt.executeUpdate("INSERT INTO materias_final VALUES(\"" + nombre + "\",'" + correlativasJson + "');");
        System.out.println("++++++ Materia y correlativas creadas correctamente ++++++");
        conexion.cerrarConnection();
    }
}
