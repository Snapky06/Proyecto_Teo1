package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.math.BigDecimal;
import com.proyecto.BaseDAO;

public class ObligacionFijaDAO extends BaseDAO {

    public void insertarObligacion(int idUsuario, int idSubcategoria, String nombre, String descripcion, BigDecimal montoFijoMensual, short diaVencimiento, boolean vigente, Date fechaInicio, Date fechaFin, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_obligacion_fija(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Obligacion fija registrada correctamente.", "Error al registrar la obligacion fija", 
            idUsuario, idSubcategoria, nombre, descripcion, montoFijoMensual, diaVencimiento, vigente, fechaInicio, fechaFin, creadoPor);
    }

    public void actualizarObligacion(int idObligacion, int idSubcategoria, String nombre, String descripcion, BigDecimal montoFijoMensual, short diaVencimiento, boolean vigente, Date fechaInicio, Date fechaFin, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_obligacion_fija(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Obligacion fija actualizada correctamente.", "Error al actualizar la obligacion fija", 
            idObligacion, idSubcategoria, nombre, descripcion, montoFijoMensual, diaVencimiento, vigente, fechaInicio, fechaFin, modificadoPor);
    }

    public void eliminarObligacion(int idObligacion) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_obligacion_fija(?)";
        ejecutarProcedimiento(sql, "Obligacion fija eliminada correctamente.", "Error al eliminar la obligacion fija", idObligacion);
    }

    public void listarObligaciones(int idUsuario) {
        String sql = "{ call sp_listar_obligaciones_fijas(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID Obligacion: " + rs.getInt("p_id_obligacion"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Monto: " + rs.getBigDecimal("p_monto_fijo_mensual"));
                    System.out.println("Vigente: " + rs.getBoolean("p_vigente"));
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar las obligaciones fijas: " + e.getMessage());
        }
    }

    public void consultarObligacion(int idObligacion) {
        String sql = "{ call sp_consultar_obligacion_fija(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idObligacion);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DATOS DE LA OBLIGACION FIJA ---");
                    System.out.println("ID Usuario: " + rs.getInt("p_id_usuario"));
                    System.out.println("ID Subcategoria: " + rs.getInt("p_id_subcategoria"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Monto mensual: " + rs.getBigDecimal("p_monto_fijo_mensual"));
                    System.out.println("Dia de vencimiento: " + rs.getShort("p_dia_vencimiento"));
                    System.out.println("Vigente: " + rs.getBoolean("p_vigente"));
                    System.out.println("Fecha de inicio: " + rs.getDate("p_fecha_inicio"));
                    System.out.println("Fecha de fin: " + rs.getDate("p_fecha_fin"));
                    System.out.println("--------------------------------");
                } else {
                    System.out.println("No se encontro la obligacion fija.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la obligacion fija: " + e.getMessage());
        }
    }
}