package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ObligacionFijaDAO {

    public void insertarObligacion(
            int idUsuario,
            int idSubcategoria,
            String nombre,
            String descripcion,
            BigDecimal montoFijoMensual,
            short diaVencimiento,
            boolean vigente,
            Date fechaInicio,
            Date fechaFin,
            String creadoPor) {

        String sql =
                "EXECUTE PROCEDURE sp_insertar_obligacion_fija("
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = Database.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idSubcategoria);
            ps.setString(3, nombre);
            ps.setString(4, descripcion);
            ps.setBigDecimal(5, montoFijoMensual);
            ps.setShort(6, diaVencimiento);
            ps.setBoolean(7, vigente);
            ps.setDate(8, fechaInicio);

            if (fechaFin == null) {
                ps.setNull(9, java.sql.Types.DATE);
            } else {
                ps.setDate(9, fechaFin);
            }

            ps.setString(10, creadoPor);
            ps.execute();

            System.out.println(
                    "Obligacion fija registrada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar la obligacion fija: "
                            + e.getMessage()
            );
        }
    }

    public void consultarObligacion(int idObligacion) {
        String sql =
                "{ call sp_consultar_obligacion_fija(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idObligacion);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            "\n--- DATOS DE LA OBLIGACION FIJA ---"
                    );
                    System.out.println(
                            "ID usuario: "
                                    + rs.getInt("p_id_usuario")
                    );
                    System.out.println(
                            "ID subcategoria: "
                                    + rs.getInt("p_id_subcategoria")
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
                            "Monto mensual: "
                                    + rs.getBigDecimal(
                                            "p_monto_fijo_mensual")
                    );
                    System.out.println(
                            "Dia de vencimiento: "
                                    + rs.getShort("p_dia_vencimiento")
                    );
                    System.out.println(
                            "Vigente: "
                                    + rs.getBoolean("p_vigente")
                    );
                    System.out.println(
                            "Fecha de inicio: "
                                    + rs.getDate("p_fecha_inicio")
                    );
                    System.out.println(
                            "Fecha de fin: "
                                    + rs.getDate("p_fecha_fin")
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
                            "No se encontro la obligacion fija."
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al consultar la obligacion fija: "
                            + e.getMessage()
            );
        }
    }

    public void listarObligaciones(int idUsuario) {
        String sql =
                "{ call sp_listar_obligaciones_fijas(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idUsuario);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("p_id_obligacion")
                                    + " - "
                                    + rs.getString("p_nombre")
                                    + " - Monto: "
                                    + rs.getBigDecimal(
                                            "p_monto_fijo_mensual")
                                    + " - Vigente: "
                                    + rs.getBoolean("p_vigente")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar las obligaciones fijas: "
                            + e.getMessage()
            );
        }
    }

    public void actualizarObligacion(
            int idObligacion,
            int idSubcategoria,
            String nombre,
            String descripcion,
            BigDecimal montoFijoMensual,
            short diaVencimiento,
            boolean vigente,
            Date fechaInicio,
            Date fechaFin,
            String modificadoPor) {

        String sql =
                "{ call sp_actualizar_obligacion_fija("
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idObligacion);
            cs.setInt(2, idSubcategoria);
            cs.setString(3, nombre);
            cs.setString(4, descripcion);
            cs.setBigDecimal(5, montoFijoMensual);
            cs.setShort(6, diaVencimiento);
            cs.setBoolean(7, vigente);
            cs.setDate(8, fechaInicio);

            if (fechaFin == null) {
                cs.setNull(9, java.sql.Types.DATE);
            } else {
                cs.setDate(9, fechaFin);
            }

            cs.setString(10, modificadoPor);
            cs.execute();

            System.out.println(
                    "Obligacion fija actualizada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al actualizar la obligacion fija: "
                            + e.getMessage()
            );
        }
    }

    public void eliminarObligacion(int idObligacion) {
        String sql =
                "{ call sp_eliminar_obligacion_fija(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idObligacion);
            cs.execute();

            System.out.println(
                    "Obligacion fija eliminada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al eliminar la obligacion fija: "
                            + e.getMessage()
            );
        }
    }
}