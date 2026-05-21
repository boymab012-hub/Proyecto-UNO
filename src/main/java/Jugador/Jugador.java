package Jugador;

import Carta.Carta;

import java.util.ArrayList;
import java.util.Scanner;

public class Jugador {

    private String nombre;
    private ArrayList<Carta>mano; // aqui guardamos las cartas del jugador
    private int bank = 0;  // por si queremos añadir apuesta
    public Scanner sc = new Scanner(System.in);

    public Jugador(String nombre){

        this.nombre = nombre;
        mano = new ArrayList<>();

    }

    //METODOS IMPORTANTES PRINCIPALES!!!





    //METODO QUE RETORNA CARTA, DENTRO DE EL SE MUESTRA CARTA MANOJUGADOR Y SE RETORNA LA CARTA ELEGIDA
    //USAMOS SCANNER Y METODO mostrarMano()

   public Carta usarCarta(){

       int numero;

       //METODO DE SEGURIDAD PARA ELEGIR UNA POSICION CORRECTA DE LA MANO Y NO DE ERROR
       do{

       System.out.println("CARTAS JUGADOR: " + nombre);
       System.out.println();

     mostrarMano();

       System.out.println();
       System.out.println("Que Carta Quieres Jugar ?");

           numero = sc.nextInt();

           //metodo de seguridad por si jugador pulsa un 0 o un numero mayor de los elementos del array
           if (numero > mano.size() || numero < 0) {

               System.out.println("NO TIENES CARTAS EN ESTA POSICION");
           }

       }while (numero > mano.size() || numero < 1);

       return mano.get(numero - 1 );
   }


   public void soltarCartaUsada(Carta carta){

        mano.remove(carta);

   }


    //METODO QUE IMPRIME POR PANTALLA LAS CARTAS DEL JUGADOR

    public void mostrarMano(){

        for(int i = 0; i < mano.size(); i++){

            System.out.println( "(" + (i+1) +")"  + mano.get(i).toString());
        }

    }

    //Metodo para añadir carta a mano del jugador
    public void recibirCarta(Carta carta){

        mano.add(carta);
    }


    public String getNombre() {
        return nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public int getBank() {
        return bank;
    }



}
