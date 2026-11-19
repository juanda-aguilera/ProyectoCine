package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import com.gitb.juandaaguilera.Sistema.persistencia.UsuarioDAO;

// PATRON DE COMPORTAMIENTO - Observer (el observador concreto)
// Unica responsabilidad: las visitas y el ascenso a Premium. VentaServicio no sabe que existe:
// solo le avisa que hubo una venta.
public class FidelizacionServicio implements VentaObserver {
    private final UsuarioDAO usuarioDAO;

    public FidelizacionServicio(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public String alConfirmarVenta(Venta venta) throws BaseDatosException {
        if (!venta.cuentaComoVisita()) {
            return "";
        }
        Cliente cliente = venta.getCliente();
        String mensaje;
        if (cliente.registrarVisita()) {
            mensaje = "Nueva visita registrada. Total de visitas: " + cliente.getVisitas();
        } else {
            mensaje = "Ya tenía una visita contada hoy. Total de visitas: " + cliente.getVisitas();
        }
        if (cliente.ascenderMembresia()) {
            mensaje += "\n\n¡" + cliente.getNombre() + " completó " + cliente.getVisitas()
                    + " visitas y ahora tiene membresía " + cliente.getMembresia().getTipo() + "!";
        }
        usuarioDAO.actualizarFidelizacion(cliente);
        return mensaje;
    }
}
