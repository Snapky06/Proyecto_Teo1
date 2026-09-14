CREATE EXCEPTION ex_transaccion_datos_invalidos
'Los datos de la transaccion son invalidos';

CREATE EXCEPTION ex_transaccion_mes_invalido
'El mes de la transaccion debe estar entre 1 y 12';

CREATE EXCEPTION ex_transaccion_presupuesto_no_existe
'El presupuesto no existe o no pertenece al usuario';

CREATE EXCEPTION ex_transaccion_fuera_vigencia
'El anio y mes estan fuera de la vigencia del presupuesto';

CREATE EXCEPTION ex_transaccion_subcategoria_no_existe
'La subcategoria no existe';

CREATE EXCEPTION ex_transaccion_tipo_invalido
'El tipo de transaccion no coincide con el tipo de la categoria';

CREATE EXCEPTION ex_presupuesto_no_finalizado
'El presupuesto aun no ha finalizado';