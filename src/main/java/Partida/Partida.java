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
private int acumulacion = 0;
private boolean acumulacionActivaMasCuatro = false;
private boolean acumulacionActiva = false;
private String colorActivo;
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


//----------------METODO PARA CAMBIAR COLOR DE LA MESA-----------

    public void activarColorMesa(){



        System.out.println("↓↓↓↓↓↓↓ ¿ QUE COLOR QUIERES QUE HALLA EN LA MESA ?↓↓↓↓↓↓↓");
        System.out.println();

        String respuesta = sc.nextLine();

        //metodo de seguridad para elegir solo entre los 4 colores
        while(!respuesta.equalsIgnoreCase("rojo") &&
                !respuesta.equalsIgnoreCase("azul") &&
                !respuesta.equalsIgnoreCase("verde") &&
                !respuesta.equalsIgnoreCase("amarillo")) {

        System.out.println("1. ROJO ");
        System.out.println("2. AZUL ");
        System.out.println("3. VERDE ");
        System.out.println("4. AMARILLO ");

        if(respuesta.equalsIgnoreCase("rojo")){

            System.out.println("Haz cambiado el color de la mesa a Rojo");

            colorActivo = "rojo";

        } else if (respuesta.equalsIgnoreCase("azul")) {

            System.out.println("Haz cambiado el color de la mesa a Azul");

            colorActivo = "azul";

        } else if (respuesta.equalsIgnoreCase("verde")) {

            System.out.println("Haz cambiado el color de la mesa a Verde");

            colorActivo = "verde";
        }else {

            System.out.println("Haz cambiado el color de la mesa a Amarillo");

            colorActivo = "amarillo";

        }

        }//llave WHILE
    }






//--------------------↓↓↓↓↓↓↓↓↓METODOS DE COMPROBACION DE CARTAS↓↓↓↓↓↓↓↓↓------------------------

