package com.gitb.juandaaguilera.Sistema;

// Excepcion propia: se lanza cuando el documento digitado no es de ningun cliente
public class ClienteNoEncontradoException extends Exception {

    public ClienteNoEncontradoException(int documento) {
        super("No existe un cliente con el documento " + documento);
    }
}
