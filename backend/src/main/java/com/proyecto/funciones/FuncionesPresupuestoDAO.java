package com.proyecto.funciones;

import com.proyecto.BaseDAO;
import java.math.BigDecimal;
import java.sql.Date;

public class FuncionesPresupuestoDAO extends BaseDAO {

    public BigDecimal calcularPorcentajeEjecutado(int idSubcategoria, int idPresupuesto, short anio, short mes) {
        String sql = "{ call fn_calcular_porcentaje_ejecutado(?, ?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al calcular el porcentaje ejecutado", idSubcategoria, idPresupuesto, anio, mes);
    }

    public BigDecimal obtenerBalanceSubcategoria(int idPresupuesto, int idSubcategoria, short anio, short mes) {
        String sql = "{ call fn_obtener_balance_subcategoria(?, ?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al obtener el balance de la subcategoria", idPresupuesto, idSubcategoria, anio, mes);
    }

    public BigDecimal obtenerTotalCategoriaMes(int idCategoria, int idPresupuesto, short anio, short mes) {
        String sql = "{ call fn_obtener_total_categoria_mes(?, ?, ?, ?) }";
        return ejecutarFuncionDecimal(sql, "Error al obtener el total de la categoria", idCategoria, idPresupuesto, anio, mes);
    }

    public Boolean validarVigenciaPresupuesto(Date fecha, int idPresupuesto) {
        String sql = "{ call fn_validar_vigencia_presupuesto(?, ?) }";
        return ejecutarFuncionBooleana(sql, "Error al validar la vigencia del presupuesto", fecha, idPresupuesto);
    }
}