# Sistema de Ventas y Membresías - Cine

## Tabla de contenido
- [Arquitectura del proyecto](#arquitectura-del-proyecto)
- [Datos de ejemplo precargados](#datos-de-ejemplo-precargados)
- [Menú principal](#menú-principal)
- [Reglas de negocio](#reglas-de-negocio)
- [Descripción de cada clase](#descripción-de-cada-clase)
- [Flujo de uso recomendado](#flujo-de-uso-recomendado)
- [Notas y limitaciones conocidas](#notas-y-limitaciones-conocidas)

---

## Arquitectura del proyecto

| Clase | Rol |
|---|---|
| `SistemaCine` | Clase principal. Contiene el menú, el bucle de ejecución y la lógica de orquestación (registrar, vender, mostrar). |
| `Cliente` | Representa a una persona que compra boletas o confitería. Guarda visitas y membresía. |
| `Membresia` | Representa el plan BÁSICA o PREMIUM, con sus descuentos asociados. |
| `Pelicula` | Representa una película en cartelera con su precio de boleta. |
| `ProductoConfiteria` | Representa un producto o combo de confitería. |
| `Venta` | Representa una transacción (boleta o confitería), calculando subtotal, descuento y total. |

Todas las listas de datos (`clientes`, `peliculas`, `productos`, `ventas`) se guardan **en memoria** (`ArrayList`) mientras el programa está abierto. **No hay persistencia**: al cerrar la aplicación, todos los datos se pierden.

## Datos de ejemplo precargados

Al iniciar, `cargarDatosDemo()` registra automáticamente:

- **3 clientes**: Juan (1001), María (1002), Carlos (1003).
- **3 películas**: *Duna: Parte Dos*, *Intensamente 2*, *Del Revés*.
- **3 productos de confitería**: Crispetas grandes, Gaseosa mediana, Combo pareja.
- **Juan** ya tiene membresía **BÁSICA** y **29 visitas** registradas, para que puedas probar fácilmente el ascenso automático a **PREMIUM** comprando una boleta más a su nombre (documento `1001`).

## Menú principal

Al ejecutar el programa se repite un menú con 11 opciones hasta elegir "Salir":

| Opción | Función | Qué hace |
|---|---|---|
| 1 | Registrar cliente | Crea un nuevo cliente (nombre, documento, edad) sin membresía ni visitas. |
| 2 | Registrar nueva película | Agrega una película a la cartelera. |
| 3 | Registrar nuevo producto de confitería | Agrega un producto o combo al catálogo. |
| 4 | Comprar membresía básica | Asigna membresía BÁSICA a un cliente existente. |
| 5 | Vender boleta (taquilla) | Registra la venta de boletas de una película a un cliente y cuenta su visita del día. |
| 6 | Vender producto de confitería | Registra la venta de uno o varios productos de confitería. |
| 7 | Mostrar clientes | Lista todos los clientes registrados con su info básica. |
| 8 | Mostrar películas | Lista la cartelera actual. |
| 9 | Mostrar productos de confitería | Lista el catálogo de confitería. |
| 10 | Mostrar ventas realizadas | Lista todas las ventas hechas en la sesión actual. |
| 11 | Salir | Termina el programa. |

Ahora te explicamos a detalle cada opción.

### 1. Registrar cliente

Solicita, en orden: **nombre**, **documento** (número) y **edad** (número). Crea un `Cliente` sin membresía (`null`) y con 0 visitas, y lo agrega a la lista de clientes.

No valida que el documento no esté repetido, ni que los campos numéricos sean válidos: si escribes texto donde se espera un número, el programa lanzará una excepción (`NumberFormatException`) y se detendrá.

### 2. Registrar nueva película

Solicita: **nombre**, **género**, **duración en minutos**, **clasificación** (ej. "Todo público", "12 años") y **precio de la boleta**. La agrega a la cartelera.

### 3. Registrar nuevo producto de confitería

Solicita: **nombre**, **tipo** (ej. Snack, Bebida, Combo) y **precio**. Lo agrega al catálogo.

### 4. Comprar membresía básica

1. Pide el **documento** del cliente (busca por coincidencia exacta).
2. Si no existe, informa "Cliente no encontrado."
3. Si existe, le asigna una membresía `BASICA` (precio $20.000, 10% descuento en boleta, 5% en confitería) **reemplazando cualquier membresía previa**.

### 5. Vender boleta (taquilla)

1. Verifica que haya películas en cartelera.
2. Pide el documento del cliente.
3. Muestra la lista de películas disponibles (con género, clasificación y precio) para elegir una por número.
4. Pide la **cantidad de boletas**.
5. Crea una `Venta` tipo `"BOLETA"`, calcula subtotal, descuento (según la membresía del cliente) y total, y la imprime.
6. Registra la **visita del día** del cliente (ver [reglas de negocio](#reglas-de-negocio)).
7. Si el cliente cumple la condición de 30 visitas, se le asciende automáticamente a **PREMIUM**.

### 6. Vender producto de confitería

1. Verifica que haya productos en catálogo.
2. Pide el documento del cliente.
3. Muestra la lista de productos disponibles para elegir uno por número.
4. Pide la **cantidad**.
5. Crea una `Venta` tipo `"CONFITERIA"`, calcula subtotal, descuento y total, y la imprime.

Nota: la venta de confitería **no** registra visita del cliente; solo la compra de boletas cuenta como visita.

### 7, 8, 9, 10. Mostrar listados

Recorren e imprimen en consola toda la información de clientes, películas, productos o ventas registradas hasta el momento, usando el método `mostrarInformacion()` (o `mostrarVenta()`) de cada clase.

### 11. Salir

Imprime un mensaje de despedida y termina el bucle principal del programa.

---

## Reglas de negocio

### Visitas y ascenso a membresía Premium

- Una **visita** solo se cuenta si el cliente compra al menos una boleta.
- **No importa cuántas boletas compre en el mismo día**: solo se contabiliza **una visita por día natural** (`LocalDate.now()`), comparando contra la fecha de la última visita registrada (`fechaUltimaVisita`).
- Cuando un cliente con membresía **BÁSICA** acumula **30 visitas en días distintos**, en la siguiente compra de boleta se le asigna automáticamente una nueva membresía **PREMIUM**, reemplazando la básica.
- Un cliente **sin membresía** puede acumular visitas, pero no puede ascender a Premium (la lógica de `puedeObtenerPremium()` exige tener primero membresía BÁSICA).

### Membresías y descuentos

| Membresía | Precio | Descuento en boleta | Descuento en confitería | Cómo se obtiene |
|---|---|---|---|---|
| Ninguna | — | 0% | 0% | Estado inicial de todo cliente nuevo |
| BÁSICA | $20.000 | 10% | 5% | Se compra (opción 4 del menú) |
| PREMIUM | $0 (no se compra) | 25% | 15% | Se gana automáticamente al llegar a 30 visitas en días distintos siendo BÁSICA |

El descuento se calcula **sobre el subtotal** (precio × cantidad) de cada venta, según el tipo (`BOLETA` o `CONFITERIA`).

### Cálculo de una venta

Para toda venta (boleta o confitería), el orden de cálculo es:

1. **Subtotal** = precio unitario × cantidad.
2. **Descuento** = subtotal × (porcentaje de descuento de la membresía del cliente / 100). Si el cliente no tiene membresía, el descuento es $0.
3. **Total** = Subtotal − Descuento.

---

## Descripción de cada clase

### `Cliente`

Atributos: `nombre`, `documento`, `edad`, `membresia`, `visitas`, `fechaUltimaVisita`.

Métodos de comportamiento clave:
- `registrarVisita()`: suma una visita solo si es un día distinto al de la última visita. Devuelve `true`/`false` según si se contó o no.
- `puedeObtenerPremium()`: `true` si tiene membresía BÁSICA y ≥30 visitas.
- `calcularDescuento(tipoVenta, precio)`: delega el cálculo del descuento a la membresía, según si la venta es de boleta o confitería.
- `mostrarInformacion()`: imprime los datos del cliente en consola.

### `Membresia`

Atributos: `tipo`, `precio`, `descuentoBoleta`, `descuentoConfiteria`.

Tiene dos constructores:
- Uno general (`tipo, precio, descuentoBoleta, descuentoConfiteria`) para valores personalizados.
- Uno simplificado (`tipo`) que asigna automáticamente los valores estándar de BÁSICA o PREMIUM descritos arriba.

Métodos: `calcularDescuentoBoleta(precio)` y `calcularDescuentoConfiteria(precio)`, que devuelven el **valor en pesos** del descuento (no el precio final).

### `Pelicula`

Atributos: `nombre`, `genero`, `duracion` (minutos), `clasificacion`, `precioBoleta`. Incluye `cambiarPrecio()` para actualizar el precio de la boleta con validación (no permite negativos).

### `ProductoConfiteria`

Atributos: `nombre`, `tipo`, `precio`. Incluye `calcularPrecioConDescuento(porcentaje)`, útil si se quisiera aplicar un descuento puntual fuera del flujo de `Venta`.

### `Venta`

Atributos: `cliente`, `tipoVenta` (`"BOLETA"` o `"CONFITERIA"`), `producto` (nombre de la película o producto), `cantidad`, `precio` unitario, `subtotal`, `descuento`, `total`.

Método principal: `calcularTotal()`, que internamente llama a `calcularSubtotal()` y `calcularDescuento()` en orden, y deja el resultado final en `total`.

---

## Flujo de uso recomendado

1. Ejecuta el programa (carga automáticamente los datos demo).
2. Opción **7** para ver los clientes ya cargados y sus documentos.
3. Opción **5** para venderle una boleta a **Juan (documento 1001)**: al hacerlo, verás cómo pasa de 29 a 30 visitas y asciende automáticamente a **PREMIUM**.
4. Opción **1** para registrar un cliente nuevo.
5. Opción **4** para darle membresía BÁSICA al cliente que acabas de crear.
6. Opción **5** o **6** para venderle una boleta o un producto y comprobar que el descuento del 10%/5% se aplica correctamente.
7. Opción **10** para revisar el historial de ventas de la sesión.
8. Opción **11** para salir.

## Notas y limitaciones conocidas

- **Sin persistencia**: los datos viven solo en memoria; al cerrar el programa se pierden.
- **Sin validación robusta de entradas**: si se ingresa texto en un campo numérico (documento, edad, cantidad, precio), el programa lanza una excepción y se detiene en lugar de pedir el dato de nuevo.
- **Documentos duplicados permitidos**: no hay verificación de unicidad al registrar un cliente nuevo; `buscarClientePorDocumento()` (referenciada en `SistemaCine` pero no incluida en los archivos analizados) determinará cuál cliente se recupera en caso de duplicados.
- **Interfaz híbrida**: la entrada de datos es gráfica (`JOptionPane`), pero toda la salida (confirmaciones, listados, ventas) es por consola. Es importante tener ambas ventanas visibles al usar la aplicación.
