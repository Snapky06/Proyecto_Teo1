CREATE PROCEDURE sp_registrar_transaccion_completa (
    p_id_usuario INTEGER,
    p_id_presupuesto INTEGER,
    p_anio SMALLINT,
    p_mes SMALLINT,
    p_id_subcategoria INTEGER,
    p_tipo VARCHAR(10),
    p_descripcion VARCHAR(255),
    p_monto NUMERIC(12,2),
    p_fecha DATE,
    p_metodo_pago VARCHAR(20),
    p_creado_por VARCHAR(50)
)
RETURNS (
    p_id_transaccion INTEGER
)
AS
DECLARE VARIABLE v_id_usuario_presupuesto INTEGER;
DECLARE VARIABLE v_anio_inicio SMALLINT;
DECLARE VARIABLE v_mes_inicio SMALLINT;
DECLARE VARIABLE v_anio_fin SMALLINT;
DECLARE VARIABLE v_mes_fin SMALLINT;
DECLARE VARIABLE v_id_categoria INTEGER;
DECLARE VARIABLE v_tipo_categoria VARCHAR(10);
DECLARE VARIABLE v_periodo_transaccion INTEGER;
DECLARE VARIABLE v_periodo_inicio INTEGER;
DECLARE VARIABLE v_periodo_fin INTEGER;
BEGIN
    p_id_transaccion = NULL;

    IF (
        p_id_usuario IS NULL
        OR p_id_presupuesto IS NULL
        OR p_anio IS NULL
        OR p_mes IS NULL
        OR p_id_subcategoria IS NULL
        OR p_tipo IS NULL
        OR p_descripcion IS NULL
        OR p_monto IS NULL
        OR p_fecha IS NULL
        OR p_metodo_pago IS NULL
        OR p_creado_por IS NULL
    ) THEN
        EXCEPTION ex_transaccion_datos_invalidos;

    IF (p_mes < 1 OR p_mes > 12) THEN
        EXCEPTION ex_transaccion_mes_invalido;

    IF (p_anio < 1) THEN
        EXCEPTION ex_transaccion_datos_invalidos;

    IF (p_monto <= 0) THEN
        EXCEPTION ex_transaccion_datos_invalidos;

    SELECT
        "id_usuario",
        "anio_inicio",
        "mes_inicio",
        "anio_fin",
        "mes_fin"
    FROM "presupuesto"
    WHERE "id_presupuesto" = :p_id_presupuesto
    INTO
        :v_id_usuario_presupuesto,
        :v_anio_inicio,
        :v_mes_inicio,
        :v_anio_fin,
        :v_mes_fin;

    IF (
        v_id_usuario_presupuesto IS NULL
        OR v_id_usuario_presupuesto <> p_id_usuario
    ) THEN
        EXCEPTION ex_transaccion_presupuesto_no_existe;

    v_periodo_transaccion =
        (:p_anio * 12) + :p_mes;

    v_periodo_inicio =
        (v_anio_inicio * 12) + v_mes_inicio;

    v_periodo_fin =
        (v_anio_fin * 12) + v_mes_fin;

    IF (
        v_periodo_transaccion < v_periodo_inicio
        OR v_periodo_transaccion > v_periodo_fin
    ) THEN
        EXCEPTION ex_transaccion_fuera_vigencia;

    SELECT
        "id_categoria"
    FROM "subcategoria"
    WHERE "id_subcategoria" = :p_id_subcategoria
    INTO :v_id_categoria;

    IF (v_id_categoria IS NULL) THEN
        EXCEPTION ex_transaccion_subcategoria_no_existe;

    SELECT
        UPPER("tipo")
    FROM "categoria"
    WHERE "id_categoria" = :v_id_categoria
    INTO :v_tipo_categoria;

    IF (
        v_tipo_categoria IS NULL
        OR UPPER(:p_tipo) <> v_tipo_categoria
    ) THEN
        EXCEPTION ex_transaccion_tipo_invalido;

    p_id_transaccion =
        GEN_ID(gen_transaccion_id, 1);

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
    )
    VALUES (
        :p_id_transaccion,
        :p_id_usuario,
        :p_id_presupuesto,
        :p_anio,
        :p_mes,
        :p_id_subcategoria,
        NULL,
        UPPER(:p_tipo),
        :p_descripcion,
        :p_monto,
        :p_fecha,
        UPPER(:p_metodo_pago),
        NULL,
        NULL,
        CURRENT_TIMESTAMP,
        :p_creado_por,
        CURRENT_TIMESTAMP
    );

    SUSPEND;
END