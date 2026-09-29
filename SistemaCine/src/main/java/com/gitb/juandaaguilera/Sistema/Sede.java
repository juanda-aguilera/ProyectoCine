package com.gitb.juandaaguilera.Sistema;

// Clase padre abstracta de los elementos de la sede del cine: Pelicula y Sala
public abstract class Sede {

    private String nombre;

    public Sede(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        }
    }

    // cada hijo describe a su manera (para mostrarlo en listas y tablas)
    public abstract String getDescripcion();
}
