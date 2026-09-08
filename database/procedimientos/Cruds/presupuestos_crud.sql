CREATE PROCEDURE sp_insertar_presupuesto (
    p_id_usuario INTEGER,
    p_nombre VARCHAR(100),
    p_anio_inicio SMALLINT,
    p_mes_inicio SMALLINT,
    p_anio_fin SMALLINT,
    p_mes_fin SMALLINT,
    p_total_ingresos_planificados NUMERIC(12,2),
    p_total_gastos_planificados NUMERIC(12,2),
    p_total_ahorro_planificado NUMERIC(12,2),
    p_estado VARCHAR(10),
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_presupuesto INTEGER
)
AS
BEGIN
    p_id_presupuesto = GEN_ID(gen_presupuesto_id, 1);

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
    ) VALUES (
        :p_id_presupuesto,
        :p_id_usuario,
        :p_nombre,
        :p_anio_inicio,
        :p_mes_inicio,
        :p_anio_fin,
        :p_mes_fin,
        :p_total_ingresos_planificados,
        :p_total_gastos_planificados,
        :p_total_ahorro_planificado,
        CURRENT_TIMESTAMP,
        :p_estado,
        :p_creado_por,
        CURRENT_TIMESTAMP
    );
END

CREATE PROCEDURE sp_consultar_presupuesto (
    p_id_presupuesto INTEGER
)
RETURNS (
    p_id_usuario INTEGER,
    p_nombre VARCHAR(100),
    p_anio_inicio SMALLINT,
    p_mes_inicio SMALLINT,
    p_anio_fin SMALLINT,
    p_mes_fin SMALLINT,
    p_total_ingresos_planificados NUMERIC(12,2),
    p_total_gastos_planificados NUMERIC(12,2),
    p_total_ahorro_planificado NUMERIC(12,2),
    p_fecha_hora_creacion TIMESTAMP,
    p_estado VARCHAR(10),
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    p_id_usuario = NULL;

    SELECT 
        "id_usuario", "nombre", "anio_inicio", "mes_inicio", 
        "anio_fin", "mes_fin", "total_ingresos_planificados", 
        "total_gastos_planificados", "total_ahorro_planificado", 
        "fecha_hora_creacion", "estado", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "presupuesto"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO 
        :p_id_usuario, :p_nombre, :p_anio_inicio, :p_mes_inicio, 
        :p_anio_fin, :p_mes_fin, :p_total_ingresos_planificados, 
        :p_total_gastos_planificados, :p_total_ahorro_planificado, 
        :p_fecha_hora_creacion, :p_estado, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en;

    IF (p_id_usuario IS NOT NULL) THEN
        SUSPEND;

END

CREATE PROCEDURE sp_listar_presupuestos (
    p_id_usuario INTEGER
)
RETURNS (
    p_id_presupuesto INTEGER,
    p_nombre VARCHAR(100),
    p_anio_inicio SMALLINT,
    p_mes_inicio SMALLINT,
    p_anio_fin SMALLINT,
    p_mes_fin SMALLINT,
    p_total_ingresos_planificados NUMERIC(12,2),
    p_total_gastos_planificados NUMERIC(12,2),
    p_total_ahorro_planificado NUMERIC(12,2),
    p_fecha_hora_creacion TIMESTAMP,
    p_estado VARCHAR(10),
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    FOR SELECT 
        "id_presupuesto", "nombre", "anio_inicio", "mes_inicio", 
        "anio_fin", "mes_fin", "total_ingresos_planificados", 
        "total_gastos_planificados", "total_ahorro_planificado", 
        "fecha_hora_creacion", "estado", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "presupuesto"
    WHERE "id_usuario" = :p_id_usuario
    INTO 
        :p_id_presupuesto, :p_nombre, :p_anio_inicio, :p_mes_inicio, 
        :p_anio_fin, :p_mes_fin, :p_total_ingresos_planificados, 
        :p_total_gastos_planificados, :p_total_ahorro_planificado, 
        :p_fecha_hora_creacion, :p_estado, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en
    DO
    BEGIN
        SUSPEND;
    END
END

CREATE PROCEDURE sp_actualizar_presupuesto (
    p_id_presupuesto INTEGER,
    p_nombre VARCHAR(100),
    p_anio_inicio SMALLINT,
    p_mes_inicio SMALLINT,
    p_anio_fin SMALLINT,
    p_mes_fin SMALLINT,
    p_total_ingresos_planificados NUMERIC(12,2),
    p_total_gastos_planificados NUMERIC(12,2),
    p_total_ahorro_planificado NUMERIC(12,2),
    p_estado VARCHAR(10),
    p_modificado_por VARCHAR(50)
)
AS
BEGIN
    UPDATE "presupuesto"
    SET 
        "nombre" = :p_nombre,
        "anio_inicio" = :p_anio_inicio,
        "mes_inicio" = :p_mes_inicio,
        "anio_fin" = :p_anio_fin,
        "mes_fin" = :p_mes_fin,
        "total_ingresos_planificados" = :p_total_ingresos_planificados,
        "total_gastos_planificados" = :p_total_gastos_planificados,
        "total_ahorro_planificado" = :p_total_ahorro_planificado,
        "estado" = :p_estado,
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_presupuesto" = :p_id_presupuesto;
END

CREATE PROCEDURE sp_eliminar_presupuesto (
    p_id_presupuesto INTEGER
)
AS
BEGIN

    DELETE FROM "presupuesto_detalle"
    WHERE "id_presupuesto" = :p_id_presupuesto;

    DELETE FROM "presupuesto"
    WHERE "id_presupuesto" = :p_id_presupuesto;
END