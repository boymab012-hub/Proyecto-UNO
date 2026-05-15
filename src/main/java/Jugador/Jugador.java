package Jugador;

import Carta.Carta;

import java.util.ArrayList;

public class Jugador {

    private String nombre;
    private ArrayList<Carta>mano; // aqui guardamos las cartas del jugador
    private int bank = 0;  // por si queremos añadir apuesta


    public Jugador(String nombre){

        this.nombre = nombre;
        mano = new ArrayList<>();

    }

    //METODOS IMPORTANTES PRINCIPALES!!!

    /*
    METODO QUE RETORNA CARTA, DENTRO DE EL SE MUESTRA CARTA MANOJUGADOR Y SE RETORNA LA CARTA ELEGIDA
    //USAMOS SCANNER Y METODO mostrarMano()

   public Carta usarCarta(){

   }
    */

    //METODO QUE IMPRIME POR PANTALLA LAS CARTAS DEL JUGADOR

    public void mostrarMano(){

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
