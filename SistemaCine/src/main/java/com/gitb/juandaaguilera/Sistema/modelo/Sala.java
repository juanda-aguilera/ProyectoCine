package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public class Sala extends Sede {
    private int capacidad;
    private Pelicula pelicula;

    public Sala(String nombre, int capacidad) throws DatoInvalidoException {
        super(nombre);
        setCapacidad(capacidad);
        this.pelicula = null;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) throws DatoInvalidoException {
        if (capacidad <= 0) {
            throw new DatoInvalidoException("La capacidad debe ser mayor a 0");
        }
        this.capacidad = capacidad;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String getDescripcion() {
        if (pelicula == null) {
            return getNombre() + " - Sin película";
        }
        return getNombre() + " - " + pelicula.getDescripcion();
    }
}
