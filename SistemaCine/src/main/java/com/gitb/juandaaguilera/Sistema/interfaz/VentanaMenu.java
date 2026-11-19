package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.excepciones.CineException;
import com.gitb.juandaaguilera.Sistema.servicio.CineFacade;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaMenu extends JFrame {
    private interface Accion {
        void ejecutar() throws CineException;
    }

    public VentanaMenu(CineFacade cine) {
        setTitle("Cine - Menú principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("SISTEMA DE VENTAS Y MEMBRESÍAS - CINE", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        JPanel botones = new JPanel(new GridLayout(0, 2, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        agregarBoton(botones, "Registrar cliente", () -> new VentanaRegistrarCliente(cine).setVisible(true));
        agregarBoton(botones, "Registrar película", () -> new VentanaRegistrarPelicula(cine).setVisible(true));
        agregarBoton(botones, "Registrar producto de confitería", () -> new VentanaRegistrarProducto(cine).setVisible(true));
        agregarBoton(botones, "Comprar membresía básica", () -> new VentanaMembresia(cine).setVisible(true));
        agregarBoton(botones, "Ventas", () -> new VentanaMenuVentas(cine).setVisible(true));
        agregarBoton(botones, "Mostrar usuarios", () -> new VentanaConsulta(cine, "USUARIOS").setVisible(true));
        agregarBoton(botones, "Mostrar películas", () -> new VentanaConsulta(cine, "PELICULAS").setVisible(true));
        agregarBoton(botones, "Mostrar salas", () -> new VentanaConsulta(cine, "SALAS").setVisible(true));
        agregarBoton(botones, "Mostrar productos de confitería", () -> new VentanaConsulta(cine, "PRODUCTOS").setVisible(true));
        agregarBoton(botones, "Mostrar ventas realizadas", () -> new VentanaConsulta(cine, "VENTAS").setVisible(true));

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        JPanel abajo = new JPanel();
        abajo.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        abajo.setLayout(new BorderLayout());
        abajo.add(btnSalir, BorderLayout.CENTER);

        add(titulo, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void agregarBoton(JPanel panel, String texto, Accion accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> {
            try {
                accion.ejecutar();
            } catch (CineException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        panel.add(boton);
    }
}
