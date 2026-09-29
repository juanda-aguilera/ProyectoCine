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

public class VentanaRegistrarProducto extends JFrame {

    private Registros registros;
    private JTextField campoNombre;
    private JTextField campoTipo;
    private JTextField campoPrecio;
    private JTable tabla;

    public VentanaRegistrarProducto(Registros registros) {
        this.registros = registros;
        setTitle("Registrar producto de confitería");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        campoNombre = new JTextField(15);
        campoTipo = new JTextField(15);
        campoPrecio = new JTextField(15);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Nombre del producto / combo"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Tipo (Snack, Bebida, Combo)"));
        formulario.add(campoTipo);
        formulario.add(new JLabel("Precio"));
        formulario.add(campoPrecio);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        JPanel arriba = new JPanel(new BorderLayout(8, 8));
        arriba.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(btnGuardar, BorderLayout.SOUTH);

        tabla = new JTable(Tablas.productos(registros));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(500, 160));

        add(arriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void guardar() {
        try {
            String nombre = campoNombre.getText();
            String tipo = campoTipo.getText();
            double precio = Double.parseDouble(campoPrecio.getText().trim());

            String resultado = registros.registrarProducto(nombre, tipo, precio);
            JOptionPane.showMessageDialog(this, resultado);
            tabla.setModel(Tablas.productos(registros));
            campoNombre.setText("");
            campoTipo.setText("");
            campoPrecio.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número");
        }
    }
}
