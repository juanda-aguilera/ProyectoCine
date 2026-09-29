package com.gitb.juandaaguilera.Sistema;

public class Sala extends Sede {

    private int capacidad;
    private Pelicula pelicula; // relacion: en una sala se proyecta una pelicula

    public Sala(String nombre, int capacidad) {
        super(nombre);
        this.capacidad = capacidad;
        this.pelicula = null;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad > 0) {
            this.capacidad = capacidad;
        }
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
