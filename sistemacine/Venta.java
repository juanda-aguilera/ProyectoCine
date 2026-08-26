package com.gitb.juandaaguilera.sistemacine;


public class Venta {

    private Cliente cliente;
    private String tipoVenta;        // Taquilla o confiteria
    private String producto;         // nombre de la pelicula o del producto
    private int cantidad;
    private double precio;           // precio unitario
    private double subtotal;
    private double descuento;
    private double total;

    public Venta(Cliente cliente, String tipoVenta, String producto, int cantidad, double precio) {
        this.cliente = cliente;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    public double getTotal() {
        return total;
    }

    //calcula el subtotal (precio x cantidad)
    public double calcularSubtotal() {
        this.subtotal = this.precio * this.cantidad;
        return this.subtotal;
    }

    //calcula el descuento segun la membresia del cliente
    public double calcularDescuento() {
        this.descuento = cliente.calcularDescuento(tipoVenta, this.subtotal);
        return this.descuento;
    }

    //calcula el total final de la venta
    public double calcularTotal() {
        calcularSubtotal();
        calcularDescuento();
        this.total = this.subtotal - this.descuento;
        return this.total;
    }

    public void mostrarVenta() {
        System.out.println(" ------------------------------ ");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Tipo de venta: " + tipoVenta);
        System.out.println("Producto: " + producto);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Precio unitario: " + precio);
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Descuento: " + descuento);
        System.out.println("Total a pagar: " + total);
        System.out.println(" ------------------------------ ");
    }
}
