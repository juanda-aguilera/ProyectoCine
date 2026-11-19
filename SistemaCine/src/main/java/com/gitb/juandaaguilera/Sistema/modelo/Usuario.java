package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public abstract class Usuario {
    private String nombre;
    private int documento;
    private int edad;

    public Usuario(String nombre, int documento, int edad) throws DatoInvalidoException {
        setNombre(nombre);
        setDocumento(documento);
        setEdad(edad);
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

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) throws DatoInvalidoException {
        if (documento <= 0) {
            throw new DatoInvalidoException("El documento debe ser mayor a 0");
        }
        this.documento = documento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) throws DatoInvalidoException {
        if (edad < 0 || edad > 120) {
            throw new DatoInvalidoException("La edad debe estar entre 0 y 120");
        }
        this.edad = edad;
    }

    public abstract String getRol();

    public abstract String getDetalle();
}
