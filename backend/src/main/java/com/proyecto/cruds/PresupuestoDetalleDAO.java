package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.math.BigDecimal;
import com.proyecto.BaseDAO;

public class PresupuestoDetalleDAO extends BaseDAO {

    public void insertarDetalle(int idPresupuesto, int idSubcategoria, BigDecimal montoMensual, String observaciones, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_presupuesto_detalle(?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Detalle de presupuesto registrado correctamente.", "Error al registrar el detalle", 
            idPresupuesto, idSubcategoria, montoMensual, observaciones, creadoPor);
    }

    public void actualizarDetalle(int idDetalle, BigDecimal montoMensual, String observaciones, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_presupuesto_detalle(?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Detalle actualizado correctamente.", "Error al actualizar el detalle", 
            idDetalle, montoMensual, observaciones, modificadoPor);
    }

    public void eliminarDetalle(int idDetalle) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_presupuesto_detalle(?)";
        ejecutarProcedimiento(sql, "Detalle eliminado correctamente.", "Error al eliminar el detalle", idDetalle);
    }

    public void listarDetalles(int idPresupuesto) {
        String sql = "{ call sp_listar_presupuesto_detalles(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idPresupuesto);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID Detalle: " + rs.getInt("p_id_detalle"));
                    System.out.println("ID Subcategoria: " + rs.getInt("p_id_subcategoria"));
                    System.out.println("Monto mensual: " + rs.getBigDecimal("p_monto_mensual"));
                    System.out.println("Observaciones: " + rs.getString("p_observaciones"));
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar los detalles: " + e.getMessage());
        }
    }

    public void consultarDetalle(int idDetalle) {
        String sql = "{ call sp_consultar_presupuesto_detalle(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idDetalle);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DETALLES DEL REGISTRO ---");
                    System.out.println("ID Presupuesto: " + rs.getInt("p_id_presupuesto"));
                    System.out.println("ID Subcategoria: " + rs.getInt("p_id_subcategoria"));
                    System.out.println("Monto mensual: " + rs.getBigDecimal("p_monto_mensual"));
                    System.out.println("Observaciones: " + rs.getString("p_observaciones"));
                    System.out.println("Creado por: " + rs.getString("p_creado_por"));
                    System.out.println("Creado en: " + rs.getTimestamp("p_creado_en"));
                    System.out.println("--------------------------------");
                } else {
                    System.out.println("No se encontro el detalle.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el detalle: " + e.getMessage());
        }
    }
}