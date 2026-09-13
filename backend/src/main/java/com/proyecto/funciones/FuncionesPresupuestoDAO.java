package com.proyecto.funciones;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class FuncionesPresupuestoDAO {

    public BigDecimal calcularPorcentajeEjecutado(
            int idSubcategoria,
            int idPresupuesto,
            short anio,
            short mes) {

        String sql =
                "{ call fn_calcular_porcentaje_ejecutado("
                        + "?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idSubcategoria);
            cs.setInt(2, idPresupuesto);
            cs.setShort(3, anio);
            cs.setShort(4, mes);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(
                            "p_porcentaje"
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al calcular el porcentaje ejecutado: "
                            + e.getMessage()
            );
        }

        return null;
    }

    public BigDecimal obtenerBalanceSubcategoria(
            int idPresupuesto,
            int idSubcategoria,
            short anio,
            short mes) {

        String sql =
                "{ call fn_obtener_balance_subcategoria("
                        + "?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idPresupuesto);
            cs.setInt(2, idSubcategoria);
            cs.setShort(3, anio);
            cs.setShort(4, mes);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(
                            "p_balance"
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al obtener el balance de la "
                            + "subcategoria: "
                            + e.getMessage()
            );
        }

        return null;
    }

    public BigDecimal obtenerTotalCategoriaMes(
            int idCategoria,
            int idPresupuesto,
            short anio,
            short mes) {

        String sql =
                "{ call fn_obtener_total_categoria_mes("
                        + "?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idCategoria);
            cs.setInt(2, idPresupuesto);
            cs.setShort(3, anio);
            cs.setShort(4, mes);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(
                            "p_total_presupuestado"
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al obtener el total presupuestado "
                            + "de la categoria: "
                            + e.getMessage()
            );
        }

        return null;
    }
}