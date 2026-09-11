package com.proyecto.categoria;

import com.proyecto.config.Database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.PreparedStatement;

public class CategoriaDAO {

    public void insertarCategoria(
        int idUsuario,
        String nombre,
        String descripcion,
        String tipo,
        String icono,
        String colorHex,
        short ordenPresentacion,
        String creadoPor) {

    String sql = "EXECUTE PROCEDURE sp_insertar_categoria(?, ?, ?, ?, ?, ?, ?, ?)";

    try (
            Connection conn = Database.obtenerConexion();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setInt(1, idUsuario);
        ps.setString(2, nombre);
        ps.setString(3, descripcion);
        ps.setString(4, tipo);
        ps.setString(5, icono);
        ps.setString(6, colorHex);
        ps.setShort(7, ordenPresentacion);
        ps.setString(8, creadoPor);

        ps.execute();

        System.out.println("Categoria registrada correctamente.");

    } catch (Exception e) {
        System.out.println(
                "Error al registrar la categoria: " + e.getMessage()
        );
    }
}

    public void listarCategorias(int idUsuario) {
        String sql = "{ call sp_listar_categorias(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idUsuario);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("p_id_categoria")
                                    + " - "
                                    + rs.getString("p_nombre")
                                    + " - "
                                    + rs.getString("p_tipo")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error al listar las categorias: "
                    + e.getMessage());
        }
    }

    public void consultarCategoria(int idCategoria) {
        String sql = "{ call sp_consultar_categoria(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idCategoria);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n--- DETALLES DE LA CATEGORIA ---");
                    System.out.println(
                            "ID Usuario: "
                                    + rs.getInt("p_id_usuario")
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
                            "Tipo: "
                                    + rs.getString("p_tipo")
                    );
                    System.out.println(
                            "Icono: "
                                    + rs.getString("p_icono")
                    );
                    System.out.println(
                            "Color: "
                                    + rs.getString("p_color_hex")
                    );
                    System.out.println(
                            "Orden: "
                                    + rs.getShort("p_orden_presentacion")
                    );
                    System.out.println("-------------------------------");
                } else {
                    System.out.println(
                            "No se encontro la categoria con el ID: "
                                    + idCategoria
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error al consultar la categoria: "
                    + e.getMessage());
        }
    }

    public void actualizarCategoria(
            int idCategoria,
            String nombre,
            String descripcion,
            String tipo,
            String icono,
            String colorHex,
            short ordenPresentacion,
            String modificadoPor) {

        String sql = "{ call sp_actualizar_categoria(?, ?, ?, ?, ?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idCategoria);
            cs.setString(2, nombre);
            cs.setString(3, descripcion);
            cs.setString(4, tipo);
            cs.setString(5, icono);
            cs.setString(6, colorHex);
            cs.setShort(7, ordenPresentacion);
            cs.setString(8, modificadoPor);

            cs.execute();

            System.out.println("Categoria actualizada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al actualizar la categoria: "
                    + e.getMessage());
        }
    }

    public void eliminarCategoria(int idCategoria) {
        String sql = "{ call sp_eliminar_categoria(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idCategoria);
            cs.execute();

            System.out.println("Categoria eliminada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al eliminar la categoria: "
                    + e.getMessage());
        }
    }
}