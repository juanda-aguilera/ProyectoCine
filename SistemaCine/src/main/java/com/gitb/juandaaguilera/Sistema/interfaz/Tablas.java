package com.gitb.juandaaguilera.Sistema.interfaz;

import com.gitb.juandaaguilera.Sistema.modelo.Formato;
import com.gitb.juandaaguilera.Sistema.modelo.Pelicula;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.modelo.Usuario;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import java.awt.FontMetrics;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

// Arma el modelo de cada tabla (JTable) usando TablaAdapter. Las celdas no se pueden editar.
public class Tablas {
    public static JTable crear(TableModel modelo) {
        JTable tabla = new JTable();
        mostrar(tabla, modelo);
        return tabla;
    }

    public static void mostrar(JTable tabla, TableModel modelo) {
        tabla.setModel(modelo);
        FontMetrics fuente = tabla.getFontMetrics(tabla.getFont());
        for (int c = 0; c < tabla.getColumnCount(); c++) {
            int ancho = fuente.stringWidth(String.valueOf(tabla.getColumnName(c)));
            for (int f = 0; f < tabla.getRowCount(); f++) {
                ancho = Math.max(ancho, fuente.stringWidth(String.valueOf(tabla.getValueAt(f, c))));
            }
            TableColumn columna = tabla.getColumnModel().getColumn(c);
            columna.setPreferredWidth(ancho + 24);
        }
    }

    public static TableModel usuarios(List<Usuario> lista) {
        return new TablaAdapter<>(lista, new String[]{"Rol", "Nombre", "Documento", "Edad", "Detalle"},
                u -> new Object[]{u.getRol(), u.getNombre(), u.getDocumento(), u.getEdad(), u.getDetalle()});
    }

    public static TableModel peliculas(List<Pelicula> lista) {
        return new TablaAdapter<>(lista, new String[]{"Nombre", "Género", "Duración (min)", "Clasificación", "Precio boleta"},
                p -> new Object[]{p.getNombre(), p.getGenero(), p.getDuracion(), p.getClasificacion(),
                    Formato.moneda(p.getPrecioBoleta())});
    }

    public static TableModel salas(List<Sala> lista) {
        return new TablaAdapter<>(lista, new String[]{"Sala", "Capacidad", "Película que proyecta"},
                s -> new Object[]{s.getNombre(), s.getCapacidad(),
                    s.getPelicula() == null ? "Sin película" : s.getPelicula().getNombre()});
    }

    public static TableModel productos(List<ProductoConfiteria> lista) {
        return new TablaAdapter<>(lista, new String[]{"Producto", "Tipo", "Precio"},
                p -> new Object[]{p.getNombre(), p.getTipo(), Formato.moneda(p.getPrecio())});
    }

    public static TableModel ventas(List<Venta> lista) {
        return new TablaAdapter<>(lista,
                new String[]{"Fecha", "Cliente", "Tipo", "Producto", "Cantidad", "Subtotal", "Descuento", "Total"},
                v -> new Object[]{v.getFecha().format(Formato.FECHA_HORA), v.getCliente().getNombre(), v.getTipoVenta(),
                    v.getProducto(), v.getCantidad(), Formato.moneda(v.getSubtotal()),
                    Formato.moneda(v.getDescuento()), Formato.moneda(v.getTotal())});
    }
}
