package com.gitb.juandaaguilera.Sistema;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Segundo menu: el usuario elige si la venta es de taquilla o de confiteria
public class VentanaMenuVentas extends JFrame {

    public VentanaMenuVentas(Registros registros) {
        setTitle("Ventas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel pregunta = new JLabel("¿Qué tipo de venta desea realizar?", SwingConstants.CENTER);

        JButton btnTaquilla = new JButton("Taquilla (boletas)");
        btnTaquilla.addActionListener(e -> {
            new VentanaVenta(registros, true).setVisible(true);
            dispose();
        });

        JButton btnConfiteria = new JButton("Confitería");
        btnConfiteria.addActionListener(e -> {
            new VentanaVenta(registros, false).setVisible(true);
            dispose();
        });

        JButton btnVolver = new JButton("Volver al menú principal");
        btnVolver.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panel.add(pregunta);
        panel.add(btnTaquilla);
        panel.add(btnConfiteria);
        panel.add(btnVolver);

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
