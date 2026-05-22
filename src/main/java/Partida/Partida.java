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
private boolean saltar = false;
private int acumulacion = 0;
private boolean acumulacionActiva = false;
private boolean colorActivo = false;
private String color;
private HashMap<Integer, Jugador>jugadores;
private boolean partidaActiva = true;
public Scanner sc;




public Partida(){

    this.jugadores = new HashMap<>();
    this.mazoBasura = new ArrayList<>();


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

   // crearJugadores();

    repartirMazoMesa();
    //este metodo te muestra los jugadores registrados
    mostrarJugadores();

    cartaMesa = sacarCartaMesa();

    mostrarCartaMesa();

    int clave = 1;


    while (partidaActiva){

        //si se uso anteriormente calta saltar se pasara el turno adelantado
    if (saltar){

       clave = comprobarSALTAR(clave);

        saltar = false;

        System.out.println("EL JUGADOR SIGUIENTE HA PERDIDO EL TURNO");

    }

        turnoJugador(jugadores.get(clave));

        comprobarGanador(jugadores.get(clave));


        mostrarCartaMesa();



            //retorna el sentido si es NORMAL dara el siguiente jugador si es REVERSA dara el anterior
        clave = comprobarSentido(clave);



    }// llave while

}


public void comprobarGanador(Jugador jugador){


    if (jugador.getMano().size() == 0){

        partidaActiva = false;

        System.out.println("\u001B[93m👑🏆 VICTORIA: " + jugador.getNombre() + " 🏆👑\u001B[0m");

    }
}


public void turnoJugador(Jugador jugador){


comprobarManoJugador(jugador);


}




    public int comprobarSentido(int posActual){


        if (reversa){
            //si posicion actual es 1 dame la ultima posicion
            if(posActual == 1){

                return jugadores.size();
            }
            //sino dame la posicion anterior
                return posActual - 1;

        } else {
            //si posicion actual es la ultima posicion dame la primera
            if(posActual == jugadores.size()){

                return 1;
            }
                //sino dame la posicion siguiente
                return posActual + 1;
            }
        }





public int comprobarSALTAR(int posActual){



        //si posicion actual es la ultima devuelveme la primera
    if (reversa){

        if (posActual == 1){

            return jugadores.size();
        }

        return  posActual - 1;
    }


        if (posActual == jugadores.size()){

            return 1;
        }

            //sino dame la posicion siguiente
            return posActual + 1;
}



//METODO QUE COMPRUEBA Y EJECUTA MANO DE JUGADOR
public void comprobarManoJugador(Jugador jugador){

    if (cartaMesa.getValor().equalsIgnoreCase("+4")){

        comprobarCartaMasCuatro(jugador);

    }else if (cartaMesa.getValor().equalsIgnoreCase("+2")){

        comprobarCartaMasDos(jugador);

    } else if (cartaMesa.getValor().equalsIgnoreCase("CAMBIO_COLOR")) {

        comprobarCartaCambioColor(jugador);

    }else if (cartaMesa.getValor().equalsIgnoreCase("SALTAR")){

    comprobarCartaSaltar(jugador);

    }else if(cartaMesa.getValor().equalsIgnoreCase("REVERSA")){

        comprobarCartaReversa(jugador);

    } else {

        comprobarCartaNumero(jugador);
    }

}


