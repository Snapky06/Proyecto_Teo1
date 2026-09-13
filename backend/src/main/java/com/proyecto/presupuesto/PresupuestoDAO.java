package com.proyecto.presupuesto;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PresupuestoDAO {

    public void insertarPresupuesto(
            int idUsuario,
            String nombre,
            short anioInicio,
            short mesInicio,
            short anioFin,
            short mesFin,
            BigDecimal totalIngresos,
            BigDecimal totalGastos,
            BigDecimal totalAhorro,
            String estado,
            String creadoPor) {

        String sql =
                "EXECUTE PROCEDURE sp_insertar_presupuesto("
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = Database.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idUsuario);
            ps.setString(2, nombre);
            ps.setShort(3, anioInicio);
            ps.setShort(4, mesInicio);
            ps.setShort(5, anioFin);
            ps.setShort(6, mesFin);
            ps.setBigDecimal(7, totalIngresos);
            ps.setBigDecimal(8, totalGastos);
            ps.setBigDecimal(9, totalAhorro);
            ps.setString(10, estado);
            ps.setString(11, creadoPor);

            ps.execute();

            System.out.println(
                    "Presupuesto registrado correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar el presupuesto: "
                            + e.getMessage()
            );
        }
    }

    public void listarPresupuestos(int idUsuario) {
        String sql = "{ call sp_listar_presupuestos(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idUsuario);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("p_id_presupuesto")
                                    + " - "
                                    + rs.getString("p_nombre")
                                    + " - Estado: "
                                    + rs.getString("p_estado")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar los presupuestos: "
                            + e.getMessage()
            );
        }
    }

    public void consultarPresupuesto(int idPresupuesto) {
        String sql = "{ call sp_consultar_presupuesto(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idPresupuesto);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            "\n--- DETALLES DEL PRESUPUESTO ---"
                    );
                    System.out.println(
                            "ID Usuario: "
                                    + rs.getInt("p_id_usuario")
                    );
                    System.out.println(
                            "Nombre: "
                                    + rs.getString("p_nombre")
                    );
                    System.out.println(
                            "Anio inicio: "
                                    + rs.getShort("p_anio_inicio")
                    );
                    System.out.println(
                            "Mes inicio: "
                                    + rs.getShort("p_mes_inicio")
                    );
                    System.out.println(
                            "Anio fin: "
                                    + rs.getShort("p_anio_fin")
                    );
                    System.out.println(
                            "Mes fin: "
                                    + rs.getShort("p_mes_fin")
                    );
                    System.out.println(
                            "Total ingresos: "
                                    + rs.getBigDecimal(
                                            "p_total_ingresos_planificados")
                    );
                    System.out.println(
                            "Total gastos: "
                                    + rs.getBigDecimal(
                                            "p_total_gastos_planificados")
                    );
                    System.out.println(
                            "Total ahorro: "
                                    + rs.getBigDecimal(
                                            "p_total_ahorro_planificado")
                    );
                    System.out.println(
                            "Estado: "
                                    + rs.getString("p_estado")
                    );
                    System.out.println(
                            "Fecha de creacion: "
                                    + rs.getTimestamp(
                                            "p_fecha_hora_creacion")
                    );
                    System.out.println(
                            "--------------------------------"
                    );
                } else {
                    System.out.println(
                            "No se encontro el presupuesto con el ID: "
                                    + idPresupuesto
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al consultar el presupuesto: "
                            + e.getMessage()
            );
        }
    }

    public void actualizarPresupuesto(
            int idPresupuesto,
            String nombre,
            short anioInicio,
            short mesInicio,
            short anioFin,
            short mesFin,
            BigDecimal totalIngresos,
            BigDecimal totalGastos,
            BigDecimal totalAhorro,
            String estado,
            String modificadoPor) {

        String sql =
                "{ call sp_actualizar_presupuesto("
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idPresupuesto);
            cs.setString(2, nombre);
            cs.setShort(3, anioInicio);
            cs.setShort(4, mesInicio);
            cs.setShort(5, anioFin);
            cs.setShort(6, mesFin);
            cs.setBigDecimal(7, totalIngresos);
            cs.setBigDecimal(8, totalGastos);
            cs.setBigDecimal(9, totalAhorro);
            cs.setString(10, estado);
            cs.setString(11, modificadoPor);

            cs.execute();

            System.out.println(
                    "Presupuesto actualizado correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al actualizar el presupuesto: "
                            + e.getMessage()
            );
        }
    }

    public void eliminarPresupuesto(int idPresupuesto) {
        String sql = "{ call sp_eliminar_presupuesto(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idPresupuesto);
            cs.execute();

            System.out.println(
                    "Presupuesto eliminado correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al eliminar el presupuesto: "
                            + e.getMessage()
            );
        }
    }
}