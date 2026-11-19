package com.gitb.juandaaguilera.Sistema.excepciones;

public class CineException extends Exception {
    public CineException(String mensaje) {
        super(mensaje);
    }

    public CineException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
