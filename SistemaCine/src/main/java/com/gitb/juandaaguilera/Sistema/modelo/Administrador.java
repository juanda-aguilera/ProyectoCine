package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public class Administrador extends Usuario {
    private String area;

    public Administrador(String nombre, int documento, int edad, String area) throws DatoInvalidoException {
        super(nombre, documento, edad);
        setArea(area);
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) throws DatoInvalidoException {
        if (area == null || area.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir el área");
        }
        this.area = area.trim();
    }

    @Override
    public String getRol() {
        return "Administrador";
    }

    @Override
    public String getDetalle() {
        return "Área a cargo: " + area;
    }
}
