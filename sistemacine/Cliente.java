package com.gitb.juandaaguilera.sistemacine;

import java.time.LocalDate;

public class Cliente {

    private String nombre;
    private int documento;
    private int edad;
    private Membresia membresia;
    private int visitas;
    private LocalDate fechaUltimaVisita; // controla que solo se cuente 1 visita por dia

    // un cliente nuevo inicia sin membresia y sin visitas
    public Cliente(String nombre, int documento, int edad) {
        this.nombre = nombre;
        this.documento = documento;
        this.edad = (edad >= 0) ? edad : 0;
        this.membresia = null;
        this.visitas = 0;
        this.fechaUltimaVisita = null;
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

    //registra una visita del cliente al cine sin importar cuantas boletas compre 
    //el mismo dia, solo se suma UNA visita por dia. Para llegar a la membresia 
    //Premium se necesitan 30 dias distintos con al menos una compra de boleta 
    //cada uno. Devuelve true si la visita se contabilizo (dia nuevo) o false si
    //ese dia ya se le habia contado una visita.
    public boolean registrarVisita() {
        LocalDate hoy = LocalDate.now();
        if (fechaUltimaVisita == null || !fechaUltimaVisita.isEqual(hoy)) {
            this.visitas++;
            this.fechaUltimaVisita = hoy;
            return true;
        }
        return false;
    }

    //determina si el cliente ya cumple la condicion para acceder a la membresia Premium
    public boolean puedeObtenerPremium() {
        if (membresia == null) {
            return false;
        }
        return membresia.getTipo().equalsIgnoreCase("BASICA") && this.visitas >= 30;
    }

    //Metodo de comportamiento: calcula el descuento que le corresponde al cliente
    //sobre un precio dado, segun su membresia. Si no tiene membresia, no hay descuento.
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

    public void mostrarInformacion() {
        System.out.println("Cliente: " + nombre
                + " | Documento: " + documento
                + " | Edad: " + edad
                + " | Visitas: " + visitas
                + " | Membresia: " + (membresia == null ? "Ninguna" : membresia.getTipo()));
    }
}