//----------------METODO PARA CAMBIAR COLOR DE LA MESA-----------

    public void activarColorMesa(){



        String respuesta;


        //metodo de seguridad para elegir solo entre los 4 colores
        do {
            System.out.println("↓↓↓↓↓↓↓ ¿ QUE COLOR QUIERES QUE HALLA EN LA MESA ?↓↓↓↓↓↓↓");
            System.out.println();


            System.out.println("1. ROJO ");
            System.out.println("2. AZUL ");
            System.out.println("3. VERDE ");
            System.out.println("4. AMARILLO ");

            respuesta = sc.nextLine();

            if (!respuesta.equalsIgnoreCase("rojo") &&
                    !respuesta.equalsIgnoreCase("azul") &&
                    !respuesta.equalsIgnoreCase("verde") &&
                    !respuesta.equalsIgnoreCase("amarillo")){

                System.out.println("EL COLOR ELEGIDO NO ES VALIDO ");
            }


        } while(!respuesta.equalsIgnoreCase("rojo") &&
                !respuesta.equalsIgnoreCase("azul") &&
                !respuesta.equalsIgnoreCase("verde") &&
                !respuesta.equalsIgnoreCase("amarillo"));



        if(respuesta.equalsIgnoreCase("rojo")){

            System.out.println("HAZ CAMBIADO EL COLOR DE LA MESA A "+"\u001B[31m" + "ROJO" + "\u001B[0m");
            System.out.println();
            System.out.println("\u001B[32m" + "COLOR ACTIVO: " + "\u001B[31m" + "(ROJO)" + "\u001B[0m");

            colorActivo = true;
            color = "rojo";


        } else if (respuesta.equalsIgnoreCase("Azul")) {

            System.out.println("HAZ CAMBIADO EL COLOR DE LA MESA A "+"\u001B[34m" + "AZUL" + "\u001B[0m");
            System.out.println();
            System.out.println("\u001B[32m" + "COLOR ACTIVO: " + "\u001B[34m" + "(AZUL)" + "\u001B[0m");

            colorActivo = true;
            color= "azul";

        } else if (respuesta.equalsIgnoreCase("Verde")) {

            System.out.println("HAZ CAMBIADO EL COLOR DE LA MESA A " + "\u001B[32m" + "VERDE" + "\u001B[0m");
            System.out.println();
            System.out.println("\u001B[32m" + "COLOR ACTIVO: " + "\u001B[32m" + "(VERDE)" + "\u001B[0m");

            colorActivo = true;
            color = "verde";
        }else {

            System.out.println("HAZ CAMBIADO EL COLOR DE LA MESA A "+"\u001B[33m" + "AMARILLO" + "\u001B[0m");

            System.out.println();
            System.out.println("\u001B[32m" + "COLOR ACTIVO: " + "\u001B[0m" + "\u001B[33m" + "(AMARILLO)" + "\u001B[0m");

            colorActivo = true;
            color = "amarillo";

        }


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



            //forEach
            for(Carta cartaJugador : manoJugador){

                if (acumulacionActiva && colorActivo){

                //comprobamos que halla carta para usar en la mano del jugador
                if(cartaJugador.getValor().equalsIgnoreCase("+4")){

                    poseerCarta = true;
                }

                }
                    if (!acumulacionActiva && colorActivo){

                        if ( cartaJugador.getValor().equalsIgnoreCase(color)){

                            poseerCarta = true;
                        }

                    }


            }


            //si el jugador tiene cartas en la mano puede usarla
            if(poseerCarta ){




                if(acumulacionActiva && colorActivo){
                    System.out.println("TIENES CARTA PARA JUGAR");
                    System.out.println();

                    //metodo se seguridad para que la carta elegida sea un +4
                while(!cartaValida) {


                    cartaJugada = jugador.usarCarta();


                    if(cartaJugada.getValor().equalsIgnoreCase("+4")){

                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());

                        //aplicar efectos
                        ejecutarCartaJugada(cartaJugada);

                    }else {

                        System.err.println("La carta elegida no es un +4");
                        System.out.println();

                    }


                }// LLAVE while

                    //si no hay acumulacion puedo jugar un +4 igualmente o una carta del color activo
                } else if (!acumulacionActiva && colorActivo) {


                    while(!cartaValida) {

                        System.out.println("TIENES CARTA PARA JUGAR");
                        System.out.println();

                        cartaJugada = jugador.usarCarta();


                        if(cartaJugada.getTipo().equalsIgnoreCase("COMODIN") ||
                                cartaJugada.getColor().equalsIgnoreCase(color)){

                            cartaValida = true;

                            cambiarCartaMesa(cartaJugada);

                            jugador.soltarCartaUsada(cartaJugada);

                            System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());

                            //aplicar efectos
                            ejecutarCartaJugada(cartaJugada);

                        }else {

                            System.out.println("LA CARTA ELEGIDA NO COINCIDE CON EL COLOR ACTIVO O NO ES UN +4");
                            System.out.println();

                        }


                    }// LLAVE while


                }


            }else {



                //si no hay acumulacion es que ya alguien mas lo recibio y no  tiene carta del color activo  cogera una del mazoMesa
                if (!acumulacionActiva && colorActivo){

                    repartir1Carta(jugador);

                    //obtenemos la ultima carta del jugador
                    Carta ultimaCarta= manoJugador.get(manoJugador.size() - 1);

                    //si la ultima carta que recibe es (color o comodin) la podra usar o guardar
                    comprobarCartaRecibidaEnColor(ultimaCarta,jugador);


                    //si hay acumulacion o no recibira las cartas que correspondan
                }else {

                        repartirAcumulacion(jugador,acumulacion);

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


            //forEach
        if (acumulacionActiva){

            //TE OBLIGA A QUE ELIJAS UN +2 O +4
            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador +2 o un+4
                if(cartaJugador.getValor().equalsIgnoreCase("+2") ||
                cartaJugador.getValor().equalsIgnoreCase("+4")) {

                    poseerCarta = true;
                }

            }

            //SI NO HAY ACUMULACION ACTIVA ES QUE YA ALGUIEN MAS RECIBIO EL +2
        }else {
            //PUEDES USAR COLOR DE LA MESA, +2 O COMODIN
                for(Carta cartaJugador : manoJugador){

                    //comprobamos que halla carta para usar en la mano del jugador que sea con respecto al color
                    if(cartaJugador.getColor().equalsIgnoreCase(cartaMesa.getColor()) ||
                            cartaJugador.getValor().equalsIgnoreCase("+2") ||
                                cartaJugador.getTipo().equalsIgnoreCase("COMODIN")) {

                        poseerCarta = true;
                    }

                }
        }

            //si el jugador tiene cartas en la mano puede usarla
            if(poseerCarta ){

                    //metodo se seguridad para que la carta elegida sea un +2 o +4

                    if (acumulacionActiva){
                        System.out.println("TIENES CARTA PARA JUGAR");
                        System.out.println();

                        while(!cartaValida) {


                            cartaJugada = jugador.usarCarta();

                    //quiero permitir que si antes alguien uso un +2 se puede usar un +4
                    if(cartaJugada.getValor().equalsIgnoreCase("+2") ||
                            cartaJugada.getValor().equalsIgnoreCase("+4")){


                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());

                        ejecutarCartaJugada(cartaJugada);

                    }else {

                        System.err.println("LA CARTA ELEGIDA NO ES UN +2 O UN +4");
                        System.out.println();

                    }

                }// LLAVE while
                        //SI LA ACUMULACION NO ESTA ACTIVA COMPROBARA LA CARTA QUE JUEGUES CON RESPECTO A LA DE LA MESA QUE ES UN +2
                }else {

                    while(!cartaValida) {
                        System.out.println("TIENES CARTA PARA JUGAR");
                        System.out.println();

                        cartaJugada = jugador.usarCarta();

                        //SI NO HAY ACUMULACION SE PODRA USAR OTRO +2 O CARTA MISMO COLOR DE MESA O COMODIN
                        if(cartaJugada.getValor().equalsIgnoreCase("+2") ||
                                cartaJugada.getColor().equalsIgnoreCase(cartaMesa.getColor())||
                                    cartaJugada.getTipo().equalsIgnoreCase("COMODIN")){

                            cartaValida = true;

                            cambiarCartaMesa(cartaJugada);

                            jugador.soltarCartaUsada(cartaJugada);

                            System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());

                            ejecutarCartaJugada(cartaJugada);

                        }else {

                            System.err.println("LA CARTA ELEGIDA NO COINCIDE CON EL COLOR DE LA cartaMESA");
                            System.out.println();

                        }

                    }// LLAVE while

                }


            }else {
                    //si el jugador no tiene carta +2 y no hay acumulacion es porque ya alguien recibio ese cumulo
                    //por lo que la cartaMesa seria un +2 pero en el turno no te toca recibir
                    if(!acumulacionActiva){

                        repartir1Carta(jugador);

                        //obtenemos la ultima carta del jugador
                        Carta ultimaCarta= manoJugador.get(manoJugador.size() - 1);

                        //si la ultima carta que recibe es (color o comodin) la podra usar o guardar
                        comprobarCartaRecibidaEnColor(ultimaCarta,jugador);

                        //si hay acumulacion anterior recibe ese cumulo de cartas
                    } else {

                    repartirAcumulacion(jugador,acumulacion);

                }

            }
        }





    public void comprobarCartaCambioColor(Jugador jugador){

        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false;
        boolean poseerCarta = false;
        Carta cartaJugada;

            for (Carta carta : manoJugador){

                if (carta.getColor().equalsIgnoreCase(color) || carta.getTipo().equalsIgnoreCase("comodin")){

                    poseerCarta = true;

                }
            }

            if(poseerCarta){

                while (!cartaValida){
                    System.out.println("TIENES CARTA PARA JUGAR");
                    System.out.println();

                cartaJugada = jugador.usarCarta();

                if(cartaJugada.getColor().equalsIgnoreCase(color) || cartaJugada.getTipo().equalsIgnoreCase("comodin")){

                    cartaValida = true;

                    cambiarCartaMesa(cartaJugada);

                    jugador.soltarCartaUsada(cartaJugada);

                    colorActivo = false;

                    System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + cartaJugada.toString());

                    //Aplica efectos
                    ejecutarCartaJugada(cartaJugada);


                }else {

                    System.err.println("LA CARTA ELEGIDA NO ESTA PERMITIDA");
                    System.out.println();
                }

                }//llave WHILE

            }else{

                repartir1Carta(jugador);

                    //obtenemos la ultima carta del jugador
                Carta ultimaCarta= manoJugador.get(manoJugador.size() - 1);

                //si la ultima carta que recibe es (color o comodin) la podra usar o guardar
                comprobarCartaRecibidaEnColor(ultimaCarta,jugador);

            }

        }




    public void comprobarCartaSaltar(Jugador jugador){

        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false;
        boolean poseerCarta = false;
        Carta cartaJugada;

    if (cartaMesa.getValor().equalsIgnoreCase("SALTAR")){


        for (Carta carta : manoJugador){

            if (carta.getTipo().equalsIgnoreCase("COMODIN") ||
                    carta.getValor().equalsIgnoreCase("SALTAR") ||
                    carta.getColor().equalsIgnoreCase(cartaMesa.getColor()) ){

                poseerCarta = true;

            }
        }

        if(poseerCarta){


            while (!cartaValida){
                System.out.println("TIENES CARTA PARA JUGAR");
                System.out.println();

               cartaJugada = jugador.usarCarta();

                if (cartaJugada.getValor().equalsIgnoreCase("SALTAR") ||
                        cartaJugada.getColor().equalsIgnoreCase(cartaMesa.getColor()) ||
                cartaJugada.getTipo().equalsIgnoreCase("COMODIN")){


                    cartaValida = true;

                    cambiarCartaMesa(cartaJugada);

                    jugador.soltarCartaUsada(cartaJugada);

                    //Aplica efectos
                    ejecutarCartaJugada(cartaJugada);

                }else{

                    System.err.println("LA CARTA JUGADA NO ESTA PERMITIDA");
                    System.out.println();
                }
            }

            //si no posee carta en la mano para jugar
        }else{

            repartir1Carta(jugador);

            //obtenemos la ultima carta recibida
            Carta ultimaCarta= manoJugador.get(jugador.getMano().size() - 1);

            comprobarCartaRecibidaEnSaltar(ultimaCarta,jugador);



            }

        }

    }




    public void comprobarCartaReversa(Jugador jugador){

        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false;
        boolean poseerCarta = false;
        Carta cartaJugada;

        if (cartaMesa.getValor().equalsIgnoreCase("REVERSA")){


            for (Carta carta : manoJugador){

                if (carta.getTipo().equalsIgnoreCase("COMODIN") ||
                        carta.getValor().equalsIgnoreCase("REVERSA")||
                        carta.getColor().equalsIgnoreCase(cartaMesa.getColor())){

                    poseerCarta = true;

                }
            }

            if(poseerCarta){


                while (!cartaValida){
                    System.out.println("TIENES CARTA PARA JUGAR");
                    System.out.println();

                    cartaJugada = jugador.usarCarta();

                    if (cartaJugada.getTipo().equalsIgnoreCase("COMODIN")||
                            cartaJugada.getValor().equalsIgnoreCase("REVERSA")||
                    cartaJugada.getColor().equalsIgnoreCase(cartaMesa.getColor())){


                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        //Aplica efectos
                        ejecutarCartaJugada(cartaJugada);


                    }else{

                        System.err.println("LA CARTA JUGADA NO ESTA PERMITIDA");
                        System.out.println();
                    }


                }


                //si no posee carta en la mano para jugar
            }else{

                repartir1Carta(jugador);

                //obtenemos la ultima carta recibida
                Carta ultimaCarta= jugador.getMano().get(jugador.getMano().size() - 1);

                comprobarCartaRecibidaEnReversa(ultimaCarta,jugador);



            }

        }

    }



        //METODO PARA APLICAR AFECTOS DE CARTA
        public void ejecutarCartaJugada(Carta cartaJugada){


            //estas acumulaciones son para luego si el siguiente jugador tiene +4 o +2  se sigue acumulando
            //activamos acumulacion si es +4
            if (cartaJugada.getValor().equalsIgnoreCase("+4")){


                    //se puede usar un +4 para un +2 pero no un +2 para un +4
                if (acumulacionActiva){

                    acumulacion += 4;

                }else {

                    //si la acumulacion no esta activa es que ya alguien recibio la acumulacion
                    acumulacionActiva = true;
                    acumulacion = 4;
                }


                activarColorMesa();

                //activamos acumulacion si es +2
            }else if (cartaJugada.getValor().equalsIgnoreCase("+2")){

                if (acumulacionActiva){

                    acumulacion +=2 ;

                }else {
                    //si la acumulacion no estaba activa quiere decir que ya alguien recibio ese +2 y si usas otro +2 se activa de nuevo
                    acumulacionActiva = true;
                    acumulacion = 2;
                }

            } else if (cartaJugada.getValor().equalsIgnoreCase("cambio_color")) {


                activarColorMesa();

                //NUEVA IMPLEMENTACION PARA CARTA SALTAR
            }else if (cartaJugada.getValor().equalsIgnoreCase("SALTAR")){

                //metodo de seguridad para que saltar este en true
                    if(colorActivo){

                        colorActivo = false;
                    }

                    saltar = true;


                //NUEVA IMPLEMENTACION PARA CARTA REVERSA
            } else if (cartaJugada.getValor().equalsIgnoreCase("REVERSA")){

                if (colorActivo){

                    colorActivo = false;
                }
                //si antes reversa no esta activo activalo y sino desactivalo
                if (!reversa){

                    reversa = true;
                }else {

                    reversa = false;
                }

            }else {

                if (colorActivo){

                    colorActivo = false;

                }
            }


        }




        public void comprobarCartaNumero(Jugador jugador){

            ArrayList<Carta>manoJugador = jugador.getMano();
            boolean cartaValida = false;
            boolean poseerCarta = false;
            Carta cartaJugada;



            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador +2 o un+4
                if(cartaJugador.getValor().equalsIgnoreCase(cartaMesa.getValor()) ||
                        cartaJugador.getColor().equalsIgnoreCase(cartaMesa.getColor() ) ||
                cartaJugador.getTipo().equalsIgnoreCase("COMODIN")) {

                    poseerCarta = true;

                }
            }


            if (poseerCarta){



                while (!cartaValida){

                    System.out.println("TIENES CARTA PARA JUGAR");
                    System.out.println();

                    cartaJugada = jugador.usarCarta();

                    if(cartaJugada.getValor().equalsIgnoreCase(cartaMesa.getValor()) ||
                            cartaJugada.getColor().equalsIgnoreCase(cartaMesa.getColor())||
                    cartaJugada.getTipo().equalsIgnoreCase("COMODIN")){

                        cartaValida = true;

                        System.out.println("LA CARTA JUGADA ES VALIDA");
                        System.out.println();

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        //Aplica efectos
                        ejecutarCartaJugada(cartaJugada);


                    }else {

                        System.err.println("LA CARTA ELEGIDA NO ESTA PERMITIDA");
                        System.out.println();
                    }

                }

            }else {

                System.out.println("NO TIENES CARTAS PARA JUGAR");
                System.out.println();

                repartir1Carta(jugador);

                Carta ultimaCarta = manoJugador.get(manoJugador.size() - 1);

                comprobarCartaRecibidaEnNumero(ultimaCarta,jugador);

            }

        }










