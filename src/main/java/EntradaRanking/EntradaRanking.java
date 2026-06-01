package EntradaRanking;

public class EntradaRanking {


    private int id;
    private int idJugador;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasJugadas;

    public EntradaRanking(int id, int idJugador, int ganadas, int perdidas, int jugadas) {
        this.id               = id;
        this.idJugador        = idJugador;
        this.partidasGanadas  = ganadas;
        this.partidasPerdidas = perdidas;
        this.partidasJugadas  = jugadas;
    }

    public int getId()               { return id; }
    public int getIdJugador()        { return idJugador; }
    public int getPartidasGanadas()  { return partidasGanadas; }
    public int getPartidasPerdidas() { return partidasPerdidas; }
    public int getPartidasJugadas()  { return partidasJugadas; }

    @Override
    public String toString() {
        return String.format("ID jugador: %d | Ganadas: %d | Perdidas: %d | Jugadas: %d",
                idJugador, partidasGanadas, partidasPerdidas, partidasJugadas);
    }





}
