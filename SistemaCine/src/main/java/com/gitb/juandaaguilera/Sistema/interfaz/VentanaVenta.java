package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.excepciones.CineException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import com.gitb.juandaaguilera.Sistema.servicio.CineFacade;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
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

public class VentanaVenta extends JFrame {
    private final CineFacade cine;
    private final boolean taquilla;
    private List<Sala> salas;
    private List<ProductoConfiteria> productos;
    private JTextField campoDocumento;
    private JTextField campoCantidad;
    private JComboBox<String> comboItems;
    private JTextArea areaResumen;

    public VentanaVenta(CineFacade cine, boolean taquilla) throws CineException {
        this.cine = cine;
        this.taquilla = taquilla;
        setTitle(taquilla ? "Venta en taquilla" : "Venta en confitería");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        campoDocumento = new JTextField(15);
        campoCantidad = new JTextField(15);
        if (taquilla) {
            salas = cine.listarSalas();
            comboItems = new JComboBox<>(Combos.textos(salas));
        } else {
            productos = cine.listarProductos();
            comboItems = new JComboBox<>(Combos.textos(productos));
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

    private Venta armarVenta() throws CineException {
        int documento = Integer.parseInt(campoDocumento.getText().trim());
        int cantidad = Integer.parseInt(campoCantidad.getText().trim());

        Cliente cliente = cine.buscarCliente(documento);

        int indice = comboItems.getSelectedIndex();
        if (indice < 0) {
            throw new CineException("No hay opciones disponibles para vender");
        }

        if (taquilla) {
            return cine.crearVentaBoleta(cliente, salas.get(indice), cantidad);
        }
        return cine.crearVentaConfiteria(cliente, productos.get(indice), cantidad);
    }

    private void procesar(boolean confirmar) {
        try {
            Venta venta = armarVenta();
            areaResumen.setText(venta.getResumen());
            if (confirmar) {
                String mensaje = cine.confirmarVenta(venta);
                JOptionPane.showMessageDialog(this, mensaje);
                campoDocumento.setText("");
                campoCantidad.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El documento y la cantidad deben ser números");
        } catch (CineException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