public void comprobarCartaRecibidaEnNumero(Carta ultimaCarta, Jugador jugador){


        if(ultimaCarta.getValor().equalsIgnoreCase(cartaMesa.getValor()) ||
                ultimaCarta.getColor().equalsIgnoreCase(cartaMesa.getColor())){

            eleccionCartaRecibida(ultimaCarta,jugador);
        }
        }


        public void comprobarCartaRecibidaEnReversa(Carta ultimaCarta, Jugador jugador){

            if (ultimaCarta.getValor().equalsIgnoreCase("REVERSA") ||
                    ultimaCarta.getColor().equalsIgnoreCase(cartaMesa.getColor())||
                    ultimaCarta.getTipo().equalsIgnoreCase("COMODIN")){


                eleccionCartaRecibida(ultimaCarta,jugador);


            }



        }

public void comprobarCartaRecibidaEnSaltar(Carta ultimaCarta,Jugador jugador){

    //si la carta recibida es igual a saltar o tenga un color igual o sea un comodin se podra jusar
    if (ultimaCarta.getTipo().equalsIgnoreCase("COMODIN") ||
            ultimaCarta.getValor().equalsIgnoreCase("SALTAR") ||
            ultimaCarta.getColor().equalsIgnoreCase(cartaMesa.getColor())){

        eleccionCartaRecibida(ultimaCarta,jugador);


    }
}


            //AVISAR SI CARTA RECIBIDA SE PUEDE JUGAR O NO,TENIENDO EN CUENTA EL COLOR ACTIVO
    public void comprobarCartaRecibidaEnColor(Carta ultimaCarta,Jugador jugador){

        //si la ultima carta que recibo se puede usar  (color o comodin)

        if (ultimaCarta.getTipo().equalsIgnoreCase("comodin") ||
          ultimaCarta.getColor().equalsIgnoreCase(color)){

            eleccionCartaRecibida(ultimaCarta,jugador);

        }

    }


