package com.gitb.juandaaguilera.Sistema.persistencia;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;

public interface SalaDAO extends Consultable<Sala> {
    void asignarPelicula(Sala sala) throws BaseDatosException;
}
