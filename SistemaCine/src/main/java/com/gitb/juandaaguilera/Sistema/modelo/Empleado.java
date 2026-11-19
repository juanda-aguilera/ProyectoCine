package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;

public class Empleado extends Usuario {
    private String cargo;

    public Empleado(String nombre, int documento, int edad, String cargo) throws DatoInvalidoException {
        super(nombre, documento, edad);
        setCargo(cargo);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) throws DatoInvalidoException {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe escribir el cargo");
        }
        this.cargo = cargo.trim();
    }

    @Override
    public String getRol() {
        return "Empleado";
    }

    @Override
    public String getDetalle() {
        return "Cargo: " + cargo;
    }
}
