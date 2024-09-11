package com.project.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conex {
    public static Connection getConection(){
        Connection conexion = null;
        var baseDatos = "hospitalx";
        var url ="jdbc:mysql://localhost:3306/" + baseDatos;
        var usuario= "root";
        var password= "winchi01";

        //aqui importo la clase del driver de jdbc en la memoria

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("paso un error durante la coneccion revisa bien las credenciales"
                    + e.getMessage());
        }
        return  conexion;
    }

    public static void main(String[] args) {
        var conexion = com.project.config.conex.getConection();
        if (conexion != null){
            System.out.println("conexion existosa:   " + conexion);
        }else {
            System.out.println("error a la hora de conectarse");
        }

    }

}
