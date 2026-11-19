package com.gitb.juandaaguilera.Sistema.persistencia.impl;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.persistencia.ConexionBD;
import com.gitb.juandaaguilera.Sistema.persistencia.ProductoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {
    @Override
    public List<ProductoConfiteria> listar() throws BaseDatosException {
        List<ProductoConfiteria> productos = new ArrayList<>();
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT nombre, tipo, precio FROM producto_confiteria ORDER BY id_producto");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(new ProductoConfiteria(rs.getString("nombre"), rs.getString("tipo"), rs.getDouble("precio")));
            }
        } catch (SQLException | DatoInvalidoException ex) {
            throw new BaseDatosException("No se pudieron leer los productos: " + ex.getMessage(), ex);
        }
        return productos;
    }

    @Override
    public void guardar(ProductoConfiteria producto) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO producto_confiteria (nombre, tipo, precio) VALUES (?, ?, ?)")) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getTipo());
            ps.setDouble(3, producto.getPrecio());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo guardar el producto: " + ex.getMessage(), ex);
        }
    }
}
