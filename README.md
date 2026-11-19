# Sistema de Ventas y Membresías - Cine

Proyecto final de **Programación 1** (Universidad Libre, docente Andrés Castaño).
Aplicación de escritorio en **Java (Swing)** con base de datos **MySQL (XAMPP)** para vender boletas y productos
de confitería de un cine, aplicando descuentos según la membresía del cliente.

**Integrantes:** Juan David Aguilera y Marianna Fernandez

## Contenido del repositorio

| Carpeta / archivo | Qué contiene |
|---|---|
| `SistemaCine/` | Proyecto Maven con todo el código fuente (`pom.xml` y `src`) |
| `database/cine_db.sql` | Script que crea la base de datos `cine_db`, sus tablas y los datos de ejemplo |
| `docs/DOCUMENTACION.md` | Documentación oficial del proyecto: arquitectura, modelo de datos, diagramas, SOLID y patrones |
| `README.md` | Este archivo: integrantes, ejecución y mapa del proyecto |

## Cómo ejecutar

**Requisitos:** JDK 26 (el `pom.xml` usa `maven.compiler.release` 26), NetBeans (o Maven) y XAMPP.
La primera vez Maven necesita internet para descargar el driver `mysql-connector-j`.

1. **Base de datos.** Abrir XAMPP e iniciar **MySQL** (Apache solo hace falta para phpMyAdmin).
2. Entrar a <http://localhost/phpmyadmin> > pestaña **Importar** > elegir `database/cine_db.sql` > **Continuar**.
   (También funciona por consola: `mysql -u root < database/cine_db.sql`). El script se puede importar varias veces:
   deja la base como nueva, con los datos de ejemplo.
3. **Proyecto.** En NetBeans: *File > Open Project* y elegir la carpeta `SistemaCine`. Ejecutar la clase principal
   `com.gitb.juandaaguilera.Sistema.SistemaCine` (botón *Run*).
4. **Conexión.** Por defecto usa `localhost:3306`, usuario `root` y sin clave (lo normal en XAMPP). Si tu MySQL es
   distinto, cambia `SistemaCine/src/main/resources/bd.properties`.

Si MySQL no está encendido o falta el script, el programa muestra un mensaje explicando qué hacer y no se cierra con un error.

**Datos de ejemplo** (los carga `cine_db.sql`): clientes Juan (1001), Maria (1002) y Carlos (1003); empleada Laura (2001);
administrador Andres (3001); 3 películas, 3 salas y 3 productos de confitería. Juan ya tiene membresía BÁSICA y 29 visitas:
su próxima compra de boletas lo asciende a PREMIUM.

## Mapa del proyecto

Paquete base: `com.gitb.juandaaguilera.Sistema` (los paquetes de abajo son subpaquetes de este).

### Pilares de POO

| Concepto | Dónde está | Cómo se usa |
|---|---|---|
| Herencia | `modelo`: `Usuario` → `Cliente`, `Empleado`, `Administrador`; `Sede` → `Pelicula`, `Sala`; `Venta` → `VentaBoleta`, `VentaConfiteria`. `membresia`: `Membresia` → `MembresiaBasica`, `MembresiaPremium`, `SinMembresia` | Las hijas reutilizan los datos del padre y especializan su comportamiento (por ejemplo, cada membresía calcula su descuento). |
| Polimorfismo | `Tablas.usuarios` (`interfaz`), `Venta.calcularTotal` (`modelo`), `Cliente.ascenderMembresia` (`modelo`) | Una `List<Usuario>` guarda clientes, empleados y administradores y se llama `getRol()`/`getDetalle()` sin saber el tipo; `calcularTotal()` pide el descuento a la `Venta` y a la membresía sin saber cuál es. |
| Encapsulamiento | Todas las clases de `modelo` y `membresia` | Atributos `private`; los setters validan y lanzan `DatoInvalidoException` (documento ≤ 0, precio negativo, cantidad ≤ 0...). |
| Abstracción | `Usuario`, `Sede`, `Venta`, `Membresia`, `CreadorMembresia` | Clases `abstract` con métodos abstractos: `getRol()`, `getDescripcion()`, `getTipoVenta()`, `descuentoSegun()`, `cuentaComoVisita()`, `crearMembresia()`. |
| Interfaces | `Descontable` (`membresia`), `Describible` (`modelo`), `Consultable`, `Registrable` y los `*DAO` (`persistencia`), `VentaObserver` (`servicio`) | Varias clases las implementan y el resto del código las usa como tipo (por ejemplo, `Combos.textos(List<? extends Describible>)` o los servicios con `UsuarioDAO`). |
| Manejo de errores | `excepciones`: `CineException` (base), `DatoInvalidoException`, `ClienteNoEncontradoException`, `BaseDatosException` | Las ventanas atrapan `CineException` con `try/catch` y muestran el mensaje; una entrada inválida o una falla de la base de datos nunca cierra la aplicación. |

