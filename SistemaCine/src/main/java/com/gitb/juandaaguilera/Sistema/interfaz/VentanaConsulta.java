package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.excepciones.CineException;
import com.gitb.juandaaguilera.Sistema.servicio.CineFacade;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class VentanaConsulta extends JFrame {
    public VentanaConsulta(CineFacade cine, String tipo) throws CineException {
        setTitle("Consulta: " + tipo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        TableModel modelo;
        switch (tipo) {
            case "USUARIOS":
                modelo = Tablas.usuarios(cine.listarUsuarios());
                break;
            case "PELICULAS":
                modelo = Tablas.peliculas(cine.listarPeliculas());
                break;
            case "SALAS":
                modelo = Tablas.salas(cine.listarSalas());
                break;
            case "PRODUCTOS":
                modelo = Tablas.productos(cine.listarProductos());
                break;
            default:
                modelo = Tablas.ventas(cine.listarVentas());
        }

        JTable tabla = Tablas.crear(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(800, 250));

        add(scroll);
        pack();
        setLocationRelativeTo(null);
    }
}
