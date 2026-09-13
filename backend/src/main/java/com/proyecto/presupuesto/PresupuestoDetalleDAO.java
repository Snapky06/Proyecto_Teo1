package com.proyecto.presupuesto;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PresupuestoDetalleDAO {

    public void insertarDetalle(
            int idPresupuesto,
            int idSubcategoria,
            BigDecimal montoMensual,
            String observaciones,
            String creadoPor) {

        String sql =
                "EXECUTE PROCEDURE sp_insertar_presupuesto_detalle("
                        + "?, ?, ?, ?, ?)";

        try (
                Connection conn = Database.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idPresupuesto);
            ps.setInt(2, idSubcategoria);
            ps.setBigDecimal(3, montoMensual);
            ps.setString(4, observaciones);
            ps.setString(5, creadoPor);

            ps.execute();

            System.out.println(
                    "Detalle de presupuesto registrado correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar el detalle: "
                            + e.getMessage()
            );
        }
    }

    public void listarDetalles(int idPresupuesto) {
        String sql =
                "{ call sp_listar_presupuesto_detalles(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idPresupuesto);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("p_id_detalle")
                                    + " - Subcategoria: "
                                    + rs.getInt("p_id_subcategoria")
                                    + " - Monto: "
                                    + rs.getBigDecimal(
                                            "p_monto_mensual")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar los detalles: "
                            + e.getMessage()
            );
        }
    }

    public void consultarDetalle(int idDetalle) {
        String sql =
                "{ call sp_consultar_presupuesto_detalle(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idDetalle);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            "\n--- DETALLES DEL REGISTRO ---"
                    );
                    System.out.println(
                            "ID Presupuesto: "
                                    + rs.getInt("p_id_presupuesto")
                    );
                    System.out.println(
                            "ID Subcategoria: "
                                    + rs.getInt("p_id_subcategoria")
                    );
                    System.out.println(
                            "Monto mensual: "
                                    + rs.getBigDecimal(
                                            "p_monto_mensual")
                    );
                    System.out.println(
                            "Observaciones: "
                                    + rs.getString(
                                            "p_observaciones")
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
                            "--------------------------------"
                    );
                } else {
                    System.out.println(
                            "No se encontro el detalle con el ID: "
                                    + idDetalle
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al consultar el detalle: "
                            + e.getMessage()
            );
        }
    }

    public void actualizarDetalle(
            int idDetalle,
            BigDecimal montoMensual,
            String observaciones,
            String modificadoPor) {

        String sql =
                "{ call sp_actualizar_presupuesto_detalle("
                        + "?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idDetalle);
            cs.setBigDecimal(2, montoMensual);
            cs.setString(3, observaciones);
            cs.setString(4, modificadoPor);

            cs.execute();

            System.out.println(
                    "Detalle actualizado correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al actualizar el detalle: "
                            + e.getMessage()
            );
        }
    }

    public void eliminarDetalle(int idDetalle) {
        String sql =
                "{ call sp_eliminar_presupuesto_detalle(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idDetalle);
            cs.execute();

            System.out.println(
                    "Detalle eliminado correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al eliminar el detalle: "
                            + e.getMessage()
            );
        }
    }
}