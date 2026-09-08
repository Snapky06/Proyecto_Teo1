CREATE PROCEDURE sp_insertar_obligacion_fija (
    p_id_usuario INTEGER,
    p_id_subcategoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_monto_fijo_mensual NUMERIC(12,2),
    p_dia_vencimiento SMALLINT,
    p_vigente BOOLEAN,
    p_fecha_inicio DATE,
    p_fecha_fin DATE,
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_obligacion INTEGER
)
AS
BEGIN
    p_id_obligacion = GEN_ID(gen_obligacion_fija_id, 1);

    INSERT INTO "obligacion_fija" (
        "id_obligacion",
        "id_usuario",
        "id_subcategoria",
        "nombre",
        "descripcion",
        "monto_fijo_mensual",
        "dia_vencimiento",
        "vigente",
        "fecha_inicio",
        "fecha_fin",
        "creado_por",
        "creado_en"
    ) VALUES (
        :p_id_obligacion,
        :p_id_usuario,
        :p_id_subcategoria,
        :p_nombre,
        :p_descripcion,
        :p_monto_fijo_mensual,
        :p_dia_vencimiento,
        :p_vigente,
        :p_fecha_inicio,
        :p_fecha_fin,
        :p_creado_por,
        CURRENT_TIMESTAMP
    );
END

CREATE PROCEDURE sp_consultar_obligacion_fija (
    p_id_obligacion INTEGER
)
RETURNS (
    p_id_usuario INTEGER,
    p_id_subcategoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_monto_fijo_mensual NUMERIC(12,2),
    p_dia_vencimiento SMALLINT,
    p_vigente BOOLEAN,
    p_fecha_inicio DATE,
    p_fecha_fin DATE,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    p_id_usuario = NULL;

    SELECT 
        "id_usuario", "id_subcategoria", "nombre", "descripcion", 
        "monto_fijo_mensual", "dia_vencimiento", "vigente", 
        "fecha_inicio", "fecha_fin", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "obligacion_fija"
    WHERE "id_obligacion" = :p_id_obligacion
    INTO 
        :p_id_usuario, :p_id_subcategoria, :p_nombre, :p_descripcion, 
        :p_monto_fijo_mensual, :p_dia_vencimiento, :p_vigente, 
        :p_fecha_inicio, :p_fecha_fin, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en;

    IF (p_id_usuario IS NOT NULL) THEN
        SUSPEND;

END

CREATE PROCEDURE sp_listar_obligaciones_fijas (
    p_id_usuario INTEGER
)
RETURNS (
    p_id_obligacion INTEGER,
    p_id_subcategoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_monto_fijo_mensual NUMERIC(12,2),
    p_dia_vencimiento SMALLINT,
    p_vigente BOOLEAN,
    p_fecha_inicio DATE,
    p_fecha_fin DATE,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    FOR SELECT 
        "id_obligacion", "id_subcategoria", "nombre", "descripcion", 
        "monto_fijo_mensual", "dia_vencimiento", "vigente", 
        "fecha_inicio", "fecha_fin", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "obligacion_fija"
    WHERE "id_usuario" = :p_id_usuario
    INTO 
        :p_id_obligacion, :p_id_subcategoria, :p_nombre, :p_descripcion, 
        :p_monto_fijo_mensual, :p_dia_vencimiento, :p_vigente, 
        :p_fecha_inicio, :p_fecha_fin, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en
    DO
    BEGIN
        SUSPEND;
    END
END

CREATE PROCEDURE sp_actualizar_obligacion_fija (
    p_id_obligacion INTEGER,
    p_id_subcategoria INTEGER,
    p_nombre VARCHAR(100),
    p_descripcion VARCHAR(255),
    p_monto_fijo_mensual NUMERIC(12,2),
    p_dia_vencimiento SMALLINT,
    p_vigente BOOLEAN,
    p_fecha_inicio DATE,
    p_fecha_fin DATE,
    p_modificado_por VARCHAR(50)
)
AS
BEGIN
    UPDATE "obligacion_fija"
    SET 
        "id_subcategoria" = :p_id_subcategoria,
        "nombre" = :p_nombre,
        "descripcion" = :p_descripcion,
        "monto_fijo_mensual" = :p_monto_fijo_mensual,
        "dia_vencimiento" = :p_dia_vencimiento,
        "vigente" = :p_vigente,
        "fecha_inicio" = :p_fecha_inicio,
        "fecha_fin" = :p_fecha_fin,
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_obligacion" = :p_id_obligacion;
END

CREATE PROCEDURE sp_eliminar_obligacion_fija (
    p_id_obligacion INTEGER
)
AS
BEGIN
    DELETE FROM "obligacion_fija"
    WHERE "id_obligacion" = :p_id_obligacion;
END