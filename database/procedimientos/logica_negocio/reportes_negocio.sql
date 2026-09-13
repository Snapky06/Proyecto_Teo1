CREATE PROCEDURE sp_calcular_monto_ejecutado_mes (
    p_id_subcategoria INTEGER,
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
      AND "anio" = :p_anio
      AND "mes" = :p_mes
      AND UPPER("tipo") = 'GASTO'
    INTO :monto_ejecutado;

    SUSPEND;
END