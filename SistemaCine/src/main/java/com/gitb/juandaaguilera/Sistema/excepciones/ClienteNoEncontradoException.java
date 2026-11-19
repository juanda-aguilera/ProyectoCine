package com.gitb.juandaaguilera.Sistema.excepciones;

public class ClienteNoEncontradoException extends CineException {
    public ClienteNoEncontradoException(int documento) {
        super("No existe un cliente con el documento " + documento);
    }
}
