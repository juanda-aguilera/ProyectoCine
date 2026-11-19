package com.gitb.juandaaguilera.Sistema.persistencia.impl;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.ClienteNoEncontradoException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.membresia.CreadorMembresia;
import com.gitb.juandaaguilera.Sistema.modelo.Administrador;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.Empleado;
import com.gitb.juandaaguilera.Sistema.modelo.Usuario;
import com.gitb.juandaaguilera.Sistema.persistencia.ConexionBD;
import com.gitb.juandaaguilera.Sistema.persistencia.UsuarioDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    private static final String SELECT_USUARIOS
            = "SELECT u.documento, u.nombre, u.edad, u.rol, c.visitas, c.fecha_ultima_visita, "
            + "m.tipo AS membresia, e.cargo, a.area "
            + "FROM usuario u "
            + "LEFT JOIN cliente c ON c.documento = u.documento "
            + "LEFT JOIN membresia m ON m.id_membresia = c.id_membresia "
            + "LEFT JOIN empleado e ON e.documento = u.documento "
            + "LEFT JOIN administrador a ON a.documento = u.documento ";

    @Override
    public List<Usuario> listar() throws BaseDatosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(SELECT_USUARIOS + "ORDER BY u.documento");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                usuarios.add(usuarioDesde(rs));
            }
        } catch (SQLException | DatoInvalidoException ex) {
            throw new BaseDatosException("No se pudieron leer los usuarios: " + ex.getMessage(), ex);
        }
        return usuarios;
    }

    @Override
    public Cliente buscarCliente(int documento) throws ClienteNoEncontradoException, BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(SELECT_USUARIOS + "WHERE u.documento = ? AND u.rol = 'CLIENTE'")) {
            ps.setInt(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return clienteDesde(rs);
                }
            }
        } catch (SQLException | DatoInvalidoException ex) {
            throw new BaseDatosException("No se pudo buscar al cliente: " + ex.getMessage(), ex);
        }
        throw new ClienteNoEncontradoException(documento);
    }

    @Override
    public boolean existeDocumento(int documento) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement("SELECT 1 FROM usuario WHERE documento = ?")) {
            ps.setInt(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo revisar el documento: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void guardar(Cliente cliente) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try {
            con.setAutoCommit(false);
            try (PreparedStatement psUsuario = con.prepareStatement(
                    "INSERT INTO usuario (documento, nombre, edad, rol) VALUES (?, ?, ?, 'CLIENTE')");
                    PreparedStatement psCliente = con.prepareStatement(
                            "INSERT INTO cliente (documento, visitas, fecha_ultima_visita, id_membresia) "
                            + "VALUES (?, ?, ?, (SELECT id_membresia FROM membresia WHERE tipo = ?))")) {
                psUsuario.setInt(1, cliente.getDocumento());
                psUsuario.setString(2, cliente.getNombre());
                psUsuario.setInt(3, cliente.getEdad());
                psUsuario.executeUpdate();

                psCliente.setInt(1, cliente.getDocumento());
                psCliente.setInt(2, cliente.getVisitas());
                asignarFecha(psCliente, 3, cliente);
                psCliente.setString(4, cliente.getMembresia().getTipo());
                psCliente.executeUpdate();
            }
            con.commit();
        } catch (SQLException ex) {
            deshacer(con);
            throw new BaseDatosException("No se pudo guardar el cliente: " + ex.getMessage(), ex);
        } finally {
            restaurarAutoCommit(con);
        }
    }

    @Override
    public void actualizarFidelizacion(Cliente cliente) throws BaseDatosException {
        Connection con = ConexionBD.getInstancia().obtenerConexion();
        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE cliente SET visitas = ?, fecha_ultima_visita = ?, "
                + "id_membresia = (SELECT id_membresia FROM membresia WHERE tipo = ?) WHERE documento = ?")) {
            ps.setInt(1, cliente.getVisitas());
            asignarFecha(ps, 2, cliente);
            ps.setString(3, cliente.getMembresia().getTipo());
            ps.setInt(4, cliente.getDocumento());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo actualizar al cliente: " + ex.getMessage(), ex);
        }
    }

    private static Usuario usuarioDesde(ResultSet rs) throws SQLException, DatoInvalidoException {
        String rol = rs.getString("rol");
        if (rol.equals("EMPLEADO")) {
            return new Empleado(rs.getString("nombre"), rs.getInt("documento"), rs.getInt("edad"), rs.getString("cargo"));
        }
        if (rol.equals("ADMINISTRADOR")) {
            return new Administrador(rs.getString("nombre"), rs.getInt("documento"), rs.getInt("edad"), rs.getString("area"));
        }
        return clienteDesde(rs);
    }

    static Cliente clienteDesde(ResultSet rs) throws SQLException, DatoInvalidoException {
        Cliente cliente = new Cliente(rs.getString("nombre"), rs.getInt("documento"), rs.getInt("edad"));
        cliente.setVisitas(rs.getInt("visitas"));
        Date fecha = rs.getDate("fecha_ultima_visita");
        cliente.setFechaUltimaVisita(fecha == null ? null : fecha.toLocalDate());
        // Factory Method: el texto de la base de datos se convierte en la membresia correcta
        cliente.setMembresia(CreadorMembresia.para(rs.getString("membresia")).crearMembresia());
        return cliente;
    }

    private void asignarFecha(PreparedStatement ps, int posicion, Cliente cliente) throws SQLException {
        if (cliente.getFechaUltimaVisita() == null) {
            ps.setNull(posicion, Types.DATE);
        } else {
            ps.setDate(posicion, Date.valueOf(cliente.getFechaUltimaVisita()));
        }
    }

    private void deshacer(Connection con) {
        try {
            con.rollback();
        } catch (SQLException ex) {
        }
    }

    private void restaurarAutoCommit(Connection con) {
        try {
            con.setAutoCommit(true);
        } catch (SQLException ex) {
        }
    }
}
