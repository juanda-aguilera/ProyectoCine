package com.gitb.juandaaguilera.Sistema;

public class MembresiaPremium extends Membresia {

    // la Premium no se compra, se gana con 30 visitas
    public MembresiaPremium() {
        super("PREMIUM", 0.0);
    }

    // 25% en boletas
    @Override
    public double calcularDescuentoBoleta(double precio) {
        return precio * 0.25;
    }

    // 15% en confiteria
    @Override
    public double calcularDescuentoConfiteria(double precio) {
        return precio * 0.15;
    }
}
