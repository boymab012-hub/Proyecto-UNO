package Jugador;

import Carta.Carta;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Jugador {
    private int id;
    private String nombre;
    private ArrayList<Carta> mano; // aqui guardamos las cartas del jugador


    public Jugador(String nombre) {

        this.nombre = nombre;
        mano = new ArrayList<>();

    }




    // Para cuando el DAO lee un jugador de la base de datos (con id)
    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }



    //METODOS IMPORTANTES PRINCIPALES!!!


    public void cargarColoresLetra(ArrayList<String> colores) {
        colores.add("\u001B[31m");
        colores.add("\u001B[0m");
        colores.add("\u001B[32m");
        colores.add("\u001B[0m");
        colores.add("\u001B[33m");
        colores.add("\u001B[0m");
        colores.add("\u001B[34m");
        colores.add("\u001B[0m");
        colores.add("\u001B[35m");
        colores.add("\u001B[0m");

    }

    //METODO QUE RETORNA CARTA, DENTRO DE EL SE MUESTRA CARTA MANOJUGADOR Y SE RETORNA LA CARTA ELEGIDA
    //USAMOS SCANNER Y METODO mostrarMano()

    public Carta usarCarta() {
        Scanner sc = new Scanner(System.in);
        int numero = 0;


        do {
            System.out.println("CARTAS JUGADOR: " + nombre);
            System.out.println();
            mostrarMano();
            System.out.println();
            System.out.println("¿Qué carta quieres jugar?");

            try {

                numero = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("ENTRADA INVÁLIDA, introduce un número.");
                sc.nextLine(); // limpiar el buffer
                numero = 0;   // forzar que el do-while repita
            }

            if (numero < 1 || numero > mano.size()) {
                System.out.println("NO TIENES CARTA EN ESA POSICIÓN.");
                numero = 0;
            }

        } while (numero < 1 || numero > mano.size());

        return mano.get(numero - 1);
    }


    public void soltarCartaUsada(Carta carta) {

        mano.remove(carta);

    }


    //METODO QUE IMPRIME POR PANTALLA LAS CARTAS DEL JUGADOR INCLUYENDO COLORES DX

    public void mostrarMano() {

        //CREAMOS ARRAY CON LOS STRINGS//CODIGOS DE COLOR
        ArrayList<String> colores = new ArrayList<>();

        //CARGAMOS STRINGS
        cargarColoresLetra(colores);

        for (int i = 0; i < mano.size(); i++) {

            //ENVIAMOS LA CARTA, UN INDICE, Y EL ARRAYLIST CON LOS STRING DE COLORES
            aplicarColoresLetra(mano.get(i), i, colores);


        }

    }


    //METODO EXTRA PARA APLICAR COLORS AL SUPERtoString
    public void aplicarColoresLetra(Carta carta, int i, ArrayList<String> colores) {

        if (carta.getColor().equalsIgnoreCase("ROJO")) {

            System.out.println("(" + (i + 1) + ")" + mano.get(i).toStringSuper(colores.get(0), colores.get(1)));

        } else if (carta.getColor().equalsIgnoreCase("VERDE")) {

            System.out.println("(" + (i + 1) + ")" + mano.get(i).toStringSuper(colores.get(2), colores.get(3)));


        } else if (carta.getColor().equalsIgnoreCase("AMARILLO")) {

            System.out.println("(" + (i + 1) + ")" + mano.get(i).toStringSuper(colores.get(4), colores.get(5)));

        } else if (carta.getColor().equalsIgnoreCase("AZUL")) {

            System.out.println("(" + (i + 1) + ")" + mano.get(i).toStringSuper(colores.get(6), colores.get(7)));
        } else {

            System.out.println("(" + (i + 1) + ")" + mano.get(i).toStringSuper(colores.get(8), colores.get(9)));
        }


    }


    //Metodo para añadir carta a mano del jugador
    public void recibirCarta(Carta carta) {

        mano.add(carta);
    }


    public int getId() {return id; }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }


    public void añadirCartaPrueba(Carta carta) {

        this.mano.add(carta);






    }

}
