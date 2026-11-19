package com.gitb.juandaaguilera.Sistema.membresia;

public class CreadorSinMembresia extends CreadorMembresia {
    @Override
    public Membresia crearMembresia() {
        return new SinMembresia();
    }
}
