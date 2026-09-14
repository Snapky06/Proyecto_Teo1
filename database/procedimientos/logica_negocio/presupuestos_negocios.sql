CREATE PROCEDURE sp_calcular_balance_mensual (
    p_id_usuario INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    total_ingresos NUMERIC(12,2),
    total_gastos NUMERIC(12,2),
    total_ahorros NUMERIC(12,2),
    balance_final NUMERIC(12,2)
)
AS
BEGIN
    SELECT
        COALESCE(SUM(
            CASE
                WHEN UPPER("tipo") = 'INGRESO' THEN "monto"
                ELSE 0
            END
        ), 0),

        COALESCE(SUM(
            CASE
                WHEN UPPER("tipo") = 'GASTO' THEN "monto"
                ELSE 0
            END
        ), 0),

        COALESCE(SUM(
            CASE
                WHEN UPPER("tipo") = 'AHORRO' THEN "monto"
                ELSE 0
            END
        ), 0)

    FROM "transaccion"
    WHERE "id_usuario" = :p_id_usuario
      AND "id_presupuesto" = :p_id_presupuesto
      AND "anio" = :p_anio
      AND "mes" = :p_mes

    INTO
        :total_ingresos,
        :total_gastos,
        :total_ahorros;

    balance_final =
        :total_ingresos
        - :total_gastos
        - :total_ahorros;

    SUSPEND;
END

CREATE PROCEDURE sp_cerrar_presupuesto (
    p_id_presupuesto INTEGER,
    p_modificado_por VARCHAR(50)
)
RETURNS (
    p_id_presupuesto_resultado INTEGER,
    p_estado VARCHAR(10),
    p_total_presupuestado NUMERIC(12,2),
    p_total_ejecutado NUMERIC(12,2),
    p_porcentaje_ejecucion NUMERIC(12,2)
)
AS
DECLARE VARIABLE v_anio_fin SMALLINT;
DECLARE VARIABLE v_mes_fin SMALLINT;
DECLARE VARIABLE v_anio_actual INTEGER;
DECLARE VARIABLE v_mes_actual INTEGER;
BEGIN
    p_id_presupuesto_resultado = NULL;
    p_estado = NULL;
    p_total_presupuestado = 0;
    p_total_ejecutado = 0;
    p_porcentaje_ejecucion = 0;

    SELECT
        "anio_fin",
        "mes_fin",
        "estado"
    FROM "presupuesto"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO
        :v_anio_fin,
        :v_mes_fin,
        :p_estado;

    IF (p_estado IS NULL) THEN
        EXIT;

    v_anio_actual = EXTRACT(YEAR FROM CURRENT_DATE);
    v_mes_actual = EXTRACT(MONTH FROM CURRENT_DATE);

    IF (
        (v_anio_fin * 12 + v_mes_fin)
        >=
        (v_anio_actual * 12 + v_mes_actual)
    ) THEN
        EXCEPTION ex_presupuesto_no_finalizado;

    SELECT
        COALESCE(SUM("monto_mensual"), 0)
    FROM "presupuesto_detalle"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO :p_total_presupuestado;

    SELECT
        COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_presupuesto" = :p_id_presupuesto
      AND UPPER("tipo") = 'GASTO'
    INTO :p_total_ejecutado;

    IF (p_total_presupuestado > 0) THEN
        p_porcentaje_ejecucion =
            (p_total_ejecutado / p_total_presupuestado) * 100;
    ELSE
        p_porcentaje_ejecucion = 0;

    UPDATE "presupuesto"
    SET
        "estado" = 'CERRADO',
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_presupuesto" = :p_id_presupuesto;

    p_id_presupuesto_resultado = p_id_presupuesto;
    p_estado = 'CERRADO';

    SUSPEND;
END