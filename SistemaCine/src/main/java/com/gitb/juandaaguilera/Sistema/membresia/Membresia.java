package com.gitb.juandaaguilera.Sistema.membresia;

// Clase padre abstracta. No define los descuentos: cada hija los implementa a su manera
// (patron Strategy: cada membresia es una estrategia de descuento distinta).
public abstract class Membresia implements Descontable {
    private final String tipo;
    private final double precio;

    protected Membresia(String tipo, double precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean esVigente() {
        return true;
    }

    public Membresia ascender(int visitas) {
        return this;
    }
}
