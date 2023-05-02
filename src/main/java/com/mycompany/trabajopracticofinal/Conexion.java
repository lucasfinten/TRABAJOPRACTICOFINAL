package com.mycompany.trabajopracticofinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection conectar = null;
    String usuario = "root";
    String contraseña = "1234";
    String bd = "argprograma";
    String ip = "localhost";
    String puerto = "3306";
    //ruta a DB
    String ruta = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    public Connection estableceConexion() {

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(ruta, usuario, contraseña);
            System.out.println("\n*La base de datos a sido conectada correctamente*");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(" No se conecto, vuelva a intentarlo mas tarde " + e);
        }
        return conectar;
    }

    public void cerrarConnection() throws SQLException {
        try {
            conectar.close();
            System.out.println("*Se ah desconectado satisfactoriamente*\n");
        } catch (SQLException e) {
        }
    }
}
