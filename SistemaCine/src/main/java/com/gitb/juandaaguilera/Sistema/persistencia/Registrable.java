package com.gitb.juandaaguilera.Sistema.persistencia;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;

// Interfaz pequeña (segregacion de interfaces): solo sirve para guardar.
public interface Registrable<T> {
    void guardar(T objeto) throws BaseDatosException;
}
