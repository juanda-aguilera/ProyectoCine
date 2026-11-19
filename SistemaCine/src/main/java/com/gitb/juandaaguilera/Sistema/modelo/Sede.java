package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public abstract class Sede implements Describible {
    private int id;
    private String nombre;

    public Sede(String nombre) throws DatoInvalidoException {
        setNombre(nombre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws DatoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir el nombre");
        }
        this.nombre = nombre.trim();
    }
}
