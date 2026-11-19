package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import com.gitb.juandaaguilera.Sistema.modelo.VentaBoleta;
import com.gitb.juandaaguilera.Sistema.modelo.VentaConfiteria;

// PATRON CREACIONAL - Builder
// Arma una venta paso a paso (cliente, que se vende y cantidad) y construir() valida todo
// y la deja con su subtotal, descuento y total ya calculados.
public class VentaBuilder {
    private Cliente cliente;
    private Sala sala;
    private ProductoConfiteria producto;
    private int cantidad;

    public VentaBuilder conCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public VentaBuilder deBoletas(Sala sala) {
        this.sala = sala;
        return this;
    }

    public VentaBuilder deConfiteria(ProductoConfiteria producto) {
        this.producto = producto;
        return this;
    }

    public VentaBuilder conCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public Venta construir() throws DatoInvalidoException {
        if (cliente == null) {
            throw new DatoInvalidoException("La venta necesita un cliente");
        }
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0");
        }
        Venta venta;
        if (sala != null && producto == null) {
            if (sala.getPelicula() == null) {
                throw new DatoInvalidoException("Esa sala no tiene película asignada");
            }
            venta = new VentaBoleta(cliente, sala.getPelicula().getNombre() + " (" + sala.getNombre() + ")",
                    cantidad, sala.getPelicula().getPrecioBoleta());
        } else if (producto != null && sala == null) {
            venta = new VentaConfiteria(cliente, producto.getNombre(), cantidad, producto.getPrecio());
        } else {
            throw new DatoInvalidoException("Debe elegir boletas o un producto de confitería (solo uno)");
        }
        venta.calcularTotal();
        return venta;
    }
}
