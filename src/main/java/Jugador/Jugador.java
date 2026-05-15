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

    public void recibirCarta(Carta carta){

        mano.add(carta);
    }


    public void mostrarMano(){

        System.out.println("Mano de: " + nombre);


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
