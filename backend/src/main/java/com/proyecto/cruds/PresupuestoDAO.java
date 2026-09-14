package com.proyecto.cruds;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proyecto.config.Database;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.proyecto.BaseDAO;

public class PresupuestoDAO extends BaseDAO {

    public void insertarPresupuesto(int idUsuario, String nombre, short anioInicio, short mesInicio, short anioFin, short mesFin, BigDecimal ingresos, BigDecimal gastos, BigDecimal ahorro, String estado, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_presupuesto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Presupuesto registrado correctamente.", "Error al registrar el presupuesto", 
            idUsuario, nombre, anioInicio, mesInicio, anioFin, mesFin, ingresos, gastos, ahorro, estado, creadoPor);
    }

    public void actualizarPresupuesto(int idPresupuesto, String nombre, short anioInicio, short mesInicio, short anioFin, short mesFin, BigDecimal ingresos, BigDecimal gastos, BigDecimal ahorro, String estado, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_presupuesto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Presupuesto actualizado correctamente.", "Error al actualizar el presupuesto", 
            idPresupuesto, nombre, anioInicio, mesInicio, anioFin, mesFin, ingresos, gastos, ahorro, estado, modificadoPor);
    }

    public void eliminarPresupuesto(int idPresupuesto) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_presupuesto(?)";
        ejecutarProcedimiento(sql, "Presupuesto eliminado correctamente.", "Error al eliminar el presupuesto", idPresupuesto);
    }

    public void listarPresupuestos(int idUsuario) {
        String sql = "{ call sp_listar_presupuestos(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID Presupuesto: " + rs.getInt("p_id_presupuesto"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Anio/Mes Inicio: " + rs.getShort("p_anio_inicio") + "-" + rs.getShort("p_mes_inicio"));
                    System.out.println("Anio/Mes Fin: " + rs.getShort("p_anio_fin") + "-" + rs.getShort("p_mes_fin"));
                    System.out.println("Estado: " + rs.getString("p_estado"));
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar los presupuestos: " + e.getMessage());
        }
    }

    public void consultarPresupuesto(int idPresupuesto) {
        String sql = "{ call sp_consultar_presupuesto(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idPresupuesto);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DETALLES DEL PRESUPUESTO ---");
                    System.out.println("ID Usuario: " + rs.getInt("p_id_usuario"));
                    System.out.println("Nombre: " + rs.getString("p_nombre"));
                    System.out.println("Inicio: " + rs.getShort("p_anio_inicio") + "-" + rs.getShort("p_mes_inicio"));
                    System.out.println("Fin: " + rs.getShort("p_anio_fin") + "-" + rs.getShort("p_mes_fin"));
                    System.out.println("Total Ingresos: " + rs.getBigDecimal("p_total_ingresos_planificados"));
                    System.out.println("Total Gastos: " + rs.getBigDecimal("p_total_gastos_planificados"));
                    System.out.println("Total Ahorros: " + rs.getBigDecimal("p_total_ahorro_planificado"));
                    System.out.println("Estado: " + rs.getString("p_estado"));
                    System.out.println("--------------------------------");
                } else {
                    System.out.println("No se encontro el presupuesto con ID: " + idPresupuesto);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el presupuesto: " + e.getMessage());
        }
    }

    public void crearPresupuestoCompleto(int idUsuario, String nombre, short anioInicio, short mesInicio, short anioFin, short mesFin, String json, String creadoPor) {
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<PresupuestoDetalleJson>>(){}.getType();
        
        try (Connection conn = Database.obtenerConexion()) {
            conn.setAutoCommit(false);

            try {
                List<PresupuestoDetalleJson> detalles = gson.fromJson(json, tipoLista);
                if (detalles == null || detalles.isEmpty()) {
                    throw new IllegalArgumentException("La lista de subcategorias no puede estar vacia.");
                }

                Set<Integer> subcategoriasRegistradas = new HashSet<>();
                BigDecimal totalGastos = BigDecimal.ZERO;
                BigDecimal totalIngresos = BigDecimal.ZERO;
                BigDecimal totalAhorros = BigDecimal.ZERO;

                String sqlTipoSubcategoria = "{ call sp_obtener_tipo_subcategoria(?) }";
                try (CallableStatement csTipo = conn.prepareCall(sqlTipoSubcategoria)) {
                    for (PresupuestoDetalleJson detalle : detalles) {
                        int idSub = detalle.getId_subcategoria();
                        BigDecimal monto = detalle.getMonto_mensual();

                        if (monto == null || monto.signum() <= 0) {
                            throw new IllegalArgumentException("El JSON contiene un detalle invalido.");
                        }

                        if (!subcategoriasRegistradas.add(idSub)) {
                            throw new IllegalArgumentException("La subcategoria no puede repetirse.");
                        }

                        csTipo.setInt(1, idSub);
                        try (ResultSet rs = csTipo.executeQuery()) {
                            if (rs.next()) {
                                String tipo = rs.getString("p_tipo").trim().toUpperCase();
                                if (tipo.equals("INGRESO")) {
                                    totalIngresos = totalIngresos.add(monto);
                                } else if (tipo.equals("GASTO")) {
                                    totalGastos = totalGastos.add(monto);
                                } else if (tipo.equals("AHORRO")) {
                                    totalAhorros = totalAhorros.add(monto);
                                } else {
                                    throw new IllegalArgumentException("Tipo de categoria invalido.");
                                }
                            } else {
                                throw new IllegalArgumentException("La subcategoria no existe.");
                            }
                        }
                    }
                }

                String sqlPresupuesto = "EXECUTE PROCEDURE sp_insertar_presupuesto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                int idPresupuestoInsertado = -1;
                
                try (PreparedStatement ps = conn.prepareStatement(sqlPresupuesto)) {
                    ps.setInt(1, idUsuario);
                    ps.setString(2, nombre);
                    ps.setShort(3, anioInicio);
                    ps.setShort(4, mesInicio);
                    ps.setShort(5, anioFin);
                    ps.setShort(6, mesFin);
                    ps.setBigDecimal(7, totalIngresos);
                    ps.setBigDecimal(8, totalGastos);
                    ps.setBigDecimal(9, totalAhorros);
                    ps.setString(10, "ACTIVO");
                    ps.setString(11, creadoPor);
                    
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            idPresupuestoInsertado = rs.getInt("p_id_presupuesto");
                        }
                    }
                }

                if (idPresupuestoInsertado == -1) {
                    throw new IllegalStateException("No se pudo obtener el ID del presupuesto.");
                }

                String sqlDetalle = "EXECUTE PROCEDURE sp_insertar_presupuesto_detalle(?, ?, ?, ?, ?)";
                try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
                    for (PresupuestoDetalleJson detalle : detalles) {
                        psDetalle.setInt(1, idPresupuestoInsertado);
                        psDetalle.setInt(2, detalle.getId_subcategoria());
                        psDetalle.setBigDecimal(3, detalle.getMonto_mensual());
                        psDetalle.setNull(4, java.sql.Types.VARCHAR);
                        psDetalle.setString(5, creadoPor);
                        psDetalle.execute();
                    }
                }

                conn.commit();
                System.out.println("Presupuesto completo registrado con ID: " + idPresupuestoInsertado);

            } catch (Exception e) {
                conn.rollback();
                System.out.println("Error al crear el presupuesto completo: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            System.out.println("Error de base de datos: " + e.getMessage());
        }
    }
}