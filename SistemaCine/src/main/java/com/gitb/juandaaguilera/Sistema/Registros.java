package com.gitb.juandaaguilera.Sistema;

import java.time.LocalDate;
import java.util.ArrayList;

// Guarda las listas y contiene la logica del cine. Las ventanas solo piden y muestran datos.
public class Registros {

    // un solo ArrayList guarda clientes, empleados y administradores (polimorfismo)
    private ArrayList<Usuario> usuarios;
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Sala> salas;
    private ArrayList<ProductoConfiteria> productos;
    private ArrayList<Venta> ventas;

    public Registros() {
        this.usuarios = new ArrayList<>();
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
        cargarDatosDemo();
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public ArrayList<ProductoConfiteria> getProductos() {
        return productos;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    // datos de ejemplo
    private void cargarDatosDemo() {
        Cliente juan = new Cliente("Juan", 1001, 25);
        usuarios.add(juan);
        usuarios.add(new Cliente("Maria", 1002, 30));
        usuarios.add(new Cliente("Carlos", 1003, 19));
        usuarios.add(new Empleado("Laura", 2001, 28, "Taquillera"));
        usuarios.add(new Administrador("Andres", 3001, 40, "Gerencia general"));

        Pelicula duna = new Pelicula("Duna: Parte Dos", "Ciencia ficción", 166, "12 años", 15000);
        Pelicula intensamente = new Pelicula("Intensamente 2", "Animación", 96, "Todo público", 13000);
        Pelicula delReves = new Pelicula("Del Revés", "Terror", 110, "15 años", 14000);
        peliculas.add(duna);
        peliculas.add(intensamente);
        peliculas.add(delReves);

        // cada sala proyecta una pelicula
        Sala sala1 = new Sala("Sala 1", 80);
        Sala sala2 = new Sala("Sala 2", 60);
        Sala sala3 = new Sala("Sala 3", 100);
        sala1.setPelicula(duna);
        sala2.setPelicula(intensamente);
        sala3.setPelicula(delReves);
        salas.add(sala1);
        salas.add(sala2);
        salas.add(sala3);

        productos.add(new ProductoConfiteria("Crispetas grandes", "Snack", 9000));
        productos.add(new ProductoConfiteria("Gaseosa mediana", "Bebida", 6000));
        productos.add(new ProductoConfiteria("Combo pareja", "Combo", 22000));

        // Juan tiene membresia basica y 29 visitas; su ultima visita fue ayer,
        // asi la proxima compra de boleta completa la visita 30 y sube a Premium
        juan.setMembresia(new MembresiaBasica());
        juan.setVisitas(29);
        juan.setFechaUltimaVisita(LocalDate.now().minusDays(1));
    }

    // ===== REGISTROS =====
    public String registrarCliente(String nombre, int documento, int edad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Debe escribir el nombre";
        }
        if (documento <= 0) {
            return "El documento debe ser mayor a 0";
        }
        for (Usuario u : usuarios) {
            if (u.getDocumento() == documento) {
                return "Ya existe un usuario con ese documento";
            }
        }
        usuarios.add(new Cliente(nombre.trim(), documento, edad));
        return "Cliente registrado con éxito";
    }

    public String registrarPelicula(String nombre, String genero, int duracion,
            String clasificacion, double precio, int indiceSala) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Debe escribir el nombre de la película";
        }
        if (duracion <= 0 || precio < 0) {
            return "La duración debe ser mayor a 0 y el precio no puede ser negativo";
        }
        Pelicula pelicula = new Pelicula(nombre.trim(), genero, duracion, clasificacion, precio);
        peliculas.add(pelicula);
        // la pelicula se proyecta en la sala elegida
        if (indiceSala >= 0 && indiceSala < salas.size()) {
            salas.get(indiceSala).setPelicula(pelicula);
        }
        return "Película registrada con éxito";
    }

    public String registrarProducto(String nombre, String tipo, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Debe escribir el nombre del producto";
        }
        if (precio < 0) {
            return "El precio no puede ser negativo";
        }
        productos.add(new ProductoConfiteria(nombre.trim(), tipo, precio));
        return "Producto registrado con éxito";
    }

    public String comprarMembresiaBasica(int documento) throws ClienteNoEncontradoException {
        Cliente cliente = buscarCliente(documento);
        cliente.setMembresia(new MembresiaBasica());
        return cliente.getNombre() + " ahora tiene membresía BASICA";
    }

    // busca al cliente por documento; si no existe lanza la excepcion propia
    public Cliente buscarCliente(int documento) throws ClienteNoEncontradoException {
        for (Usuario u : usuarios) {
            if (u instanceof Cliente && u.getDocumento() == documento) {
                return (Cliente) u;
            }
        }
        throw new ClienteNoEncontradoException(documento);
    }

    // ===== VENTAS =====
    // arma la venta y calcula subtotal, descuento y total (todavia NO la guarda)
    public Venta crearVentaBoleta(Cliente cliente, Sala sala, int cantidad) {
        Pelicula pelicula = sala.getPelicula();
        Venta venta = new Venta(cliente, "BOLETA", pelicula.getNombre() + " (" + sala.getNombre() + ")",
                cantidad, pelicula.getPrecioBoleta());
        venta.calcularTotal();
        return venta;
    }

    public Venta crearVentaConfiteria(Cliente cliente, ProductoConfiteria producto, int cantidad) {
        Venta venta = new Venta(cliente, "CONFITERIA", producto.getNombre(), cantidad, producto.getPrecio());
        venta.calcularTotal();
        return venta;
    }

    // guarda la venta; si es de boletas cuenta la visita y revisa si sube a Premium
    public String confirmarVenta(Venta venta) {
        ventas.add(venta);
        String mensaje = "Venta registrada\n\n" + venta.getResumen();

        if (venta.getTipoVenta().equals("BOLETA")) {
            Cliente cliente = venta.getCliente();
            // solo se suma UNA visita por dia (30 dias distintos para la Premium)
            if (cliente.registrarVisita()) {
                mensaje += "\n\nNueva visita registrada. Total de visitas: " + cliente.getVisitas();
            } else {
                mensaje += "\n\nYa tenía una visita contada hoy. Total de visitas: " + cliente.getVisitas();
            }
            if (cliente.puedeObtenerPremium()) {
                cliente.setMembresia(new MembresiaPremium());
                mensaje += "\n\n¡" + cliente.getNombre() + " completó 30 visitas y ahora tiene membresía PREMIUM!";
            }
        }
        return mensaje;
    }

    // ===== TEXTOS PARA LOS COMBOBOX =====
    public ArrayList<String> descripcionesSalas() {
        ArrayList<String> lista = new ArrayList<>();
        for (Sala s : salas) {
            lista.add(s.getDescripcion());
        }
        return lista;
    }

    public ArrayList<String> descripcionesProductos() {
        ArrayList<String> lista = new ArrayList<>();
        for (ProductoConfiteria p : productos) {
            lista.add(p.getDescripcion());
        }
        return lista;
    }
}
