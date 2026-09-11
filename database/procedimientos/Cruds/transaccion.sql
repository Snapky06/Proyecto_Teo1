CREATE PROCEDURE sp_insertar_transaccion (
    p_id_usuario INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT,
    p_id_subcategoria INTEGER,
    p_id_obligacion INTEGER,
    p_tipo VARCHAR(10),
    p_descripcion VARCHAR(255),
    p_monto NUMERIC(12,2),
    p_fecha DATE,
    p_metodo_pago VARCHAR(20),
    p_numero_factura VARCHAR(50),
    p_observaciones VARCHAR(255),
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_transaccion INTEGER
)
AS
BEGIN
    p_id_transaccion = GEN_ID(gen_transaccion_id, 1);

    INSERT INTO "transaccion" (
        "id_transaccion",
        "id_usuario",
        "id_presupuesto",
        "anio",
        "mes",
        "id_subcategoria",
        "id_obligacion",
        "tipo",
        "descripcion",
        "monto",
        "fecha",
        "metodo_pago",
        "numero_factura",
        "observaciones",
        "fecha_hora_registro",
        "creado_por",
        "creado_en"
    ) VALUES (
        :p_id_transaccion,
        :p_id_usuario,
        :p_id_presupuesto,
        :p_anio,
        :p_mes,
        :p_id_subcategoria,
        :p_id_obligacion,
        :p_tipo,
        :p_descripcion,
        :p_monto,
        :p_fecha,
        :p_metodo_pago,
        :p_numero_factura,
        :p_observaciones,
        CURRENT_TIMESTAMP,
        :p_creado_por,
        CURRENT_TIMESTAMP
    );
END

CREATE PROCEDURE sp_consultar_transaccion (
    p_id_transaccion INTEGER
)
RETURNS (
    p_id_usuario INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT,
    p_id_subcategoria INTEGER,
    p_id_obligacion INTEGER,
    p_tipo VARCHAR(10),
    p_descripcion VARCHAR(255),
    p_monto NUMERIC(12,2),
    p_fecha DATE,
    p_metodo_pago VARCHAR(20),
    p_numero_factura VARCHAR(50),
    p_observaciones VARCHAR(255),
    p_fecha_hora_registro TIMESTAMP,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    p_id_usuario = NULL;

    SELECT 
        "id_usuario", "id_presupuesto", "anio", "mes", 
        "id_subcategoria", "id_obligacion", "tipo", "descripcion", 
        "monto", "fecha", "metodo_pago", "numero_factura", 
        "observaciones", "fecha_hora_registro", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "transaccion"
    WHERE "id_transaccion" = :p_id_transaccion
    INTO 
        :p_id_usuario, :p_id_presupuesto, :p_anio, :p_mes, 
        :p_id_subcategoria, :p_id_obligacion, :p_tipo, :p_descripcion, 
        :p_monto, :p_fecha, :p_metodo_pago, :p_numero_factura, 
        :p_observaciones, :p_fecha_hora_registro, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en;

    IF (p_id_usuario IS NOT NULL) THEN
        SUSPEND;
END

CREATE PROCEDURE sp_listar_transacciones (
    p_id_usuario INTEGER
)
RETURNS (
    p_id_transaccion INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT,
    p_id_subcategoria INTEGER,
    p_id_obligacion INTEGER,
    p_tipo VARCHAR(10),
    p_descripcion VARCHAR(255),
    p_monto NUMERIC(12,2),
    p_fecha DATE,
    p_metodo_pago VARCHAR(20),
    p_numero_factura VARCHAR(50),
    p_observaciones VARCHAR(255),
    p_fecha_hora_registro TIMESTAMP,
    p_creado_por VARCHAR(50),
    p_modificado_por VARCHAR(50),
    p_creado_en TIMESTAMP,
    p_modificado_en TIMESTAMP
)
AS
BEGIN
    FOR SELECT 
        "id_transaccion", "id_presupuesto", "anio", "mes", 
        "id_subcategoria", "id_obligacion", "tipo", "descripcion", 
        "monto", "fecha", "metodo_pago", "numero_factura", 
        "observaciones", "fecha_hora_registro", "creado_por", 
        "modificado_por", "creado_en", "modificado_en"
    FROM "transaccion"
    WHERE "id_usuario" = :p_id_usuario
    INTO 
        :p_id_transaccion, :p_id_presupuesto, :p_anio, :p_mes, 
        :p_id_subcategoria, :p_id_obligacion, :p_tipo, :p_descripcion, 
        :p_monto, :p_fecha, :p_metodo_pago, :p_numero_factura, 
        :p_observaciones, :p_fecha_hora_registro, :p_creado_por, 
        :p_modificado_por, :p_creado_en, :p_modificado_en
    DO
    BEGIN
        SUSPEND;
    END
END

CREATE PROCEDURE sp_actualizar_transaccion (
    p_id_transaccion INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT,
    p_id_subcategoria INTEGER,
    p_id_obligacion INTEGER,
    p_tipo VARCHAR(10),
    p_descripcion VARCHAR(255),
    p_monto NUMERIC(12,2),
    p_fecha DATE,
    p_metodo_pago VARCHAR(20),
    p_numero_factura VARCHAR(50),
    p_observaciones VARCHAR(255),
    p_modificado_por VARCHAR(50)
)
AS
BEGIN
    UPDATE "transaccion"
    SET 
        "id_presupuesto" = :p_id_presupuesto,
        "anio" = :p_anio,
        "mes" = :p_mes,
        "id_subcategoria" = :p_id_subcategoria,
        "id_obligacion" = :p_id_obligacion,
        "tipo" = :p_tipo,
        "descripcion" = :p_descripcion,
        "monto" = :p_monto,
        "fecha" = :p_fecha,
        "metodo_pago" = :p_metodo_pago,
        "numero_factura" = :p_numero_factura,
        "observaciones" = :p_observaciones,
        "modificado_por" = :p_modificado_por,
        "modificado_en" = CURRENT_TIMESTAMP
    WHERE "id_transaccion" = :p_id_transaccion;
END

CREATE PROCEDURE sp_eliminar_transaccion (
    p_id_transaccion INTEGER
)
AS
BEGIN
    DELETE FROM "transaccion"
    WHERE "id_transaccion" = :p_id_transaccion;
END