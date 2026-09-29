package com.gitb.juandaaguilera.Sistema;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Ventana principal: reemplaza el menu de JOptionPane
public class VentanaMenu extends JFrame {

    private Registros registros;

    public VentanaMenu(Registros registros) {
        this.registros = registros;
        setTitle("Cine - Menú principal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("SISTEMA DE VENTAS Y MEMBRESÍAS - CINE", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));

        JPanel botones = new JPanel(new GridLayout(0, 2, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        agregarBoton(botones, "Registrar cliente", () -> new VentanaRegistrarCliente(registros).setVisible(true));
        agregarBoton(botones, "Registrar película", () -> new VentanaRegistrarPelicula(registros).setVisible(true));
        agregarBoton(botones, "Registrar producto de confitería", () -> new VentanaRegistrarProducto(registros).setVisible(true));
        agregarBoton(botones, "Comprar membresía básica", () -> new VentanaMembresia(registros).setVisible(true));
        agregarBoton(botones, "Ventas", () -> new VentanaMenuVentas(registros).setVisible(true));
        agregarBoton(botones, "Mostrar usuarios", () -> new VentanaConsulta(registros, "USUARIOS").setVisible(true));
        agregarBoton(botones, "Mostrar películas", () -> new VentanaConsulta(registros, "PELICULAS").setVisible(true));
        agregarBoton(botones, "Mostrar salas", () -> new VentanaConsulta(registros, "SALAS").setVisible(true));
        agregarBoton(botones, "Mostrar productos de confitería", () -> new VentanaConsulta(registros, "PRODUCTOS").setVisible(true));
        agregarBoton(botones, "Mostrar ventas realizadas", () -> new VentanaConsulta(registros, "VENTAS").setVisible(true));

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

    // crea un boton y le asigna lo que debe hacer al hacer clic
    private void agregarBoton(JPanel panel, String texto, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> accion.run());
        panel.add(boton);
    }
}
