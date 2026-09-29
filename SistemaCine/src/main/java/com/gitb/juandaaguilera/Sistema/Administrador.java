package com.gitb.juandaaguilera.Sistema;

public class Administrador extends Usuario {

    private String area;

    public Administrador(String nombre, int documento, int edad, String area) {
        super(nombre, documento, edad);
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
