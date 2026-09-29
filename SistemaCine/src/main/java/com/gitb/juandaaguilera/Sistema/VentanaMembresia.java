package com.gitb.juandaaguilera.Sistema;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VentanaMembresia extends JFrame {

    private Registros registros;
    private JTextField campoDocumento;
    private JTable tabla;

    public VentanaMembresia(Registros registros) {
        this.registros = registros;
        setTitle("Comprar membresía básica");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        campoDocumento = new JTextField(15);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Documento del cliente"));
        formulario.add(campoDocumento);

        JButton btnComprar = new JButton("Comprar membresía básica");
        btnComprar.addActionListener(e -> comprar());

        JPanel arriba = new JPanel(new BorderLayout(8, 8));
        arriba.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(btnComprar, BorderLayout.SOUTH);

        tabla = new JTable(Tablas.usuarios(registros));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(600, 180));

        add(arriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void comprar() {
        try {
            int documento = Integer.parseInt(campoDocumento.getText().trim());
            String resultado = registros.comprarMembresiaBasica(documento);
            JOptionPane.showMessageDialog(this, resultado);
            tabla.setModel(Tablas.usuarios(registros));
            campoDocumento.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El documento debe ser un número");
        } catch (ClienteNoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
