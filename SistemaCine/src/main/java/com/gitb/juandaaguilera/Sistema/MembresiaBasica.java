package com.gitb.juandaaguilera.Sistema;

public class MembresiaBasica extends Membresia {

    public MembresiaBasica() {
        super("BASICA", 20000.0);
    }

    // 10% en boletas
    @Override
    public double calcularDescuentoBoleta(double precio) {
        return precio * 0.10;
    }

    // 5% en confiteria
    @Override
    public double calcularDescuentoConfiteria(double precio) {
        return precio * 0.05;
    }
}
