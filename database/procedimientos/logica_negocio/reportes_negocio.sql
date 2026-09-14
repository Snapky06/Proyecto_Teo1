CREATE PROCEDURE sp_calcular_monto_ejecutado_mes (
    p_id_subcategoria INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    monto_ejecutado NUMERIC(12,2)
)
AS
BEGIN
    SELECT
        COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_subcategoria" = :p_id_subcategoria
      AND "id_presupuesto" = :p_id_presupuesto
      AND "anio" = :p_anio
      AND "mes" = :p_mes
      AND UPPER("tipo") = 'GASTO'
    INTO :monto_ejecutado;

    SUSPEND;
END

CREATE PROCEDURE sp_calcular_porcentaje_ejecucion_mes (
    p_id_subcategoria INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    porcentaje_ejecucion NUMERIC(12,2)
)
AS
DECLARE VARIABLE v_monto_ejecutado NUMERIC(12,2);
DECLARE VARIABLE v_monto_presupuestado NUMERIC(12,2);
BEGIN
    SELECT
        COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_subcategoria" = :p_id_subcategoria
      AND "id_presupuesto" = :p_id_presupuesto
      AND "anio" = :p_anio
      AND "mes" = :p_mes
      AND UPPER("tipo") = 'GASTO'
    INTO :v_monto_ejecutado;

    SELECT
        COALESCE(SUM("monto_mensual"), 0)
    FROM "presupuesto_detalle"
    WHERE "id_subcategoria" = :p_id_subcategoria
      AND "id_presupuesto" = :p_id_presupuesto
    INTO :v_monto_presupuestado;

    IF (v_monto_presupuestado > 0) THEN
        porcentaje_ejecucion =
            (v_monto_ejecutado / v_monto_presupuestado) * 100;
    ELSE
        porcentaje_ejecucion = 0;

    SUSPEND;
END

CREATE PROCEDURE sp_obtener_resumen_categoria_mes (
    p_id_categoria INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    monto_presupuestado NUMERIC(12,2),
    monto_ejecutado NUMERIC(12,2),
    porcentaje_ejecucion NUMERIC(12,2)
)
AS
BEGIN
    SELECT
        COALESCE(SUM(pd."monto_mensual"), 0)
    FROM "presupuesto_detalle" pd
    INNER JOIN "subcategoria" sc
        ON sc."id_subcategoria" = pd."id_subcategoria"
    WHERE pd."id_presupuesto" = :p_id_presupuesto
      AND sc."id_categoria" = :p_id_categoria
    INTO :monto_presupuestado;

    SELECT
        COALESCE(SUM(t."monto"), 0)
    FROM "transaccion" t
    INNER JOIN "subcategoria" sc
        ON sc."id_subcategoria" = t."id_subcategoria"
    WHERE t."id_presupuesto" = :p_id_presupuesto
      AND t."anio" = :p_anio
      AND t."mes" = :p_mes
      AND sc."id_categoria" = :p_id_categoria
      AND UPPER(t."tipo") = 'GASTO'
    INTO :monto_ejecutado;

    IF (monto_presupuestado > 0) THEN
        porcentaje_ejecucion =
            (monto_ejecutado / monto_presupuestado) * 100;
    ELSE
        porcentaje_ejecucion = 0;

    SUSPEND;
END