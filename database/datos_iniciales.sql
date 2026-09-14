INSERT INTO "usuario" (
    "id_usuario",
    "nombres",
    "apellidos",
    "correo_electronico",
    "fecha_registro",
    "salario_mensual_base",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES (
    1,
    'Carlos',
    'Martinez',
    'carlos.martinez@correo.com',
    '2026-01-05',
    28000.00,
    TRUE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "usuario" (
    "id_usuario",
    "nombres",
    "apellidos",
    "correo_electronico",
    "fecha_registro",
    "salario_mensual_base",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES (
    2,
    'Ana',
    'Lopez',
    'ana.lopez@correo.com',
    '2026-01-07',
    22000.00,
    TRUE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "usuario" (
    "id_usuario",
    "nombres",
    "apellidos",
    "correo_electronico",
    "fecha_registro",
    "salario_mensual_base",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES (
    3,
    'Miguel',
    'Hernandez',
    'miguel.hernandez@correo.com',
    '2026-01-10',
    35000.00,
    TRUE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "usuario" (
    "id_usuario",
    "nombres",
    "apellidos",
    "correo_electronico",
    "fecha_registro",
    "salario_mensual_base",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES (
    4,
    'Laura',
    'Castillo',
    'laura.castillo@correo.com',
    '2026-01-12',
    18500.00,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    1,
    1,
    'Salario',
    'Ingresos principales del usuario',
    'INGRESO',
    'salario',
    '#2E7D32',
    1,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    2,
    1,
    'Vivienda',
    'Gastos relacionados con la vivienda',
    'GASTO',
    'casa',
    '#C62828',
    2,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    3,
    1,
    'Ahorro',
    'Fondos destinados al ahorro',
    'AHORRO',
    'ahorro',
    '#1565C0',
    3,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    4,
    2,
    'Ingresos extra',
    'Ingresos adicionales',
    'INGRESO',
    'dinero',
    '#388E3C',
    1,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    5,
    2,
    'Alimentacion',
    'Gastos de comida y supermercado',
    'GASTO',
    'comida',
    '#EF6C00',
    2,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    6,
    2,
    'Fondo de emergencia',
    'Ahorro para emergencias',
    'AHORRO',
    'fondo',
    '#1976D2',
    3,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    7,
    3,
    'Honorarios',
    'Ingresos por trabajos independientes',
    'INGRESO',
    'trabajo',
    '#2E7D32',
    1,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    8,
    3,
    'Transporte',
    'Gastos de transporte y combustible',
    'GASTO',
    'auto',
    '#AD1457',
    2,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    9,
    3,
    'Inversiones',
    'Dinero destinado a inversiones',
    'AHORRO',
    'inversion',
    '#283593',
    3,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    10,
    4,
    'Pension',
    'Ingresos por pension',
    'INGRESO',
    'pension',
    '#558B2F',
    1,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    11,
    4,
    'Salud',
    'Gastos medicos y medicamentos',
    'GASTO',
    'salud',
    '#6A1B9A',
    2,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "categoria" (
    "id_categoria",
    "id_usuario",
    "nombre",
    "descripcion",
    "tipo",
    "icono",
    "color_hex",
    "orden_presentacion",
    "creado_por",
    "creado_en"
)
VALUES (
    12,
    4,
    'Ahorro personal',
    'Ahorro personal mensual',
    'AHORRO',
    'ahorro',
    '#0277BD',
    3,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    13,
    1,
    'Salario mensual',
    'Salario base mensual',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    14,
    2,
    'Alquiler',
    'Pago mensual de alquiler',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    15,
    2,
    'Servicios basicos',
    'Agua, energia e internet',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    16,
    3,
    'Fondo de emergencia',
    'Ahorro para emergencias',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    17,
    4,
    'Trabajo independiente',
    'Ingresos por trabajos ocasionales',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    18,
    5,
    'Supermercado',
    'Compras de supermercado',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    19,
    5,
    'Restaurantes',
    'Comidas fuera de casa',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    20,
    6,
    'Ahorro emergencia',
    'Aporte al fondo de emergencia',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    21,
    7,
    'Servicios profesionales',
    'Ingresos por servicios profesionales',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    22,
    8,
    'Combustible',
    'Combustible del vehiculo',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    23,
    8,
    'Transporte publico',
    'Bus y taxi',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    24,
    9,
    'Inversion mensual',
    'Aporte mensual a inversiones',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    25,
    10,
    'Pension mensual',
    'Ingreso mensual por pension',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    26,
    11,
    'Medicamentos',
    'Compra de medicamentos',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    27,
    11,
    'Consultas medicas',
    'Consultas y examenes medicos',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "subcategoria" (
    "id_subcategoria",
    "id_categoria",
    "nombre",
    "descripcion",
    "activa",
    "es_default",
    "creado_por",
    "creado_en"
)
VALUES
(
    28,
    12,
    'Ahorro personal',
    'Aporte de ahorro personal',
    TRUE,
    FALSE,
    'SEED',
    CURRENT_TIMESTAMP
);

SET GENERATOR gen_usuario_id TO 4;
SET GENERATOR gen_categoria_id TO 12;
SET GENERATOR gen_subcategoria_id TO 28;

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    1,
    1,
    'Presupuesto anual 2025',
    2025,
    1,
    2025,
    12,
    28000.00,
    17500.00,
    5000.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    2,
    1,
    'Presupuesto anual 2026',
    2026,
    1,
    2026,
    12,
    30000.00,
    19000.00,
    6000.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    3,
    2,
    'Presupuesto de Ana 2026',
    2026,
    1,
    2026,
    12,
    24000.00,
    14500.00,
    4500.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    4,
    2,
    'Presupuesto trimestral Ana',
    2026,
    7,
    2026,
    9,
    24000.00,
    13000.00,
    4000.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    5,
    3,
    'Presupuesto profesional 2026',
    2026,
    1,
    2026,
    12,
    40000.00,
    22000.00,
    9000.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    6,
    3,
    'Presupuesto segundo semestre',
    2026,
    7,
    2026,
    12,
    42000.00,
    24000.00,
    10000.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    7,
    4,
    'Presupuesto salud 2026',
    2026,
    1,
    2026,
    12,
    18500.00,
    10500.00,
    3000.00,
    CURRENT_TIMESTAMP,
    'ACTIVO',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto" (
    "id_presupuesto",
    "id_usuario",
    "nombre",
    "anio_inicio",
    "mes_inicio",
    "anio_fin",
    "mes_fin",
    "total_ingresos_planificados",
    "total_gastos_planificados",
    "total_ahorro_planificado",
    "fecha_hora_creacion",
    "estado",
    "creado_por",
    "creado_en"
)
VALUES
(
    8,
    4,
    'Presupuesto corto de prueba',
    2026,
    7,
    2026,
    9,
    18500.00,
    9000.00,
    2500.00,
    CURRENT_TIMESTAMP,
    'BORRADOR',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (1, 1, 13, 28000.00, 'Ingreso mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (2, 1, 14, 7000.00, 'Alquiler mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (3, 1, 15, 2500.00, 'Servicios basicos', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (4, 1, 16, 5000.00, 'Ahorro mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (5, 2, 13, 30000.00, 'Ingreso mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (6, 2, 14, 7500.00, 'Alquiler mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (7, 2, 15, 3000.00, 'Servicios basicos', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (8, 2, 16, 6000.00, 'Fondo de emergencia', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (9, 3, 17, 24000.00, 'Ingresos extra', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (10, 3, 18, 5000.00, 'Supermercado', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (11, 3, 19, 2500.00, 'Restaurantes', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (12, 3, 20, 4500.00, 'Ahorro emergencia', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (13, 4, 17, 24000.00, 'Ingresos trimestrales', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (14, 4, 18, 4500.00, 'Supermercado', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (15, 4, 19, 2000.00, 'Restaurantes', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (16, 4, 20, 4000.00, 'Ahorro emergencia', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (17, 5, 21, 40000.00, 'Honorarios profesionales', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (18, 5, 22, 4000.00, 'Combustible', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (19, 5, 23, 1500.00, 'Transporte publico', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (20, 5, 24, 9000.00, 'Inversion mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (21, 6, 21, 42000.00, 'Honorarios segundo semestre', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (22, 6, 22, 4500.00, 'Combustible', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (23, 6, 23, 1800.00, 'Transporte publico', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (24, 6, 24, 10000.00, 'Inversion mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (25, 7, 25, 18500.00, 'Pension mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (26, 7, 26, 1800.00, 'Medicamentos', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (27, 7, 27, 1200.00, 'Consultas medicas', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (28, 7, 28, 3000.00, 'Ahorro personal', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (29, 8, 25, 18500.00, 'Pension mensual', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (30, 8, 26, 1500.00, 'Medicamentos', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (31, 8, 27, 1000.00, 'Consultas medicas', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "presupuesto_detalle"
("id_detalle", "id_presupuesto", "id_subcategoria", "monto_mensual", "observaciones", "creado_por", "creado_en")
VALUES (32, 8, 28, 2500.00, 'Ahorro personal', 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    1,
    1,
    14,
    'Alquiler de vivienda',
    'Pago mensual de alquiler',
    7000.00,
    5,
    TRUE,
    '2025-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    2,
    1,
    15,
    'Internet y servicios',
    'Pago de servicios del hogar',
    2500.00,
    10,
    TRUE,
    '2025-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    3,
    1,
    16,
    'Aporte de emergencia',
    'Ahorro mensual programado',
    5000.00,
    15,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    4,
    2,
    18,
    'Compra de supermercado',
    'Presupuesto recurrente de supermercado',
    5000.00,
    8,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    5,
    2,
    20,
    'Ahorro de emergencia',
    'Aporte mensual al fondo',
    4500.00,
    20,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    6,
    2,
    19,
    'Restaurantes',
    'Gasto recurrente de comidas',
    2500.00,
    25,
    FALSE,
    '2026-01-01',
    '2026-06-30',
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    7,
    3,
    22,
    'Combustible mensual',
    'Combustible del vehiculo',
    4000.00,
    12,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    8,
    3,
    24,
    'Aporte de inversion',
    'Aporte mensual para inversion',
    9000.00,
    18,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    9,
    3,
    23,
    'Transporte publico',
    'Transporte recurrente',
    1800.00,
    28,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    10,
    4,
    26,
    'Medicamentos mensuales',
    'Compra mensual de medicamentos',
    1800.00,
    3,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    11,
    4,
    27,
    'Consulta medica',
    'Control medico mensual',
    1200.00,
    16,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "obligacion_fija"
(
    "id_obligacion",
    "id_usuario",
    "id_subcategoria",
    "nombre",
    "descripcion",
    "monto_fijo_mensual",
    "dia_vencimiento",
    "vigente",
    "fecha_inicio",
    "fecha_fin",
    "creado_por",
    "creado_en"
)
VALUES
(
    12,
    4,
    28,
    'Ahorro personal mensual',
    'Aporte mensual de ahorro',
    2500.00,
    22,
    TRUE,
    '2026-01-01',
    NULL,
    'SEED',
    CURRENT_TIMESTAMP
);

INSERT INTO "transaccion"
(
    "id_transaccion",
    "id_usuario",
    "id_presupuesto",
    "anio",
    "mes",
    "id_subcategoria",
    "id_obligacion",
    "tipo",
    "descripcion",
    "monto",
    "fecha",
    "metodo_pago",
    "numero_factura",
    "observaciones",
    "fecha_hora_registro",
    "creado_por",
    "creado_en"
)
VALUES
(1, 1, 2, 2026, 1, 13, NULL, 'INGRESO', 'Salario enero', 30000.00, '2026-01-30', 'TRANSFERENCIA', 'ING-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP);

INSERT INTO "transaccion"
VALUES
(2, 1, 2, 2026, 1, 14, 1, 'GASTO', 'Alquiler enero', 7000.00, '2026-01-05', 'TRANSFERENCIA', 'ALQ-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(3, 1, 2, 2026, 1, 15, 2, 'GASTO', 'Servicios enero', 2300.00, '2026-01-10', 'TRANSFERENCIA', 'SER-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(4, 1, 2, 2026, 1, 16, 3, 'AHORRO', 'Ahorro enero', 5000.00, '2026-01-15', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(5, 1, 2, 2026, 2, 13, NULL, 'INGRESO', 'Salario febrero', 30000.00, '2026-02-27', 'TRANSFERENCIA', 'ING-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(6, 1, 2, 2026, 2, 14, 1, 'GASTO', 'Alquiler febrero', 7000.00, '2026-02-05', 'TRANSFERENCIA', 'ALQ-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(7, 1, 2, 2026, 2, 15, 2, 'GASTO', 'Servicios febrero', 2700.00, '2026-02-10', 'TRANSFERENCIA', 'SER-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(8, 1, 2, 2026, 2, 16, 3, 'AHORRO', 'Ahorro febrero', 5500.00, '2026-02-15', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(9, 1, 2, 2026, 3, 13, NULL, 'INGRESO', 'Salario marzo', 30000.00, '2026-03-30', 'TRANSFERENCIA', 'ING-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(10, 1, 2, 2026, 3, 14, 1, 'GASTO', 'Alquiler marzo', 7000.00, '2026-03-05', 'TRANSFERENCIA', 'ALQ-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(11, 1, 2, 2026, 3, 15, 2, 'GASTO', 'Servicios marzo', 2900.00, '2026-03-10', 'TRANSFERENCIA', 'SER-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(12, 1, 2, 2026, 3, 16, 3, 'AHORRO', 'Ahorro marzo', 6000.00, '2026-03-15', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(13, 2, 3, 2026, 1, 17, NULL, 'INGRESO', 'Trabajo enero', 24000.00, '2026-01-28', 'TRANSFERENCIA', 'TR-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(14, 2, 3, 2026, 1, 18, 4, 'GASTO', 'Supermercado enero', 4700.00, '2026-01-08', 'TARJETA_DEBITO', 'SUP-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(15, 2, 3, 2026, 1, 19, NULL, 'GASTO', 'Restaurante enero', 2200.00, '2026-01-18', 'TARJETA_CREDITO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(16, 2, 3, 2026, 1, 20, 5, 'AHORRO', 'Fondo enero', 4500.00, '2026-01-20', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(17, 2, 3, 2026, 2, 17, NULL, 'INGRESO', 'Trabajo febrero', 24000.00, '2026-02-28', 'TRANSFERENCIA', 'TR-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(18, 2, 3, 2026, 2, 18, 4, 'GASTO', 'Supermercado febrero', 5300.00, '2026-02-08', 'TARJETA_DEBITO', 'SUP-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(19, 2, 3, 2026, 2, 19, NULL, 'GASTO', 'Restaurante febrero', 2800.00, '2026-02-19', 'TARJETA_CREDITO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(20, 2, 3, 2026, 2, 20, 5, 'AHORRO', 'Fondo febrero', 4500.00, '2026-02-20', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(21, 2, 3, 2026, 3, 17, NULL, 'INGRESO', 'Trabajo marzo', 24000.00, '2026-03-28', 'TRANSFERENCIA', 'TR-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(22, 2, 3, 2026, 3, 18, 4, 'GASTO', 'Supermercado marzo', 4900.00, '2026-03-08', 'TARJETA_DEBITO', 'SUP-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(23, 2, 3, 2026, 3, 19, NULL, 'GASTO', 'Restaurante marzo', 3100.00, '2026-03-22', 'TARJETA_CREDITO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(24, 2, 3, 2026, 3, 20, 5, 'AHORRO', 'Fondo marzo', 4500.00, '2026-03-20', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(25, 3, 5, 2026, 1, 21, NULL, 'INGRESO', 'Honorarios enero', 40000.00, '2026-01-30', 'TRANSFERENCIA', 'HON-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(26, 3, 5, 2026, 1, 22, 7, 'GASTO', 'Combustible enero', 3800.00, '2026-01-12', 'TARJETA_DEBITO', 'COM-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(27, 3, 5, 2026, 1, 23, 9, 'GASTO', 'Transporte enero', 1400.00, '2026-01-28', 'EFECTIVO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(28, 3, 5, 2026, 1, 24, 8, 'AHORRO', 'Inversion enero', 9000.00, '2026-01-18', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(29, 3, 5, 2026, 2, 21, NULL, 'INGRESO', 'Honorarios febrero', 40000.00, '2026-02-28', 'TRANSFERENCIA', 'HON-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(30, 3, 5, 2026, 2, 22, 7, 'GASTO', 'Combustible febrero', 4200.00, '2026-02-12', 'TARJETA_DEBITO', 'COM-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(31, 3, 5, 2026, 2, 23, 9, 'GASTO', 'Transporte febrero', 1700.00, '2026-02-28', 'EFECTIVO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(32, 3, 5, 2026, 2, 24, 8, 'AHORRO', 'Inversion febrero', 9500.00, '2026-02-18', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(33, 3, 5, 2026, 3, 21, NULL, 'INGRESO', 'Honorarios marzo', 40000.00, '2026-03-30', 'TRANSFERENCIA', 'HON-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(34, 3, 5, 2026, 3, 22, 7, 'GASTO', 'Combustible marzo', 4500.00, '2026-03-12', 'TARJETA_DEBITO', 'COM-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(35, 3, 5, 2026, 3, 23, 9, 'GASTO', 'Transporte marzo', 1900.00, '2026-03-28', 'EFECTIVO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(36, 3, 5, 2026, 3, 24, 8, 'AHORRO', 'Inversion marzo', 10000.00, '2026-03-18', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(37, 4, 7, 2026, 1, 25, NULL, 'INGRESO', 'Pension enero', 18500.00, '2026-01-30', 'TRANSFERENCIA', 'PEN-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(38, 4, 7, 2026, 1, 26, 10, 'GASTO', 'Medicamentos enero', 1750.00, '2026-01-03', 'EFECTIVO', 'MED-001', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(39, 4, 7, 2026, 1, 27, 11, 'GASTO', 'Consulta enero', 1200.00, '2026-01-16', 'EFECTIVO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(40, 4, 7, 2026, 1, 28, 12, 'AHORRO', 'Ahorro enero', 2500.00, '2026-01-22', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(41, 4, 7, 2026, 2, 25, NULL, 'INGRESO', 'Pension febrero', 18500.00, '2026-02-27', 'TRANSFERENCIA', 'PEN-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(42, 4, 7, 2026, 2, 26, 10, 'GASTO', 'Medicamentos febrero', 1900.00, '2026-02-03', 'EFECTIVO', 'MED-002', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(43, 4, 7, 2026, 2, 27, 11, 'GASTO', 'Consulta febrero', 1150.00, '2026-02-16', 'EFECTIVO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(44, 4, 7, 2026, 2, 28, 12, 'AHORRO', 'Ahorro febrero', 2500.00, '2026-02-22', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(45, 4, 7, 2026, 3, 25, NULL, 'INGRESO', 'Pension marzo', 18500.00, '2026-03-27', 'TRANSFERENCIA', 'PEN-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(46, 4, 7, 2026, 3, 26, 10, 'GASTO', 'Medicamentos marzo', 2100.00, '2026-03-03', 'EFECTIVO', 'MED-003', NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(47, 4, 7, 2026, 3, 27, 11, 'GASTO', 'Consulta marzo', 1300.00, '2026-03-16', 'EFECTIVO', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);

INSERT INTO "transaccion"
VALUES
(48, 4, 7, 2026, 3, 28, 12, 'AHORRO', 'Ahorro marzo', 2500.00, '2026-03-22', 'TRANSFERENCIA', NULL, NULL, CURRENT_TIMESTAMP, 'SEED', CURRENT_TIMESTAMP, NULL, NULL);