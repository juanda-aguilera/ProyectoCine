package com.gitb.juandaaguilera.Sistema.interfaz;

import java.util.List;
import java.util.function.Function;
import javax.swing.table.AbstractTableModel;

// PATRON ESTRUCTURAL - Adapter
// La JTable de Swing solo entiende la interfaz TableModel (filas y columnas), y el programa
// tiene listas de objetos (List<Usuario>, List<Venta>...). Esta clase adapta una cosa a la otra
// para cualquier tipo de objeto: la lista no se modifica y la tabla no conoce las clases del modelo.
public class TablaAdapter<T> extends AbstractTableModel {
    private final List<T> datos;
    private final String[] columnas;
    private final Function<T, Object[]> convertirFila;

    public TablaAdapter(List<T> datos, String[] columnas, Function<T, Object[]> convertirFila) {
        this.datos = datos;
        this.columnas = columnas;
        this.convertirFila = convertirFila;
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columna) {
        return columnas[columna];
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        return convertirFila.apply(datos.get(fila))[columna];
    }
}
