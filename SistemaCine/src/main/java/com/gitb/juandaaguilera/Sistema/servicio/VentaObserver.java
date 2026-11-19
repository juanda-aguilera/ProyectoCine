package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;

// PATRON DE COMPORTAMIENTO - Observer (la interfaz del observador)
// Quien quiera enterarse de que se confirmo una venta implementa esta interfaz.
// Devuelve un texto para mostrar al usuario (vacio si no hay nada que decir).
public interface VentaObserver {
    String alConfirmarVenta(Venta venta) throws BaseDatosException;
}
