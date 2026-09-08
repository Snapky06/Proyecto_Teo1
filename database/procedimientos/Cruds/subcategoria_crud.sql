CREATE PROCEDURE sp_insertar_subcategoria (
    p_id_categoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_subcategoria INTEGER
)
AS
BEGIN
    p_id_subcategoria = GEN_ID(gen_subcategoria_id, 1);

    INSERT INTO "subcategoria" (
        "id_subcategoria",
        "id_categoria",
        "nombre",
        "descripcion",
        "activa",
        "es_default",
        "creado_por",
        "creado_en"
    ) VALUES (
        :p_id_subcategoria,
        :p_id_categoria,
        :p_nombre,
        :p_descripcion,
        1,
        0,
        :p_creado_por,
        CURRENT_TIMESTAMP
    );
END

CREATE PROCEDURE sp_consultar_subcategoria (
    p_id_subcategoria INTEGER
)
RETURNS (
    p_id_categoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    p_id_categoria = NULL;

    SELECT 
        "id_categoria", "nombre", "descripcion",
        "creado_por", "modificado_por", "creado_en", "modificado_en"
    FROM "subcategoria"
    WHERE "id_subcategoria" = :p_id_subcategoria
    INTO 
        :p_id_categoria, :p_nombre, :p_descripcion,
        :p_creado_por, :p_modificado_por, :p_creado_en, :p_modificado_en;

    IF (p_id_categoria IS NOT NULL) THEN
        SUSPEND;
END

CREATE PROCEDURE sp_actualizar_subcategoria (
    p_id_subcategoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_modificado_por VARCHAR(50)
)
AS
BEGIN
    UPDATE "subcategoria"
    SET 
        "nombre" = :p_nombre,
        "descripcion" = :p_descripcion,
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_subcategoria" = :p_id_subcategoria;
END

CREATE PROCEDURE sp_listar_subcategoria (
    p_id_categoria INTEGER
)
RETURNS (
    p_id_subcategoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_activa INTEGER,
    p_es_default INTEGER,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    FOR SELECT 
        "id_subcategoria", "nombre", "descripcion", 
        "activa", "es_default", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "subcategoria"
    WHERE "id_categoria" = :p_id_categoria
    INTO 
        :p_id_subcategoria, :p_nombre, :p_descripcion, 
        :p_activa, :p_es_default, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en
    DO
    BEGIN
        SUSPEND;
    END
END

CREATE PROCEDURE sp_eliminar_subcategoria (
    p_id_subcategoria INTEGER
)
AS
BEGIN
    DELETE FROM "subcategoria"
    WHERE "id_subcategoria" = :p_id_subcategoria;
END