package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.membresia.Descontable;
import java.time.LocalDateTime;

public class VentaBoleta extends Venta {
    public VentaBoleta(Cliente cliente, String producto, int cantidad, double precio) throws DatoInvalidoException {
        super(cliente, producto, cantidad, precio);
    }

    public VentaBoleta(Cliente cliente, String membresia, String producto, int cantidad, double precio,
            double subtotal, double descuento, double total, LocalDateTime fecha) {
        super(cliente, membresia, producto, cantidad, precio, subtotal, descuento, total, fecha);
    }

    @Override
    public String getTipoVenta() {
        return "BOLETA";
    }

    @Override
    protected double descuentoSegun(Descontable membresia, double subtotal) {
        return membresia.calcularDescuentoBoleta(subtotal);
    }

    @Override
    public boolean cuentaComoVisita() {
        return true;
    }
}
