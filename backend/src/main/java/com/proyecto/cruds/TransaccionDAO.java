package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.math.BigDecimal;
import com.proyecto.BaseDAO;

public class TransaccionDAO extends BaseDAO {

    public void insertarTransaccion(int idUsuario, int idPresupuesto, short anio, short mes, int idSubcategoria, Integer idObligacion, String tipo, String descripcion, BigDecimal monto, Date fecha, String metodoPago, String numeroFactura, String observaciones, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_transaccion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Transaccion registrada correctamente.", "Error al registrar la transaccion", 
            idUsuario, idPresupuesto, anio, mes, idSubcategoria, idObligacion, tipo, descripcion, monto, fecha, metodoPago, numeroFactura, observaciones, creadoPor);
    }

    public void actualizarTransaccion(int idTransaccion, int idPresupuesto, short anio, short mes, int idSubcategoria, Integer idObligacion, String tipo, String descripcion, BigDecimal monto, Date fecha, String metodoPago, String numeroFactura, String observaciones, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_transaccion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Transaccion actualizada correctamente.", "Error al actualizar la transaccion", 
            idTransaccion, idPresupuesto, anio, mes, idSubcategoria, idObligacion, tipo, descripcion, monto, fecha, metodoPago, numeroFactura, observaciones, modificadoPor);
    }

    public void eliminarTransaccion(int idTransaccion) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_transaccion(?)";
        ejecutarProcedimiento(sql, "Transaccion eliminada correctamente.", "Error al eliminar la transaccion", idTransaccion);
    }

    public void listarTransacciones(int idUsuario) {
        String sql = "{ call sp_listar_transacciones(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID Transaccion: " + rs.getInt("p_id_transaccion"));
                    System.out.println("Tipo: " + rs.getString("p_tipo"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Monto: " + rs.getBigDecimal("p_monto"));
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar las transacciones: " + e.getMessage());
        }
    }

    public void consultarTransaccion(int idTransaccion) {
        String sql = "{ call sp_consultar_transaccion(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idTransaccion);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DATOS DE LA TRANSACCION ---");
                    System.out.println("ID Usuario: " + rs.getInt("p_id_usuario"));
                    System.out.println("ID Presupuesto: " + rs.getInt("p_id_presupuesto"));
                    System.out.println("Anio: " + rs.getShort("p_anio"));
                    System.out.println("Mes: " + rs.getShort("p_mes"));
                    System.out.println("ID Subcategoria: " + rs.getInt("p_id_subcategoria"));
                    System.out.println("ID Obligacion: " + rs.getObject("p_id_obligacion"));
                    System.out.println("Tipo: " + rs.getString("p_tipo"));
                    System.out.println("Descripcion: " + rs.getString("p_descripcion"));
                    System.out.println("Monto: " + rs.getBigDecimal("p_monto"));
                    System.out.println("Fecha: " + rs.getDate("p_fecha"));
                    System.out.println("Metodo de pago: " + rs.getString("p_metodo_pago"));
                    System.out.println("Numero de factura: " + rs.getString("p_numero_factura"));
                    System.out.println("Observaciones: " + rs.getString("p_observaciones"));
                    System.out.println("--------------------------------");
                } else {
                    System.out.println("No se encontro la transaccion.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la transaccion: " + e.getMessage());
        }
    }
}