package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.excepciones.CineException;
import com.gitb.juandaaguilera.Sistema.servicio.CineFacade;
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

public class VentanaRegistrarCliente extends JFrame {
    private final CineFacade cine;
    private JTextField campoNombre;
    private JTextField campoDocumento;
    private JTextField campoEdad;
    private JTable tabla;

    public VentanaRegistrarCliente(CineFacade cine) throws CineException {
        this.cine = cine;
        setTitle("Registrar cliente");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        campoNombre = new JTextField(15);
        campoDocumento = new JTextField(15);
        campoEdad = new JTextField(15);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Nombre"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Documento"));
        formulario.add(campoDocumento);
        formulario.add(new JLabel("Edad"));
        formulario.add(campoEdad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        JPanel arriba = new JPanel(new BorderLayout(8, 8));
        arriba.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(btnGuardar, BorderLayout.SOUTH);

        tabla = Tablas.crear(Tablas.usuarios(cine.listarUsuarios()));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(600, 180));

        add(arriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void guardar() {
        try {
            String nombre = campoNombre.getText();
            int documento = Integer.parseInt(campoDocumento.getText().trim());
            int edad = Integer.parseInt(campoEdad.getText().trim());

            String resultado = cine.registrarCliente(nombre, documento, edad);
            JOptionPane.showMessageDialog(this, resultado);
            Tablas.mostrar(tabla, Tablas.usuarios(cine.listarUsuarios()));
            vaciarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El documento y la edad deben ser números");
        } catch (CineException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void vaciarCampos() {
        campoNombre.setText("");
        campoDocumento.setText("");
        campoEdad.setText("");
    }
}
