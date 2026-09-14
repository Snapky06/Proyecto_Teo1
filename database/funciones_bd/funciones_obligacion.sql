CREATE PROCEDURE fn_dias_hasta_vencimiento (
    p_id_obligacion INTEGER
) RETURNS (
    p_dias INTEGER
) AS
DECLARE VARIABLE v_dia_vencimiento SMALLINT;
DECLARE VARIABLE v_vigente BOOLEAN;
DECLARE VARIABLE v_fecha_inicio DATE;
DECLARE VARIABLE v_fecha_fin DATE;
DECLARE VARIABLE v_dia_actual INTEGER;
DECLARE VARIABLE v_mes_actual INTEGER;
DECLARE VARIABLE v_anio_actual INTEGER;
DECLARE VARIABLE v_dias_mes_actual INTEGER;
BEGIN
    p_dias = NULL;

    SELECT
        "dia_vencimiento",
        "vigente",
        "fecha_inicio",
        "fecha_fin"
    FROM "obligacion_fija"
    WHERE "id_obligacion" = :p_id_obligacion
    INTO
        :v_dia_vencimiento,
        :v_vigente,
        :v_fecha_inicio,
        :v_fecha_fin;

    IF (v_dia_vencimiento IS NOT NULL) THEN
    BEGIN
        IF (
            v_vigente = TRUE
            AND CURRENT_DATE >= v_fecha_inicio
            AND (
                v_fecha_fin IS NULL
                OR CURRENT_DATE <= v_fecha_fin
            )
        ) THEN
        BEGIN
            v_dia_actual = EXTRACT(DAY FROM CURRENT_DATE);
            v_mes_actual = EXTRACT(MONTH FROM CURRENT_DATE);
            v_anio_actual = EXTRACT(YEAR FROM CURRENT_DATE);

            SELECT p_dias 
            FROM fn_obtener_dias_mes(:v_anio_actual, :v_mes_actual) 
            INTO :v_dias_mes_actual;

            IF (v_dia_vencimiento >= v_dia_actual) THEN
            BEGIN
                p_dias = v_dia_vencimiento - v_dia_actual;
            END
            ELSE
            BEGIN
                p_dias = v_dias_mes_actual - v_dia_actual + v_dia_vencimiento;
            END
        END
    END
    SUSPEND;
END