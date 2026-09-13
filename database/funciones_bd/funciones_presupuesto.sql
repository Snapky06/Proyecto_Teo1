
CREATE PROCEDURE fn_calcular_porcentaje_ejecutado (
    p_id_subcategoria INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    p_porcentaje NUMERIC(12,2)
)
AS
DECLARE VARIABLE v_monto_ejecutado NUMERIC(12,2);
DECLARE VARIABLE v_monto_presupuestado NUMERIC(12,2);
BEGIN
    SELECT COALESCE(SUM("monto"), 0)
    FROM "transaccion"
    WHERE "id_subcategoria" = :p_id_subcategoria
      AND "id_presupuesto" = :p_id_presupuesto
      AND "anio" = :p_anio
      AND "mes" = :p_mes
    INTO :v_monto_ejecutado;

    SELECT COALESCE(SUM("monto_mensual"), 0)
    FROM "presupuesto_detalle"
    WHERE "id_presupuesto" = :p_id_presupuesto
      AND "id_subcategoria" = :p_id_subcategoria
    INTO :v_monto_presupuestado;

    IF (v_monto_presupuestado > 0) THEN
        p_porcentaje =
            (v_monto_ejecutado / v_monto_presupuestado) * 100;
    ELSE
        p_porcentaje = 0;

    SUSPEND;
END


CREATE PROCEDURE fn_obtener_total_categoria_mes (
    p_id_categoria INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    p_total_presupuestado NUMERIC(12,2)
)
AS
DECLARE VARIABLE v_periodo_consultado INTEGER;
DECLARE VARIABLE v_periodo_inicio INTEGER;
DECLARE VARIABLE v_periodo_fin INTEGER;
BEGIN
    v_periodo_consultado =
        (:p_anio * 12) + :p_mes;

    SELECT
        ("anio_inicio" * 12) + "mes_inicio",
        ("anio_fin" * 12) + "mes_fin"
    FROM "presupuesto"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO :v_periodo_inicio, :v_periodo_fin;

    IF (v_periodo_consultado >= v_periodo_inicio
        AND v_periodo_consultado <= v_periodo_fin) THEN
    BEGIN
        SELECT COALESCE(SUM(pd."monto_mensual"), 0)
        FROM "presupuesto_detalle" pd
        INNER JOIN "subcategoria" sc
            ON sc."id_subcategoria" = pd."id_subcategoria"
        WHERE pd."id_presupuesto" = :p_id_presupuesto
          AND sc."id_categoria" = :p_id_categoria
        INTO :p_total_presupuestado;
    END
    ELSE
        p_total_presupuestado = 0;

    SUSPEND;
END

CREATE PROCEDURE fn_obtener_total_ejecutado_categoria_mes (
    p_id_categoria INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT
)
RETURNS (
    p_total_ejecutado NUMERIC(12,2)
)
AS
BEGIN
    SELECT COALESCE(SUM(t."monto"), 0)
    FROM "transaccion" t
    INNER JOIN "subcategoria" sc
        ON sc."id_subcategoria" = t."id_subcategoria"
    WHERE sc."id_categoria" = :p_id_categoria
      AND t."anio" = :p_anio
      AND t."mes" = :p_mes
    INTO :p_total_ejecutado;

    SUSPEND;
END

CREATE PROCEDURE fn_validar_vigencia_presupuesto (
    p_fecha DATE,
    p_id_presupuesto INTEGER
)
RETURNS (
    p_vigente BOOLEAN
)
AS
DECLARE VARIABLE v_anio_inicio SMALLINT;
DECLARE VARIABLE v_mes_inicio SMALLINT;
DECLARE VARIABLE v_anio_fin SMALLINT;
DECLARE VARIABLE v_mes_fin SMALLINT;
DECLARE VARIABLE v_periodo_fecha INTEGER;
DECLARE VARIABLE v_periodo_inicio INTEGER;
DECLARE VARIABLE v_periodo_fin INTEGER;
BEGIN
    p_vigente = FALSE;

    SELECT
        "anio_inicio",
        "mes_inicio",
        "anio_fin",
        "mes_fin"
    FROM "presupuesto"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO
        :v_anio_inicio,
        :v_mes_inicio,
        :v_anio_fin,
        :v_mes_fin;

    IF (
        p_fecha IS NOT NULL
        AND v_anio_inicio IS NOT NULL
    ) THEN
    BEGIN
        v_periodo_fecha =
            (EXTRACT(YEAR FROM :p_fecha) * 12)
            + EXTRACT(MONTH FROM :p_fecha);

        v_periodo_inicio =
            (v_anio_inicio * 12) + v_mes_inicio;

        v_periodo_fin =
            (v_anio_fin * 12) + v_mes_fin;

        IF (
            v_periodo_fecha >= v_periodo_inicio
            AND v_periodo_fecha <= v_periodo_fin
        ) THEN
            p_vigente = TRUE;
    END

    SUSPEND;
END