--Crud Usuario

CREATE PROCEDURE sp_insertar_usuario (
p_nombres varchar(100),
p_apellidos varchar(100),
p_correo_electronico varchar(150),
p_fecha_registro DATE,
p_salario_mensual_base numeric(12,2),
p_creado_por varchar(50)
)
RETURNS (p_id_usuario integer) 
AS 
BEGIN
	INSERT INTO "usuario" ("nombres","apellidos",
	"correo_electronico","fecha_registro","salario_mensual_base",
	"estado","creado_por"
	) VALUES (
	:p_nombres, :p_apellidos, :p_correo_electronico,
	:p_fecha_registro, :p_salario_mensual_base, TRUE , :p_creado_por
	);

p_id_usuario = GEN_ID(gen_usuario_id, 0);
END

CREATE PROCEDURE sp_consultar_usuario(p_id_usuario integer)
RETURNS (
	p_nombres varchar(100),
	p_apellidos varchar(100),
	p_correo_electronico varchar(150),
	p_fecha_registro date,
	p_salario_mensual_base NUMERIC(12,2),
	p_estado BOOLEAN,
	p_creado_por varchar(50),
	p_modificado_por varchar(50),
	p_creado_en timestamp,
	p_modificado_en timestamp
)
AS 
BEGIN
	 SELECT "nombres","apellidos","correo_electronico"
	,"fecha_registro","salario_mensual_base","estado","creado_por",
	"modificado_por","creado_en","modificado_en"
	FROM "usuario" WHERE "id_usuario" = :p_id_usuario
	INTO :p_nombres , :p_apellidos ,
	:p_correo_electronico , :p_fecha_registro ,
	:p_salario_mensual_base , :p_estado,
	:p_creado_por , :p_modificado_por ,
	:p_creado_en , :p_modificado_en; 
END

CREATE PROCEDURE sp_listar_usuarios
RETURNS (
	p_id_usuario integer,
	p_nombres varchar(100),
	p_apellidos varchar(100),
	p_correo_electronico varchar(150),
	p_fecha_registro date,
	p_salario_mensual_base NUMERIC(12,2),
	p_estado BOOLEAN,
	p_creado_por varchar(50),
	p_modificado_por varchar(50),
	p_creado_en timestamp,
	p_modificado_en timestamp
)
AS 
BEGIN
	FOR SELECT "id_usuario","nombres","apellidos","correo_electronico"
	,"fecha_registro","salario_mensual_base","estado","creado_por",
	"modificado_por","creado_en","modificado_en"
	FROM "usuario" 
	INTO :p_id_usuario,:p_nombres , :p_apellidos ,
	:p_correo_electronico , :p_fecha_registro ,
	:p_salario_mensual_base , :p_estado,
	:p_creado_por , :p_modificado_por ,
	:p_creado_en , :p_modificado_en
DO 
SUSPEND;
END 

CREATE PROCEDURE sp_eliminar_usuario(p_id_usuario integer, p_usuario varchar(100))
AS 
BEGIN 
	UPDATE "usuario"
	SET "estado" = FALSE
	, "modificado_por" = :p_usuario
	WHERE "id_usuario" = :p_id_usuario;
END

CREATE PROCEDURE sp_actualizar_usuario(
p_id_usuario integer,
usuario varchar(50),
p_nombres varchar(100),
p_apellidos varchar(100),
p_correo_electronico varchar(150),
p_salario_mensual_base NUMERIC(12,2)
)
AS 
BEGIN 
	UPDATE "usuario"
	SET "nombres" = COALESCE(:p_nombres,"nombres")
	, "apellidos" = COALESCE(:p_apellidos,"apellidos")
	, "correo_electronico" = COALESCE(:p_correo_electronico,"correo_electronico")
	, "salario_mensual_base" = COALESCE(:p_salario_mensual_base,"salario_mensual_base")
	, "modificado_por" = :usuario
	WHERE "id_usuario" = :p_id_usuario;
END
