package com.proyecto.funciones;

import com.proyecto.BaseDAO;
import java.math.BigDecimal;

public class FuncionesTransaccionDAO extends BaseDAO {

    public BigDecimal calcularMontoEjecutado(int idSubcategoria, short anio, short mes) {
        String sql = "{ call fn_calcular_monto_ejecutado(?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al calcular el monto ejecutado", idSubcategoria, anio, mes);
    }

    public BigDecimal obtenerTotalEjecutadoCategoriaMes(int idCategoria, short anio, short mes) {
        String sql = "{ call fn_obtener_total_ejecutado_categoria_mes(?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al calcular el total ejecutado", idCategoria, anio, mes);
    }

    public BigDecimal calcularProyeccionGastoMensual(int idSubcategoria, short anio, short mes) {
        String sql = "{ call fn_calcular_proyeccion_gasto_mensual(?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al calcular la proyeccion de gasto", idSubcategoria, anio, mes);
    }

    public BigDecimal obtenerPromedioGastoSubcategoria(int idUsuario, int idSubcategoria, int cantidadMeses) {
        String sql = "{ call fn_obtener_promedio_gasto_subcategoria(?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al obtener el promedio de gasto", idUsuario, idSubcategoria, cantidadMeses);
    }
}