//METODO PARA PREGUNTAR SI QUIERE USAR LA CARTA RECIBIDA O NO
    public void eleccionCartaRecibida(Carta ultimaCarta, Jugador jugador){

        System.out.println("LA CARTA QUE RECIBISTE ES: " + ultimaCarta.toString() );
        System.out.println("PUEDES USARLA O QUEDARTELA QUE OPCION ELIGES ?");
        System.out.println("OPCION 1 - USAR CARTA ");
        System.out.println("OPCION 2 - CONSERVAR CARTA");

        int respuesta;

        respuesta = sc.nextInt();


        //SI RESPUESTA ES 1 SE USA LA CARTA Y SE APLICA LOS EFECTOS
        //SI RESPUESTA ES 2 PASA AL SIGUIENTE TURNO Y SE MANTIENE cartaMesa ANTERIOR

        if (respuesta == 1){

            cambiarCartaMesa(ultimaCarta);

            jugador.soltarCartaUsada(ultimaCarta);

            //EVALUAMOS DE NUEVO SI LA CARTA JUGADA ES UN +4 O +2 O SINO O SI ES OTRO COMODIN O SI ES UNA CARTA NUMERO
            //YA QUE HAY EFECTOS AL USAR LA CARTA

            ejecutarCartaJugada(ultimaCarta);

            System.out.println("El jugador " + jugador.getNombre() + " Ha usado la carta " + ultimaCarta.toString());

        }else{

            System.out.println("HAZ DECIDIDO CONSERVAR TU CARTA !");

        }

    }




