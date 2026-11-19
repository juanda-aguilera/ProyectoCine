package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import com.gitb.juandaaguilera.Sistema.persistencia.VentaDAO;
import java.util.ArrayList;
import java.util.List;

// Unica responsabilidad: crear, guardar y consultar ventas.
// Es el "sujeto" del patron Observer: avisa a sus observadores cuando se confirma una venta.
public class VentaServicio {
    private final VentaDAO ventaDAO;
    private final List<VentaObserver> observadores = new ArrayList<>();

    public VentaServicio(VentaDAO ventaDAO) {
        this.ventaDAO = ventaDAO;
    }

    public void agregarObservador(VentaObserver observador) {
        observadores.add(observador);
    }

    public Venta crearVentaBoleta(Cliente cliente, Sala sala, int cantidad) throws DatoInvalidoException {
        // Builder
        return new VentaBuilder().conCliente(cliente).deBoletas(sala).conCantidad(cantidad).construir();
    }

    public Venta crearVentaConfiteria(Cliente cliente, ProductoConfiteria producto, int cantidad)
            throws DatoInvalidoException {
        // Builder
        return new VentaBuilder().conCliente(cliente).deConfiteria(producto).conCantidad(cantidad).construir();
    }

    public String confirmarVenta(Venta venta) throws BaseDatosException {
        ventaDAO.guardar(venta);
        StringBuilder mensaje = new StringBuilder("Venta registrada\n\n" + venta.getResumen());
        for (VentaObserver observador : observadores) {
            String extra = observador.alConfirmarVenta(venta);
            if (!extra.isEmpty()) {
                mensaje.append("\n\n").append(extra);
            }
        }
        return mensaje.toString();
    }

    public List<Venta> listarVentas() throws BaseDatosException {
        return ventaDAO.listar();
    }
}