### Persistencia (30 %)

| Concepto | Dónde está | Cómo se usa |
|---|---|---|
| Base de datos MySQL | `database/cine_db.sql` | Crea 9 tablas con llaves primarias, foráneas y restricciones (`CHECK`), y carga los datos de ejemplo. |
| Acceso a datos (DAO + JDBC) | `persistencia` (interfaces) y `persistencia.impl` (`UsuarioDAOImpl`, `PeliculaDAOImpl`, `SalaDAOImpl`, `ProductoDAOImpl`, `VentaDAOImpl`) | Cada DAO lee y guarda con `PreparedStatement`; el alta de un cliente usa una transacción (tablas `usuario` y `cliente`). |
| Configuración de la conexión | `SistemaCine/src/main/resources/bd.properties` | Define URL, usuario y clave; lo lee `ConexionBD`. |

### Interfaz gráfica (30 %)

| Concepto | Dónde está | Cómo se usa |
|---|---|---|
| Ventanas Swing | `interfaz`: `VentanaMenu`, `VentanaMenuVentas`, `VentanaVenta`, `VentanaRegistrarCliente`, `VentanaRegistrarPelicula`, `VentanaRegistrarProducto`, `VentanaMembresia`, `VentanaConsulta` | Formularios, listas desplegables y tablas; las ventanas solo piden datos y muestran resultados, la lógica está en `servicio`. |
| Punto de entrada | `SistemaCine` (paquete base) | Comprueba la base de datos, crea las piezas y abre `VentanaMenu`. |

### Principios SOLID (10 %)

| Principio | Dónde está | Cómo se usa |
|---|---|---|
| **S** - Responsabilidad única | `servicio`: `UsuarioServicio`, `CatalogoServicio`, `VentaServicio`, `FidelizacionServicio`; `persistencia.impl`: un DAO por tabla; `interfaz`: ventanas sin lógica | Cada clase tiene una sola razón para cambiar: las visitas y el ascenso a Premium viven solo en `FidelizacionServicio`; guardar ventas solo en `VentaServicio` y `VentaDAOImpl`. |
| **O** - Abierto/cerrado | `Venta` y sus hijas; `Membresia` y sus hijas; `VentaObserver` | Un nuevo tipo de venta es una nueva hija de `Venta`; una nueva membresía es una hija de `Membresia`; una nueva reacción al vender es un nuevo `VentaObserver`. No se modifica `Cliente`, `VentaServicio` ni las ventanas. |
| **L** - Sustitución de Liskov | `Membresia` (incluida `SinMembresia`), `Usuario`, `Venta` | `Cliente` y `Venta` usan cualquier `Membresia` sin `instanceof` ni comparar textos de tipo: básica, premium o sin membresía se comportan según el mismo contrato. |
| **I** - Segregación de interfaces | `Consultable`, `Registrable`, `SalaDAO`, `VentaDAO`, `Describible`, `VentaObserver` | Interfaces pequeñas: `VentaDAO` solo lee y guarda (no modifica ni borra) y `SalaDAO` no declara `guardar` porque las salas no se crean desde la app. |
| **D** - Inversión de dependencias | `servicio` (reciben `*DAO` por constructor), `SistemaCine`, `CineFacade` | Los servicios dependen de las interfaces `UsuarioDAO`, `VentaDAO`... y no de JDBC; `SistemaCine` es el único lugar que decide qué clase concreta se usa. Las ventanas solo conocen `CineFacade`. |

### Patrones de diseño (30 %)

