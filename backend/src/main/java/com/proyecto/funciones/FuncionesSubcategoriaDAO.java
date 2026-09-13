package com.proyecto.funciones;

import com.proyecto.config.Database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class FuncionesSubcategoriaDAO {

    public Integer obtenerCategoriaPorSubcategoria(
            int idSubcategoria) {

        String sql =
                "{ call fn_obtener_categoria_por_subcategoria(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idSubcategoria);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al obtener la categoria de la subcategoria: "
                            + e.getMessage()
            );
        }

        return null;
    }
}