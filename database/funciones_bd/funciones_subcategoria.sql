CREATE PROCEDURE fn_obtener_categoria_por_subcategoria (
    p_id_subcategoria INTEGER
)
RETURNS (
    p_id_categoria INTEGER
)
AS
BEGIN
    p_id_categoria = NULL;

    SELECT
        "id_categoria"
    FROM "subcategoria"
    WHERE "id_subcategoria" = :p_id_subcategoria
    INTO
        :p_id_categoria;

    SUSPEND;
END