| Tipo | Patrón | Dónde está | Cómo se usa |
|---|---|---|---|
| Creacional | **Singleton** | `persistencia.ConexionBD` | Una sola instancia con una sola conexión a MySQL que comparten todos los DAO (`ConexionBD.getInstancia()`). |
| Creacional | **Factory Method** | `membresia.CreadorMembresia` y `CreadorBasica`, `CreadorPremium`, `CreadorSinMembresia` | `crearMembresia()` devuelve la membresía correcta: al leer un cliente de la base de datos (`UsuarioDAOImpl`), al comprar la básica (`UsuarioServicio`) y al ascender a Premium (`MembresiaBasica`). |
| Creacional | **Builder** | `servicio.VentaBuilder` | `VentaServicio` arma cada venta paso a paso (`conCliente`, `deBoletas`/`deConfiteria`, `conCantidad`) y `construir()` la valida y calcula su total. |
| Estructural | **Facade** | `servicio.CineFacade` | Todas las ventanas hablan solo con la fachada, que reparte el trabajo entre los tres servicios. |
| Estructural | **Adapter** | `interfaz.TablaAdapter` (usado en `interfaz.Tablas`) | Adapta una `List<T>` de objetos al `TableModel` que exige la `JTable`, para usuarios, películas, salas, productos y ventas. |
| Comportamiento | **Strategy** | `membresia.Descontable` y `MembresiaBasica` / `MembresiaPremium` / `SinMembresia` | El descuento es una estrategia intercambiable: `Venta.descuentoSegun(...)` la usa sin saber cuál es y el resultado cambia según la membresía del cliente. |
| Comportamiento | **Observer** | `servicio.VentaObserver` (observador), `FidelizacionServicio` (observador concreto), `VentaServicio` (sujeto) | Al confirmar una venta `VentaServicio` avisa a sus observadores; `FidelizacionServicio` cuenta la visita y asciende a Premium. Se registra en `SistemaCine`. |
| Extra | Null Object | `membresia.SinMembresia` | Un cliente sin membresía tiene un objeto con descuento 0 en vez de `null`, así no hay que revisar nulos. |

## Estructura de paquetes

```
com.gitb.juandaaguilera.Sistema
├── SistemaCine              clase principal
├── excepciones              CineException, DatoInvalidoException, ClienteNoEncontradoException, BaseDatosException
├── modelo                   Usuario, Cliente, Empleado, Administrador, Sede, Pelicula, Sala, ProductoConfiteria,
│                            Venta, VentaBoleta, VentaConfiteria, Describible, Formato
├── membresia                Descontable, Membresia, MembresiaBasica, MembresiaPremium, SinMembresia, CreadorMembresia (+3 creadores)
├── persistencia             ConexionBD, Consultable, Registrable, UsuarioDAO, PeliculaDAO, SalaDAO, ProductoDAO, VentaDAO
│   └── impl                 implementaciones JDBC de cada DAO
├── servicio                 UsuarioServicio, CatalogoServicio, VentaServicio, FidelizacionServicio, VentaObserver, VentaBuilder, CineFacade
└── interfaz                 ventanas Swing, Tablas, TablaAdapter, Combos
```

## Reglas de negocio

| Membresía | Precio | Descuento en boletas | Descuento en confitería | Cómo se obtiene |
|---|---|---|---|---|
| Ninguna | - | 0 % | 0 % | Estado inicial de todo cliente nuevo |
| BÁSICA | $20.000 | 10 % | 5 % | Se compra desde el menú principal |
| PREMIUM | $0 (no se compra) | 25 % | 15 % | Se gana automáticamente con 30 visitas siendo BÁSICA |

- **Venta:** subtotal = precio × cantidad; descuento = el que calcula la membresía del cliente; total = subtotal − descuento. El
  descuento usa la membresía que el cliente tiene *antes* de la compra.
- **Visitas:** solo cuentan las ventas de **boletas**, una por día por cliente. Con membresía BÁSICA y 30 visitas el cliente pasa a PREMIUM
  en esa misma compra. Sin membresía acumula visitas pero no asciende.
- Un cliente que ya tiene membresía no puede volver a comprar la básica.

## Flujo de prueba recomendado

1. **Mostrar usuarios** para ver los clientes y sus documentos.
2. **Ventas > Taquilla:** documento `1001` (Juan), Sala 1, cantidad 2 > **Calcular** (10 % de descuento) > **Confirmar venta**: pasa de 29 a
   30 visitas y asciende a **PREMIUM**.
3. **Ventas > Confitería:** documento `1001`, Combo pareja, cantidad 1: ahora el descuento es del 15 %.
4. **Registrar cliente** (nuevo) y **Comprar membresía básica** para asignársela; vuelve a vender y revisa el 10 % y el 5 %.
5. **Registrar película** y elige una sala: la película nueva pasa a proyectarse en esa sala.
6. Cierra y vuelve a abrir el programa: **los datos siguen ahí** porque están en la base de datos. Revisa también las tablas en phpMyAdmin.
7. Prueba entradas inválidas (cantidad 0, letras en el documento, documento inexistente): el programa muestra un mensaje y sigue funcionando.
