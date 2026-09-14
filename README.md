```
# Sistema de Presupuesto Personal

Sistema de gestión financiera personal desarrollado en Java 17, JDBC e InterBase. Permite administrar usuarios, categorías, subcategorías, presupuestos, obligaciones fijas y transacciones, además de generar reportes financieros mediante procedimientos almacenados.

## Tecnologías utilizadas

- Java 17
- Maven
- JDBC
- InterBase
- InterClient
- Gson 2.11.0
- DBeaver para administrar y probar la base de datos

## Funcionalidades principales

El sistema permite:

- Registrar, consultar, actualizar y desactivar usuarios.
- Crear categorías de ingresos, gastos y ahorros.
- Crear subcategorías relacionadas con categorías.
- Crear y administrar presupuestos.
- Registrar detalles de presupuesto por subcategoría.
- Registrar obligaciones fijas mensuales.
- Registrar ingresos, gastos y ahorros.
- Validar transacciones de acuerdo con el presupuesto.
- Consultar balances mensuales.
- Calcular porcentajes de ejecución.
- Obtener resúmenes por categoría.
- Consultar días hasta el vencimiento de obligaciones.
- Procesar obligaciones mensuales.
- Calcular proyecciones y promedios de gastos.
- Cerrar presupuestos finalizados.
- Crear presupuestos completos utilizando una lista JSON procesada desde Java.

## Estructura del proyecto

```text
Proyect_Teo1/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/proyecto/
│       ├── Main.java
│       ├── config/
│       │   └── Database.java
│       ├── cruds/
│       │   ├── UsuarioDAO.java
│       │   ├── CategoriaDAO.java
│       │   ├── SubcategoriaDAO.java
│       │   ├── PresupuestoDAO.java
│       │   ├── PresupuestoDetalleDAO.java
│       │   ├── ObligacionFijaDAO.java
│       │   ├── TransaccionDAO.java
│       │   └── PresupuestoDetalleJson.java
│       ├── funciones/
│       │   ├── FuncionesObligacionDAO.java
│       │   ├── FuncionesPresupuestoDAO.java
│       │   ├── FuncionesSubcategoriaDAO.java
│       │   ├── FuncionesTransaccionDAO.java
│       │   └── ProcedimientosNegocioDAO.java
│       └── menus/
│           ├── Menu.java
│           ├── MenuHelper.java
│           ├── cruds_menu/
│           └── reportes_menu/
├── database/
│   ├── estructura/
│   │   ├── schema_interbase.sql
│   │   └── generators_interbase.sql
│   ├── procedimientos/
│   │   ├── cruds/
│   │   └── logica_negocio/
│   ├── funciones_bd/
│   ├── triggers/
│   ├── datos_iniciales.sql
│   └── datosJSON.json
├── docs/
│   ├── Diagrama_Proyecto.dbml
│   └── Proyecto Teo 1_*.png
├── Requerimientos/
│   └── Definicion_Proyecto_Sistema_Presupuesto_Personal_v2.pdf
└── README.md

```





## Tablas principales


La base de datos contiene las siguientes tablas:



- `usuario`

- `categoria`

- `subcategoria`

- `presupuesto`

- `presupuesto_detalle`

- `obligacion_fija`

- `transaccion`



### Usuario


Guarda los datos personales y financieros básicos de cada usuario.


El campo `estado` indica si el usuario está activo. El procedimiento de eliminación cambia el estado a inactivo en lugar de borrar el registro.


### Categoría


Cada categoría pertenece a un usuario y tiene uno de estos tipos:


Plain text






```
INGRESO
GASTO
AHORRO

