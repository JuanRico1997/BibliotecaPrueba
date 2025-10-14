package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDb {
    public static Connection objConnection = null;

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String URL = "jdbc:mysql://localhost:3306/bibliotecaNova";
            String USER = "root";
            String PASSWORD = "Qwe.123*";

            objConnection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException e){
            System.out.println("Driver no instalado " + e.getMessage());
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        return objConnection;
    }

    public static void closeConnection() {
        try{
            if(objConnection != null){
                if(!objConnection.isClosed()){
                    objConnection.close();
                    System.out.println("Conexion cerrada");
                }
            }
        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
