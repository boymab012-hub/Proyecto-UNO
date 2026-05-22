import Carta.Carta;
import ConexionBD.ConexionBD;
import Jugador.Jugador;
import Partida.Partida;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

    /*
       Partida partida1 = new Partida();

        partida1.crearJugadores();

        partida1.mostrarJugadores();



        //               PRUEBASSSSS     //

        Jugador jugador1 = new Jugador("PEDRO");

        jugador1.getMano().add( new Carta(1,"Rojo","0","Numero"));
        jugador1.getMano().add( new Carta(2,"Verde","8","Numero"));
        jugador1.getMano().add( new Carta(3,"Amarillo","5","Numero"));
        jugador1.getMano().add( new Carta(4,"Morado","2","Numero"));





       Carta carta =  jugador1.usarCarta();

        System.out.println(carta.toString());

        System.out.println("El jugador + " + jugador1.getNombre() + "Ha usado la carta " + carta.toString());


*/


/*
        Partida partida1 = new Partida();

                partida1.ingresarJugador(1,"Darwin");
                partida1.ingresarJugador(2,"PEDRO");

                partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(1,"ESPECIAL","+4","COMODIN"));


            partida1.getJugadores().get(2).añadirCartaPrueba(new Carta(1,"ESPECIAL","+4","COMODIN"));
            partida1.getJugadores().get(2).añadirCartaPrueba(new Carta(1,"ESPECIAL","+4","COMODIN"));
            partida1.getJugadores().get(2).añadirCartaPrueba(new Carta(1,"ESPECIAL","+4","COMODIN"));


            partida1.inicarPartida();


*/


        Partida partida1 = new Partida();
        partida1.inicarPartida();


    }





}
