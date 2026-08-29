CREATE TABLE "usuario" (
	"id_usuario" INTEGER NOT NULL,
	"nombres" VARCHAR(100) NOT NULL,
	"apellidos" VARCHAR(100) NOT NULL,
	"correo_electronico" VARCHAR(150) NOT NULL UNIQUE,
	"fecha_registro" DATE NOT NULL,
	"salario_mensual_base" NUMERIC(12,2) NOT NULL,
	"estado" BOOLEAN DEFAULT TRUE NOT NULL,
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_usuario")
);

CREATE TABLE "categoria" (
	"id_categoria" INTEGER NOT NULL,
	"id_usuario" INTEGER NOT NULL,
	"nombre" VARCHAR(100) NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"tipo" VARCHAR(10) NOT NULL,
	"icono" VARCHAR(50),
	"color_hex" CHAR(7),
	"orden_presentacion" SMALLINT DEFAULT 0 NOT NULL,
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_categoria")
);

CREATE TABLE "subcategoria" (
	"id_subcategoria" INTEGER NOT NULL,
	"id_categoria" INTEGER NOT NULL,
	"nombre" VARCHAR(100) NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"activa" BOOLEAN NOT NULL,
	"es_default" BOOLEAN NOT NULL,
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_subcategoria")
);

CREATE TABLE "presupuesto" (
	"id_presupuesto" INTEGER NOT NULL,
	"id_usuario" INTEGER NOT NULL,
	"nombre" VARCHAR(100) NOT NULL,
	"anio_inicio" SMALLINT NOT NULL,
	"mes_inicio" SMALLINT NOT NULL,
	"anio_fin" SMALLINT NOT NULL,
	"mes_fin" SMALLINT NOT NULL,
	"total_ingresos_planificados" NUMERIC(12,2) NOT NULL,
	"total_gastos_planificados" NUMERIC(12,2) NOT NULL,
	"total_ahorro_planificado" NUMERIC(12,2) NOT NULL,
	"fecha_hora_creacion" TIMESTAMP NOT NULL,
	"estado" VARCHAR(10) NOT NULL,
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_presupuesto")
);

CREATE TABLE "presupuesto_detalle" (
	"id_detalle" INTEGER NOT NULL,
	"id_presupuesto" INTEGER NOT NULL,
	"id_subcategoria" INTEGER NOT NULL,
	"monto_mensual" NUMERIC(12,2) NOT NULL,
	"observaciones" VARCHAR(255),
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_detalle")
);

CREATE TABLE "obligacion_fija" (
	"id_obligacion" INTEGER NOT NULL,
	"id_usuario" INTEGER NOT NULL,
	"id_subcategoria" INTEGER NOT NULL,
	"nombre" VARCHAR(100) NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"monto_fijo_mensual" NUMERIC(12,2) NOT NULL,
	"dia_vencimiento" SMALLINT NOT NULL,
	"vigente" BOOLEAN NOT NULL,
	"fecha_inicio" DATE NOT NULL,
	"fecha_fin" DATE,
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_obligacion")
);

CREATE TABLE "transaccion" (
	"id_transaccion" INTEGER NOT NULL,
	"id_usuario" INTEGER NOT NULL,
	"id_presupuesto" INTEGER NOT NULL,
	"anio" SMALLINT NOT NULL,
	"mes" SMALLINT NOT NULL,
	"id_subcategoria" INTEGER NOT NULL,
	"id_obligacion" INTEGER,
	"tipo" VARCHAR(10) NOT NULL,
	"descripcion" VARCHAR(255) NOT NULL,
	"monto" NUMERIC(12,2) NOT NULL,
	"fecha" DATE NOT NULL,
	"metodo_pago" VARCHAR(20) NOT NULL,
	"numero_factura" VARCHAR(50),
	"observaciones" VARCHAR(255),
	"fecha_hora_registro" TIMESTAMP NOT NULL,
	"creado_por" VARCHAR(50) NOT NULL,
	"modificado_por" VARCHAR(50),
	"creado_en" TIMESTAMP NOT NULL,
	"modificado_en" TIMESTAMP,
	PRIMARY KEY("id_transaccion")
);

ALTER TABLE "categoria"
ADD CONSTRAINT fk_categoria_usuario
FOREIGN KEY("id_usuario") REFERENCES "usuario"("id_usuario")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "subcategoria"
ADD CONSTRAINT fk_subcategoria_categoria
FOREIGN KEY("id_categoria") REFERENCES "categoria"("id_categoria")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "presupuesto"
ADD CONSTRAINT fk_presupuesto_usuario
FOREIGN KEY("id_usuario") REFERENCES "usuario"("id_usuario")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "presupuesto_detalle"
ADD CONSTRAINT fk_detalle_presupuesto
FOREIGN KEY("id_presupuesto") REFERENCES "presupuesto"("id_presupuesto")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "presupuesto_detalle"
ADD CONSTRAINT fk_detalle_subcategoria
FOREIGN KEY("id_subcategoria") REFERENCES "subcategoria"("id_subcategoria")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "obligacion_fija"
ADD CONSTRAINT fk_obligacion_usuario
FOREIGN KEY("id_usuario") REFERENCES "usuario"("id_usuario")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "obligacion_fija"
ADD CONSTRAINT fk_obligacion_subcategoria
FOREIGN KEY("id_subcategoria") REFERENCES "subcategoria"("id_subcategoria")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "transaccion"
ADD CONSTRAINT fk_transaccion_usuario
FOREIGN KEY("id_usuario") REFERENCES "usuario"("id_usuario")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "transaccion"
ADD CONSTRAINT fk_transaccion_presupuesto
FOREIGN KEY("id_presupuesto") REFERENCES "presupuesto"("id_presupuesto")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "transaccion"
ADD CONSTRAINT fk_transaccion_subcategoria
FOREIGN KEY("id_subcategoria") REFERENCES "subcategoria"("id_subcategoria")
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE "transaccion"
ADD CONSTRAINT fk_transaccion_obligacion
FOREIGN KEY("id_obligacion") REFERENCES "obligacion_fija"("id_obligacion")
ON UPDATE NO ACTION ON DELETE NO ACTION;