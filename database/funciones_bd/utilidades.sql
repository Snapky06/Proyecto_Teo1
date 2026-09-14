CREATE PROCEDURE fn_obtener_dias_mes (
    p_anio SMALLINT,
    p_mes SMALLINT
) RETURNS (
    p_dias SMALLINT
) AS
DECLARE VARIABLE v_residuo_anio INTEGER;
BEGIN

    IF (p_mes = 1 OR p_mes = 3 OR p_mes = 5 OR p_mes = 7 OR p_mes = 8 OR p_mes = 10 OR p_mes = 12) THEN
        p_dias = 31;

    ELSE IF (p_mes = 4 OR p_mes = 6 OR p_mes = 9 OR p_mes = 11) THEN
        p_dias = 30;

    ELSE 
    BEGIN
        v_residuo_anio = p_anio - (p_anio / 4) * 4;
        IF (v_residuo_anio = 0) THEN
            p_dias = 29;
        ELSE
            p_dias = 28;
    END
    
    SUSPEND;
END