//METODO QUE COMPRUEBA Y EJECUTA CARTA +4 ↓↓↓↓↓↓↓↓↓

    public void comprobarCartaMasCuatro(Jugador jugador){

//SI TIENE CARTA +4 LA PUEDE JUGAR , SINO RECIBE EFECTO +4
        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false; //si la carta que elegimos se puede usar cambia a valida
        boolean poseerCarta = false; // cuando encontremos una carta cambia a poseerCarta
        Carta cartaJugada;// variable que guarda el retorno de la carta elegida

        //METODO PARA ENCONTRAR CARTA  +4 EN MANO DE JUGADOR

        //si cartaMesa es +4
        if (cartaMesa.getValor().equalsIgnoreCase("+4")){


            //forEach
            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador
                if(cartaJugador.getValor().equalsIgnoreCase(cartaMesa.getValor())){

                    poseerCarta = true;
                }

            }

            //si el jugador tiene cartas en la mano puede usarla
            if(poseerCarta ){


                //metodo se seguridad para que la carta elegida sea un +4

                while(!cartaValida) {


                    cartaJugada = jugador.usarCarta();

                    if(cartaJugada.getValor().equalsIgnoreCase("+4")){

                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());


                    if (!acumulacionActivaMasCuatro){

                        acumulacion = 4;
                        acumulacionActivaMasCuatro= true;
                    }else{

                        acumulacion += 4 ;

                    }

                    //si la acumulacion esta activa

                        activarColorMesa();


                    }else {

                        System.out.println("La carta elegida no es un +4");

                    }

                }// LLAVE while


                //si el jugador no tiene carta +4 recibe el efecto de la carta
            }else {

                if(acumulacionActivaMasCuatro){

                    repartirAcumulacion(jugador,acumulacion);

                    acumulacionActivaMasCuatro = false;
                    acumulacion = 0;

                }else{

                    repartir4Cartas(jugador);
                }




            }

        }

    }




    //METODO QUE COMPRUEBA Y EJECUTA CARTA +2 ↓↓↓↓↓↓↓↓↓

    public void comprobarCartaMasDos(Jugador jugador){

//SI TIENE CARTA +2 LA PUEDE JUGAR , SINO RECIBE EFECTO +2
        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false;
        boolean poseerCarta = false;
        Carta cartaJugada;


        //METODO PARA ENCONTRAR CARTA  +2 EN MANO DE JUGADOR

        //si cartaMesa es +2
        if (cartaMesa.getValor().equalsIgnoreCase("+2")){

            //forEach
            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador +2 o un+4
                if(cartaJugador.getValor().equalsIgnoreCase(cartaMesa.getValor()) ||
                cartaJugador.getValor().equalsIgnoreCase("+4")) {

                    poseerCarta = true;
                }

            }

            //si el jugador tiene cartas en la mano puede usarla
            if(poseerCarta ){

                //metodo se seguridad para que la carta elegida sea un +2 o +4

                while(!cartaValida) {


                    cartaJugada = jugador.usarCarta();

                    //quiero permitir que si antes alguien uso un +2 se puede usar un +4
                    if(cartaJugada.getValor().equalsIgnoreCase("+2") ||
                            cartaJugada.getValor().equalsIgnoreCase("+4")){


                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());


                        //si carta jugada es +2 se acumula 2 en acumulacion
                        if (cartaJugada.getValor().equalsIgnoreCase("+2")){

                            //si no han lanzado antes un +2 acumulacion empieza en +2
                        if (!acumulacionActiva){

                            acumulacion = 2;
                            acumulacionActiva = true;
                        }
                            else{

                                acumulacion += 2;
                        }

                            //llave if +2
                            }    else {

                            acumulacionActivaMasCuatro = true;
                            acumulacion +=4;

                            }


                    }else {

                        System.err.println("LA CARTA ELEGIDA NO ES UN +2 O UN +4");

                    }

                }// LLAVE while


                //si el jugador no tiene carta +2 recibe el efecto de la carta
            }else {

                //si hay acumulacion anterior recibe ese cumulo de cartas
                if (acumulacionActiva || acumulacionActivaMasCuatro){

                    repartirAcumulacion(jugador,acumulacion);

                    acumulacionActiva = false;
                    acumulacionActivaMasCuatro = false;
                    acumulacion = 0;


                    //sino recibe solo 2 cartas
                }else{

                    repartir2Cartas(jugador);
                }


            }
        }

    }



    public void comprobarCartaCambioColor(Jugador jugador){

        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false;
        boolean poseerCarta = false;
        Carta cartaJugada;


        if(cartaMesa.getValor().equalsIgnoreCase("cambio_color")){


            for (Carta carta : manoJugador){

                if (carta.getColor().equalsIgnoreCase(colorActivo) || carta.getTipo().equalsIgnoreCase("comodin")){

                    poseerCarta = true;

                }

            }

            if(poseerCarta){

                while (!cartaValida){


                cartaJugada = jugador.usarCarta();

                if(cartaJugada.getColor().equalsIgnoreCase(colorActivo) || cartaJugada.getTipo().equalsIgnoreCase("comodin")){

                    cartaValida = true;

                    cambiarCartaMesa(cartaJugada);

                    jugador.soltarCartaUsada(cartaJugada);


                    System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());


                    //estas acumulaciones son para luego si el siguiente jugador tiene +4 o +2  se sigue acumulando
                    //activamos acumulacion si es +4
                    if (cartaJugada.getValor().equalsIgnoreCase("+4")){

                        acumulacionActivaMasCuatro= true;
                        acumulacion += 4;

                        activarColorMesa();

                        //activamos acumulacion si es +2
                    }else if (cartaJugada.getValor().equalsIgnoreCase("+2")){

                        acumulacionActiva = true;
                        acumulacion = 2;

                    } else if (cartaJugada.getValor().equalsIgnoreCase("cambio_color")) {

                        activarColorMesa();

                        //NUEVA IMPLEMENTACION PARA CARTA PASAR
                    }else if (cartaJugada.getColor().equalsIgnoreCase(colorActivo) &&
                            cartaJugada.getValor().equalsIgnoreCase("SALTAR")){


                        //NUEVA IMPLEMENTACION PARA CARTA REVERSA
                    } else if (cartaJugada.getColor().equalsIgnoreCase(colorActivo) &&
                    cartaJugada.getTipo().equalsIgnoreCase("REVERSA")){

                    }

                }else {

                    System.err.println("LA CARTA ELEGIDA NO ESTA PERMITIDA");
                }

                }//llave WHILE


            }else{

                repartir1Carta(jugador);

                    //si la ultima carta que recibo se puede usar  (color o comodin)
                if(manoJugador.get(manoJugador.size()- 1).getColor().equalsIgnoreCase(colorActivo) ||
                        manoJugador.get(manoJugador.size()- 1).getTipo().equalsIgnoreCase("comodin")){


                    Carta cartaRecibida = manoJugador.get(manoJugador.size() - 1);

                    System.out.println("LA CARTA QUE RECIBISTE ES: " + cartaRecibida.toString() );
                    System.out.println("PUEDES USARLA O QUEDARTELA QUE OPCION ELIGES ?");
                    System.out.println("OPCION 1 - USAR CARTA ");
                    System.out.println("OPCION 2 - CONSERVAR CARTA");

                    int respuesta;

                    respuesta = sc.nextInt();


                    //SI RESPUESTA ES 1 SE USA LA CARTA Y SE APLICA LOS EFECTOS
                    //SI RESPUESTA ES 2 PASA AL SIGUIENTE TURNO Y SE MANTIENE cartaMesa ANTERIOR

                    if (respuesta == 1){

                        cambiarCartaMesa(cartaRecibida);

                        jugador.soltarCartaUsada(cartaRecibida);

                        //EVALUAMOS DE NUEVO SI LA CARTA JUGADA ES UN +4 O +2 O SINO O SI ES OTRO COMODIN O SI ES UNA CARTA NUMERO
                        //YA QUE HAY EFECTOS AL USAR LA CARTA

                        ejecutarCartaRecibidaEnColor(cartaRecibida,jugador);

                    }else{

                        System.out.println("HAZ DECIDIDO CONSERVAR TU CARTA !");

                    }










                }

            }



        }



    }

