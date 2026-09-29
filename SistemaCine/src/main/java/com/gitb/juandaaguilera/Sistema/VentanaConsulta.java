package com.gitb.juandaaguilera.Sistema;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// Una sola ventana para todas las consultas: solo cambia la tabla que se muestra
public class VentanaConsulta extends JFrame {

    public VentanaConsulta(Registros registros, String tipo) {
        setTitle("Consulta: " + tipo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultTableModel modelo;
        switch (tipo) {
            case "USUARIOS":
                modelo = Tablas.usuarios(registros);
                break;
            case "PELICULAS":
                modelo = Tablas.peliculas(registros);
                break;
            case "SALAS":
                modelo = Tablas.salas(registros);
                break;
            case "PRODUCTOS":
                modelo = Tablas.productos(registros);
                break;
            default:
                modelo = Tablas.ventas(registros);
        }

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(650, 250));

        add(scroll);
        pack();
        setLocationRelativeTo(null);
    }
}