```





Al crear una categoría, el trigger `trg_categoria_crea_subcat_general` genera automáticamente una subcategoría llamada `General`.


### Subcategoría


Cada subcategoría pertenece a una categoría. Puede marcarse como activa o inactiva y puede ser la subcategoría predeterminada de una categoría.


### Presupuesto


Pertenece a un usuario y tiene un período definido por:



- Año y mes de inicio.

- Año y mes de finalización.

- Estado.

- Totales planificados de ingresos, gastos y ahorros.



### Presupuesto detalle


Relaciona un presupuesto con una subcategoría y guarda el monto mensual planificado.


### Obligación fija


Representa un gasto o ahorro recurrente. Incluye:



- Monto mensual.

- Día de vencimiento.

- Fecha de inicio.

- Fecha de finalización opcional.

- Estado de vigencia.



### Transacción


Registra un movimiento financiero:



- Ingreso.

- Gasto.

- Ahorro.



Cada transacción pertenece a un usuario, presupuesto y subcategoría. Opcionalmente puede relacionarse con una obligación fija.


## Procedimientos CRUD


Los procedimientos almacenados se encuentran en:


Plain text






```
database/procedimientos/cruds/

```





### Usuarios


Plain text






```
sp_insertar_usuario
sp_listar_usuarios
sp_consultar_usuario
sp_actualizar_usuario
sp_eliminar_usuario

```





### Categorías


Plain text






```
sp_insertar_categoria
sp_listar_categorias
sp_consultar_categoria
sp_actualizar_categoria
sp_eliminar_categoria

```





### Subcategorías


Plain text






```
sp_insertar_subcategoria
sp_listar_subcategoria
sp_consultar_subcategoria
sp_actualizar_subcategoria
sp_eliminar_subcategoria
sp_obtener_tipo_subcategoria

```





### Presupuestos


Plain text






```
sp_insertar_presupuesto
sp_listar_presupuestos
sp_consultar_presupuesto
sp_actualizar_presupuesto
sp_eliminar_presupuesto

```





### Detalles de presupuesto


Plain text






```
sp_insertar_presupuesto_detalle
sp_listar_presupuesto_detalles
sp_consultar_presupuesto_detalle
sp_actualizar_presupuesto_detalle
sp_eliminar_presupuesto_detalle

```





### Obligaciones fijas


Plain text






```
sp_insertar_obligacion_fija
sp_listar_obligaciones_fijas
sp_consultar_obligacion_fija
sp_actualizar_obligacion_fija
sp_eliminar_obligacion_fija

```





### Transacciones


Plain text






```
sp_insertar_transaccion
sp_listar_transacciones
sp_consultar_transaccion
sp_actualizar_transaccion
sp_eliminar_transaccion

```





## Procedimientos de lógica de negocio


Se encuentran en:


Plain text






```
database/procedimientos/logica_negocio/

```





### Presupuestos


Plain text






```
sp_calcular_balance_mensual
sp_cerrar_presupuesto

```





### Reportes


Plain text






```
sp_calcular_monto_ejecutado_mes
sp_calcular_porcentaje_ejecucion_mes
sp_obtener_resumen_categoria_mes

```





### Obligaciones


Plain text






```
sp_procesar_obligaciones_mes

```





### Transacciones


Plain text






```
sp_registrar_transaccion_completa

```





## Funciones de negocio


Debido a las limitaciones de la versión de InterBase utilizada, las funciones se implementaron como procedimientos almacenados seleccionables que utilizan `RETURNS` y `SUSPEND`.


Se encuentran en:


Plain text






```
database/funciones_bd/

```





### Funciones de presupuesto


Plain text






```
fn_calcular_porcentaje_ejecutado
fn_obtener_total_categoria_mes
fn_obtener_total_ejecutado_categoria_mes
fn_validar_vigencia_presupuesto

```





### Funciones de subcategoría


Plain text






```
fn_obtener_categoria_por_subcategoria

```





### Funciones de transacciones


Plain text






```
fn_calcular_monto_ejecutado
fn_obtener_balance_subcategoria
fn_calcular_proyeccion_gasto_mensual
fn_obtener_promedio_gasto_subcategoria

```





### Funciones de obligaciones


Plain text






```
fn_dias_hasta_vencimiento

```





## Triggers


Los triggers se encuentran en:


Plain text






```
database/triggers/triggers_interbase.sql

```





Sus funciones principales son:



- Generar IDs automáticamente cuando no se proporciona uno.

- Establecer fechas de creación.

- Actualizar automáticamente las fechas de modificación.

- Crear una subcategoría `General` al insertar una categoría.

- Establecer fechas de creación de presupuestos y transacciones.



## Auditoría


Las tablas contienen campos de auditoría:


Plain text






```
creado_por
modificado_por
creado_en
modificado_en

