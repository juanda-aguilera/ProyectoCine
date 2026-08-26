package com.gitb.juandaaguilera.sistemacine;


public class ProductoConfiteria {

    private String nombre;
    private String tipo;
    private double precio;

    public ProductoConfiteria(String nombre, String tipo, double precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        }
    }

    //calcula el precio final aplicando un porcentaje de descuento sobre el precio del producto
    public double calcularPrecioConDescuento(double porcentajeDescuento) {
        double descuento = this.precio * (porcentajeDescuento / 100.0);
        return this.precio - descuento;
    }

    public void mostrarInformacion() {
        System.out.println("Producto: " + nombre
                + " | Tipo: " + tipo
                + " | Precio: " + precio);
    }
}
