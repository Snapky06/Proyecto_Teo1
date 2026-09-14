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
    )
    VALUES (
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

    SUSPEND;
END

CREATE PROCEDURE sp_consultar_presupuesto_detalle (
    p_id_detalle INTEGER
)
RETURNS (
    p_id_presupuesto INTEGER,
    p_id_subcategoria INTEGER,
    p_monto_mensual NUMERIC(12,2),
    p_observaciones VARCHAR(255),
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    p_id_presupuesto = NULL;

    SELECT 
        "id_presupuesto", "id_subcategoria", "monto_mensual", 
        "observaciones", "creado_por", "modificado_por", 
        "creado_en", "modificado_en"
    FROM "presupuesto_detalle"
    WHERE "id_detalle" = :p_id_detalle
    INTO 
        :p_id_presupuesto, :p_id_subcategoria, :p_monto_mensual, 
        :p_observaciones, :p_creado_por, :p_modificado_por, 
        :p_creado_en, :p_modificado_en;

    IF (p_id_presupuesto IS NOT NULL) THEN
    SUSPEND;

END

CREATE PROCEDURE sp_listar_presupuesto_detalles (
    p_id_presupuesto INTEGER
)
RETURNS (
    p_id_detalle INTEGER,
    p_id_subcategoria INTEGER,
    p_monto_mensual NUMERIC(12,2),
    p_observaciones VARCHAR(255),
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    FOR SELECT 
        "id_detalle", "id_subcategoria", "monto_mensual", 
        "observaciones", "creado_por", "modificado_por", 
        "creado_en", "modificado_en"
    FROM "presupuesto_detalle"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO 
        :p_id_detalle, :p_id_subcategoria, :p_monto_mensual, 
        :p_observaciones, :p_creado_por, :p_modificado_por, 
        :p_creado_en, :p_modificado_en
    DO
    BEGIN
        SUSPEND;
    END
END

CREATE PROCEDURE sp_actualizar_presupuesto_detalle (
    p_id_detalle INTEGER,
    p_monto_mensual NUMERIC(12,2),
    p_observaciones VARCHAR(255),
    p_modificado_por VARCHAR(50)
)
AS
BEGIN
    UPDATE "presupuesto_detalle"
    SET 
        "monto_mensual" = :p_monto_mensual,
        "observaciones" = :p_observaciones,
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_detalle" = :p_id_detalle;
END

CREATE PROCEDURE sp_eliminar_presupuesto_detalle (
    p_id_detalle INTEGER
)
AS
BEGIN
    DELETE FROM "presupuesto_detalle"
    WHERE "id_detalle" = :p_id_detalle;
END