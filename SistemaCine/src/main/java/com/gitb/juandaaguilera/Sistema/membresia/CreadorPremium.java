package com.gitb.juandaaguilera.Sistema.membresia;

public class CreadorPremium extends CreadorMembresia {
    @Override
    public Membresia crearMembresia() {
        return new MembresiaPremium();
    }
}
