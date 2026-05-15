package ConexionBD;

import java.sql.*;

public class ConexionBD {

    public static Connection instance = null;
    public static final String url = "jdbc:mysql://localhost:3306/uno_db";
    public static final String usuario ="root";
    public static final String contraseña= "0414358062dD#";




    public static Connection getConnection() {

        try {

            if (instance == null) {

                instance = DriverManager.getConnection(url, usuario, contraseña);

                System.out.println("Conexion establecida");
            }

        } catch (SQLException e) {

            System.out.println("Error en la conexion");
            e.printStackTrace();
        }

        return instance;
    }
}