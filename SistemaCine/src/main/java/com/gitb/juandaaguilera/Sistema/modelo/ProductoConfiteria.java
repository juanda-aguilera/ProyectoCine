package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public class ProductoConfiteria implements Describible {
    private String nombre;
    private String tipo;
    private double precio;

    public ProductoConfiteria(String nombre, String tipo, double precio) throws DatoInvalidoException {
        setNombre(nombre);
        setTipo(tipo);
        setPrecio(precio);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws DatoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir el nombre del producto");
        }
        this.nombre = nombre.trim();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) throws DatoInvalidoException {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir el tipo del producto");
        }
        this.tipo = tipo.trim();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) throws DatoInvalidoException {
        if (precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    @Override
    public String getDescripcion() {
        return nombre + " (" + tipo + ") - " + Formato.moneda(precio);
    }
}
