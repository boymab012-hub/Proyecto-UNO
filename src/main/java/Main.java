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



        Partida partida1 = new Partida();

                partida1.ingresarJugador(1,"Darwin");
                partida1.ingresarJugador(2,"PEDRO");

                partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(1,"ESPECIAL","+4","COMODIN"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(1,"VERDE","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(2,"AZUL","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(3,"AMARILLO","REVERSA","ACCION"));
        partida1.getJugadores().get(2).añadirCartaPrueba(new Carta(4,"ROJO","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(5,"VERDE","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(6,"AZUL","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(7,"AMARILLO","REVERSA","ACCION"));
        partida1.getJugadores().get(2).añadirCartaPrueba(new Carta(8,"ROJO","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(9,"VERDE","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(10,"AZUL","REVERSA","ACCION"));
        partida1.getJugadores().get(1).añadirCartaPrueba(new Carta(11,"AMARILLO","REVERSA","ACCION"));
        partida1.getJugadores().get(2).añadirCartaPrueba(new Carta(12,"ROJO","REVERSA","ACCION"));




        partida1.inicarPartida();




    }





}
