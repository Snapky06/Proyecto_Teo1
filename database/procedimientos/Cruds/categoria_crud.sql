CREATE PROCEDURE sp_insertar_categoria (
    p_id_usuario INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_tipo VARCHAR(10),
    p_icono VARCHAR(50),
    p_color_hex CHAR(7),
    p_orden_presentacion SMALLINT,
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_categoria INTEGER
)
AS
BEGIN
    p_id_categoria = GEN_ID(gen_categoria_id, 1);

    INSERT INTO "categoria" (
        "id_categoria",
        "id_usuario",
        "nombre",
        "descripcion",
        "tipo",
        "icono",
        "color_hex",
        "orden_presentacion",
        "creado_por",
        "creado_en"
    ) VALUES (
        :p_id_categoria,
        :p_id_usuario,
        :p_nombre,
        :p_descripcion,
        :p_tipo,
        :p_icono,
        :p_color_hex,
        COALESCE(:p_orden_presentacion, 0),
        :p_creado_por,
        CURRENT_TIMESTAMP
    );
END

CREATE PROCEDURE sp_consultar_categoria (
    p_id_categoria INTEGER
)
RETURNS (
    p_id_usuario INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_tipo VARCHAR(10),
    p_icono VARCHAR(50),
    p_color_hex CHAR(7),
    p_orden_presentacion SMALLINT,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    SELECT 
        "id_usuario", "nombre", "descripcion", "tipo",
        "icono", "color_hex", "orden_presentacion",
        "creado_por", "modificado_por", "creado_en", "modificado_en"
    FROM "categoria"
    WHERE "id_categoria" = :p_id_categoria
    INTO 
        :p_id_usuario, :p_nombre, :p_descripcion, :p_tipo,
        :p_icono, :p_color_hex, :p_orden_presentacion,
        :p_creado_por, :p_modificado_por, :p_creado_en, :p_modificado_en;
END

CREATE PROCEDURE sp_listar_categorias (
    p_id_usuario INTEGER
)
RETURNS (
p_id_categoria INTEGER,
p_nombre VARCHAR(100),
p_descripcion VARCHAR(255),
p_tipo VARCHAR(10),
p_icono VARCHAR(50),
p_color_hex CHAR(7),
p_orden_presentacion SMALLINT,
p_creado_por VARCHAR(50),
p_modificado_por VARCHAR(50),
p_creado_en TIMESTAMP,
p_modificado_en TIMESTAMP
)
AS
BEGIN
    FOR SELECT 
        "id_categoria", "nombre", "descripcion", "tipo",
        "icono", "color_hex", "orden_presentacion",
        "creado_por", "modificado_por", "creado_en", "modificado_en"
    FROM "categoria"
    WHERE "id_usuario" = :p_id_usuario
    INTO 
    :p_id_categoria, :p_nombre, :p_descripcion, :p_tipo,
    :p_icono, :p_color_hex, :p_orden_presentacion,
    :p_creado_por, :p_modificado_por, :p_creado_en, :p_modificado_en
    DO
    BEGIN
        SUSPEND;
    END
END

CREATE PROCEDURE sp_actualizar_categoria (
    p_id_categoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_tipo VARCHAR(10),
    p_icono VARCHAR(50),
    p_color_hex CHAR(7),
    p_orden_presentacion SMALLINT,
    p_modificado_por VARCHAR(50)
)
AS
BEGIN
    UPDATE "categoria"
    SET 
        "nombre" = :p_nombre,
        "descripcion" = :p_descripcion,
        "tipo" = :p_tipo,
        "icono" = :p_icono,
        "color_hex" = :p_color_hex,
        "orden_presentacion" = :p_orden_presentacion,
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_categoria" = :p_id_categoria;
END

CREATE PROCEDURE sp_eliminar_categoria (
    p_id_categoria INTEGER
)
AS
BEGIN
    DELETE FROM "subcategoria" 
    WHERE "id_categoria" = :p_id_categoria;

    DELETE FROM "categoria" 
    WHERE "id_categoria" = :p_id_categoria;
END

