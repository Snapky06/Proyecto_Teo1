CREATE PROCEDURE sp_procesar_obligaciones_mes (
    p_id_usuario INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT,
    p_id_presupuesto INTEGER
)
RETURNS (
    p_id_obligacion INTEGER,
    p_nombre VARCHAR(100),
    p_monto_fijo_mensual NUMERIC(12,2),
    p_dia_vencimiento SMALLINT,
    p_fecha_vencimiento DATE,
    p_estado_pago VARCHAR(20),
    p_dias_hasta_vencimiento INTEGER,
    p_fecha_ultimo_pago DATE,
    p_alerta VARCHAR(100)
)
AS
DECLARE VARIABLE v_fecha_ultimo_pago DATE;
DECLARE VARIABLE v_fecha_inicio_mes DATE;
DECLARE VARIABLE v_dias_mes INTEGER;
DECLARE VARIABLE v_dias_calculados INTEGER;
DECLARE VARIABLE v_dia_actual INTEGER;
DECLARE VARIABLE v_mes_actual INTEGER;
DECLARE VARIABLE v_anio_actual INTEGER;
DECLARE VARIABLE v_anio_recorrido INTEGER;
DECLARE VARIABLE v_mes_recorrido INTEGER;
DECLARE VARIABLE v_dias_mes_recorrido INTEGER;
DECLARE VARIABLE v_residuo_anio INTEGER;
DECLARE VARIABLE v_mes_texto VARCHAR(2);
DECLARE VARIABLE v_dia_texto VARCHAR(2);
BEGIN
    IF (p_mes < 1 OR p_mes > 12) THEN
        EXIT;

    IF (
        p_mes = 1
        OR p_mes = 3
        OR p_mes = 5
        OR p_mes = 7
        OR p_mes = 8
        OR p_mes = 10
        OR p_mes = 12
    ) THEN
        v_dias_mes = 31;
    ELSE IF (
        p_mes = 4
        OR p_mes = 6
        OR p_mes = 9
        OR p_mes = 11
    ) THEN
        v_dias_mes = 30;
    ELSE
    BEGIN
        v_residuo_anio =
            p_anio - (p_anio / 4) * 4;

        IF (v_residuo_anio = 0) THEN
            v_dias_mes = 29;
        ELSE
            v_dias_mes = 28;
    END

    IF (p_mes < 10) THEN
        v_mes_texto = '0' || CAST(p_mes AS VARCHAR(1));
    ELSE
        v_mes_texto = CAST(p_mes AS VARCHAR(2));

    v_fecha_inicio_mes =
        CAST(
            CAST(p_anio AS VARCHAR(4))
            || '-'
            || :v_mes_texto
            || '-01'
            AS DATE
        );

    FOR
        SELECT
            o."id_obligacion",
            o."nombre",
            o."monto_fijo_mensual",
            o."dia_vencimiento"
        FROM "obligacion_fija" o
        WHERE o."id_usuario" = :p_id_usuario
          AND o."vigente" = TRUE
          AND o."dia_vencimiento" <= :v_dias_mes
          AND o."fecha_inicio" <= :v_fecha_inicio_mes
          AND (
                o."fecha_fin" IS NULL
                OR o."fecha_fin" >= :v_fecha_inicio_mes
          )
        ORDER BY
            o."dia_vencimiento",
            o."nombre"
        INTO
            :p_id_obligacion,
            :p_nombre,
            :p_monto_fijo_mensual,
            :p_dia_vencimiento
    DO
    BEGIN
        IF (p_dia_vencimiento < 10) THEN
            v_dia_texto =
                '0' || CAST(p_dia_vencimiento AS VARCHAR(1));
        ELSE
            v_dia_texto =
                CAST(p_dia_vencimiento AS VARCHAR(2));

        p_fecha_vencimiento =
            CAST(
                CAST(p_anio AS VARCHAR(4))
                || '-'
                || :v_mes_texto
                || '-'
                || :v_dia_texto
                AS DATE
            );

        v_dia_actual = EXTRACT(DAY FROM CURRENT_DATE);
        v_mes_actual = EXTRACT(MONTH FROM CURRENT_DATE);
        v_anio_actual = EXTRACT(YEAR FROM CURRENT_DATE);

        IF (
            v_anio_actual = p_anio
            AND v_mes_actual = p_mes
        ) THEN
        BEGIN
            p_dias_hasta_vencimiento =
                p_dia_vencimiento - v_dia_actual;
        END
        ELSE IF (
            (p_anio * 12 + p_mes)
            > (v_anio_actual * 12 + v_mes_actual)
        ) THEN
        BEGIN
            v_dias_calculados = 0;
            v_dias_calculados =
                v_dias_calculados
                + v_dias_mes
                - v_dia_actual;

            v_anio_recorrido = v_anio_actual;
            v_mes_recorrido = v_mes_actual + 1;

            IF (v_mes_recorrido > 12) THEN
            BEGIN
                v_mes_recorrido = 1;
                v_anio_recorrido = v_anio_recorrido + 1;
            END

            WHILE (
                (v_anio_recorrido * 12 + v_mes_recorrido)
                < (p_anio * 12 + p_mes)
            ) DO
            BEGIN
                IF (
                    v_mes_recorrido = 1
                    OR v_mes_recorrido = 3
                    OR v_mes_recorrido = 5
                    OR v_mes_recorrido = 7
                    OR v_mes_recorrido = 8
                    OR v_mes_recorrido = 10
                    OR v_mes_recorrido = 12
                ) THEN
                    v_dias_mes_recorrido = 31;
                ELSE IF (
                    v_mes_recorrido = 4
                    OR v_mes_recorrido = 6
                    OR v_mes_recorrido = 9
                    OR v_mes_recorrido = 11
                ) THEN
                    v_dias_mes_recorrido = 30;
                ELSE
                BEGIN
                    v_residuo_anio =
                        v_anio_recorrido
                        - (v_anio_recorrido / 4) * 4;

                    IF (v_residuo_anio = 0) THEN
                        v_dias_mes_recorrido = 29;
                    ELSE
                        v_dias_mes_recorrido = 28;
                END

                v_dias_calculados =
                    v_dias_calculados
                    + v_dias_mes_recorrido;

                v_mes_recorrido = v_mes_recorrido + 1;

                IF (v_mes_recorrido > 12) THEN
                BEGIN
                    v_mes_recorrido = 1;
                    v_anio_recorrido = v_anio_recorrido + 1;
                END
            END

            v_dias_calculados =
                v_dias_calculados + p_dia_vencimiento;

            p_dias_hasta_vencimiento =
                v_dias_calculados;
        END
        ELSE
        BEGIN
            v_dias_calculados = 0;
            v_anio_recorrido = p_anio;
            v_mes_recorrido = p_mes;

            WHILE (
                (v_anio_recorrido * 12 + v_mes_recorrido)
                < (v_anio_actual * 12 + v_mes_actual)
            ) DO
            BEGIN
                IF (
                    v_mes_recorrido = 1
                    OR v_mes_recorrido = 3
                    OR v_mes_recorrido = 5
                    OR v_mes_recorrido = 7
                    OR v_mes_recorrido = 8
                    OR v_mes_recorrido = 10
                    OR v_mes_recorrido = 12
                ) THEN
                    v_dias_mes_recorrido = 31;
                ELSE IF (
                    v_mes_recorrido = 4
                    OR v_mes_recorrido = 6
                    OR v_mes_recorrido = 9
                    OR v_mes_recorrido = 11
                ) THEN
                    v_dias_mes_recorrido = 30;
                ELSE
                BEGIN
                    v_residuo_anio =
                        v_anio_recorrido
                        - (v_anio_recorrido / 4) * 4;

                    IF (v_residuo_anio = 0) THEN
                        v_dias_mes_recorrido = 29;
                    ELSE
                        v_dias_mes_recorrido = 28;
                END

                IF (
                    (v_anio_recorrido * 12 + v_mes_recorrido)
                    = (p_anio * 12 + p_mes)
                ) THEN
                    v_dias_calculados =
                        v_dias_calculados
                        + v_dias_mes_recorrido
                        - p_dia_vencimiento;
                ELSE
                    v_dias_calculados =
                        v_dias_calculados
                        + v_dias_mes_recorrido;

                v_mes_recorrido = v_mes_recorrido + 1;

                IF (v_mes_recorrido > 12) THEN
                BEGIN
                    v_mes_recorrido = 1;
                    v_anio_recorrido = v_anio_recorrido + 1;
                END
            END

            v_dias_calculados =
                v_dias_calculados + v_dia_actual;

            p_dias_hasta_vencimiento =
                0 - v_dias_calculados;
        END

        SELECT
            MAX(t."fecha")
        FROM "transaccion" t
        WHERE t."id_usuario" = :p_id_usuario
          AND t."id_presupuesto" = :p_id_presupuesto
          AND t."id_obligacion" = :p_id_obligacion
          AND t."anio" = :p_anio
          AND t."mes" = :p_mes
          AND UPPER(t."tipo") = 'GASTO'
        INTO :v_fecha_ultimo_pago;

        p_fecha_ultimo_pago = v_fecha_ultimo_pago;

        IF (v_fecha_ultimo_pago IS NOT NULL) THEN
        BEGIN
            p_estado_pago = 'PAGADA';
            p_alerta = 'Obligacion pagada';
        END
        ELSE IF (p_dias_hasta_vencimiento < 0) THEN
        BEGIN
            p_estado_pago = 'VENCIDA';
            p_alerta = 'Obligacion vencida sin pago';
        END
        ELSE IF (p_dias_hasta_vencimiento <= 3) THEN
        BEGIN
            p_estado_pago = 'POR VENCER';
            p_alerta = 'Vence en 3 dias o menos';
        END
        ELSE
        BEGIN
            p_estado_pago = 'PENDIENTE';
            p_alerta = 'Obligacion pendiente';
        END

        SUSPEND;
    END
END