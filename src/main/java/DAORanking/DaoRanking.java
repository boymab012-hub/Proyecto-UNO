package DAORanking;
import EntradaRanking.EntradaRanking;
import Jugador.Jugador;

import java.sql.*;
import java.util.ArrayList;
import ConexionBD .*;

    public class DaoRanking {

        private Connection conn;

        public DaoRanking() {
            this.conn = ConexionBD.getConnection();  // usa tu clase de conexión
        }

        // ── JUGADOR ─────────────────────────────────────────────────

        // Inserta jugador nuevo y devuelve el id generado
        public int insertarJugador(String nombre) throws SQLException {
            String sql = "INSERT INTO jugador (nombre) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nombre);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
            return -1;
        }

        // Busca jugador por nombre, devuelve null si no existe
        public Jugador buscarJugadorPorNombre(String nombre) throws SQLException {
            String sql = "SELECT * FROM jugador WHERE nombre = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Jugador(rs.getInt("id"), rs.getString("nombre"));
            }
            return null;
        }

        // ── RANKING ─────────────────────────────────────────────────

        // Devuelve el ranking completo ordenado por victorias
        public ArrayList<EntradaRanking> obtenerRanking() throws SQLException {
            ArrayList<EntradaRanking> lista = new ArrayList<>();
            String sql = "SELECT * FROM ranking ORDER BY partidas_ganadas DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new EntradaRanking(
                        rs.getInt("id"),
                        rs.getInt("id_jugador"),
                        rs.getInt("partidas_ganadas"),
                        rs.getInt("partidas_perdidas"),
                        rs.getInt("partidas_jugadas")
                ));
            }
            return lista;
        }

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
                actualizarRanking(idJugador, gano);
                //SI NO INSERTALO
            } else {
                insertarEnRanking(idJugador, gano);
            }
        }




}
