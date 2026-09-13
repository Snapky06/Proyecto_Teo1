package com.proyecto.funciones;

import com.proyecto.config.Database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class FuncionesObligacionDAO {

    public Integer diasHastaVencimiento(int idObligacion) {

        String sql =
                "{ call fn_dias_hasta_vencimiento(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idObligacion);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    int indiceColumna = 1;
                    return rs.getInt(indiceColumna);
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al calcular los dias hasta el vencimiento: "
                            + e.getMessage()
            );
        }

        return null;
    }
}