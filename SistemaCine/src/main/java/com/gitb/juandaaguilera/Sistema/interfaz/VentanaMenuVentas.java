package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.excepciones.CineException;
import com.gitb.juandaaguilera.Sistema.servicio.CineFacade;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaMenuVentas extends JFrame {
    public VentanaMenuVentas(CineFacade cine) {
        setTitle("Ventas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel pregunta = new JLabel("¿Qué tipo de venta desea realizar?", SwingConstants.CENTER);

        JButton btnTaquilla = new JButton("Taquilla (boletas)");
        btnTaquilla.addActionListener(e -> abrirVenta(cine, true));

        JButton btnConfiteria = new JButton("Confitería");
        btnConfiteria.addActionListener(e -> abrirVenta(cine, false));

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

    private void abrirVenta(CineFacade cine, boolean taquilla) {
        try {
            new VentanaVenta(cine, taquilla).setVisible(true);
            dispose();
        } catch (CineException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
