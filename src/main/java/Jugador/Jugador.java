package Jugador;

import Carta.Carta;

import java.util.ArrayList;
import java.util.Scanner;

public class Jugador {

    private String nombre;
    private boolean jugadorActivo = true;
    private boolean saltar = false;
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


       System.out.println("Estas son tus cartas");
       System.out.println();

     mostrarMano();

       System.out.println();
       System.out.println("Que carta eliges ?");

       int numero;

       numero = sc.nextInt();

       return mano.get(numero -1 );

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


    public boolean getSaltar() {
        return saltar;
    }

    public void setSaltar(boolean saltar) {
        this.saltar = saltar;
    }

    public boolean getJugadorActivo(){

        return jugadorActivo;
    }

    public void setJugadorActivo(boolean jugadorActivo) {
        this.jugadorActivo = jugadorActivo;
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
