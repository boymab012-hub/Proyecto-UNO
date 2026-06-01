package Ranking;

import Jugador.Jugador;

public class Ranking {
    private int id;
    private Jugador jugador;
    private int partidasJugadas;
    private int partidasGanadas;
    private int puntos;

    // constructor
    public Ranking(int id, Jugador jugador) {
        this.id = id;
        this.jugador = jugador;
        this.partidasJugadas = 0;
        this.partidasGanadas = 0;
        this.puntos = 0;
    }

    // getters
    public int getId() { return id; }
    public Jugador getJugador() { return jugador; }
    public int getPartadasJugadas() { return partidasJugadas; }
    public int getPartidasGanadas() { return partidasGanadas; }
    public int getPuntos() { return puntos; }
}
