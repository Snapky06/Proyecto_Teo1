package com.proyecto.funciones;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class FuncionesTransaccionDAO {

    public BigDecimal calcularMontoEjecutado(
            int idSubcategoria,
            short anio,
            short mes) {

        String sql =
                "{ call fn_calcular_monto_ejecutado(?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idSubcategoria);
            cs.setShort(2, anio);
            cs.setShort(3, mes);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(
                            "p_monto_ejecutado"
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al calcular el monto ejecutado: "
                            + e.getMessage()
            );
        }

        return null;
    }

    public BigDecimal obtenerTotalEjecutadoCategoriaMes(
            int idCategoria,
            short anio,
            short mes) {

        String sql =
                "{ call fn_obtener_total_ejecutado_categoria_mes("
                        + "?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idCategoria);
            cs.setShort(2, anio);
            cs.setShort(3, mes);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(
                            "p_total_ejecutado"
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al calcular el total ejecutado "
                            + "de la categoria: "
                            + e.getMessage()
            );
        }

        return null;
    }
}