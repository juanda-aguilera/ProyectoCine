package com.gitb.juandaaguilera.Sistema;

// Excepcion propia: se lanza cuando un dato del formulario no es valido (cantidad, sala sin pelicula, etc.)
public class DatoInvalidoException extends Exception {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
