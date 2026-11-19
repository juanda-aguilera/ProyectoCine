package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.ClienteNoEncontradoException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.membresia.CreadorBasica;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.Usuario;
import com.gitb.juandaaguilera.Sistema.persistencia.UsuarioDAO;
import java.util.List;

// Unica responsabilidad: reglas de los usuarios y de la compra de membresia.
// Depende de la interfaz UsuarioDAO, no de la clase que usa JDBC (inversion de dependencias).
public class UsuarioServicio {
    private final UsuarioDAO usuarioDAO;

    public UsuarioServicio(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public List<Usuario> listarUsuarios() throws BaseDatosException {
        return usuarioDAO.listar();
    }

    public Cliente buscarCliente(int documento) throws ClienteNoEncontradoException, BaseDatosException {
        return usuarioDAO.buscarCliente(documento);
    }

    public void registrarCliente(String nombre, int documento, int edad) throws DatoInvalidoException, BaseDatosException {
        Cliente cliente = new Cliente(nombre, documento, edad);
        if (usuarioDAO.existeDocumento(documento)) {
            throw new DatoInvalidoException("Ya existe un usuario con ese documento");
        }
        usuarioDAO.guardar(cliente);
    }

    public String comprarMembresiaBasica(int documento)
            throws ClienteNoEncontradoException, DatoInvalidoException, BaseDatosException {
        Cliente cliente = usuarioDAO.buscarCliente(documento);
        if (cliente.getMembresia().esVigente()) {
            throw new DatoInvalidoException(cliente.getNombre() + " ya tiene membresía "
                    + cliente.getMembresia().getTipo());
        }
        // Factory Method
        cliente.setMembresia(new CreadorBasica().crearMembresia());
        usuarioDAO.actualizarFidelizacion(cliente);
        return cliente.getNombre() + " ahora tiene membresía " + cliente.getMembresia().getTipo();
    }
}
