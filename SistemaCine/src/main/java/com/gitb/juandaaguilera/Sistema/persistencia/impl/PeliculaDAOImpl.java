package com.gitb.juandaaguilera.Sistema.persistencia.impl;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Pelicula;
import com.gitb.juandaaguilera.Sistema.persistencia.ConexionBD;
import com.gitb.juandaaguilera.Sistema.persistencia.PeliculaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAOImpl implements PeliculaDAO {
    @Override
    public List<Pelicula> listar() throws BaseDatosException {
        List<Pelicula> peliculas = new ArrayList<>();
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT id_pelicula, nombre, genero, duracion_min, clasificacion, precio_boleta "
                + "FROM pelicula ORDER BY id_pelicula");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula(rs.getString("nombre"), rs.getString("genero"),
                        rs.getInt("duracion_min"), rs.getString("clasificacion"), rs.getDouble("precio_boleta"));
                pelicula.setId(rs.getInt("id_pelicula"));
                peliculas.add(pelicula);
            }
        } catch (SQLException | DatoInvalidoException ex) {
            throw new BaseDatosException("No se pudieron leer las películas: " + ex.getMessage(), ex);
        }
        return peliculas;
    }

    @Override
    public void guardar(Pelicula pelicula) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO pelicula (nombre, genero, duracion_min, clasificacion, precio_boleta) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pelicula.getNombre());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3, pelicula.getDuracion());
            ps.setString(4, pelicula.getClasificacion());
            ps.setDouble(5, pelicula.getPrecioBoleta());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    pelicula.setId(claves.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo guardar la película: " + ex.getMessage(), ex);
        }
    }
}
