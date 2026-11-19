package com.gitb.juandaaguilera.Sistema.modelo;

import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.membresia.Membresia;
import com.gitb.juandaaguilera.Sistema.membresia.SinMembresia;
import java.time.LocalDate;

public class Cliente extends Usuario {
    private Membresia membresia;
    private int visitas;
    private LocalDate fechaUltimaVisita;

    public Cliente(String nombre, int documento, int edad) throws DatoInvalidoException {
        super(nombre, documento, edad);
        this.membresia = new SinMembresia();
        this.visitas = 0;
        this.fechaUltimaVisita = null;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = (membresia == null) ? new SinMembresia() : membresia;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) throws DatoInvalidoException {
        if (visitas < 0) {
            throw new DatoInvalidoException("Las visitas no pueden ser negativas");
        }
        this.visitas = visitas;
    }

    public LocalDate getFechaUltimaVisita() {
        return fechaUltimaVisita;
    }

    public void setFechaUltimaVisita(LocalDate fecha) {
        this.fechaUltimaVisita = fecha;
    }

    @Override
    public String getRol() {
        return "Cliente";
    }

    public boolean registrarVisita() {
        LocalDate hoy = LocalDate.now();
        if (fechaUltimaVisita == null || !fechaUltimaVisita.isEqual(hoy)) {
            this.visitas++;
            this.fechaUltimaVisita = hoy;
            return true;
        }
        return false;
    }

    public boolean ascenderMembresia() {
        // Liskov: funciona con cualquier Membresia sin revisar su tipo
        Membresia nueva = membresia.ascender(visitas);
        if (nueva != membresia) {
            membresia = nueva;
            return true;
        }
        return false;
    }

    @Override
    public String getDetalle() {
        return "Visitas: " + visitas + " | Membresía: " + membresia.getTipo();
    }
}
