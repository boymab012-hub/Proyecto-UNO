package Partida;

import Carta.Carta;
import CartaDAO.DaoCarta;
import Jugador.Jugador;

import java.sql.SQLOutput;
import java.util.*;

public class Partida {


private ArrayList<Carta>mazoMesa;
private HashMap<Integer, Jugador>jugadores;
private Scanner sc;

public Partida(){

    this.jugadores = new HashMap<>();

    //extraemos las cartas de la base de datos y guardamos en atributo de partida
    this.mazoMesa = DaoCarta.cargarMazo();

    //metodo util para mezclar cartas dentro del arraylist
    Collections.shuffle(mazoMesa);

    sc = new Scanner(System.in);

}

//AQUI INICIAMOS TODO EL JUEGO PARA UN MAIN LIMPIO DX.
public void inicarPartida(){



    crearJugadores();

    mostrarJugadores();






}













//metodo para preguntar crear el jugador por medio de scanner

    public void crearJugadores(){

        boolean salir = false;
        int i = 1;

       do {

            System.out.println("Jugador: " + i + " Que Nombre De Usuario Deseas? ");

            String respuesta = sc.nextLine();

            ingresarJugador(i,respuesta);

            //comprobamos limite de 4 jugadores
            if( i == 4){

                System.out.println("Se ha alcanzado el maximo de jugadores");

                salir = true;
            }else {

                System.out.println("Quieres añadir otro jugador ?: ");


                respuesta = sc.nextLine();

            }



           if(respuesta.equalsIgnoreCase("si")){


               i++;

           }else if (respuesta.equalsIgnoreCase("no")){

               //comprobamos que el minimo sea 2 jugadores
               if( i < 2){

                   System.out.println("Tienen que haber 2 jugadores minimos");

                   i++;

               } else {

                   salir = true;
               }
           }



        //ponemos como limite 4 jugadores y si el usuario no quiere mas jugadores sale del bucle
       }while (!salir);

    }



//metodo para añadir jugador al hashmap por clave valor
public void ingresarJugador(Integer clave, String nombre){
    jugadores.put(clave,new Jugador(nombre));
    System.out.println("Jugador: " + nombre + " agregado correctamente");
}



//metodo para mostrar jugadores
    //recorremos el hashmap usando su clave y  valor

    public void mostrarJugadores(){

        for (Map.Entry<Integer,Jugador> entry : jugadores.entrySet()){

            Integer clave = entry.getKey();
            Jugador jugador = entry.getValue();

            System.out.println("Jugador: " + clave +" Nombre: " + jugador.getNombre());
        }

    }



}
