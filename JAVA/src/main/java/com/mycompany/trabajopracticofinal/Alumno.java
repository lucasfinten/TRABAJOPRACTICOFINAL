package com.mycompany.trabajopracticofinal;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Alumno {

    
 String nombre;
 int legajo;
  

ArrayList<String> materiasAprobadas = new ArrayList<>();

   
    public Alumno(String nombre, int legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
       
    }
    public Alumno() {
    }
    public String getNombre() {
    return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getLegajo() {
        return legajo;
    }
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
    public ArrayList<String> getMateriasAprobadas() {
        return materiasAprobadas;
    }
    public void setMateriasAprobadas(ArrayList<String> materiasAprobadas) {
        this.materiasAprobadas = materiasAprobadas;
    }
    @Override
    public String toString() {
        return "Alumno{" + "nombre=" + nombre + "legajo=" + legajo + " materiasAprobadas=" + materiasAprobadas + '}';
    }
    public static void agregarAlumnos() throws SQLException {
        Scanner sc = new Scanner(System.in);
        Conexion conexion = new Conexion();
        int legajo;
  
        System.out.println("Ingrese el nombre del alumno: ");
        String nombre = sc.nextLine();
        
        while (!nombre.matches("^[a-zA-Z\\s]+$")) {
            System.out.println("Error. Ingrese nombre del alumno: ");
            nombre = sc.nextLine();
        }
        
        do {
            System.out.println("Ingrese el numero de legajo de 5 digitos: ");
            while (!sc.hasNextInt()) {
                System.out.println("Error. Ingrese legajo de 5 digitos: ");
                sc.next();
            }
            legajo = sc.nextInt();
        } while (!String.valueOf(legajo).matches("[0-9]{5}"));

        System.out.println("+ Datos Correctos\nAlumno: " + nombre
                + "\nLegajo: " + legajo
                + "\n+");

        System.out.println("¿Cuantas materias tiene aprobadas?");
        int numMatApr = sc.nextInt();

        ArrayList<String> materiasAprobadas = new ArrayList<>();
        System.out.println("¿Que materias tiene aprobadas?");
        for (int i = 0; i < numMatApr; i++) {
            materiasAprobadas.add(sc.next());
        }

        String aprobadasJson = new Gson().toJson(materiasAprobadas);

        
        conexion.estableceConexion();

        Statement stmt = conexion.conectar.createStatement();
        stmt.executeUpdate("INSERT INTO alumnos VALUE (\"" + nombre + "\"," + legajo + "," + aprobadasJson + ");");

        System.out.println("\n+Sus datos fueron cargados de manera correcta+\nAlumno: " + nombre
                + "\nLegajo N°: " + legajo
                + "\nMaterias Aprobadas: " + materiasAprobadas) ;
        System.out.println("+\n");
        
        conexion.cerrarConnection();
    }
      }

  