package com.gitb.juandaaguilera.Sistema.persistencia;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import java.util.List;

// Interfaz pequeña: solo sirve para leer. Los DAO que no guardan nada no la mezclan con Registrable
// (principio de segregacion de interfaces).
public interface Consultable<T> {
    List<T> listar() throws BaseDatosException;
}
