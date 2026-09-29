package com.gitb.juandaaguilera.Sistema;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Pedido: la misma ventana sirve para taquilla (boletas) y para confiteria
public class VentanaVenta extends JFrame {

    private Registros registros;
    private boolean taquilla;
    private JTextField campoDocumento;
    private JTextField campoCantidad;
    private JComboBox<String> comboItems;
    private JTextArea areaResumen;

    public VentanaVenta(Registros registros, boolean taquilla) {
        this.registros = registros;
        this.taquilla = taquilla;
        setTitle(taquilla ? "Venta en taquilla" : "Venta en confitería");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        campoDocumento = new JTextField(15);
        campoCantidad = new JTextField(15);
        if (taquilla) {
            comboItems = new JComboBox<>(registros.descripcionesSalas().toArray(new String[0]));
        } else {
            comboItems = new JComboBox<>(registros.descripcionesProductos().toArray(new String[0]));
        }

        JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
        formulario.add(new JLabel("Documento del cliente"));
        formulario.add(campoDocumento);
        formulario.add(new JLabel(taquilla ? "Sala / Película" : "Producto"));
        formulario.add(comboItems);
        formulario.add(new JLabel(taquilla ? "Cantidad de boletas" : "Cantidad"));
        formulario.add(campoCantidad);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.addActionListener(e -> procesar(false));
        JButton btnConfirmar = new JButton("Confirmar venta");
        btnConfirmar.addActionListener(e -> procesar(true));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnCalcular);
        botones.add(btnConfirmar);
        botones.add(btnCerrar);

        areaResumen = new JTextArea(10, 35);
        areaResumen.setEditable(false);

        JPanel arriba = new JPanel(new BorderLayout(8, 8));
        arriba.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(botones, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(areaResumen);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        add(arriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    // lee el formulario y arma la venta con su subtotal, descuento y total
    private Venta armarVenta() throws ClienteNoEncontradoException, DatoInvalidoException {
        int documento = Integer.parseInt(campoDocumento.getText().trim());
        int cantidad = Integer.parseInt(campoCantidad.getText().trim());
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0");
        }

        Cliente cliente = registros.buscarCliente(documento);

        int indice = comboItems.getSelectedIndex();
        if (indice < 0) {
            throw new DatoInvalidoException("No hay opciones disponibles para vender");
        }

        if (taquilla) {
            Sala sala = registros.getSalas().get(indice);
            if (sala.getPelicula() == null) {
                throw new DatoInvalidoException("Esa sala no tiene película asignada");
            }
            return registros.crearVentaBoleta(cliente, sala, cantidad);
        } else {
            ProductoConfiteria producto = registros.getProductos().get(indice);
            return registros.crearVentaConfiteria(cliente, producto, cantidad);
        }
    }

    // confirmar = false solo muestra el calculo; confirmar = true ademas registra la venta
    private void procesar(boolean confirmar) {
        try {
            Venta venta = armarVenta();
            areaResumen.setText(venta.getResumen());
            if (confirmar) {
                String mensaje = registros.confirmarVenta(venta);
                JOptionPane.showMessageDialog(this, mensaje);
                campoDocumento.setText("");
                campoCantidad.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El documento y la cantidad deben ser números");
        } catch (ClienteNoEncontradoException | DatoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
