package com.gitb.juandaaguilera.sistemacine;

public class Membresia {

    private String tipo;
    private double precio;
    private double descuentoBoleta;
    private double descuentoConfiteria;

    // Constructor de la membresia
    public Membresia(String tipo, double precio, double descuentoBoleta, double descuentoConfiteria) {
        this.tipo = tipo;
        this.precio = precio;
        this.descuentoBoleta = descuentoBoleta;
        this.descuentoConfiteria = descuentoConfiteria;
    }

    // Constructor que solo recibe el tipo y asigna automaticamente
    // el precio y los descuentos segun las reglas del cine
    public Membresia(String tipo) {
        this.tipo = tipo;
        if (tipo.equalsIgnoreCase("PREMIUM")) {
            this.precio = 0.0; // la Premium no se compra, se gana con visitas
            this.descuentoBoleta = 25.0;
            this.descuentoConfiteria = 15.0;
        } else {
            this.tipo = "BASICA";
            this.precio = 20000.0;
            this.descuentoBoleta = 10.0;
            this.descuentoConfiteria = 5.0;
        }
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo != null && !tipo.isEmpty()) {
            this.tipo = tipo;
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

    public double getDescuentoBoleta() {
        return descuentoBoleta;
    }

    public void setDescuentoBoleta(double descuentoBoleta) {
        if (descuentoBoleta >= 0 && descuentoBoleta <= 100) {
            this.descuentoBoleta = descuentoBoleta;
        }
    }

    public double getDescuentoConfiteria() {
        return descuentoConfiteria;
    }

    public void setDescuentoConfiteria(double descuentoConfiteria) {
        if (descuentoConfiteria >= 0 && descuentoConfiteria <= 100) {
            this.descuentoConfiteria = descuentoConfiteria;
        }
    }

    // Metodos que calculan el valor del descuento en pesos
    // sobre un precio base (no el precio final, solo lo que se descuenta)
    public double calcularDescuentoBoleta(double precioBoleta) {
        return precioBoleta * (this.descuentoBoleta / 100.0);
    }

    public double calcularDescuentoConfiteria(double precioProducto) {
        return precioProducto * (this.descuentoConfiteria / 100.0);
    }

    public void mostrarInformacion() {
        System.out.println("Membresia: " + tipo
                + " | Precio: " + precio
                + " | Desc. boleta: " + descuentoBoleta + "%"
                + " | Desc. confiteria: " + descuentoConfiteria + "%");
    }
}
