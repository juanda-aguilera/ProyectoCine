package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.membresia.Descontable;
import java.time.LocalDateTime;

public class VentaConfiteria extends Venta {
    public VentaConfiteria(Cliente cliente, String producto, int cantidad, double precio) throws DatoInvalidoException {
        super(cliente, producto, cantidad, precio);
    }

    public VentaConfiteria(Cliente cliente, String membresia, String producto, int cantidad, double precio,
            double subtotal, double descuento, double total, LocalDateTime fecha) {
        super(cliente, membresia, producto, cantidad, precio, subtotal, descuento, total, fecha);
    }

    @Override
    public String getTipoVenta() {
        return "CONFITERIA";
    }

    @Override
    protected double descuentoSegun(Descontable membresia, double subtotal) {
        return membresia.calcularDescuentoConfiteria(subtotal);
    }

    @Override
    public boolean cuentaComoVisita() {
        return false;
    }
}
