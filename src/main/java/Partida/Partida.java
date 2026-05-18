package Partida;

import Carta.Carta;
import CartaDAO.DaoCarta;
import Jugador.Jugador;
import java.util.*;

public class Partida {


private ArrayList<Carta>mazoMesa;
private ArrayList<Carta>mazoBasura;
private Carta cartaMesa;
private boolean reversa = false;
private int acumulacion;
private boolean acumulacionActiva = false;
private HashMap<Integer, Jugador>jugadores;
public Scanner sc;

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




public void turnoJugador(Jugador jugador){

comprobarManoJugador(jugador);

}



//METODO QUE COMPRUEBA Y EJECUTA MANO DE JUGADOR
public void comprobarManoJugador(Jugador jugador){

    if (cartaMesa.getValor().equalsIgnoreCase("+4")){

        comprobarCartaMasCuatro(jugador);

    }else if (cartaMesa.getValor().equalsIgnoreCase("+2")){

        comprobarCartaMasDos(jugador);
    }



}

//--------------------↓↓↓↓↓↓↓↓↓METODOS DE COMPROBACION DE CARTAS↓↓↓↓↓↓↓↓↓------------------------

//METODO QUE COMPRUEBA Y EJECUTA CARTA +4 ↓↓↓↓↓↓↓↓↓

    public void comprobarCartaMasCuatro(Jugador jugador){

//SI TIENE CARTA +4 LA PUEDE JUGAR , SINO RECIBE EFECTO +4
        ArrayList<Carta>manoJugador = jugador.getMano();

        //METODO PARA ENCONTRAR CARTA  +4 EN MANO DE JUGADOR

        //si cartaMesa es +4
        if (cartaMesa.getValor().equalsIgnoreCase("+4")){
            boolean cartaValida = false;
            boolean poseerCarta = false;

            //forEach
            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador
                if(cartaJugador.getValor().equalsIgnoreCase(cartaMesa.getValor())){

                    poseerCarta = true;
                }

            }

            //si el jugador tiene cartas en la mano puede usarla
            if(poseerCarta ){

                Carta cartaJugada;

                //metodo se seguridad para que la carta elegida sea un +4

                while(!cartaValida) {


                    cartaJugada = jugador.usarCarta();

                    if(cartaJugada.getValor().equalsIgnoreCase("+4")){

                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());


                    }else {

                        System.out.println("La carta elegida no es un +4");

                    }

                }// LLAVE while


                //si el jugador no tiene carta +4 recibe el efecto de la carta
            }else {

                repartir4Cartas(jugador);

            }

        }

    }




    //METODO QUE COMPRUEBA Y EJECUTA CARTA +2 ↓↓↓↓↓↓↓↓↓

    public void comprobarCartaMasDos(Jugador jugador){

//SI TIENE CARTA +2 LA PUEDE JUGAR , SINO RECIBE EFECTO +2
        ArrayList<Carta>manoJugador = jugador.getMano();

        //METODO PARA ENCONTRAR CARTA  +4 EN MANO DE JUGADOR

        //si cartaMesa es +2
        if (cartaMesa.getValor().equalsIgnoreCase("+2")){
            boolean cartaValida = false;
            boolean poseerCarta = false;

            //forEach
            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador
                if(cartaJugador.getValor().equalsIgnoreCase(cartaMesa.getValor())){

                    poseerCarta = true;
                }

            }

            //si el jugador tiene cartas en la mano puede usarla
            if(poseerCarta ){

                Carta cartaJugada;

                //metodo se seguridad para que la carta elegida sea un +4

                while(!cartaValida) {


                    cartaJugada = jugador.usarCarta();

                    if(cartaJugada.getValor().equalsIgnoreCase("+2")){

                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());


                    }else {

                        System.out.println("La carta elegida no es un +2");

                    }

                }// LLAVE while


                //si el jugador no tiene carta +4 recibe el efecto de la carta
            }else {

                repartir2Cartas(jugador);

            }
        }

    }





//------------------↑↑↑↑↑↑↑↑↑↑↑ METODOS DE COMPROBACION DE CARTAS↑↑↑↑↑↑↑↑↑↑↑------------





public void enviarCartaBasura(Carta carta){

    mazoBasura.add(carta);
}


public void cambiarMazoMesa(ArrayList<Carta>mazoBasura){

    mazoMesa = mazoBasura;

}

    //metodo que cambia la cartaMesa, guarda carta anterior en mazoBasura y agrega nueva carta
    public void cambiarCartaMesa(Carta carta){

        mazoBasura.add(cartaMesa);

        cartaMesa = carta;

    }



    //la primera cartaMesa no puede ser un comodin
    //usamos for each para recorrer el mazo y al encontrar una carta de tipo numero
    //  la retorne y salga del bucle

    public Carta sacarCartaMesa(){

        Carta cartaEncontrada = null;

            for (Carta carta : mazoMesa){

                if(carta.getTipo().equalsIgnoreCase("NUMERO")){

                    return carta;
                }
            }

            return null;
    }





//-----------↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓METODOS DE ADMINISTRACION DE JUGADORES↓↓↓↓↓↓↓↓↓----------

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



    public void mostrarJugadoresActivos(){

        for (Map.Entry<Integer,Jugador> entry : jugadores.entrySet()){

            Integer clave = entry.getKey();
            Jugador jugador = entry.getValue();

            if (jugador.getJugadorActivo()){

            System.out.println("Jugador: " + clave +" Nombre: " + jugador.getNombre());

            }

        }

    }

    //--------↑↑↑↑↑↑↑↑↑↑METODOS PARA ADMINISTRAR JUGADORES↑↑↑↑↑↑↑↑↑↑-----




//-----------------METODOS POR IMPLEMENTAR LOGICA-----------------


    //carta de la mesa boca arriba
    public void mostrarCartaMesa(){

    }



    //Repartimos 7 cartas a cada jugador, Recorriendo el hashmap y usando metodo recibir carta de Jugador
    public void repartirMazoMesa(){

    //Recorremos el hashmap y en cada vuelta dentro del for añadimos 7 cartas
                for(Jugador jugador : jugadores.values()){

                    for (int i=0; i < 7; i++){

                        //mazoMesa.remove() eliminacarta del array y lo devuelve
                        jugador.recibirCarta(mazoMesa.remove(i));
                    }
                }

}

//METODO QUE REPARTE SOLO 4 CARTAS

    //Aqui puedo crear un metodo para recibir la acumulacion en caso de que este activa

    public void repartir4Cartas(Jugador jugador){

        System.out.println("El jugador " + jugador.getNombre() + " recibe 4 cartas");

        for (int i = 0; i < 4; i++){

            jugador.recibirCarta(mazoMesa.remove(i));
        }

    }

    public void repartir2Cartas(Jugador jugador){

        System.out.println("El jugador " + jugador.getNombre() + " recibe 2 cartas");

        for (int i = 0; i < 2; i++){

            jugador.recibirCarta(mazoMesa.remove(i));
        }

    }


    public void repartir1Carta(Jugador jugador){

        System.out.println("El jugador " + jugador.getNombre() + " recibe 1 carta");

        jugador.recibirCarta(mazoMesa.remove(0));

    }








    //METODO IMPORTANTE!!!! Aqui pienso implementar logica para el efecto de la carta
    //LA CLASE CARTA!! tendra su metodo de AplicarEfecto sobre CartaMesa o Jugador
   public void comprobarCarta(Carta carta){


   }



}

