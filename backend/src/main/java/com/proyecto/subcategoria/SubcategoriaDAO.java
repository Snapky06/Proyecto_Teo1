package com.proyecto.subcategoria;

import com.proyecto.config.Database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SubcategoriaDAO {

    public void insertarSubcategoria(
            int idCategoria,
            String nombre,
            String descripcion,
            String creadoPor) {

        String sql =
                "EXECUTE PROCEDURE sp_insertar_subcategoria(?, ?, ?, ?)";

        try (
                Connection conn = Database.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idCategoria);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setString(4, creadoPor);

            ps.execute();

            System.out.println(
                    "Subcategoria registrada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar la subcategoria: "
                            + e.getMessage()
            );
        }
    }

    public void consultarSubcategoria(int idSubcategoria) {
        String sql = "{ call sp_consultar_subcategoria(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idSubcategoria);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            "\n--- DETALLES DE LA SUBCATEGORIA ---"
                    );
                    System.out.println(
                            "ID Categoria: "
                                    + rs.getInt("p_id_categoria")
                    );
                    System.out.println(
                            "Nombre: "
                                    + rs.getString("p_nombre")
                    );
                    System.out.println(
                            "Descripcion: "
                                    + rs.getString("p_descripcion")
                    );
                    System.out.println(
                            "Creado por: "
                                    + rs.getString("p_creado_por")
                    );
                    System.out.println(
                            "Creado en: "
                                    + rs.getTimestamp("p_creado_en")
                    );
                    System.out.println(
                            "Modificado por: "
                                    + rs.getString("p_modificado_por")
                    );
                    System.out.println(
                            "Modificado en: "
                                    + rs.getTimestamp("p_modificado_en")
                    );
                    System.out.println(
                            "-----------------------------------"
                    );
                } else {
                    System.out.println(
                            "No se encontro la subcategoria con el ID: "
                                    + idSubcategoria
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al consultar la subcategoria: "
                            + e.getMessage()
            );
        }
    }

    public void listarSubcategorias(int idCategoria) {
        String sql = "{ call sp_listar_subcategoria(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idCategoria);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("p_id_subcategoria")
                                    + " - "
                                    + rs.getString("p_nombre")
                                    + " - "
                                    + rs.getString("p_descripcion")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar las subcategorias: "
                            + e.getMessage()
            );
        }
    }

    public void actualizarSubcategoria(
            int idSubcategoria,
            String nombre,
            String descripcion,
            String modificadoPor) {

        String sql =
                "{ call sp_actualizar_subcategoria(?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idSubcategoria);
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setString(4, modificadoPor);

            cs.execute();

            System.out.println(
                    "Subcategoria actualizada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al actualizar la subcategoria: "
                            + e.getMessage()
            );
        }
    }

    public void eliminarSubcategoria(int idSubcategoria) {
        String sql = "{ call sp_eliminar_subcategoria(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idSubcategoria);

            cs.execute();

            System.out.println(
                    "Subcategoria eliminada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al eliminar la subcategoria: "
                            + e.getMessage()
            );
        }
    }
}