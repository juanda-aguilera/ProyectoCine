package com.gitb.juandaaguilera.sistemacine;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class SistemaCine {

    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Pelicula> peliculas = new ArrayList<>();
    private static ArrayList<ProductoConfiteria> productos = new ArrayList<>();
    private static ArrayList<Venta> ventas = new ArrayList<>();

    public static void main(String[] args) {

        cargarDatosDemo();

        int opcion = 0;

        do {
            String menu = """
                ========================================
                 SISTEMA DE VENTAS Y MEMBRESIAS - CINE
                ========================================

                1. Registrar cliente
                2. Registrar nueva pelicula
                3. Registrar nuevo producto de confiteria
                4. Comprar membresia basica
                5. Vender boleta (taquilla)
                6. Vender producto de confiteria
                7. Mostrar clientes
                8. Mostrar peliculas
                9. Mostrar productos de confiteria
                10. Mostrar ventas realizadas
                11. Salir

                Seleccione una opcion:
                          """;

            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    registrarPelicula();
                    break;
                case 3:
                    registrarProducto();
                    break;
                case 4:
                    comprarMembresiaBasica();
                    break;
                case 5:
                    venderBoleta();
                    break;
                case 6:
                    venderConfiteria();
                    break;
                case 7:
                    mostrarClientes();
                    break;
                case 8:
                    mostrarPeliculas();
                    break;
                case 9:
                    mostrarProductos();
                    break;
                case 10:
                    mostrarVentas();
                    break;
                case 11:
                    System.out.println("Gracias por usar el sistema del cine.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 11);
    }

//datos de ejemplo
    public static void cargarDatosDemo() {
        //3 clientes
        Cliente juan = new Cliente("Juan", 1001, 25);
        Cliente maria = new Cliente("Maria", 1002, 30);
        Cliente carlos = new Cliente("Carlos", 1003, 19);
        clientes.add(juan);
        clientes.add(maria);
        clientes.add(carlos);

        //3 Peliculas
        peliculas.add(new Pelicula("Duna: Parte Dos", "Ciencia ficcion", 166, "12 años", 15000));
        peliculas.add(new Pelicula("Intensamente 2", "Animacion", 96, "Todo publico", 13000));
        peliculas.add(new Pelicula("Del Reves", "Terror", 110, "15 años", 14000));

        //3 Productos de confiteria
        productos.add(new ProductoConfiteria("Crispetas grandes", "Snack", 9000));
        productos.add(new ProductoConfiteria("Gaseosa mediana", "Bebida", 6000));
        productos.add(new ProductoConfiteria("Combo pareja", "Combo", 22000));

        // Ejemplo: Juan tiene membresia basica y ya acumulo 29
        //visitas en 29 dias distintos. Se deja su ultima visita en el dia
        // de ayer para que la proxima compra de boleta (hoy) cuente como
        // un nuevo dia y complete la visita numero 30.
        Membresia basicaJuan = new Membresia("BASICA");
        juan.setMembresia(basicaJuan);
        juan.setVisitas(29);
        juan.setFechaUltimaVisita(java.time.LocalDate.now().minusDays(1));

        System.out.println("Datos de ejemplo cargados: 3 clientes, 3 peliculas y 3 productos.");
    }

    // crea un nuevo cliente
    public static void registrarCliente() {
        String nombre = JOptionPane.showInputDialog("Nombre del cliente: ");
        int documento = Integer.parseInt(JOptionPane.showInputDialog("Documento del cliente: "));
        int edad = Integer.parseInt(JOptionPane.showInputDialog("Edad del cliente: "));

        Cliente cliente = new Cliente(nombre, documento, edad);
        clientes.add(cliente);
        System.out.println("Cliente registrado correctamente.");
    }

    // crea una nueva pelicula o producto
    public static void registrarPelicula() {
        String nombre = JOptionPane.showInputDialog("Nombre de la pelicula: ");
        String genero = JOptionPane.showInputDialog("Genero: ");
        int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duracion (minutos): "));
        String clasificacion = JOptionPane.showInputDialog("Clasificacion (ej. Todo publico, 12 años): ");
        double precioBoleta = Double.parseDouble(JOptionPane.showInputDialog("Precio de la boleta: "));

        Pelicula pelicula = new Pelicula(nombre, genero, duracion, clasificacion, precioBoleta);
        peliculas.add(pelicula);
        System.out.println("Pelicula registrada correctamente.");
    }

    public static void registrarProducto() {
        String nombre = JOptionPane.showInputDialog("Nombre del producto/combo: ");
        String tipo = JOptionPane.showInputDialog("Tipo (ej. Snack, Bebida, Combo): ");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio: "));

        ProductoConfiteria producto = new ProductoConfiteria(nombre, tipo, precio);
        productos.add(producto);
        System.out.println("Producto registrado correctamente.");
    }

    public static void comprarMembresiaBasica() {
        Cliente cliente = buscarClientePorDocumento();
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        Membresia membresia = new Membresia("BASICA");
        cliente.setMembresia(membresia);
        System.out.println("El cliente " + cliente.getNombre() + " ahora tiene membresia BASICA.");
    }

    // Ventas
    public static void venderBoleta() {
        if (peliculas.isEmpty()) {
            System.out.println("No hay peliculas registradas.");
            return;
        }

        Cliente cliente = buscarClientePorDocumento();
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        int indice = elegirDeLista("PELICULAS DISPONIBLES", descripcionesPeliculas());
        if (indice < 0 || indice >= peliculas.size()) {
            System.out.println("Opcion invalida.");
            return;
        }
        Pelicula pelicula = peliculas.get(indice);

        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de boletas a comprar: "));
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
            return;
        }

        Venta venta = new Venta(cliente, "BOLETA", pelicula.getNombre(), cantidad, pelicula.getPrecioBoleta());
        venta.calcularTotal();
        ventas.add(venta);
        venta.mostrarVenta();

        // Sin importar cuantas boletas se compren, solo se suma UNA visita
        // por dia (se necesitan 30 dias distintos para la membresia Premium)
        boolean visitaNueva = cliente.registrarVisita();
        if (visitaNueva) {
            System.out.println(cliente.getNombre() + " registro una nueva visita. Total de visitas: " + cliente.getVisitas());
        } else {
            System.out.println(cliente.getNombre() + " ya tenia una visita contabilizada hoy. Total de visitas: " + cliente.getVisitas());
        }

        if (cliente.puedeObtenerPremium()) {
            cliente.setMembresia(new Membresia("PREMIUM"));
            System.out.println(cliente.getNombre() + " ha alcanzado las 30 visitas en dias distintos y ahora tiene membresia PREMIUM.");
        }
    }

    public static void venderConfiteria() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        Cliente cliente = buscarClientePorDocumento();
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        int indice = elegirDeLista("PRODUCTOS DE CONFITERIA DISPONIBLES", descripcionesProductos());
        if (indice < 0 || indice >= productos.size()) {
            System.out.println("Opcion invalida.");
            return;
        }
        ProductoConfiteria producto = productos.get(indice);

        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad: "));
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
            return;
        }

        Venta venta = new Venta(cliente, "CONFITERIA", producto.getNombre(), cantidad, producto.getPrecio());
        venta.calcularTotal();
        ventas.add(venta);
        venta.mostrarVenta();
    }

    // menu para escoger pelicula o producto
    private static int elegirDeLista(String titulo, ArrayList<String> opciones) {
        StringBuilder menu = new StringBuilder();
        menu.append(titulo).append("\n\n");
        for (int i = 0; i < opciones.size(); i++) {
            menu.append((i + 1)).append(". ").append(opciones.get(i)).append("\n");
        }
        menu.append("\nDigite el numero de la opcion que desea comprar: ");

        int seleccion = Integer.parseInt(JOptionPane.showInputDialog(menu.toString()));
        return seleccion - 1;
    }

    private static ArrayList<String> descripcionesPeliculas() {
        ArrayList<String> lista = new ArrayList<>();
        for (Pelicula p : peliculas) {
            lista.add(p.getNombre() + " (" + p.getGenero() + ", " + p.getClasificacion() + ") - $" + p.getPrecioBoleta());
        }
        return lista;
    }

    private static ArrayList<String> descripcionesProductos() {
        ArrayList<String> lista = new ArrayList<>();
        for (ProductoConfiteria p : productos) {
            lista.add(p.getNombre() + " (" + p.getTipo() + ") - $" + p.getPrecio());
        }
        return lista;
    }

    // Muestra informacion que se selecciono en el menu
    public static void mostrarClientes() {
        System.out.println(" ===== CLIENTES ===== ");
        for (Cliente c : clientes) {
            c.mostrarInformacion();
        }
    }

    public static void mostrarPeliculas() {
        System.out.println(" ===== PELICULAS EN CARTELERA ===== ");
        for (Pelicula p : peliculas) {
            p.mostrarInformacion();
        }
    }

    public static void mostrarProductos() {
        System.out.println(" ===== PRODUCTOS DE CONFITERIA ===== ");
        for (ProductoConfiteria p : productos) {
            p.mostrarInformacion();
        }
    }

    public static void mostrarVentas() {
        System.out.println(" ===== VENTAS REALIZADAS ===== ");
        if (ventas.isEmpty()) {
            System.out.println("Aun no se han registrado ventas.");
            return;
        }
        for (Venta v : ventas) {
            v.mostrarVenta();
        }
    }

    // busca al cliente por su documento para añadirle los datos de su visita
    //(boletas o productos de confiteria, visitas, etc)
    public static Cliente buscarClientePorDocumento() {
        int documento = Integer.parseInt(JOptionPane.showInputDialog("Documento del cliente: "));
        for (Cliente c : clientes) {
            if (c.getDocumento() == documento) {
                return c;
            }
        }
        return null;
    }
}
