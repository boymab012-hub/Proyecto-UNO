package CartaDAO;

import Carta.Carta;
import ConexionBD.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoCarta {


    //Metodo que conecta a la base de datos y devuelve un arrayList con el mazo completo

    public static ArrayList<Carta> cargarMazo(){

        ArrayList<Carta>mazo = new ArrayList<>();

        //Declaramos la consulta y guardamos en variable sql

        String sql = "SELECT * FROM cartas";

        //Guardamos conexion base de dato en variable con

        Connection con = ConexionBD.getConnection();

        //colocamos una comprobacion de conexion

        if(con == null){

            System.out.println("conexion fallida");

            return mazo;
        }



        // creamos un tryCatch por si da error
        try{

            //cremos objeto con conexion a base de datos para ejecutar sql
            Statement st =con.createStatement();

            //creamos objeto de tipo ResultSet que guarda las filas de msql
            //y ejecutamos la consulta con el objeto st que devuelve las filas de la bs
            ResultSet rs = st.executeQuery(sql);

            //bucle para recorrer filas
            while(rs.next()){

                //por cada fila creamos un objeto de tipo carta
                Carta carta = new Carta(
                        rs.getInt("id"),
                        rs.getString("color"),
                        rs.getString("valor"),
                        rs.getString("tipo")
                );

                //guardamos cada objeto de tipo carta en el arraylist
                mazo.add(carta);
            }



            //cerramos conexiones
            rs.close();
            st.close();
            con.close();

            System.out.println("Mazo cargado : " + mazo.size() + " Cartas");

        }catch (SQLException e){

            System.err.println("Error al cargar mazo: " + e.getMessage());
        }

        //retornamos el mazo completo
        return mazo;
    }



}
