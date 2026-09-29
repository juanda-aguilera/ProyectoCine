package com.gitb.juandaaguilera.Sistema;

// Clase padre abstracta: no se crean "Usuario" a secas, solo Cliente, Empleado o Administrador
public abstract class Usuario {

    private String nombre;
    private int documento;
    private int edad;

    public Usuario(String nombre, int documento, int edad) {
        this.nombre = nombre;
        this.documento = documento;
        this.edad = (edad >= 0) ? edad : 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        }
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        if (documento > 0) {
            this.documento = documento;
        }
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad >= 0) {
            this.edad = edad;
        }
    }

    // cada hijo define su rol y su detalle (polimorfismo)
    public abstract String getRol();

    public abstract String getDetalle();
}
