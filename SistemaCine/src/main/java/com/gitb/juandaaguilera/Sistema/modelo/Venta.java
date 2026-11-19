package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.membresia.Descontable;
import java.time.LocalDateTime;

// Clase padre abstracta de las ventas. Lo que cambia entre una venta de boletas y una de
// confiteria (como se calcula el descuento y si cuenta como visita) lo define cada hija:
// asi se pueden agregar nuevos tipos de venta sin tocar esta clase (principio abierto/cerrado).
public abstract class Venta {
    private final Cliente cliente;
    private final String membresia;
    private final String producto;
    private final int cantidad;
    private final double precio;
    private double subtotal;
    private double descuento;
    private double total;
    private final LocalDateTime fecha;

    protected Venta(Cliente cliente, String producto, int cantidad, double precio) throws DatoInvalidoException {
        if (cliente == null) {
            throw new DatoInvalidoException("La venta necesita un cliente");
        }
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0");
        }
        if (precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
        this.cliente = cliente;
        this.membresia = cliente.getMembresia().getTipo();
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fecha = LocalDateTime.now();
    }

    protected Venta(Cliente cliente, String membresia, String producto, int cantidad, double precio,
            double subtotal, double descuento, double total, LocalDateTime fecha) {
        this.cliente = cliente;
        this.membresia = membresia;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.total = total;
        this.fecha = fecha;
    }

    public abstract String getTipoVenta();

    protected abstract double descuentoSegun(Descontable membresia, double subtotal);

    public abstract boolean cuentaComoVisita();

    public Cliente getCliente() {
        return cliente;
    }

    public String getMembresia() {
        return membresia;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double calcularTotal() {
        this.subtotal = this.precio * this.cantidad;
        // Strategy: el descuento lo calcula la membresia del cliente, sea cual sea
        this.descuento = descuentoSegun(cliente.getMembresia(), this.subtotal);
        this.total = this.subtotal - this.descuento;
        return this.total;
    }

    public String getResumen() {
        return "Cliente: " + cliente.getNombre()
                + "\nMembresía: " + membresia
                + "\nTipo de venta: " + getTipoVenta()
                + "\nProducto: " + producto
                + "\nCantidad: " + cantidad
                + "\nPrecio unitario: " + Formato.moneda(precio)
                + "\nSubtotal: " + Formato.moneda(subtotal)
                + "\nDescuento: " + Formato.moneda(descuento)
                + "\nTotal a pagar: " + Formato.moneda(total);
    }
}
