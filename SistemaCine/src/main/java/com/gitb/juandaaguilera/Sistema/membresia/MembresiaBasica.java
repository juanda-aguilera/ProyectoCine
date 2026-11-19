package com.gitb.juandaaguilera.Sistema.membresia;

public class MembresiaBasica extends Membresia {
    public static final int VISITAS_PARA_PREMIUM = 30;

    public MembresiaBasica() {
        super("BASICA", 20000.0);
    }

    @Override
    public double calcularDescuentoBoleta(double precio) {
        return precio * 0.10;
    }

    @Override
    public double calcularDescuentoConfiteria(double precio) {
        return precio * 0.05;
    }

    @Override
    public Membresia ascender(int visitas) {
        if (visitas >= VISITAS_PARA_PREMIUM) {
            // Factory Method
            return new CreadorPremium().crearMembresia();
        }
        return this;
    }
}
