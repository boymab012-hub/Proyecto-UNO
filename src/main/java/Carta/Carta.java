package Carta;

import ConexionBD.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Carta {

    //Atributos
    private int id;
    private  String color;
    private String valor;
    private String tipo;


    //Constructor

    public Carta(int id, String color, String valor, String tipo){

        this.id = id;
        this.color = color;
        this.valor = valor;
        this.tipo = tipo;
    }


//METODOS PRINCIPALES!!!








    // Getters
    public int getId()       { return id; }
    public String getColor() { return color; }
    public String getValor() { return valor; }
    public String getTipo()  { return tipo; }

@Override
public String toString() {
    return "[" + color + " - " + valor + "]";
}



}


