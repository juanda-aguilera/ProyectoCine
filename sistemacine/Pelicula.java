package com.gitb.juandaaguilera.sistemacine;


public class Pelicula {

    private String nombre;
    private String genero;
    private int duracion; // en minutos
    private String clasificacion;
    private double precioBoleta;

    public Pelicula(String nombre, String genero, int duracion, String clasificacion, double precioBoleta) {
        this.nombre = nombre;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.precioBoleta = precioBoleta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        }
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        if (duracion > 0) {
            this.duracion = duracion;
        }
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public double getPrecioBoleta() {
        return precioBoleta;
    }

    public void setPrecioBoleta(double precioBoleta) {
        if (precioBoleta >= 0) {
            this.precioBoleta = precioBoleta;
        }
    }

    // Metodo de comportamiento: muestra la informacion de la pelicula
    public void mostrarInformacion() {
        System.out.println("Pelicula: " + nombre
                + " | Genero: " + genero
                + " | Duracion: " + duracion + " min"
                + " | Clasificacion: " + clasificacion
                + " | Precio boleta: " + precioBoleta);
    }

    // Metodo de comportamiento: permite actualizar el precio de la boleta
    public void cambiarPrecio(double nuevoPrecio) {
        if (nuevoPrecio >= 0) {
            this.precioBoleta = nuevoPrecio;
        }
    }
}
