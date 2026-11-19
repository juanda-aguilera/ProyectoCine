package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public class Pelicula extends Sede {
    private String genero;
    private int duracion;
    private String clasificacion;
    private double precioBoleta;

    public Pelicula(String nombre, String genero, int duracion, String clasificacion, double precioBoleta)
            throws DatoInvalidoException {
        super(nombre);
        setGenero(genero);
        setDuracion(duracion);
        setClasificacion(clasificacion);
        setPrecioBoleta(precioBoleta);
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) throws DatoInvalidoException {
        if (genero == null || genero.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir el género de la película");
        }
        this.genero = genero.trim();
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) throws DatoInvalidoException {
        if (duracion <= 0) {
            throw new DatoInvalidoException("La duración debe ser mayor a 0");
        }
        this.duracion = duracion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) throws DatoInvalidoException {
        if (clasificacion == null || clasificacion.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir la clasificación de la película");
        }
        this.clasificacion = clasificacion.trim();
    }

    public double getPrecioBoleta() {
        return precioBoleta;
    }

    public void setPrecioBoleta(double precioBoleta) throws DatoInvalidoException {
        if (precioBoleta < 0) {
            throw new DatoInvalidoException("El precio de la boleta no puede ser negativo");
        }
        this.precioBoleta = precioBoleta;
    }

    @Override
    public String getDescripcion() {
        return getNombre() + " (" + clasificacion + ") - " + Formato.moneda(precioBoleta);
    }
}
