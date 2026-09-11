CREATE PROCEDURE sp_insertar_usuario (
    p_nombres VARCHAR(100),
    p_apellidos VARCHAR(100),
    p_correo_electronico VARCHAR(150),
    p_fecha_registro DATE,
    p_salario_mensual_base NUMERIC(12,2),
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_usuario INTEGER
)
AS
BEGIN
    p_id_usuario = GEN_ID(gen_usuario_id, 1);

    INSERT INTO "usuario" (
        "id_usuario",
        "nombres",
        "apellidos",
        "correo_electronico",
        "fecha_registro",
        "salario_mensual_base",
        "estado",
        "creado_por",
        "creado_en"
    ) VALUES (
        :p_id_usuario,
        :p_nombres,
        :p_apellidos,
        :p_correo_electronico,
        :p_fecha_registro,
        :p_salario_mensual_base,
        TRUE,
        :p_creado_por,
        CURRENT_TIMESTAMP
    );
END;

CREATE  PROCEDURE sp_consultar_usuario (
    p_id_usuario INTEGER
)
RETURNS (
    p_nombres VARCHAR(100),
    p_apellidos VARCHAR(100),
    p_correo_electronico VARCHAR(150),
    p_fecha_registro DATE,
    p_salario_mensual_base NUMERIC(12,2),
    p_estado BOOLEAN,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    SELECT
        "nombres",
        "apellidos",
        "correo_electronico",
        "fecha_registro",
        "salario_mensual_base",
        "estado",
        "creado_por",
        "modificado_por",
        "creado_en",
        "modificado_en"
    FROM "usuario"
    WHERE "id_usuario" = :p_id_usuario
    INTO
        :p_nombres,
        :p_apellidos,
        :p_correo_electronico,
        :p_fecha_registro,
        :p_salario_mensual_base,
        :p_estado,
        :p_creado_por,
        :p_modificado_por,
        :p_creado_en,
        :p_modificado_en;

    IF (p_nombres IS NOT NULL) THEN
        SUSPEND;
END

CREATE PROCEDURE sp_actualizar_usuario (
    p_id_usuario INTEGER,
    usuario VARCHAR(50),
    p_nombres VARCHAR(100),
    p_apellidos VARCHAR(100),
    p_correo_electronico VARCHAR(150),
    p_salario_mensual_base NUMERIC(12,2)
)
AS
BEGIN
    UPDATE "usuario"
    SET "nombres" = COALESCE(:p_nombres, "nombres"),
        "apellidos" = COALESCE(:p_apellidos, "apellidos"),
        "correo_electronico" = COALESCE(:p_correo_electronico, "correo_electronico"),
        "salario_mensual_base" = COALESCE(:p_salario_mensual_base, "salario_mensual_base"),
        "modificado_por" = :usuario,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_usuario" = :p_id_usuario;
END;

CREATE PROCEDURE sp_eliminar_usuario (
    p_id_usuario INTEGER,
    p_usuario VARCHAR(100)
)
AS
BEGIN
    UPDATE "usuario"
    SET "estado" = FALSE,
        "modificado_por" = :p_usuario,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_usuario" = :p_id_usuario;
END;