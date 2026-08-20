SET TERM ^ ;

CREATE TRIGGER trg_usuario_bi FOR "usuario"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_usuario" IS NULL) THEN
    NEW."id_usuario" = GEN_ID(gen_usuario_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_usuario_bu FOR "usuario"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_categoria_bi FOR "categoria"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_categoria" IS NULL) THEN
    NEW."id_categoria" = GEN_ID(gen_categoria_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_categoria_bu FOR "categoria"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_categoria_crea_subcat_general FOR "categoria"
ACTIVE AFTER INSERT POSITION 0
AS
BEGIN
  INSERT INTO "subcategoria" ("id_categoria", "nombre", "descripcion", "activa", "es_default", "creado_por", "creado_en")
  VALUES (NEW."id_categoria", 'General', 'Subcategoria general', 1, 1, NEW."creado_por", CURRENT_TIMESTAMP);
END^

CREATE TRIGGER trg_subcategoria_bi FOR "subcategoria"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_subcategoria" IS NULL) THEN
    NEW."id_subcategoria" = GEN_ID(gen_subcategoria_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_subcategoria_bu FOR "subcategoria"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_presupuesto_bi FOR "presupuesto"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_presupuesto" IS NULL) THEN
    NEW."id_presupuesto" = GEN_ID(gen_presupuesto_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
  IF (NEW."fecha_hora_creacion" IS NULL) THEN
    NEW."fecha_hora_creacion" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_presupuesto_bu FOR "presupuesto"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_presupuesto_detalle_bi FOR "presupuesto_detalle"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_detalle" IS NULL) THEN
    NEW."id_detalle" = GEN_ID(gen_presupuesto_detalle_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_presupuesto_detalle_bu FOR "presupuesto_detalle"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_obligacion_fija_bi FOR "obligacion_fija"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_obligacion" IS NULL) THEN
    NEW."id_obligacion" = GEN_ID(gen_obligacion_fija_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_obligacion_fija_bu FOR "obligacion_fija"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_transaccion_bi FOR "transaccion"
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW."id_transaccion" IS NULL) THEN
    NEW."id_transaccion" = GEN_ID(gen_transaccion_id, 1);
  IF (NEW."creado_en" IS NULL) THEN
    NEW."creado_en" = CURRENT_TIMESTAMP;
  IF (NEW."fecha_hora_registro" IS NULL) THEN
    NEW."fecha_hora_registro" = CURRENT_TIMESTAMP;
END^

CREATE TRIGGER trg_transaccion_bu FOR "transaccion"
ACTIVE BEFORE UPDATE POSITION 0
AS
BEGIN
  NEW."modificado_en" = CURRENT_TIMESTAMP;
END^

SET TERM ; ^

COMMIT;