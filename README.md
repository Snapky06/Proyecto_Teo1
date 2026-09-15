Sistema de gestión financiera personal desarrollado en Java 17, JDBC e InterBase. Este proyecto permite administrar el flujo de dinero de un usuario a través del registro de categorías, presupuestos, obligaciones fijas y transacciones, generando reportes financieros apoyados por una sólida base de datos relacional y una interfaz de línea de comandos (CLI).

## Tabla de Contenidos
1. [Tecnologías Utilizadas](#1-tecnologías-utilizadas)
2. [Decisiones de Arquitectura y Patrones de Diseño](#2-decisiones-de-arquitectura-y-patrones-de-diseño)
3. [Estructura del Proyecto Java](#3-estructura-del-proyecto-java)
4. [Estructura de la Base de Datos](#4-estructura-de-la-base-de-datos)
5. [Manual de Usuario y Ejecución](#5-manual-de-usuario-y-ejecución)

---

## 1. Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Gestor de Dependencias:** Maven
- **Motor de Base de Datos:** InterBase
- **Conectividad:** JDBC (Driver heredado `interclient.jar`)
- **Procesamiento JSON:** Gson 2.11.0

---

## 2. Decisiones de Arquitectura y Patrones de Diseño

La aplicación se ha construido respetando la Separación de Responsabilidades, el principio DRY (*Don't Repeat Yourself*) y aplicando el patrón de diseño **DAO (Data Access Object)**.

### Herencia y Centralización JDBC (`BaseDAO`)
Para evitar la redundancia de código en las operaciones CRUD (abrir conexión, preparar *statements*, atrapar excepciones), se implementó una clase abstracta `BaseDAO`. 
- **Varargs (`Object...`) e `instanceof`**: El método centralizado recibe un arreglo variable de parámetros. Dado que el driver `interclient.jar` es estricto con los tipos de datos, se utiliza `instanceof` en tiempo de ejecución para mapear correctamente los tipos de Java (`Short`, `Date`, `BigDecimal`) a los tipos de SQL compatibles con InterBase, evitando excepciones de conversión.
- **Funciones Escalares**: `BaseDAO` provee submétodos tipados (`ejecutarFuncionEntera`, `ejecutarFuncionDecimal`, etc.) para manejar limpiamente las funciones SQL que devuelven un solo valor.

### Interfaces de Consola Escalables (`MenuBase`)
Todos los menús interactivos heredan de la clase abstracta `MenuBase`, la cual encapsula el ciclo de vida del menú (`while(!salir)`). Las clases hijas solo necesitan implementar el diseño visual del menú y su estructura condicional (`switch`). Las validaciones de teclado y control de excepciones (ej. `NumberFormatException`) están delegadas a `MenuHelper`.

### Lógica Centralizada en la Base de Datos
Debido a la naturaleza financiera del sistema, los cálculos matemáticos críticos (balance mensual, porcentajes, días hasta vencimiento) se delegaron a **Procedimientos Almacenados** y **Funciones** en InterBase. 
*Nota:* Para sortear las limitaciones de InterBase respecto al manejo de fechas, se creó la función utilitaria `fn_obtener_dias_mes`, centralizando la validación de años bisiestos y días por mes.

### Transaccionalidad Manual (Carga JSON)
Para la carga masiva de presupuestos, se altera el comportamiento de JDBC mediante `setAutoCommit(false)`. Esto permite iterar sobre los objetos parseados por **Gson** usando *Records* de Java 17 y, si ocurre un error, ejecutar un `ROLLBACK` manual, garantizando la Atomicidad (ACID).

---

## 3. Estructura del Proyecto Java

```text
backend/src/main/java/com/proyecto/
├── BaseDAO.java                 # Clase abstracta para centralizar operaciones JDBC
├── Main.java                    # Punto de entrada de la aplicación
├── config/
│   └── Database.java            # Configuración centralizada de credenciales y conexión
├── cruds/
│   ├── CategoriaDAO.java...     # Clases que heredan de BaseDAO para escrituras y lecturas
│   └── PresupuestoDetalleJson.java # Record (Java 17) para DTO de la carga JSON
├── funciones/
│   └── ...                      # DAOs orientados a ejecutar reglas de negocio y reportes
└── menus/
    ├── MenuBase.java            # Manejo del ciclo de vida de los menús de consola
    ├── MenuHelper.java          # Validaciones de input (Scanner) y constantes del sistema
    ├── cruds_menu/              # Menús interactivos para cada entidad
    └── reportes_menu/           # Interfaz de reportería analítica (14 funciones)
4. Estructura de la Base de Datos
El sistema se compone de 7 tablas principales, manejadas mediante Generadores auto-incrementables (GEN_ID) y auditadas automáticamente por Triggers (creado_por, creado_en, modificado_en).

usuario: Datos de la persona. Implementa "Soft Delete" (borrado lógico) a través del campo estado.

categoria: Clasificación superior (INGRESO, GASTO o AHORRO). Un trigger (trg_categoria_crea_subcat_general) crea automáticamente una subcategoría "General" al insertar un registro.

subcategoria: División granular de una categoría (ej. "Alquiler", "Internet").

presupuesto: Agrupador macro que delimita un marco temporal (año/mes inicio a año/mes fin).

presupuesto_detalle: Tabla puente que asigna un valor planificado mensual a una subcategoría.

obligacion_fija: Pagos recurrentes mensuales con un día exacto de vencimiento.

transaccion: Núcleo del sistema. Registra el movimiento real de dinero. Valida que su tipo coincida estrictamente con el tipo heredado de su subcategoría.

5. Manual de Usuario y Ejecución
Requisitos Previos
Instancia de InterBase en ejecución.

Archivo de base de datos .IB ubicado en la ruta especificada en Database.java (por defecto: C:/InterBase/data/gestion_financiera.IB).

Maven instalado y configurado en las variables de entorno.

Ejecución del Proyecto
Desde la terminal, ubicado en la carpeta backend/:

Bash
mvn compile
mvn exec:java
Reglas de Uso en Consola
Fechas: Siempre utilizar el formato exacto YYYY-MM-DD (Ejemplo: 2026-08-25). En campos opcionales, presionar Enter para dejarlos vacíos.

Tipos de Categorías: Se debe respetar la escritura exacta de las palabras clave (INGRESO, GASTO o AHORRO).

Carga JSON (Opción 8 - Presupuestos): Permite crear los detalles de un presupuesto pasando un JSON en texto plano en una sola línea. Ejemplo de formato admitido:
[{"id_subcategoria":13,"monto_mensual":30000.00},{"id_subcategoria":14,"monto_mensual":7500.00}]