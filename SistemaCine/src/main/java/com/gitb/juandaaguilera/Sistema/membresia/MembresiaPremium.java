package com.gitb.juandaaguilera.Sistema.membresia;

public class MembresiaPremium extends Membresia {
    public MembresiaPremium() {
        super("PREMIUM", 0.0);
    }

    @Override
    public double calcularDescuentoBoleta(double precio) {
        return precio * 0.25;
    }

    @Override
    public double calcularDescuentoConfiteria(double precio) {
        return precio * 0.15;
    }
}
