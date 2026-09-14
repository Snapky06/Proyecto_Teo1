package com.proyecto.funciones;

import com.proyecto.BaseDAO;
import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;

public class ProcedimientosNegocioDAO extends BaseDAO {

    public void calcularBalanceMensual(int idUsuario, int idPresupuesto, short anio, short mes) {
        String sql = "{ call sp_calcular_balance_mensual(?, ?, ?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idUsuario, idPresupuesto, anio, mes);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n--- BALANCE MENSUAL ---");
                    System.out.println("Total ingresos: " + rs.getBigDecimal("total_ingresos"));
                    System.out.println("Total gastos: " + rs.getBigDecimal("total_gastos"));
                    System.out.println("Total ahorros: " + rs.getBigDecimal("total_ahorros"));
                    System.out.println("Balance final: " + rs.getBigDecimal("balance_final"));
                } else {
                    System.out.println("No se encontro informacion para ese periodo.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al calcular el balance mensual: " + e.getMessage());
        }
    }

    public void calcularMontoEjecutadoMes(int idSubcategoria, int idPresupuesto, short anio, short mes) {
        String sql = "{ call sp_calcular_monto_ejecutado_mes(?, ?, ?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idSubcategoria, idPresupuesto, anio, mes);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Monto ejecutado: " + rs.getBigDecimal("monto_ejecutado"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al calcular el monto ejecutado: " + e.getMessage());
        }
    }

    public void calcularPorcentajeEjecucionMes(int idSubcategoria, int idPresupuesto, short anio, short mes) {
        String sql = "{ call sp_calcular_porcentaje_ejecucion_mes(?, ?, ?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idSubcategoria, idPresupuesto, anio, mes);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Porcentaje de ejecucion: " + rs.getBigDecimal("porcentaje_ejecucion") + "%");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al calcular el porcentaje: " + e.getMessage());
        }
    }

    public void obtenerResumenCategoriaMes(int idCategoria, int idPresupuesto, short anio, short mes) {
        String sql = "{ call sp_obtener_resumen_categoria_mes(?, ?, ?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idCategoria, idPresupuesto, anio, mes);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n--- RESUMEN DE CATEGORIA ---");
                    System.out.println("Monto presupuestado: " + rs.getBigDecimal("monto_presupuestado"));
                    System.out.println("Monto ejecutado: " + rs.getBigDecimal("monto_ejecutado"));
                    System.out.println("Porcentaje de ejecucion: " + rs.getBigDecimal("porcentaje_ejecucion") + "%");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener el resumen de categoria: " + e.getMessage());
        }
    }

    public void procesarObligacionesMes(int idUsuario, short anio, short mes, int idPresupuesto) {
        String sql = "{ call sp_procesar_obligaciones_mes(?, ?, ?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idUsuario, anio, mes, idPresupuesto);
            
            try (ResultSet rs = cs.executeQuery()) {
                boolean encontro = false;
                while (rs.next()) {
                    encontro = true;
                    System.out.println("\nObligacion: " + rs.getString("p_nombre"));
                    System.out.println("Monto mensual: " + rs.getBigDecimal("p_monto_fijo_mensual"));
                    System.out.println("Dia de vencimiento: " + rs.getShort("p_dia_vencimiento"));
                    System.out.println("Fecha de vencimiento: " + rs.getDate("p_fecha_vencimiento"));
                    System.out.println("Estado: " + rs.getString("p_estado_pago"));
                    System.out.println("Dias hasta vencimiento: " + rs.getInt("p_dias_hasta_vencimiento"));
                    System.out.println("Ultimo pago: " + rs.getDate("p_fecha_ultimo_pago"));
                    System.out.println("Alerta: " + rs.getString("p_alerta"));
                }
                if (!encontro) {
                    System.out.println("No hay obligaciones activas para ese periodo.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar las obligaciones: " + e.getMessage());
        }
    }

    public void cerrarPresupuesto(int idPresupuesto, String modificadoPor) {
        String sql = "{ call sp_cerrar_presupuesto(?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idPresupuesto, modificadoPor);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nPresupuesto cerrado correctamente.");
                    System.out.println("ID: " + rs.getInt("p_id_presupuesto_resultado"));
                    System.out.println("Estado: " + rs.getString("p_estado"));
                    System.out.println("Total presupuestado: " + rs.getBigDecimal("p_total_presupuestado"));
                    System.out.println("Total ejecutado: " + rs.getBigDecimal("p_total_ejecutado"));
                    System.out.println("Porcentaje de ejecucion: " + rs.getBigDecimal("p_porcentaje_ejecucion") + "%");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar el presupuesto: " + e.getMessage());
        }
    }

    public void registrarTransaccionCompleta(int idUsuario, int idPresupuesto, short anio, short mes, int idSubcategoria, String tipo, String descripcion, BigDecimal monto, Date fecha, String metodoPago, String creadoPor) {
        String sql = "{ call sp_registrar_transaccion_completa(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, idUsuario, idPresupuesto, anio, mes, idSubcategoria, tipo, descripcion, monto, fecha, metodoPago, creadoPor);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Transaccion registrada con ID: " + rs.getInt("p_id_transaccion"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al registrar la transaccion completa: " + e.getMessage());
        }
    }
}