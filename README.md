
# Sistema de Presupuesto Personal

Sistema de gestión financiera personal desarrollado en Java 17, JDBC e InterBase. Este proyecto permite administrar el flujo de dinero de un usuario a través del registro de categorías, presupuestos, obligaciones fijas y transacciones, generando reportes financieros apoyados por una sólida base de datos relacional y una interfaz de línea de comandos (CLI).

---

## Tabla de Contenidos
1. [Tecnologías Utilizadas](#1-tecnologías-utilizadas)
2. [Decisiones de Arquitectura y Patrones de Diseño](#2-decisiones-de-arquitectura-y-patrones-de-diseño)
3. [Estructura del Proyecto Java](#3-estructura-del-proyecto-java)
4. [Diccionario de Datos (InterBase)](#4-diccionario-de-datos-interbase)
5. [Manual de Usuario](#5-manual-de-usuario)
6. [Guía de Pruebas y Validaciones](#6-guía-de-pruebas-y-validaciones)
7. [Configuración y Despliegue](#7-configuración-y-despliegue)

---

## 1. Tecnologías Utilizadas

- **Lenguaje:** Java 17
- **Gestor de Dependencias:** Maven
- **Motor de Base de Datos:** InterBase
- **Conectividad:** JDBC (Driver `interclient.jar`)
- **Procesamiento JSON:** Gson 2.11.0
- **Herramientas de Desarrollo:** Visual Studio Code / NetBeans, DBeaver (para la gestión de la base de datos).

---

## 2. Decisiones de Arquitectura y Patrones de Diseño

La aplicación se ha construido respetando la Separación de Responsabilidades y aplicando el patrón de diseño **DAO (Data Access Object)**.

### Lógica Centralizada en la Base de Datos
Debido a la naturaleza financiera del sistema, los cálculos matemáticos críticos (como el balance mensual, porcentajes de ejecución y proyecciones de gastos) se delegaron a **Procedimientos Almacenados** y **Funciones** en InterBase. Java actúa como el presentador de los datos y validador de interfaz, pero la carga pesada y matemática reside de forma segura en la capa de datos.

### Interacción JDBC y Transaccionalidad (JSON)
Se optó por utilizar JDBC puro (vía `interclient.jar`) en lugar de un ORM (como Hibernate) para tener un control milimétrico sobre la invocación de procedimientos.
Para la carga masiva de presupuestos mediante JSON, se altera el comportamiento por defecto de JDBC (`setAutoCommit(false)`). Esto permite iterar sobre la lista de objetos extraída por **Gson** y, si ocurre alguna validación fallida en la base de datos (por ejemplo, una subcategoría inexistente), ejecutar un `ROLLBACK` manual, garantizando la propiedad de Atomicidad (ACID).

### Borrado Lógico (Soft Delete) y Auditoría
En un sistema financiero, eliminar registros físicamente corrompe el historial de las transacciones. Por ello, tablas como `usuario` o `presupuesto` utilizan campos como `estado` o `activa` para ocultar los registros en lugar de usar sentencias `DELETE` destructivas. Además, cada tabla incorpora campos de auditoría (`creado_por`, `creado_en`, `modificado_por`, `modificado_en`) gestionados de forma transparente por **Triggers** en la base de datos.

---

## 3. Estructura del Proyecto Java

El proyecto sigue una separación clara para mantener el código organizado y escalable:

```text
backend/src/main/java/com/proyecto/
│
├── config/             # Configuración de conexión a la BD (Database.java)
├── cruds/              # Clases DAO para inserción, actualización y eliminación (UsuarioDAO, TransaccionDAO, etc.)
├── funciones/          # Clases DAO orientadas a ejecutar reglas de negocio, cálculos y reportes
├── menus/              # Lógica de Interfaz de Línea de Comandos (CLI)
│   ├── cruds_menu/     # Menús interactivos para la gestión de entidades (PresupuestoMenu.java, etc.)
│   ├── reportes_menu/  # Menús para la visualización de proyecciones y balances
│   └── MenuHelper.java # Utilidades de "Sanitización" para lectura, validación y formateo de inputs
└── Main.java           # Punto de entrada de la aplicación
El rol crítico de MenuHelper.java
Dado que las interfaces de consola son propensas a errores de tipeo humanos, MenuHelper actúa como un escudo protector. Utiliza bucles while(true) combinados con bloques try/catch para cada tipo de dato (por ejemplo, atrapando NumberFormatException para números y IllegalArgumentException para fechas). Esto evita que el programa colapse y simplemente pide al usuario que ingrese el dato de nuevo.

4. Diccionario de Datos (InterBase)
El sistema se compone de 7 tablas principales. Todas cuentan con llaves primarias alimentadas por Generadores auto-incrementables (GEN_ID).

1. usuario
Almacena la información del usuario del sistema.

id_usuario (INTEGER, PK): Identificador único.

nombres, apellidos, correo_electronico (VARCHAR): Datos de contacto. (Correo es UNIQUE).

salario_mensual_base (NUMERIC 12,2): Salario para cálculos iniciales.

estado (BOOLEAN): Implementa "Soft Delete". TRUE si está activo, FALSE si está inactivo.

2. categoria
Clasificación superior de los movimientos de dinero.

tipo (VARCHAR): Límite estricto a INGRESO, GASTO o AHORRO.

Trigger Especial: Al registrar una categoría, el trigger trg_categoria_crea_subcat_general crea automáticamente una subcategoría "General" asociada a ella.

3. subcategoria
División granular asociada a una categoría (ej. "Alquiler", "Internet", "Honorarios").

id_categoria (INTEGER, FK): Vinculación a la tabla categoría.

activa (BOOLEAN): Estado de uso de la subcategoría.

4. presupuesto
Agrupador macro que delimita un marco temporal de planificación.

anio_inicio / mes_inicio (SMALLINT): Punto de partida del presupuesto.

anio_fin / mes_fin (SMALLINT): Límite de vigencia.

estado (VARCHAR): ACTIVO, CERRADO, BORRADOR.

total_ingresos_planificados, total_gastos_planificados, total_ahorro_planificado (NUMERIC 12,2).

5. presupuesto_detalle
Tabla puente que asigna un valor monetario planificado a una subcategoría para un presupuesto específico.

monto_mensual (NUMERIC 12,2): Cuánto se planea gastar/ingresar en esa subcategoría al mes.

6. obligacion_fija
Entidad que representa un pago o ahorro que debe realizarse de forma recurrente (mensual).

monto_fijo_mensual (NUMERIC 12,2): Cantidad a pagar.

dia_vencimiento (SMALLINT): Día del mes (1-31).

vigente (BOOLEAN): TRUE si la obligación sigue activa y debe cobrarse.

7. transaccion
El núcleo del sistema transaccional. Registra el movimiento real de dinero ejecutado.

monto (NUMERIC 12,2): Valor real de la transacción.

fecha (DATE): Fecha exacta de ejecución.

tipo (VARCHAR): INGRESO, GASTO, AHORRO. Validado estrictamente contra la categoría padre de la subcategoría.

metodo_pago (VARCHAR): EFECTIVO, TARJETA_DEBITO, etc.

5. Manual de Usuario
Este manual te guiará en el uso básico del sistema por consola.

Iniciar el Sistema
Al ejecutar com.proyecto.Main, verás el menú principal interactivo:

Plaintext
=== SISTEMA DE PRESUPUESTO PERSONAL ===
1. Gestionar Usuarios
2. Gestionar Categorias
3. Gestionar Subcategorias
4. Gestionar Presupuestos
5. Gestionar Obligaciones Fijas
6. Gestionar Transacciones
7. Reportes
0. Salir del programa
Reglas de Ingreso de Datos
Navegación: Para elegir una opción, escribe el número y presiona Enter. Usa 0 para volver atrás.

Fechas: Siempre utiliza el formato exacto YYYY-MM-DD (Ejemplo: 2026-08-25). En campos opcionales (como fecha de fin), puedes presionar Enter sin escribir nada.

Decisiones (Sí/No): Escribe SI o NO cuando el sistema lo pregunte.

Opciones Estrictas: Si el menú solicita "Tipo INGRESO, GASTO o AHORRO", debes escribir la palabra exacta.

Carga Masiva de Presupuestos mediante JSON (Opción 8)
La opción 8 dentro del menú de Gestionar Presupuestos permite crear la cabecera de un presupuesto y todos sus detalles de subcategorías en un solo paso usando texto en formato JSON.

Cuando el sistema lo pida, debes pegar el texto en una sola línea (sin saltos de línea ni espacios entre las llaves del array principal).

Formato admitido:

JSON
[{"id_subcategoria":13,"monto_mensual":30000.00},{"id_subcategoria":14,"monto_mensual":7500.00}]
Consultas de Reportes
En el menú 7. Reportes, las consultas no alteran la base de datos:

Balance Mensual: Cruza tus ingresos, gastos y ahorros para darte el saldo real de ese periodo específico.

Proyección de Gasto Mensual: Evalúa tu ritmo de gasto a la fecha actual y lo proyecta estadísticamente al final del mes.

Días hasta Vencimiento: Compara el dia_vencimiento de una obligación fija con la fecha del sistema para indicar cuántos días faltan para el pago.