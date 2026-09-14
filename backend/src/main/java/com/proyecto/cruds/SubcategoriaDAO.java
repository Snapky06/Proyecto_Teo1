package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import com.proyecto.BaseDAO;

public class SubcategoriaDAO extends BaseDAO {

    public void insertarSubcategoria(int idCategoria, String nombre, String descripcion, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_subcategoria(?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Subcategoria registrada correctamente.", "Error al registrar", 
            idCategoria, nombre, descripcion, creadoPor);
    }

    public void actualizarSubcategoria(int idSubcategoria, String nombre, String descripcion, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_subcategoria(?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Subcategoria actualizada correctamente.", "Error al actualizar", 
            idSubcategoria, nombre, descripcion, modificadoPor);
    }

    public void eliminarSubcategoria(int idSubcategoria) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_subcategoria(?)";
        ejecutarProcedimiento(sql, "Subcategoria eliminada correctamente.", "Error al eliminar", 
            idSubcategoria);
    }

    public void listarSubcategorias(int idCategoria) {
        String sql = "{ call sp_listar_subcategoria(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idCategoria);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID Subcategoria: " + rs.getInt("p_id_subcategoria"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Activa: " + rs.getBoolean("p_activa"));
                    System.out.println("Es Default: " + rs.getBoolean("p_es_default"));
                    System.out.println("-----------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar las subcategorias: " + e.getMessage());
        }
    }

    public void consultarSubcategoria(int idSubcategoria) {
        String sql = "{ call sp_consultar_subcategoria(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idSubcategoria);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DETALLES DE LA SUBCATEGORIA ---");
                    System.out.println("ID Categoria: " + rs.getInt("p_id_categoria"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Creado por: " + rs.getString("p_creado_por"));
                    System.out.println("Creado en: " + rs.getTimestamp("p_creado_en"));
                    System.out.println("Modificado por: " + rs.getString("p_modificado_por"));
                    System.out.println("Modificado en: " + rs.getTimestamp("p_modificado_en"));
                    System.out.println("-----------------------------------");
                } else {
                    System.out.println("No se encontro la subcategoria.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la subcategoria: " + e.getMessage());
        }
    }
}