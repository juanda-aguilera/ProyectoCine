package com.gitb.juandaaguilera.Sistema;

// Interfaz: todo lo que de descuentos debe saber calcularlos en boletas y en confiteria
public interface Descontable {

    double calcularDescuentoBoleta(double precio);

    double calcularDescuentoConfiteria(double precio);
}
