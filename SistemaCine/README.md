# Sistema de Ventas y Membresías para un Cine

## Integrantes
- Juan David Aguilera
- Marianna Fernandez

## Jerarquías de clases (herencia)

| Clase padre | Clases hijas | Idea |
|---|---|---|
| `Usuario` (abstracta) | `Cliente`, `Empleado`, `Administrador` | Datos comunes (nombre, documento, edad); cada hijo define su rol y su detalle |
| `Membresia` (abstracta, implementa `Descontable`) | `MembresiaBasica`, `MembresiaPremium` | Cada membresía calcula sus propios descuentos de boletas y confitería |
| `Sede` (abstracta) | `Pelicula`, `Sala` | Una `Sala` tiene una `Pelicula` que se proyecta en ella |

## Otras clases
- `Descontable`: interfaz con `calcularDescuentoBoleta` y `calcularDescuentoConfiteria`.
- `ClienteNoEncontradoException` y `DatoInvalidoException`: excepciones propias.
- `ProductoConfiteria` y `Venta`: datos de la confitería y de cada pedido.
- `Registros`: guarda los `ArrayList` y tiene la lógica del cine (registrar, buscar, vender). Las ventanas no calculan nada.
- `Tablas`: arma los modelos de las `JTable`.
- `SistemaCine`: clase principal; crea `Registros` y abre `VentanaMenu`.

## Interfaz gráfica (Swing)
| Ventana | Qué hace |
|---|---|
| `VentanaMenu` | Menú principal con botones |
| `VentanaMenuVentas` | Segundo menú: Taquilla o Confitería |
| `VentanaVenta` | Pedido (cliente, sala o producto, cantidad). **Calcular** muestra subtotal, descuento y total; **Confirmar venta** la registra |
| `VentanaRegistrarCliente` / `VentanaRegistrarPelicula` / `VentanaRegistrarProducto` | Formulario y tabla con lo registrado |
| `VentanaMembresia` | Compra de membresía básica por documento |
| `VentanaConsulta` | Una tabla para usuarios, películas, salas, productos o ventas |

## Conceptos aplicados
- **Herencia:** las tres jerarquías anteriores.
- **Clases abstractas:** `Usuario`, `Membresia` y `Sede`.
- **Interfaz:** `Descontable`.
- **Polimorfismo:** `ArrayList<Usuario>` con clientes, empleados y administradores (`getRol()` y `getDetalle()` cambian según el tipo); `cliente.calcularDescuento(...)` usa la membresía que tenga, básica o premium.
- **Excepciones:** `ClienteNoEncontradoException`, `DatoInvalidoException` y `NumberFormatException` controladas en las ventanas.
- **Colecciones:** `ArrayList` de usuarios, películas, salas, productos y ventas.
- **Encapsulamiento:** atributos privados con getters y setters validados.

## Descuentos
| Membresía | Boletas | Confitería |
|---|---|---|
| Ninguna | 0 % | 0 % |
| Básica | 10 % | 5 % |
| Premium | 25 % | 15 % |

La Premium se asigna sola cuando un cliente con Básica completa 30 visitas en días distintos.

## Cómo ejecutar
Abrir la carpeta como proyecto Maven en NetBeans y ejecutar `SistemaCine`
(`com.gitb.juandaaguilera.Sistema.SistemaCine`). Al iniciar se cargan datos de ejemplo:
- Clientes: Juan (1001), Maria (1002), Carlos (1003). Empleado: Laura (2001). Administrador: Andres (3001).
- Juan ya tiene membresía Básica y 29 visitas, así que su próxima compra de boletas lo sube a Premium.

## Git (rama de entrega)
```
git checkout -b entrega
git add .
git commit -m "Segundo corte: herencia, interfaz, excepciones e interfaz grafica"
git push -u origin entrega
```
