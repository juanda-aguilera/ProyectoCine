package com.gitb.juandaaguilera.Sistema;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VentanaRegistrarPelicula extends JFrame {

    private Registros registros;
    private JTextField campoNombre;
    private JTextField campoGenero;
    private JTextField campoDuracion;
    private JTextField campoClasificacion;
    private JTextField campoPrecio;
    private JComboBox<String> comboSalas;
    private JTable tabla;

    public VentanaRegistrarPelicula(Registros registros) {
        this.registros = registros;
        setTitle("Registrar película");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        campoNombre = new JTextField(15);
        campoGenero = new JTextField(15);
        campoDuracion = new JTextField(15);
        campoClasificacion = new JTextField(15);
        campoPrecio = new JTextField(15);
        comboSalas = new JComboBox<>(registros.descripcionesSalas().toArray(new String[0]));

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Nombre"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Género"));
        formulario.add(campoGenero);
        formulario.add(new JLabel("Duración (minutos)"));
        formulario.add(campoDuracion);
        formulario.add(new JLabel("Clasificación (ej. 12 años)"));
        formulario.add(campoClasificacion);
        formulario.add(new JLabel("Precio de la boleta"));
        formulario.add(campoPrecio);
        formulario.add(new JLabel("Sala donde se proyecta"));
        formulario.add(comboSalas);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        JPanel arriba = new JPanel(new BorderLayout(8, 8));
        arriba.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(btnGuardar, BorderLayout.SOUTH);

        tabla = new JTable(Tablas.peliculas(registros));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(650, 160));

        add(arriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void guardar() {
        try {
            String nombre = campoNombre.getText();
            String genero = campoGenero.getText();
            int duracion = Integer.parseInt(campoDuracion.getText().trim());
            String clasificacion = campoClasificacion.getText();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int indiceSala = comboSalas.getSelectedIndex();

            String resultado = registros.registrarPelicula(nombre, genero, duracion, clasificacion, precio, indiceSala);
            JOptionPane.showMessageDialog(this, resultado);
            tabla.setModel(Tablas.peliculas(registros));
            vaciarCampos();
            actualizarSalas();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración y el precio deben ser números");
        }
    }

    // el texto de cada sala cambia porque ahora proyecta otra pelicula
    private void actualizarSalas() {
        comboSalas.removeAllItems();
        for (String texto : registros.descripcionesSalas()) {
            comboSalas.addItem(texto);
        }
    }

    private void vaciarCampos() {
        campoNombre.setText("");
        campoGenero.setText("");
        campoDuracion.setText("");
        campoClasificacion.setText("");
        campoPrecio.setText("");
    }
}
