package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Pelicula;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.persistencia.PeliculaDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.ProductoDAO;
import com.gitb.juandaaguilera.Sistema.persistencia.SalaDAO;
import java.util.List;

// Unica responsabilidad: lo que el cine ofrece (peliculas, salas y productos de confiteria)
public class CatalogoServicio {
    private final PeliculaDAO peliculaDAO;
    private final SalaDAO salaDAO;
    private final ProductoDAO productoDAO;

    public CatalogoServicio(PeliculaDAO peliculaDAO, SalaDAO salaDAO, ProductoDAO productoDAO) {
        this.peliculaDAO = peliculaDAO;
        this.salaDAO = salaDAO;
        this.productoDAO = productoDAO;
    }

    public List<Pelicula> listarPeliculas() throws BaseDatosException {
        return peliculaDAO.listar();
    }

    public List<Sala> listarSalas() throws BaseDatosException {
        return salaDAO.listar();
    }

    public List<ProductoConfiteria> listarProductos() throws BaseDatosException {
        return productoDAO.listar();
    }

    public void registrarPelicula(String nombre, String genero, int duracion, String clasificacion,
            double precio, Sala sala) throws DatoInvalidoException, BaseDatosException {
        Pelicula pelicula = new Pelicula(nombre, genero, duracion, clasificacion, precio);
        peliculaDAO.guardar(pelicula);
        if (sala != null) {
            sala.setPelicula(pelicula);
            salaDAO.asignarPelicula(sala);
        }
    }

    public void registrarProducto(String nombre, String tipo, double precio)
            throws DatoInvalidoException, BaseDatosException {
        productoDAO.guardar(new ProductoConfiteria(nombre, tipo, precio));
    }
}
