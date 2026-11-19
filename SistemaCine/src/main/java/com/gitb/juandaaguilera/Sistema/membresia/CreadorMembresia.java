package com.gitb.juandaaguilera.Sistema.membresia;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

// PATRON CREACIONAL - Factory Method
// El metodo fabrica crearMembresia() lo implementa cada creador hijo. Quien lo usa
// recibe una Membresia sin saber de que clase concreta es.
public abstract class CreadorMembresia {
    public abstract Membresia crearMembresia();

    public static CreadorMembresia para(String tipo) throws DatoInvalidoException {
        if (tipo == null) {
            return new CreadorSinMembresia();
        }
        switch (tipo.trim().toUpperCase()) {
            case "BASICA":
                return new CreadorBasica();
            case "PREMIUM":
                return new CreadorPremium();
            case "NINGUNA":
                return new CreadorSinMembresia();
            default:
                throw new DatoInvalidoException("Tipo de membresía desconocido: " + tipo);
        }
    }
}
