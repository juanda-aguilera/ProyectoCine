package com.gitb.juandaaguilera.Sistema.persistencia;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.ClienteNoEncontradoException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.Usuario;

public interface UsuarioDAO extends Consultable<Usuario>, Registrable<Cliente> {
    Cliente buscarCliente(int documento) throws ClienteNoEncontradoException, BaseDatosException;

    boolean existeDocumento(int documento) throws BaseDatosException;

    void actualizarFidelizacion(Cliente cliente) throws BaseDatosException;
}
