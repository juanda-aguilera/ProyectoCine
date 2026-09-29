# Sistema de Ventas y Membresías - Cine

Proyecto de Programación I (segundo corte, Programación Orientada a Objetos) - Universidad Libre.
Aplicación de escritorio en **Java (Swing)** para vender boletas y productos de confitería de un cine,
aplicando descuentos según la membresía del cliente.

**Integrantes:** Juan David Aguilera y Marianna Fernandez

## Tabla de contenido
- [Arquitectura del proyecto](#arquitectura-del-proyecto)
- [Jerarquías de clases (herencia)](#jerarquías-de-clases-herencia)
- [Conceptos de POO aplicados y dónde verlos](#conceptos-de-poo-aplicados-y-dónde-verlos)
- [Datos de ejemplo precargados](#datos-de-ejemplo-precargados)
- [Interfaz gráfica](#interfaz-gráfica)
- [Reglas de negocio](#reglas-de-negocio)
- [Descripción de cada clase](#descripción-de-cada-clase)
- [Flujo de uso recomendado](#flujo-de-uso-recomendado)
- [Cómo ejecutar](#cómo-ejecutar)
- [Entrega y Git](#entrega-y-git)
- [Notas y limitaciones conocidas](#notas-y-limitaciones-conocidas)

---

## Arquitectura del proyecto

Todo está en el paquete `com.gitb.juandaaguilera.Sistema` y se divide en tres capas:

| Capa | Clases | Responsabilidad |
|---|---|---|
| **Modelo** | `Usuario`, `Cliente`, `Empleado`, `Administrador`, `Membresia`, `MembresiaBasica`, `MembresiaPremium`, `Descontable`, `Sede`, `Pelicula`, `Sala`, `ProductoConfiteria`, `Venta` | Los datos y las reglas propias de cada objeto (por ejemplo, cada membresía calcula sus descuentos). |
| **Lógica** | `Registros`, `Tablas`, `ClienteNoEncontradoException`, `DatoInvalidoException` | `Registros` guarda las listas y ejecuta registrar, buscar y vender. `Tablas` convierte las listas en tablas. Las excepciones propias avisan de datos inválidos. |
| **Interfaz** | `SistemaCine`, `VentanaMenu`, `VentanaMenuVentas`, `VentanaVenta`, `VentanaRegistrarCliente`, `VentanaRegistrarPelicula`, `VentanaRegistrarProducto`, `VentanaMembresia`, `VentanaConsulta` | Piden datos, llaman a `Registros` y muestran el resultado. **Las ventanas no calculan nada.** |

Los datos (`usuarios`, `peliculas`, `salas`, `productos`, `ventas`) se guardan **en memoria** con `ArrayList`
dentro de `Registros`. **No hay persistencia**: al cerrar la aplicación se pierden.

Relaciones entre clases:
- `Cliente` **tiene una** `Membresia` (puede ser `null` si no tiene).
- `Sala` **tiene una** `Pelicula`: en una sala se proyecta una película.
- `Venta` **tiene un** `Cliente` y guarda la membresía que tenía al momento de comprar.
- `Registros` **tiene** las listas de todo lo anterior.

## Jerarquías de clases (herencia)

```
Usuario (abstracta)              Membresia (abstracta, implementa Descontable)      Sede (abstracta)
 ├── Cliente                      ├── MembresiaBasica                                ├── Pelicula
 ├── Empleado                     └── MembresiaPremium                               └── Sala  ──tiene una──> Pelicula
 └── Administrador
```

| Clase padre | Hijas | Qué aporta cada una |
|---|---|---|
| `Usuario` | `Cliente`, `Empleado`, `Administrador` | El padre guarda nombre, documento y edad. Cada hija define su `getRol()` y su `getDetalle()`: el cliente muestra visitas y membresía, el empleado su cargo y el administrador su área. |
| `Membresia` | `MembresiaBasica`, `MembresiaPremium` | El padre guarda tipo y precio. Cada hija implementa sus propios métodos de descuento en boletas y en confitería. |
| `Sede` | `Pelicula`, `Sala` | El padre guarda el nombre y obliga a definir `getDescripcion()`. `Pelicula` añade género, duración, clasificación y precio; `Sala` añade capacidad y la película que proyecta. |

## Conceptos de POO aplicados y dónde verlos

| Concepto | Dónde se aplica |
|---|---|
| **Herencia** | Las tres jerarquías anteriores (`extends`). |
| **Clases abstractas** | `Usuario` (`getRol()`, `getDetalle()`), `Membresia` (hereda los métodos de `Descontable` sin implementarlos) y `Sede` (`getDescripcion()`). |
| **Interfaz** | `Descontable`: `calcularDescuentoBoleta(precio)` y `calcularDescuentoConfiteria(precio)`. La implementa `Membresia` y sus hijas la completan. |
| **Polimorfismo** | 1) `ArrayList<Usuario>` guarda clientes, empleados y administradores, y `Tablas.usuarios()` llama `getRol()` y `getDetalle()` sin saber el tipo. 2) `Cliente.calcularDescuento()` llama a `membresia.calcularDescuentoBoleta()` y responde distinto si es básica o premium. |
| **Encapsulamiento** | Todos los atributos son `private`, con getters y setters que validan (por ejemplo, no aceptan precios negativos). |
| **Excepciones** | `ClienteNoEncontradoException` (`Registros.buscarCliente`), `DatoInvalidoException` (cantidad menor o igual a 0 o sala sin película, en `VentanaVenta`) y `NumberFormatException` controlada con `try/catch` en todos los formularios. |
| **Colecciones** | `ArrayList` de usuarios, películas, salas, productos y ventas, más `ArrayList<String>` para los `JComboBox`. |
| **Responsabilidades** | Modelo, lógica (`Registros`) e interfaz (ventanas) están separados. |

## Datos de ejemplo precargados

Al iniciar, `Registros` carga automáticamente:

- **3 clientes:** Juan (documento 1001), Maria (1002) y Carlos (1003).
- **1 empleado:** Laura (2001), taquillera. **1 administrador:** Andres (3001), gerencia general.
- **3 películas:** *Duna: Parte Dos* ($15.000), *Intensamente 2* ($13.000) y *Del Revés* ($14.000).
- **3 salas:** Sala 1 (80 puestos) proyecta *Duna*, Sala 2 (60) proyecta *Intensamente 2* y Sala 3 (100) proyecta *Del Revés*.
- **3 productos de confitería:** Crispetas grandes ($9.000), Gaseosa mediana ($6.000) y Combo pareja ($22.000).
- **Juan** ya tiene membresía **BÁSICA**, **29 visitas** y su última visita fue ayer. Así, comprarle una boleta lo lleva a 30 visitas y lo asciende a **PREMIUM**.

## Interfaz gráfica

Al ejecutar aparece `VentanaMenu`, con un botón por cada opción. Cada botón abre su ventana; al cerrarla se vuelve al menú.

| Botón del menú principal | Ventana | Qué hace |
|---|---|---|
| Registrar cliente | `VentanaRegistrarCliente` | Formulario (nombre, documento, edad) y tabla de usuarios. |
| Registrar película | `VentanaRegistrarPelicula` | Formulario (nombre, género, duración, clasificación, precio, **sala donde se proyecta**) y tabla de películas. |
| Registrar producto de confitería | `VentanaRegistrarProducto` | Formulario (nombre, tipo, precio) y tabla de productos. |
| Comprar membresía básica | `VentanaMembresia` | Pide el documento de un cliente y le asigna la membresía básica. |
| **Ventas** | `VentanaMenuVentas` | **Segundo menú:** Taquilla, Confitería o Volver. |
| Mostrar usuarios / películas / salas / productos / ventas realizadas | `VentanaConsulta` | Una sola ventana con una tabla que cambia según lo que se consulte. |
| Salir | - | Cierra la aplicación. |

### Ventas: `VentanaVenta`

La misma ventana sirve para taquilla y para confitería; solo cambia la lista desplegable:

| | Taquilla | Confitería |
|---|---|---|
| Lista desplegable | Sala y película (con clasificación y precio) | Producto (con tipo y precio) |
| Cantidad | Boletas | Unidades |

Campos: **documento del cliente**, **sala/película o producto**, y **cantidad**. Botones:

- **Calcular:** muestra subtotal, descuento según la membresía y total a pagar. **No registra nada**, sirve para revisar el pedido.
- **Confirmar venta:** registra la venta y muestra el resumen. En taquilla, además, cuenta la visita y revisa si el cliente asciende a Premium.
- **Cerrar:** vuelve al menú.

Errores que controla, con mensaje en pantalla:
- Documento o cantidad que no son números.
- Cantidad menor o igual a 0.
- Documento de un cliente que no existe.
- Sala sin película asignada.

## Reglas de negocio

### Membresías y descuentos

| Membresía | Precio | Descuento en boletas | Descuento en confitería | Cómo se obtiene |
|---|---|---|---|---|
| Ninguna | - | 0 % | 0 % | Estado inicial de todo cliente nuevo |
| BÁSICA | $20.000 | 10 % | 5 % | Se compra desde el menú principal |
| PREMIUM | $0 (no se compra) | 25 % | 15 % | Se gana automáticamente con 30 visitas siendo BÁSICA |

Cada tipo de membresía calcula su descuento en su propia clase (`MembresiaBasica` y `MembresiaPremium`).
Al comprar la básica se **reemplaza cualquier membresía previa**.

### Cálculo de una venta

1. **Subtotal** = precio unitario × cantidad.
2. **Descuento** = lo que calcula la membresía del cliente sobre el subtotal, según la venta sea de boleta o de confitería (si no tiene membresía, $0).
3. **Total** = subtotal - descuento.

El descuento se calcula con la membresía que el cliente tiene **antes** de la compra. Si esa compra lo asciende a Premium, el descuento Premium aplica desde la siguiente.

### Visitas y ascenso a Premium

- Una **visita** solo se cuenta cuando se **confirma** una venta de boletas. La confitería no cuenta.
- Solo se cuenta **una visita por día**, sin importar cuántas boletas compre el cliente.
- Un cliente con membresía **BÁSICA** que llega a **30 visitas** pasa a **PREMIUM** en esa misma compra.
- Un cliente sin membresía acumula visitas, pero no asciende: primero necesita la básica.

## Descripción de cada clase

### Modelo

- **`Usuario`** (abstracta): `nombre`, `documento`, `edad`; métodos abstractos `getRol()` y `getDetalle()`.
- **`Cliente`**: agrega `membresia`, `visitas` y `fechaUltimaVisita`.
  - `registrarVisita()`: suma una visita solo si es un día distinto al de la última. Devuelve `true` si la contó.
  - `puedeObtenerPremium()`: `true` si tiene membresía BÁSICA y 30 o más visitas.
  - `calcularDescuento(tipoVenta, precio)`: delega el cálculo en su membresía.
- **`Empleado`**: agrega `cargo`. **`Administrador`**: agrega `area`.
- **`Descontable`** (interfaz): contrato de los dos métodos de descuento.
- **`Membresia`** (abstracta): `tipo` y `precio`. Implementa `Descontable` sin definir los descuentos.
- **`MembresiaBasica`** / **`MembresiaPremium`**: fijan tipo y precio, e implementan los descuentos con sus porcentajes.
- **`Sede`** (abstracta): `nombre` y `getDescripcion()` abstracto.
- **`Pelicula`**: `genero`, `duracion` (minutos), `clasificacion`, `precioBoleta`.
- **`Sala`**: `capacidad` y `pelicula` (la que se proyecta; puede ser `null`).
- **`ProductoConfiteria`**: `nombre`, `tipo`, `precio`.
- **`Venta`**: `cliente`, `membresia` (al momento de comprar), `tipoVenta` (`"BOLETA"` o `"CONFITERIA"`), `producto`, `cantidad`, `precio`, `subtotal`, `descuento`, `total`. `calcularTotal()` calcula subtotal y descuento en orden y deja el total; `getResumen()` devuelve el texto que se muestra.

### Lógica

- **`Registros`**: guarda las cinco listas y carga los datos de ejemplo. Sus métodos devuelven un texto que la ventana muestra:
  - `registrarCliente`, `registrarPelicula`, `registrarProducto` y `comprarMembresiaBasica`.
  - `buscarCliente`: lanza `ClienteNoEncontradoException` si no existe.
  - `crearVentaBoleta` y `crearVentaConfiteria`: arman la venta y calculan, sin guardarla.
  - `confirmarVenta`: guarda la venta y, si es de boletas, cuenta la visita y revisa el ascenso.
- **`Tablas`**: crea el modelo (`DefaultTableModel`) de cada `JTable`, con celdas no editables.
- **`ClienteNoEncontradoException`** y **`DatoInvalidoException`**: excepciones propias (`extends Exception`).

## Flujo de uso recomendado

1. Ejecuta el programa: se abre el menú principal con los datos de ejemplo.
2. **Mostrar usuarios** para ver los clientes y sus documentos.
3. **Ventas > Taquilla:** documento `1001` (Juan), Sala 1, cantidad 2. Pulsa **Calcular** (10 % de descuento por ser Básica) y luego **Confirmar venta**: pasa de 29 a 30 visitas y asciende a **PREMIUM**.
4. **Ventas > Confitería:** documento `1001`, Combo pareja, cantidad 1. Ahora el descuento es del 15 %.
5. **Registrar cliente** para crear uno nuevo y **Comprar membresía básica** para asignársela.
6. Vuelve a **Ventas** con ese cliente y comprueba el 10 % en boletas y el 5 % en confitería.
7. **Registrar película** y elige una sala: la película nueva reemplaza a la que proyectaba esa sala.
8. **Mostrar ventas realizadas** para revisar el historial de la sesión.

## Cómo ejecutar

**Requisitos:** JDK 26 (el `pom.xml` usa `maven.compiler.release` 26) y Maven, o NetBeans.

- **NetBeans:** abrir la carpeta como proyecto Maven y ejecutar `SistemaCine`.
- **Clase principal:** `com.gitb.juandaaguilera.Sistema.SistemaCine`.

## Entrega y Git

La entrega es un único ZIP con esta estructura:

```
PROYECTO/
 ├── 01_DOCUMENTO/    Documento del proyecto.pdf
 ├── 02_CODIGO/       Proyecto_Java/   (esta carpeta)
 └── 03_PRESENTACION/ Presentacion.pdf
```

El código debe estar en Git, en una rama llamada `entrega`:

```
git checkout -b entrega
git add .
git commit -m "Segundo corte: herencia, interfaz, excepciones e interfaz grafica"
git push -u origin entrega
```

Antes de entregar: comprobar que compila y se ejecuta desde la rama `entrega` y que el contenido del ZIP coincide con lo publicado en Git.

## Notas y limitaciones conocidas

- **Sin persistencia:** los datos viven solo en memoria; al cerrar el programa se pierden.
- **Salas fijas:** hay 3 salas y no se pueden crear, editar ni eliminar desde la aplicación; solo se les asigna película.
- **Sin control de aforo:** la capacidad de la sala es informativa; no se descuentan puestos al vender boletas.
- **Empleados y administradores:** existen como usuarios y se muestran en la tabla, pero no intervienen en las ventas ni tienen permisos distintos.
- **Comprar la básica no genera una venta:** solo asigna la membresía; el precio de $20.000 no se cobra ni queda en el historial. Si el cliente era Premium, pasa a Básica.
- **Listas desplegables:** `VentanaVenta` carga las salas y productos al abrirse. Si se registra algo después, hay que volver a abrir la ventana de ventas.
- **Sin edición ni borrado:** no se pueden modificar ni eliminar clientes, películas, productos ni ventas.
- **Formato de precios:** se muestran como número decimal (por ejemplo, `$15000.0`).
