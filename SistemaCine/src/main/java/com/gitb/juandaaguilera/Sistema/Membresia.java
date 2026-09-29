package com.gitb.juandaaguilera.Sistema;

// Clase padre abstracta. No define los descuentos: cada hijo (Basica y Premium)
// implementa los metodos de la interfaz Descontable a su manera.
public abstract class Membresia implements Descontable {

    private String tipo;
    private double precio;

    public Membresia(String tipo, double precio) {
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }
}
