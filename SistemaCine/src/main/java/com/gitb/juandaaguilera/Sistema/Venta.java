package com.gitb.juandaaguilera.Sistema;

public class Venta {

    private Cliente cliente;
    private String membresia;        // membresia que tenia el cliente al momento de la venta
    private String tipoVenta;        // BOLETA o CONFITERIA
    private String producto;         // nombre de la pelicula o del producto
    private int cantidad;
    private double precio;           // precio unitario
    private double subtotal;
    private double descuento;
    private double total;

    public Venta(Cliente cliente, String tipoVenta, String producto, int cantidad, double precio) {
        this.cliente = cliente;
        this.membresia = (cliente.getMembresia() == null) ? "Ninguna" : cliente.getMembresia().getTipo();
        this.tipoVenta = tipoVenta;
        this.producto = producto;
        this.cantidad = (cantidad > 0) ? cantidad : 1;
        this.precio = precio;
        this.subtotal = 0.0;
        this.descuento = 0.0;
        this.total = 0.0;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getTipoVenta() {
        return tipoVenta;
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

    public double calcularSubtotal() {
        this.subtotal = this.precio * this.cantidad;
        return this.subtotal;
    }

    // el descuento depende de la membresia del cliente
    public double calcularDescuento() {
        this.descuento = cliente.calcularDescuento(tipoVenta, this.subtotal);
        return this.descuento;
    }

    public double calcularTotal() {
        calcularSubtotal();
        calcularDescuento();
        this.total = this.subtotal - this.descuento;
        return this.total;
    }

    public String getResumen() {
        return "Cliente: " + cliente.getNombre()
                + "\nMembresía: " + membresia
                + "\nTipo de venta: " + tipoVenta
                + "\nProducto: " + producto
                + "\nCantidad: " + cantidad
                + "\nPrecio unitario: $" + precio
                + "\nSubtotal: $" + subtotal
                + "\nDescuento: $" + descuento
                + "\nTotal a pagar: $" + total;
    }
}
