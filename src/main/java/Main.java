import ConexionBD.ConexionBD;
import Partida.Partida;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {


       Partida partida1 = new Partida();

        partida1.crearJugadores();

        partida1.mostrarJugadores();



    }



}
