package com.gitb.juandaaguilera.Sistema;

import java.time.LocalDate;

public class Cliente extends Usuario {

    private Membresia membresia;
    private int visitas;
    private LocalDate fechaUltimaVisita; // controla que solo se cuente 1 visita por dia

    // un cliente nuevo inicia sin membresia y sin visitas
    public Cliente(String nombre, int documento, int edad) {
        super(nombre, documento, edad);
        this.membresia = null;
        this.visitas = 0;
        this.fechaUltimaVisita = null;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        if (visitas >= 0) {
            this.visitas = visitas;
        }
    }

    public LocalDate getFechaUltimaVisita() {
        return fechaUltimaVisita;
    }

    // Se expone solo para poder preparar datos de ejemplo/pruebas
    public void setFechaUltimaVisita(LocalDate fecha) {
        this.fechaUltimaVisita = fecha;
    }

    @Override
    public String getRol() {
        return "Cliente";
    }

    // solo se suma UNA visita por dia, sin importar cuantas boletas compre
    public boolean registrarVisita() {
        LocalDate hoy = LocalDate.now();
        if (fechaUltimaVisita == null || !fechaUltimaVisita.isEqual(hoy)) {
            this.visitas++;
            this.fechaUltimaVisita = hoy;
            return true;
        }
        return false;
    }

    // con membresia basica y 30 visitas puede pasar a Premium
    public boolean puedeObtenerPremium() {
        if (membresia == null) {
            return false;
        }
        return membresia.getTipo().equalsIgnoreCase("BASICA") && this.visitas >= 30;
    }

    // el descuento lo calcula la membresia (polimorfismo): basica o premium
    public double calcularDescuento(String tipoVenta, double precio) {
        if (membresia == null) {
            return 0.0;
        }
        if (tipoVenta.equalsIgnoreCase("BOLETA")) {
            return membresia.calcularDescuentoBoleta(precio);
        } else {
            return membresia.calcularDescuentoConfiteria(precio);
        }
    }

    @Override
    public String getDetalle() {
        return "Visitas: " + visitas
                + " | Membresía: " + (membresia == null ? "Ninguna" : membresia.getTipo());
    }
}
