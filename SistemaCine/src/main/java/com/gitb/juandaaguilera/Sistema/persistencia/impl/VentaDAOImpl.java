package com.gitb.juandaaguilera.Sistema.persistencia.impl;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import com.gitb.juandaaguilera.Sistema.modelo.VentaBoleta;
import com.gitb.juandaaguilera.Sistema.modelo.VentaConfiteria;
import com.gitb.juandaaguilera.Sistema.persistencia.ConexionBD;
import com.gitb.juandaaguilera.Sistema.persistencia.VentaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {
    @Override
    public List<Venta> listar() throws BaseDatosException {
        List<Venta> ventas = new ArrayList<>();
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT v.tipo_venta, v.membresia AS membresia_venta, v.producto, v.cantidad, v.precio_unitario, "
                + "v.subtotal, v.descuento, v.total, v.fecha_venta, "
                + "u.documento, u.nombre, u.edad, c.visitas, c.fecha_ultima_visita, m.tipo AS membresia "
                + "FROM venta v "
                + "JOIN usuario u ON u.documento = v.documento_cliente "
                + "JOIN cliente c ON c.documento = u.documento "
                + "LEFT JOIN membresia m ON m.id_membresia = c.id_membresia "
                + "ORDER BY v.id_venta");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = UsuarioDAOImpl.clienteDesde(rs);
                String membresia = rs.getString("membresia_venta");
                String producto = rs.getString("producto");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio_unitario");
                double subtotal = rs.getDouble("subtotal");
                double descuento = rs.getDouble("descuento");
                double total = rs.getDouble("total");
                LocalDateTime fecha = rs.getTimestamp("fecha_venta").toLocalDateTime();
                if (rs.getString("tipo_venta").equals("BOLETA")) {
                    ventas.add(new VentaBoleta(cliente, membresia, producto, cantidad, precio, subtotal, descuento, total, fecha));
                } else {
                    ventas.add(new VentaConfiteria(cliente, membresia, producto, cantidad, precio, subtotal, descuento, total, fecha));
                }
            }
        } catch (SQLException | DatoInvalidoException ex) {
            throw new BaseDatosException("No se pudieron leer las ventas: " + ex.getMessage(), ex);
        }
        return ventas;
    }

    @Override
    public void guardar(Venta venta) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO venta (documento_cliente, membresia, tipo_venta, producto, cantidad, "
                + "precio_unitario, subtotal, descuento, total, fecha_venta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, venta.getCliente().getDocumento());
            ps.setString(2, venta.getMembresia());
            ps.setString(3, venta.getTipoVenta());
            ps.setString(4, venta.getProducto());
            ps.setInt(5, venta.getCantidad());
            ps.setDouble(6, venta.getPrecio());
            ps.setDouble(7, venta.getSubtotal());
            ps.setDouble(8, venta.getDescuento());
            ps.setDouble(9, venta.getTotal());
            ps.setTimestamp(10, Timestamp.valueOf(venta.getFecha()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo guardar la venta: " + ex.getMessage(), ex);
        }
    }
}