//------------------↑↑↑↑↑↑↑↑↑↑↑ METODOS DE COMPROBACION DE CARTAS ↑↑↑↑↑↑↑↑↑↑↑------------




public void recargarMazoMesa(ArrayList<Carta>mazoBasura){

    System.out.println("!RECARGANDO EL MAZO DE LA MESA!");

        //añadimos todo el mazoBasura a MazoMesa
        mazoMesa.addAll(mazoBasura);
        //limpiamos maazoBasura
        mazoBasura.clear();
        //Mezclamos mazoMesa
        Collections.shuffle(mazoMesa);


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

        for (int i = 0; i < mazoMesa.size(); i++){

            if (mazoMesa.get(i).getTipo().equalsIgnoreCase("NUMERO")){

                return mazoMesa.remove(i);

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

                System.out.println("Quieres añadir otro jugador ?. ESCRIBE SI / NO ");


                respuesta = sc.nextLine();

            }

                //SI RESPONDE QUE SI SE SUMA EL INDICE
           if(respuesta.equalsIgnoreCase("si")){

               i++;

               //SI RESPUESTA ES IGUAL A NO
           }else {

               //comprobamos que el minimo sea 2 jugadores
               if( i < 2){

                   System.out.println("Tienen que haber 2 jugadores minimos");

                   i++;

                   //SALE DEL BUCLE
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


            /*
    public void mostrarJugadoresActivos(){

        for (Map.Entry<Integer,Jugador> entry : jugadores.entrySet()){

            Integer clave = entry.getKey();
            Jugador jugador = entry.getValue();

            if (jugador.getJugadorActivo()){

            System.out.println("Jugador: " + clave +" Nombre: " + jugador.getNombre());

            }

        }

    }
*/


    //--------↑↑↑↑↑↑↑↑↑↑ METODOS PARA ADMINISTRAR JUGADORES ↑↑↑↑↑↑↑↑↑↑-----











    //-----------------------METODO  DE toString PROPIO------------------------
    public void cargarColoresLetra(ArrayList<String>colores){
        colores.add("\u001B[31m");
        colores.add("\u001B[0m");
        colores.add("\u001B[32m" );
        colores.add("\u001B[0m");
        colores.add("\u001B[33m");
        colores.add("\u001B[0m");
        colores.add("\u001B[34m");
        colores.add("\u001B[0m");
        colores.add("\u001B[35m");
        colores.add("\u001B[0m");

    }




    //carta de la mesa boca arriba
    public void mostrarCartaMesa(){

        //CREAMOS ARRAY CON LOS STRINGS//CODIGOS DE COLOR
        ArrayList<String>colores = new ArrayList<>();

        //CARGAMOS STRINGS
        cargarColoresLetra(colores);

        System.out.println();
        aplicarColoresLetra(cartaMesa,colores);
        System.out.println();
    }



    public void aplicarColoresLetra(Carta cartaIngresada,ArrayList<String>colores){

        if (cartaIngresada.getColor().equalsIgnoreCase("ROJO")){

            System.out.println("LA CARTA DE LA MESA ES: " + cartaIngresada.toStringSuper(colores.get(0),colores.get(1)));

        } else if (cartaIngresada.getColor().equalsIgnoreCase("VERDE")){

            System.out.println("LA CARTA DE LA MESA ES: " + cartaIngresada.toStringSuper(colores.get(2),colores.get(3)));


        } else if (cartaIngresada.getColor().equalsIgnoreCase("AMARILLO")) {

            System.out.println("LA CARTA DE LA MESA ES: " + cartaIngresada.toStringSuper(colores.get(4),colores.get(5)));

        } else if (cartaIngresada.getColor().equalsIgnoreCase("AZUL")) {

            System.out.println("LA CARTA DE LA MESA ES: " + cartaIngresada.toStringSuper(colores.get(6),colores.get(7)));
        }else {

            System.out.println("LA CARTA DE LA MESA ES: " + cartaIngresada.toStringSuper(colores.get(8),colores.get(9)));
        }


    }


//-----------↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑METODOS toString PROPIO---------↑↑↑↑↑↑↑↑↑↑↑↑↑↑
















    //-------↓↓↓↓↓↓↓↓ METODOS DE REPARTICION DE CARTAS ↓↓↓↓↓↓↓↓↓-----------

    //Repartimos 7 cartas a cada jugador, Recorriendo el hashmap y usando metodo recibir carta de Jugador
    public void repartirMazoMesa(){

        if (mazoMesa.size() < 50){

            recargarMazoMesa(mazoBasura);
        }

    //Recorremos el hashmap y en cada vuelta dentro del for añadimos 7 cartas
                for(Jugador jugador : jugadores.values()){

                    for (int i=0; i < 7; i++){

                        //mazoMesa.remove() eliminacarta del array y lo devuelve
                        jugador.recibirCarta(mazoMesa.remove(0));
                    }
                }

    }






    public void repartir1Carta(Jugador jugador){

        if (mazoMesa.size() < 50){

            recargarMazoMesa(mazoBasura);
        }

        jugador.recibirCarta(mazoMesa.remove(0));

        System.out.println("El Jugador " + jugador.getNombre() + " Recibe 1 Carta y Pierde El Turno!");

    }

    public void repartirAcumulacion(Jugador jugador, int acumulado){

        if (mazoMesa.size() < 50){

            recargarMazoMesa(mazoBasura);
        }

    for(int i = 0; i < acumulado; i++){

        jugador.recibirCarta(mazoMesa.remove(0));

        }

        System.out.println("El Jugador " + jugador.getNombre() + " Recibe " + acumulado + " Cartas y Pierde El Turno!");
            //al repartir lo acumulado todo vuelve a inicio
        acumulacionActiva = false;
        acumulacion = 0;


    }

    public ArrayList<Carta> getMazoMesa() {
        return mazoMesa;
    }

    public Carta getCartaMesa() {
        return cartaMesa;
    }

    public HashMap<Integer, Jugador> getJugadores() {
        return jugadores;
    }

    public boolean isColorActivo() {
        return colorActivo;
    }

    public void setColorActivo(boolean colorActivo) {
        this.colorActivo = colorActivo;
    }

    public void setAcumulacionActiva(boolean acumulacionActiva) {
        this.acumulacionActiva = acumulacionActiva;
    }

    /* --------------------NUEVAS IMPLEMENTACIONES QUE FALTAN------
1.COMPROBACION DE masoMeza Si esta vacio   -- listo
2.INSERTAR EFECTO DE SALTAR -- listo
3.INSERTAR EFECTO DE REVERSA -- listo
4. COMPROBAR LOS ERRORES QUE PUEDE DAR AL RECIBIR LA CARTA QUE SE PUEDE USAR O NO. ----COMPROBAR METODOS RECIBIR CARTA--- comparacion de condiciones. podria dar null -- listo
5.COMPROBAR EROR AL COMPROBAR CARTA DE MESA NUMERO, AL ELEGIR UNA CARTA QUE NO COINCIDE CON EL COLOR O EL NUMERO ENTRA EN UN BUCLE QUE DICE "LA CARTA ELEGIDA NO ESTA PERMITIDA"
* */





}

