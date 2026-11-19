package com.gitb.juandaaguilera.Sistema;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.interfaz.VentanaMenu;
import com.gitb.juandaaguilera.Sistema.persistencia.ConexionBD;
import com.gitb.juandaaguilera.Sistema.persistencia.PeliculaDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.ProductoDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.SalaDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.UsuarioDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.VentaDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.impl.PeliculaDAOImpl;
import com.gitb.juandaaguilera.Sistema.persistencia.impl.ProductoDAOImpl;
import com.gitb.juandaaguilera.Sistema.persistencia.impl.SalaDAOImpl;
import com.gitb.juandaaguilera.Sistema.persistencia.impl.UsuarioDAOImpl;
import com.gitb.juandaaguilera.Sistema.persistencia.impl.VentaDAOImpl;
import com.gitb.juandaaguilera.Sistema.servicio.CatalogoServicio;
import com.gitb.juandaaguilera.Sistema.servicio.CineFacade;
import com.gitb.juandaaguilera.Sistema.servicio.FidelizacionServicio;
import com.gitb.juandaaguilera.Sistema.servicio.UsuarioServicio;
import com.gitb.juandaaguilera.Sistema.servicio.VentaServicio;
import java.awt.EventQueue;
import javax.swing.JOptionPane;

// Clase principal. Aqui se "conectan" todas las piezas (inyeccion de dependencias):
// se crean los DAO, los servicios que los reciben y la fachada que usan las ventanas.
public class SistemaCine {
    public static void main(String[] args) {
        try {
            ConexionBD.getInstancia().obtenerConexion();
        } catch (BaseDatosException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "No hay conexión con la base de datos",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        PeliculaDAO peliculaDAO = new PeliculaDAOImpl();
        SalaDAO salaDAO = new SalaDAOImpl();
        ProductoDAO productoDAO = new ProductoDAOImpl();
        VentaDAO ventaDAO = new VentaDAOImpl();

        VentaServicio ventaServicio = new VentaServicio(ventaDAO);
        ventaServicio.agregarObservador(new FidelizacionServicio(usuarioDAO)); // Observer
        CineFacade cine = new CineFacade(
                new UsuarioServicio(usuarioDAO),
                new CatalogoServicio(peliculaDAO, salaDAO, productoDAO),
                ventaServicio);

        EventQueue.invokeLater(() -> new VentanaMenu(cine).setVisible(true));
    }
}
