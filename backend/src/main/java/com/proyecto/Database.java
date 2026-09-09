package com.proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:interbase://localhost/C:/InterBase/data/gestion_financiera.IB";
    private static final String USUARIO = "SYSDBA";
    private static final String CLAVE = "masterkey";

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("interbase.interclient.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontro el driver de InterBase.");
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}