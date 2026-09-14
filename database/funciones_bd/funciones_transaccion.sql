CREATE PROCEDURE fn_calcular_monto_ejecutado (
    p_id_subcategoria INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    p_monto_ejecutado NUMERIC(12,2)
)
AS
BEGIN
    SELECT COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_subcategoria" = :p_id_subcategoria
      AND "anio" = :p_anio
      AND "mes" = :p_mes
    INTO :p_monto_ejecutado;

    SUSPEND;
END



CREATE PROCEDURE fn_obtener_balance_subcategoria (
    p_id_presupuesto INTEGER,
    p_id_subcategoria INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    p_balance NUMERIC(12,2)
)
AS
DECLARE VARIABLE v_monto_ejecutado NUMERIC(12,2);
DECLARE VARIABLE v_monto_presupuestado NUMERIC(12,2);
BEGIN
    SELECT COALESCE(SUM("monto_mensual"), 0)
    FROM "presupuesto_detalle"
    WHERE "id_presupuesto" = :p_id_presupuesto
      AND "id_subcategoria" = :p_id_subcategoria
    INTO :v_monto_presupuestado;

    SELECT COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_presupuesto" = :p_id_presupuesto
      AND "id_subcategoria" = :p_id_subcategoria
      AND "anio" = :p_anio
      AND "mes" = :p_mes
    INTO :v_monto_ejecutado;

    p_balance =
        v_monto_presupuestado - v_monto_ejecutado;

    SUSPEND;
END

CREATE PROCEDURE fn_calcular_proyeccion_gasto_mensual (
    p_id_subcategoria INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
) RETURNS (
    p_proyeccion NUMERIC(12,2)
) AS
DECLARE VARIABLE v_monto_ejecutado NUMERIC(12,2);
DECLARE VARIABLE v_periodo_consultado INTEGER;
DECLARE VARIABLE v_periodo_actual INTEGER;
DECLARE VARIABLE v_dia_actual INTEGER;
DECLARE VARIABLE v_dias_mes INTEGER;
BEGIN
    p_proyeccion = 0;

    SELECT COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_subcategoria" = :p_id_subcategoria
      AND "anio" = :p_anio
      AND "mes" = :p_mes
      AND UPPER("tipo") = 'GASTO'
    INTO :v_monto_ejecutado;

    v_periodo_consultado = (:p_anio * 12) + :p_mes;
    v_periodo_actual = (EXTRACT(YEAR FROM CURRENT_DATE) * 12) + EXTRACT(MONTH FROM CURRENT_DATE);

    IF (v_periodo_consultado < v_periodo_actual) THEN
    BEGIN
        p_proyeccion = v_monto_ejecutado;
    END
    ELSE
    BEGIN
        IF (v_periodo_consultado = v_periodo_actual) THEN
        BEGIN
            v_dia_actual = EXTRACT(DAY FROM CURRENT_DATE);

            SELECT p_dias 
            FROM fn_obtener_dias_mes(:p_anio, :p_mes) 
            INTO :v_dias_mes;

            IF (v_dia_actual > 0) THEN
                p_proyeccion = (v_monto_ejecutado / v_dia_actual) * v_dias_mes;
        END
        ELSE
        BEGIN
            p_proyeccion = 0;
        END
    END
    SUSPEND;
END

CREATE PROCEDURE fn_obtener_promedio_gasto_subcategoria (
    p_id_usuario INTEGER,
    p_id_subcategoria INTEGER,
    p_cantidad_meses INTEGER
)
RETURNS (
    p_promedio NUMERIC(12,2)
)
AS
DECLARE VARIABLE v_periodo_actual INTEGER;
DECLARE VARIABLE v_periodo_inicio INTEGER;
DECLARE VARIABLE v_total_gastos NUMERIC(12,2);
BEGIN
    p_promedio = 0;

    IF (p_cantidad_meses > 0) THEN
    BEGIN
        v_periodo_actual =
            (EXTRACT(YEAR FROM CURRENT_DATE) * 12)
            + EXTRACT(MONTH FROM CURRENT_DATE);

        v_periodo_inicio =
            v_periodo_actual - :p_cantidad_meses + 1;

        SELECT COALESCE(SUM("monto"), 0)
        FROM "transaccion"
        WHERE "id_usuario" = :p_id_usuario
          AND "id_subcategoria" = :p_id_subcategoria
          AND UPPER("tipo") = 'GASTO'
          AND (("anio" * 12) + "mes")
              BETWEEN :v_periodo_inicio
              AND :v_periodo_actual
        INTO :v_total_gastos;

        p_promedio =
            v_total_gastos / :p_cantidad_meses;
    END

    SUSPEND;
END