```





Los campos `creado_en` y `modificado_en` se actualizan mediante los procedimientos y triggers correspondientes.


## Manejo de IDs y generadores


InterBase utiliza generadores para los IDs:


Plain text






```
gen_usuario_id
gen_categoria_id
gen_subcategoria_id
gen_presupuesto_id
gen_presupuesto_detalle_id
gen_obligacion_fija_id
gen_transaccion_id

```





En el funcionamiento normal de la aplicación, los procedimientos utilizan:


SQL






```
GEN_ID(nombre_del_generador, 1)

```





Los datos iniciales utilizan IDs explícitos para mantener relaciones fáciles de probar. Cuando se cargan registros con IDs manuales, los generadores deben sincronizarse con el máximo existente antes de crear nuevos registros desde Java.


No se deben volver a ejecutar valores antiguos como:


SQL






```
SET GENERATOR gen_presupuesto_id TO 8;

```





si posteriormente ya existe un presupuesto con un ID mayor.


## Creación de presupuestos con JSON


El JSON se procesa en Java utilizando Gson. No se guarda el JSON original en la base de datos.


El flujo es:


Plain text






```
JSON escrito en Java
        ↓
Gson convierte el JSON a objetos Java
        ↓
Java valida los datos
        ↓
Java consulta el tipo de cada subcategoría
        ↓
Java calcula los totales
        ↓
Se crea el presupuesto
        ↓
Se crean los detalles
        ↓
COMMIT o ROLLBACK

```





Ejemplo:


JSON






```
[
  {
    "id_subcategoria": 13,
    "monto_mensual": 30000.00
  },
  {
    "id_subcategoria": 14,
    "monto_mensual": 7500.00
  },
  {
    "id_subcategoria": 16,
    "monto_mensual": 6000.00
  }
]

```





Este JSON se transforma en:


Plain text






```
Un registro en presupuesto
Tres registros en presupuesto_detalle

```





Java valida:



- Que el JSON no esté vacío.

- Que las subcategorías existan.

- Que los montos sean positivos.

- Que no se repitan subcategorías.

- Que los tipos de categoría sean válidos.



## Configuración de la conexión


La conexión se configura en:


Plain text






```
backend/src/main/java/com/proyecto/config/Database.java

```





La configuración actual utiliza:


Plain text






```
Servidor: localhost
Base de datos: C:/InterBase/data/gestion_financiera.IB
Usuario: SYSDBA

```





Si la base está en otra ubicación, debe modificarse la constante `URL`.


## Ejecución del proyecto


Desde PowerShell:


PowerShell






```
cd "C:\Users\saidn\OneDrive\Escritorio\Teo1\Proyect_Teo1\backend"
mvn compile
mvn exec:java

```





También puede ejecutarse desde el IDE configurando como clase principal:


Plain text






```
com.proyecto.Main

```





## Menú principal


El programa contiene las siguientes opciones:


Plain text






```
1. Gestionar Usuarios
2. Gestionar Categorías
3. Gestionar Subcategorías
4. Gestionar Presupuestos
5. Gestionar Obligaciones Fijas
6. Gestionar Transacciones
7. Reportes
0. Salir del programa

```





## Datos iniciales


Los datos de prueba se encuentran en:


Plain text






```
database/datos_iniciales.sql

```





El JSON de ejemplo se encuentra en:


Plain text






```
database/datosJSON.json

```





La carga debe respetar el orden de dependencias:


Plain text






```
Usuarios
Categorías
Subcategorías
Presupuestos
Detalles de presupuesto
Obligaciones fijas
Transacciones

```





## Consideraciones



- InterBase debe estar iniciado antes de ejecutar el programa.

- El archivo `.IB` debe existir en la ruta configurada.

- El driver `interclient.jar` debe estar disponible en el proyecto.

- Los meses deben estar entre 1 y 12.

- Los años deben escribirse con cuatro dígitos.

- Los montos deben ser positivos.

- Las transacciones completas validan que el tipo coincida con la categoría de la subcategoría.

- Las transacciones completas validan que el período pertenezca al presupuesto.

- El presupuesto solo puede cerrarse cuando su período ya terminó.