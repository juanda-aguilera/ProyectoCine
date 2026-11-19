package com.gitb.juandaaguilera.Sistema.persistencia.impl;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Pelicula;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.persistencia.ConexionBD;
import com.gitb.juandaaguilera.Sistema.persistencia.SalaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class SalaDAOImpl implements SalaDAO {
    @Override
    public List<Sala> listar() throws BaseDatosException {
        List<Sala> salas = new ArrayList<>();
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT s.id_sala, s.nombre AS sala, s.capacidad, "
                + "p.id_pelicula, p.nombre AS pelicula, p.genero, p.duracion_min, p.clasificacion, p.precio_boleta "
                + "FROM sala s LEFT JOIN pelicula p ON p.id_pelicula = s.id_pelicula ORDER BY s.id_sala");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala(rs.getString("sala"), rs.getInt("capacidad"));
                sala.setId(rs.getInt("id_sala"));
                if (rs.getObject("id_pelicula") != null) {
                    Pelicula pelicula = new Pelicula(rs.getString("pelicula"), rs.getString("genero"),
                            rs.getInt("duracion_min"), rs.getString("clasificacion"), rs.getDouble("precio_boleta"));
                    pelicula.setId(rs.getInt("id_pelicula"));
                    sala.setPelicula(pelicula);
                }
                salas.add(sala);
            }
        } catch (SQLException | DatoInvalidoException ex) {
            throw new BaseDatosException("No se pudieron leer las salas: " + ex.getMessage(), ex);
        }
        return salas;
    }

    @Override
    public void asignarPelicula(Sala sala) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement("UPDATE sala SET id_pelicula = ? WHERE id_sala = ?")) {
            if (sala.getPelicula() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, sala.getPelicula().getId());
            }
            ps.setInt(2, sala.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo asignar la película a la sala: " + ex.getMessage(), ex);
        }
    }
}
