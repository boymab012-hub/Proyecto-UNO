package Partida;

import Carta.Carta;
import CartaDAO.DaoCarta;
import Jugador.Jugador;
import java.util.*;

public class Partida {


private ArrayList<Carta>mazoMesa;
private HashMap<Integer, Jugador>jugadores;
private Carta cartaMesa;
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


    // este metodo te pregunta el nombre que quieres poner al jugador
    //tambien cuantos jugadores quieres ingresar
    //tambien condiciones de maximo 4 jugadores y minimo 2
    crearJugadores();

    //este metodo te muestra los jugadores registrados
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

            //comprobamos limite de 4 jugadores para que no pregunte de nuevo
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


        //condicion que cambia dentro del bucle
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

//-----------------METODOS POR IMPLEMENTAR LOGICA-----------------


    //carta de la mesa boca arriba
    public void mostrarCartaMesa(){

    }

    //Repartimos 7 cartas a cada jugador, Recorriendo el hashmap y usando metodo recibir carta de Jugador
    public void repartirMazoMesa(){

}




    // decidimos que jugador actua
    public void turnoJugador(Jugador jugador){

    }

    //METODO IMPORTANTE!!!! Aqui pienso implementar logica para el efecto de la carta
    //LA CLASE CARTA!! tendra su metodo de AplicarEfecto sobre CartaMesa o Jugador
   public void comprobarCarta(Carta carta){


   }



}