public void ejecutarCartaRecibidaEnColor(Carta cartaRecibida,Jugador jugador){


    if(cartaRecibida.getValor().equalsIgnoreCase("+4")){

        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaRecibida.toString());


        acumulacion = 4;
        acumulacionActivaMasCuatro= true;


    }else if (cartaRecibida.getValor().equalsIgnoreCase("+2") &&
            cartaRecibida.getColor().equalsIgnoreCase(colorActivo)){

        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaRecibida.toString());

        acumulacionActiva = true;
        acumulacion = 2;

    }else if(cartaRecibida.getValor().equalsIgnoreCase("cambio_color")){

        activarColorMesa();

        //NUEVA IMPLEMENTACION SALTAR
    }else if (cartaRecibida.getValor().equalsIgnoreCase("SALTAR") &&
            cartaRecibida.getColor().equalsIgnoreCase(colorActivo)){



        //NUEVA IMPLEMENTACION REVERSA

    } else if (cartaRecibida.getValor().equalsIgnoreCase("REVERSA") &&
            cartaRecibida.getColor().equalsIgnoreCase(colorActivo)) {

    }








}

//------------------↑↑↑↑↑↑↑↑↑↑↑ METODOS DE COMPROBACION DE CARTAS ↑↑↑↑↑↑↑↑↑↑↑------------





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





//-----------↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ METODOS DE ADMINISTRACION DE JUGADORES ↓↓↓↓↓↓↓↓↓----------

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

    //--------↑↑↑↑↑↑↑↑↑↑ METODOS PARA ADMINISTRAR JUGADORES ↑↑↑↑↑↑↑↑↑↑-----




//-----------------METODOS POR IMPLEMENTAR LOGICA-----------------


    //carta de la mesa boca arriba
    public void mostrarCartaMesa(){

    }






    //-------↓↓↓↓↓↓↓↓ METODOS DE REPARTICION DE CARTAS ↓↓↓↓↓↓↓↓↓-----------

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

    public void repartirAcumulacion(Jugador jugador, int acumulado){


    for(int i = 0; i < acumulado; i++){

        jugador.recibirCarta(mazoMesa.remove(i));

    }
            //al repartir lo acumulado todo vuelve a inicio
        acumulacionActiva = false;
        acumulacionActivaMasCuatro = false;
        acumulacion = 0;


    }


/* --------------------NUEVAS IMPLEMENTACIONES QUE FALTAN------
1.COMPROBACION DE masoMeza Si esta vacio
2.INSERTAR EFECTO DE SALTAR
3.INSERTAR EFECTO DE REVERSA
* */






}

