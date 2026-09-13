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