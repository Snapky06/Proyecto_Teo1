package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import com.proyecto.BaseDAO;
import java.util.Map;

public class CategoriaDAO extends BaseDAO {

    public void insertarCategoria(int idUsuario, String nombre, String descripcion, String tipo, String icono, String colorHex, short orden, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_categoria(?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Categoria registrada correctamente.", "Error al registrar la categoria", 
            idUsuario, nombre, descripcion, tipo, icono, colorHex, orden, creadoPor);
    }

    public void actualizarCategoria(int idCategoria, String nombre, String descripcion, String tipo, String icono, String colorHex, short orden, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_categoria(?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Categoria actualizada correctamente.", "Error al actualizar la categoria", 
            idCategoria, nombre, descripcion, tipo, icono, colorHex, orden, modificadoPor);
    }

    public void eliminarCategoria(int idCategoria) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_categoria(?)";
        ejecutarProcedimiento(sql, "Categoria eliminada correctamente.", "Error al eliminar la categoria", idCategoria);
    }

    public void listarCategorias(int idUsuario) {
        String sql = "{ call sp_listar_categorias(?) }";

        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idUsuario);
            
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("p_id_categoria"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Tipo: " + rs.getString("p_tipo"));
                    System.out.println("Icono: " + rs.getString("p_icono"));
                    System.out.println("Color: " + rs.getString("p_color_hex"));
                    System.out.println("Orden: " + rs.getShort("p_orden_presentacion"));
                    System.out.println("-------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar las categorias: " + e.getMessage());
        }
    }

    public void consultarCategoria(int idCategoria) {
        String sql = "{ call sp_consultar_categoria(?) }";

        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idCategoria);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DETALLES DE LA CATEGORIA ---");
                    System.out.println("ID Usuario: " + rs.getInt("p_id_usuario"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Tipo: " + rs.getString("p_tipo"));
                    System.out.println("Icono: " + rs.getString("p_icono"));
                    System.out.println("Color: " + rs.getString("p_color_hex"));
                    System.out.println("Orden: " + rs.getShort("p_orden_presentacion"));
                    System.out.println("-------------------------------");
                } else {
                    System.out.println("No se encontro la categoria con el ID: " + idCategoria);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la categoria: " + e.getMessage());
        }
    }


public Map<Integer, String> obtenerCategoriasPorTipo(int idUsuario, String tipo) {
    return ejecutarFuncionDiccionario("{ call sp_listar_categorias_por_tipo(?, ?) }", "Error al obtener categorias", idUsuario, tipo);
}
}