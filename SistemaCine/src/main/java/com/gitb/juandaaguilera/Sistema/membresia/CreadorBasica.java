package com.gitb.juandaaguilera.Sistema.membresia;

public class CreadorBasica extends CreadorMembresia {
    @Override
    public Membresia crearMembresia() {
        return new MembresiaBasica();
    }
}
