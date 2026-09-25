# Sistema de Gestión Financiera Personal

Sistema de gestión financiera personal desarrollado en Java 17, JDBC e InterBase. Este proyecto permite administrar el flujo de dinero de un usuario a través del registro de categorías, presupuestos, obligaciones fijas y transacciones, generando reportes financieros en formato PDF apoyados por una sólida base de datos relacional y una interfaz de línea de comandos (CLI).

---

## Tecnologías Utilizadas

*   **Lenguaje:** Java 17
*   **Gestor de Dependencias:** Maven
*   **Motor de Base de Datos:** InterBase[cite: 2]
*   **Conectividad:** JDBC (Driver heredado `interclient.jar`)[cite: 2]
*   **Procesamiento JSON:** Gson 2.11.0[cite: 2]
*   **Generación de Documentos:** iTextPDF 5.5.13.3

---

## Características Principales

1.  **Manejo de Usuarios Integrado:** Sistema de login por nombre con protección del usuario `ADMIN` por defecto. Soporte para borrado lógico (Soft Delete) para no perder el historial de transacciones[cite: 2].
2.  **Carga Masiva de Presupuestos:** Permite registrar un presupuesto completo y todos sus detalles (subcategorías y montos planificados) mediante la ingesta de un documento JSON, con manejo de transacciones manuales (Rollback/Commit)[cite: 2].
3.  **Control de Obligaciones Fijas:** Gestión de pagos recurrentes con alertas de días restantes para el vencimiento[cite: 2].
4.  **Validaciones Robustas:** Interfaz de consola protegida contra entradas inválidas mediante `MenuHelper` y una excepción centralizada (`OperacionCanceladaException`) que permite abortar cualquier flujo escribiendo `CANCELAR` o `X`.

---

## Módulo de Reportes (iTextPDF)

El sistema integra la librería iTextPDF para extraer la data financiera calculada por la base de datos y exportarla en documentos PDF profesionales. Los reportes oficiales incluyen:

*   **Reporte 1: Balance Mensual.** Compara el total de ingresos, gastos y ahorros de un mes específico, generando barras de progreso visuales en ASCII y calculando el dinero disponible.
*   **Reporte 2: Distribución de Gastos.** Analiza el peso porcentual de cada categoría de gasto sobre el total consumido en el mes.
*   **Reporte 3: Análisis de Cumplimiento.** Compara línea por línea lo que se presupuestó versus lo que realmente se ejecutó (gastó) por subcategoría, detallando los montos y porcentajes alcanzados.
*   **Reporte 4: Estado de Obligaciones Fijas.** Genera una tabla estructurada con el listado de recibos fijos, fechas límite y su estado actual (PAGADA, POR VENCER, PENDIENTE o VENCIDA).

---

## Arquitectura y Patrones de Diseño

La aplicación se ha construido respetando la Separación de Responsabilidades, el principio DRY (*Don't Repeat Yourself*) y aplicando el patrón de diseño DAO (*Data Access Object*)[cite: 2].

*   **Herencia y Centralización JDBC (`BaseDAO`):** Para evitar la redundancia de código en las operaciones CRUD, se implementó una clase abstracta `BaseDAO`[cite: 2]. El método centralizado recibe un arreglo variable y utiliza `instanceof` para mapear los tipos de Java a SQL de InterBase de forma segura[cite: 2]. Además, provee métodos escalares tipados para funciones que devuelven un solo valor[cite: 2].
*   **Interfaces de Consola Escalables (`MenuBase`):** Todos los menús interactivos heredan de `MenuBase`, encapsulando el ciclo de vida de los menús y aislando la lógica de impresión de opciones[cite: 2].
*   **Lógica Centralizada en BD:** Los cálculos matemáticos críticos (balances, porcentajes, proyecciones) se delegaron a Procedimientos Almacenados y Funciones directamente en InterBase[cite: 2]. Esto aligera la carga de procesamiento en Java y garantiza la consistencia de los datos.

---

## Estructura de la Base de Datos

El sistema se compone de 7 tablas principales, manejadas mediante Generadores auto-incrementables (`GEN_ID`) y auditadas automáticamente por Triggers (`creado_por`, `creado_en`, `modificado_en`)[cite: 2]:

1.  **`usuario`:** Datos generales de la persona[cite: 2].
2.  **`categoria`:** Clasificación superior (INGRESO, GASTO o AHORRO)[cite: 2]. Un trigger crea automáticamente una subcategoría "General" al insertar[cite: 2].
3.  **`subcategoria`:** División granular (ej. "Alquiler", "Supermercado")[cite: 2]. Soporta borrado lógico mediante el campo `activa`.
4.  **`presupuesto`:** Agrupador macro que delimita un marco temporal (año/mes de inicio a año/mes de fin)[cite: 2].
5.  **`presupuesto_detalle`:** Tabla puente que asigna un valor planificado mensual a una subcategoría[cite: 2].
6.  **`obligacion_fija`:** Pagos recurrentes mensuales con un día exacto de vencimiento[cite: 2].
7.  **`transaccion`:** Núcleo del sistema. Registra el movimiento real de dinero y valida que su tipo coincida estrictamente con su subcategoría[cite: 2].

---

## Instalación y Ejecución

### Requisitos Previos
*   Instancia de InterBase en ejecución[cite: 2].
*   Archivo de base de datos `.IB` ubicado en la ruta especificada en `Database.java` (por defecto: `C:/InterBase/data/gestion_financiera.IB`)[cite: 2].
*   Maven instalado y configurado en las variables de entorno[cite: 2].

### Ejecución del Proyecto
Desde la terminal, ubicado en la raíz del proyecto (`backend/`)[cite: 2]:
```bash
mvn compile
mvn exec:java