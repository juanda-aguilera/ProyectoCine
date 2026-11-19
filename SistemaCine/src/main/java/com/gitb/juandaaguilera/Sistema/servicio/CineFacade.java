package com.gitb.juandaaguilera.Sistema.servicio;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import com.gitb.juandaaguilera.Sistema.excepciones.ClienteNoEncontradoException;
import com.gitb.juandaaguilera.Sistema.excepciones.DatoInvalidoException;
import com.gitb.juandaaguilera.Sistema.modelo.Cliente;
import com.gitb.juandaaguilera.Sistema.modelo.Pelicula;
import com.gitb.juandaaguilera.Sistema.modelo.ProductoConfiteria;
import com.gitb.juandaaguilera.Sistema.modelo.Sala;
import com.gitb.juandaaguilera.Sistema.modelo.Usuario;
import com.gitb.juandaaguilera.Sistema.modelo.Venta;
import java.util.List;

// PATRON ESTRUCTURAL - Facade (fachada)
// Las ventanas solo conocen esta clase. Ella reparte el trabajo entre los servicios
// (usuarios, catalogo y ventas), asi la interfaz no depende de la logica interna ni de la base de datos.
public class CineFacade {
    private final UsuarioServicio usuarios;
    private final CatalogoServicio catalogo;
    private final VentaServicio ventas;

    public CineFacade(UsuarioServicio usuarios, CatalogoServicio catalogo, VentaServicio ventas) {
        this.usuarios = usuarios;
        this.catalogo = catalogo;
        this.ventas = ventas;
    }

    public String registrarCliente(String nombre, int documento, int edad) throws DatoInvalidoException, BaseDatosException {
        usuarios.registrarCliente(nombre, documento, edad);
        return "Cliente registrado con éxito";
    }

    public String registrarPelicula(String nombre, String genero, int duracion, String clasificacion,
            double precio, Sala sala) throws DatoInvalidoException, BaseDatosException {
        catalogo.registrarPelicula(nombre, genero, duracion, clasificacion, precio, sala);
        return "Película registrada con éxito";
    }

    public String registrarProducto(String nombre, String tipo, double precio) throws DatoInvalidoException, BaseDatosException {
        catalogo.registrarProducto(nombre, tipo, precio);
        return "Producto registrado con éxito";
    }

    public String comprarMembresiaBasica(int documento)
            throws ClienteNoEncontradoException, DatoInvalidoException, BaseDatosException {
        return usuarios.comprarMembresiaBasica(documento);
    }

    public Cliente buscarCliente(int documento) throws ClienteNoEncontradoException, BaseDatosException {
        return usuarios.buscarCliente(documento);
    }

    public List<Usuario> listarUsuarios() throws BaseDatosException {
        return usuarios.listarUsuarios();
    }

    public List<Pelicula> listarPeliculas() throws BaseDatosException {
        return catalogo.listarPeliculas();
    }

    public List<Sala> listarSalas() throws BaseDatosException {
        return catalogo.listarSalas();
    }

    public List<ProductoConfiteria> listarProductos() throws BaseDatosException {
        return catalogo.listarProductos();
    }

    public List<Venta> listarVentas() throws BaseDatosException {
        return ventas.listarVentas();
    }

    public Venta crearVentaBoleta(Cliente cliente, Sala sala, int cantidad) throws DatoInvalidoException {
        return ventas.crearVentaBoleta(cliente, sala, cantidad);
    }

    public Venta crearVentaConfiteria(Cliente cliente, ProductoConfiteria producto, int cantidad)
            throws DatoInvalidoException {
        return ventas.crearVentaConfiteria(cliente, producto, cantidad);
    }

    public String confirmarVenta(Venta venta) throws BaseDatosException {
        return ventas.confirmarVenta(venta);
    }
}
