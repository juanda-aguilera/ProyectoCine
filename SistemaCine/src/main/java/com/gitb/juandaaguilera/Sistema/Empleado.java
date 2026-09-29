package com.gitb.juandaaguilera.Sistema;

public class Empleado extends Usuario {

    private String cargo;

    public Empleado(String nombre, int documento, int edad, String cargo) {
        super(nombre, documento, edad);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
