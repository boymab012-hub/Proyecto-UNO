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
private boolean acumulacionActivaMasCuatro = false;
private boolean acumulacionActivaMasdos = false;
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

    } else if (cartaMesa.getValor().equalsIgnoreCase("CAMBIO_COLOR")) {

        comprobarCartaCambioColor(jugador);

    }else if (cartaMesa.getValor().equalsIgnoreCase("SALTAR")){


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

                        //aplicar efectos
                        ejecutarCartaJugada(cartaJugada);


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

                        ejecutarCartaJugada(cartaJugada);

                    }else {

                        System.err.println("LA CARTA ELEGIDA NO ES UN +2 O UN +4");

                    }

                }// LLAVE while


                //si el jugador no tiene carta +2 recibe el efecto de la carta
            }else {

                //si hay acumulacion anterior recibe ese cumulo de cartas
                if (acumulacionActivaMasdos || acumulacionActivaMasCuatro){

                    repartirAcumulacion(jugador,acumulacion);

                    acumulacionActivaMasdos = false;
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

                    //Aplica efectos
                    ejecutarCartaJugada(cartaJugada);



                }else {

                    System.err.println("LA CARTA ELEGIDA NO ESTA PERMITIDA");
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

    }


    public void comprobarCartaSaltar(Jugador jugador){

        ArrayList<Carta>manoJugador = jugador.getMano();
        boolean cartaValida = false;
        boolean poseerCarta = false;
        Carta cartaJugada;

    if (cartaMesa.getValor().equalsIgnoreCase("SALTAR")){


        for (Carta carta : manoJugador){

            if (carta.getValor().equalsIgnoreCase("SALTAR") ||
                    carta.getColor().equalsIgnoreCase(cartaMesa.getColor()) ||
                    carta.getTipo().equalsIgnoreCase("COMODIN")){

                poseerCarta = true;

            }
        }

        if(poseerCarta){


            while (!cartaValida){


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

                if (carta.getValor().equalsIgnoreCase("REVERSA") ||
                        carta.getColor().equalsIgnoreCase(cartaMesa.getColor()) ||
                        carta.getTipo().equalsIgnoreCase("COMODIN")){

                    poseerCarta = true;

                }
            }

            if(poseerCarta){


                while (!cartaValida){


                    cartaJugada = jugador.usarCarta();

                    if (cartaJugada.getValor().equalsIgnoreCase("REVERSA") ||
                            cartaJugada.getColor().equalsIgnoreCase(cartaMesa.getColor()) ||
                            cartaJugada.getTipo().equalsIgnoreCase("COMODIN")){


                        cartaValida = true;

                        cambiarCartaMesa(cartaJugada);

                        jugador.soltarCartaUsada(cartaJugada);

                        //Aplica efectos
                        ejecutarCartaJugada(cartaJugada);


                    }else{

                        System.err.println("LA CARTA JUGADA NO ESTA PERMITIDA");
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
                if (acumulacionActivaMasdos || acumulacionActivaMasCuatro){

                    acumulacion += 4;

                }else {

                    acumulacion = 4;
                }


                activarColorMesa();

                //activamos acumulacion si es +2
            }else if (cartaJugada.getValor().equalsIgnoreCase("+2")){

                acumulacionActivaMasdos = true;
                acumulacion = 2;

            } else if (cartaJugada.getValor().equalsIgnoreCase("cambio_color")) {

                activarColorMesa();

                //NUEVA IMPLEMENTACION PARA CARTA PASAR
            }else if (cartaJugada.getValor().equalsIgnoreCase("SALTAR")){

                //si antes saltar esta desactivo activalo y sino desactivalo
                if(!saltar){

                    saltar = true;
                }else {

                    saltar = false;
                }




                //NUEVA IMPLEMENTACION PARA CARTA REVERSA
            } else if (cartaJugada.getTipo().equalsIgnoreCase("REVERSA")){

                //si antes reversa no esta activo activalo y sino desactivalo
                if (!reversa){

                    reversa = true;
                }else {

                    reversa = false;
                }


            }



        }





        public void comprobarCartaNumero(Jugador jugador){

            ArrayList<Carta>manoJugador = jugador.getMano();
            boolean cartaValida = false;
            boolean poseerCarta = false;
            Carta cartaJugada;



        if (cartaMesa.getTipo().equalsIgnoreCase("NUMERO")){


            for(Carta cartaJugador : manoJugador){

                //comprobamos que halla carta para usar en la mano del jugador +2 o un+4
                if(cartaJugador.getTipo().equalsIgnoreCase(cartaMesa.getTipo()) ||
                        cartaJugador.getColor().equalsIgnoreCase(cartaMesa.getColor()) ||
                        cartaJugador.getTipo().equalsIgnoreCase("COMODIN")) {

                    poseerCarta = true;
                }

            }

            if (poseerCarta){




            }


        }

        }




public void comprobarCartaRecibidaEnNumero(Carta ultimaCarta, Jugador jugador){



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
    if (ultimaCarta.getValor().equalsIgnoreCase("SALTAR") ||
            ultimaCarta.getColor().equalsIgnoreCase(cartaMesa.getColor())||
            ultimaCarta.getTipo().equalsIgnoreCase("COMODIN")){


        eleccionCartaRecibida(ultimaCarta,jugador);


    }
}


            //AVISAR SI CARTA RECIBIDA SE PUEDE JUGAR O NO,TENIENDO EN CUENTA EL COLOR ACTIVO
    public void comprobarCartaRecibidaEnColor(Carta ultimaCarta,Jugador jugador){

        //si la ultima carta que recibo se puede usar  (color o comodin)
        if(ultimaCarta.getColor().equalsIgnoreCase(colorActivo) ||
                ultimaCarta.getTipo().equalsIgnoreCase("comodin")){


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





public void enviarCartaBasura(Carta carta){

    mazoBasura.add(carta);
}


public void recargarMazoMesa(ArrayList<Carta>mazoBasura){

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
        acumulacionActivaMasdos = false;
        acumulacionActivaMasCuatro = false;
        acumulacion = 0;


    }


/* --------------------NUEVAS IMPLEMENTACIONES QUE FALTAN------
1.COMPROBACION DE masoMeza Si esta vacio
2.INSERTAR EFECTO DE SALTAR
3.INSERTAR EFECTO DE REVERSA
4. COMPROBAR LOS ERRORES QUE PUEDE DAR AL RECIBIR LA CARTA QUE SE PUEDE USAR O NO. ----COMPROBAR METODOS RECIBIR CARTA--- comparacion de condiciones. podria dar null
* */





}

