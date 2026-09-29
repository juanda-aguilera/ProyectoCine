package com.gitb.juandaaguilera.Sistema;

import javax.swing.table.DefaultTableModel;

// Arma los modelos de las tablas (JTable) a partir de las listas de Registros
public class Tablas {

    // modelo cuyas celdas no se pueden editar
    private static DefaultTableModel nuevoModelo(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    public static DefaultTableModel usuarios(Registros registros) {
        DefaultTableModel modelo = nuevoModelo("Rol", "Nombre", "Documento", "Edad", "Detalle");
        for (Usuario u : registros.getUsuarios()) {
            modelo.addRow(new Object[]{u.getRol(), u.getNombre(), u.getDocumento(), u.getEdad(), u.getDetalle()});
        }
        return modelo;
    }

    public static DefaultTableModel peliculas(Registros registros) {
        DefaultTableModel modelo = nuevoModelo("Nombre", "Género", "Duración (min)", "Clasificación", "Precio boleta");
        for (Pelicula p : registros.getPeliculas()) {
            modelo.addRow(new Object[]{p.getNombre(), p.getGenero(), p.getDuracion(), p.getClasificacion(), p.getPrecioBoleta()});
        }
        return modelo;
    }

    public static DefaultTableModel salas(Registros registros) {
        DefaultTableModel modelo = nuevoModelo("Sala", "Capacidad", "Película que proyecta");
        for (Sala s : registros.getSalas()) {
            String pelicula = (s.getPelicula() == null) ? "Sin película" : s.getPelicula().getNombre();
            modelo.addRow(new Object[]{s.getNombre(), s.getCapacidad(), pelicula});
        }
        return modelo;
    }

    public static DefaultTableModel productos(Registros registros) {
        DefaultTableModel modelo = nuevoModelo("Producto", "Tipo", "Precio");
        for (ProductoConfiteria p : registros.getProductos()) {
            modelo.addRow(new Object[]{p.getNombre(), p.getTipo(), p.getPrecio()});
        }
        return modelo;
    }

    public static DefaultTableModel ventas(Registros registros) {
        DefaultTableModel modelo = nuevoModelo("Cliente", "Tipo", "Producto", "Cantidad", "Subtotal", "Descuento", "Total");
        for (Venta v : registros.getVentas()) {
            modelo.addRow(new Object[]{v.getCliente().getNombre(), v.getTipoVenta(), v.getProducto(),
                v.getCantidad(), v.getSubtotal(), v.getDescuento(), v.getTotal()});
        }
        return modelo;
    }
}
