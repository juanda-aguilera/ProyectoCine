package com.gitb.juandaaguilera.Sistema.membresia;

// Objeto nulo (Null Object): un cliente sin membresia tiene esta, asi nunca hay que revisar "null".
// Se puede usar igual que cualquier otra Membresia (principio de sustitucion de Liskov).
public class SinMembresia extends Membresia {
    public SinMembresia() {
        super("Ninguna", 0.0);
    }

    @Override
    public double calcularDescuentoBoleta(double precio) {
        return 0.0;
    }

    @Override
    public double calcularDescuentoConfiteria(double precio) {
        return 0.0;
    }

    @Override
    public boolean esVigente() {
        return false;
    }
}
