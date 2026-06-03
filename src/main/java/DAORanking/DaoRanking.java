package DAORanking;
import EntradaRanking.EntradaRanking;
import Jugador.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ConexionBD .*;

    public class DaoRanking {

        private Connection conn = null;
        private static DaoRanking instance = null;

        public DaoRanking()  throws  SQLException{
            conn = ConexionBD.getConnection();
        }


        public static DaoRanking getInstance() throws  SQLException{
            if(instance == null){
                instance = new DaoRanking();
            }
            return  instance;
        }

        // ── JUGADOR ─────────────────────────────────────────────────

        // Inserta jugador nuevo y devuelve el id generado
        //PASAS EL NOMBRE DEL JUGADOOR Y TE DEVUELVE EL ID DE LA BASE DE DATOS.
        public int insertarJugador(String nombre) throws SQLException {

            int idJugador;
            String sql = "INSERT INTO jugador (nombre) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                idJugador = keys.getInt(1);
            }else{
                idJugador = -1;
            }

            return idJugador;

        }



        // Busca jugador por nombre, devuelve null si no existe
        public Jugador buscarJugadorPorNombre(String nombre) throws SQLException {

            Jugador retornoJugador;
            String sql = "SELECT * FROM jugador WHERE nombre = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                retornoJugador = new Jugador(rs.getInt("id"), rs.getString("nombre"));
            }else{
                retornoJugador = null;
            }

            return retornoJugador;
        }

        // ── RANKING ─────────────────────────────────────────────────



        public void obtenerRanking()throws  SQLException{

            String sql = "SELECT * FROM ranking ORDER BY partidas_ganadas DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //Mostramos el ranking del juego.
            System.out.println("Ranking actualizado: ");
            while (rs.next()){
                System.out.println("Id:" + rs.getInt("id") +
                        "\nid_jugador: " + rs.getInt("id_jugador") +
                        "\npartidas_ganadas: " + rs.getInt("partidas_ganadas") +
                        "\npartidas_jugadas: " + rs.getInt("partidas_jugadas") + "" +
                        "\n----------");
            }

        }



        /*
        // Devuelve el ranking completo ordenado por victorias
        public void mostrarRanking() throws SQLException {
            String sql = "SELECT * FROM ranking ORDER BY partidas_ganadas DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            //Mostramos el ranking del juego.
            System.out.println("Ranking actualizado: ");
            while (rs.next()){
                System.out.println("Id:" + rs.getInt("id") +
                        "\nid_jugador: " + rs.getInt("id_jugador") +
                        "\npartidas_ganadas: " + rs.getInt("partidas_ganadas") +
                        "\npartidas_jugadas: " + rs.getInt("partidas_jugadas") + "" +
                        "\n----------");
            }
        }


         */


        // Inserta primera entrada en ranking para un jugador
        private void insertarEnRanking(int idJugador, boolean gano) throws SQLException {
            String sql = "INSERT INTO ranking (id_jugador, partidas_ganadas, partidas_perdidas, partidas_jugadas) " +
                    "VALUES (?, ?, ?, 1)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, gano ? 1 : 0);
            ps.setInt(3, gano ? 0 : 1);
            ps.executeUpdate();
        }


        // Suma 1 a partidas ganadas o perdidas y a jugadas
        private void actualizarRanking(int idJugador, boolean gano) throws SQLException {
            String sql = "UPDATE ranking " +
                    "SET partidas_ganadas  = partidas_ganadas  + ?, " +
                    "    partidas_perdidas = partidas_perdidas + ?, " +
                    "    partidas_jugadas  = partidas_jugadas  + 1 " +
                    "WHERE id_jugador = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, gano ? 1 : 0);
            ps.setInt(2, gano ? 0 : 1);
            ps.setInt(3, idJugador);
            ps.executeUpdate();
        }

        // ── MÉTODO PRINCIPAL ─────────────────────────────────────────

        // Llama a este método al terminar cada partida

        //COMPRUEBA SI EL JUGADOR EXISTA
        public void registrarResultado(String nombre, boolean gano) throws SQLException {

            // 1. ¿Existe el jugador?
            Jugador jugador = buscarJugadorPorNombre(nombre);
            int idJugador;

                //SI jugador no esta en la tabla jugador lo inserta y devuevelve el ID GENERADO
            if (jugador == null) {

                idJugador = insertarJugador(nombre);

                //SI esta solo devuelve el ID
            } else {

                idJugador = jugador.getId();
            }


            // 2. ¿Tiene entrada en el ranking?
            String sqlSELECT = "SELECT id FROM ranking WHERE id_jugador = ?";
            PreparedStatement ps = conn.prepareStatement(sqlSELECT);
            ps.setInt(1, idJugador);
            ResultSet rs = ps.executeQuery();

                //SI EL id ESTA EN EL RANKING ACTUALIZALO
            if (rs.next()) {

                //Actualiza el ranking del jugador ya existente.
                actualizarRanking(idJugador, gano);
                //Si el jugador no está registrado, insertalo en el ranking dependiendo de su estado final de la partida.
            } else {
                insertarEnRanking(idJugador, gano);
            }
        }




}
