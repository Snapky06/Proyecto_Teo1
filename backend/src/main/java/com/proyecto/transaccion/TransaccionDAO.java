package com.proyecto.transaccion;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransaccionDAO {

    public void insertarTransaccion(
            int idUsuario,
            int idPresupuesto,
            short anio,
            short mes,
            int idSubcategoria,
            Integer idObligacion,
            String tipo,
            String descripcion,
            BigDecimal monto,
            Date fecha,
            String metodoPago,
            String numeroFactura,
            String observaciones,
            String creadoPor) {

        String sql =
                "EXECUTE PROCEDURE sp_insertar_transaccion("
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = Database.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idPresupuesto);
            ps.setShort(3, anio);
            ps.setShort(4, mes);
            ps.setInt(5, idSubcategoria);

            if (idObligacion == null) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, idObligacion);
            }

            ps.setString(7, tipo);
            ps.setString(8, descripcion);
            ps.setBigDecimal(9, monto);
            ps.setDate(10, fecha);
            ps.setString(11, metodoPago);

            if (numeroFactura == null || numeroFactura.isBlank()) {
                ps.setNull(12, java.sql.Types.VARCHAR);
            } else {
                ps.setString(12, numeroFactura);
            }

            if (observaciones == null || observaciones.isBlank()) {
                ps.setNull(13, java.sql.Types.VARCHAR);
            } else {
                ps.setString(13, observaciones);
            }

            ps.setString(14, creadoPor);
            ps.execute();

            System.out.println(
                    "Transaccion registrada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al registrar la transaccion: "
                            + e.getMessage()
            );
        }
    }

    public void consultarTransaccion(int idTransaccion) {
        String sql =
                "{ call sp_consultar_transaccion(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idTransaccion);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println(
                            "\n--- DATOS DE LA TRANSACCION ---"
                    );
                    System.out.println(
                            "ID usuario: "
                                    + rs.getInt("p_id_usuario")
                    );
                    System.out.println(
                            "ID presupuesto: "
                                    + rs.getInt("p_id_presupuesto")
                    );
                    System.out.println(
                            "Anio: "
                                    + rs.getShort("p_anio")
                    );
                    System.out.println(
                            "Mes: "
                                    + rs.getShort("p_mes")
                    );
                    System.out.println(
                            "ID subcategoria: "
                                    + rs.getInt("p_id_subcategoria")
                    );
                    System.out.println(
                            "ID obligacion: "
                                    + rs.getObject("p_id_obligacion")
                    );
                    System.out.println(
                            "Tipo: "
                                    + rs.getString("p_tipo")
                    );
                    System.out.println(
                            "Descripcion: "
                                    + rs.getString("p_descripcion")
                    );
                    System.out.println(
                            "Monto: "
                                    + rs.getBigDecimal("p_monto")
                    );
                    System.out.println(
                            "Fecha: "
                                    + rs.getDate("p_fecha")
                    );
                    System.out.println(
                            "Metodo de pago: "
                                    + rs.getString("p_metodo_pago")
                    );
                    System.out.println(
                            "Numero de factura: "
                                    + rs.getString("p_numero_factura")
                    );
                    System.out.println(
                            "Observaciones: "
                                    + rs.getString("p_observaciones")
                    );
                    System.out.println(
                            "--------------------------------"
                    );
                } else {
                    System.out.println(
                            "No se encontro la transaccion."
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al consultar la transaccion: "
                            + e.getMessage()
            );
        }
    }

    public void listarTransacciones(int idUsuario) {
        String sql =
                "{ call sp_listar_transacciones(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idUsuario);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    System.out.println(
                            rs.getInt("p_id_transaccion")
                                    + " - "
                                    + rs.getString("p_tipo")
                                    + " - "
                                    + rs.getBigDecimal("p_monto")
                                    + " - "
                                    + rs.getDate("p_fecha")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar las transacciones: "
                            + e.getMessage()
            );
        }
    }

    public void actualizarTransaccion(
            int idTransaccion,
            int idPresupuesto,
            short anio,
            short mes,
            int idSubcategoria,
            Integer idObligacion,
            String tipo,
            String descripcion,
            BigDecimal monto,
            Date fecha,
            String metodoPago,
            String numeroFactura,
            String observaciones,
            String modificadoPor) {

        String sql =
                "{ call sp_actualizar_transaccion("
                        + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idTransaccion);
            cs.setInt(2, idPresupuesto);
            cs.setShort(3, anio);
            cs.setShort(4, mes);
            cs.setInt(5, idSubcategoria);

            if (idObligacion == null) {
                cs.setNull(6, java.sql.Types.INTEGER);
            } else {
                cs.setInt(6, idObligacion);
            }

            cs.setString(7, tipo);
            cs.setString(8, descripcion);
            cs.setBigDecimal(9, monto);
            cs.setDate(10, fecha);
            cs.setString(11, metodoPago);

            if (numeroFactura == null || numeroFactura.isBlank()) {
                cs.setNull(12, java.sql.Types.VARCHAR);
            } else {
                cs.setString(12, numeroFactura);
            }

            if (observaciones == null || observaciones.isBlank()) {
                cs.setNull(13, java.sql.Types.VARCHAR);
            } else {
                cs.setString(13, observaciones);
            }

            cs.setString(14, modificadoPor);
            cs.execute();

            System.out.println(
                    "Transaccion actualizada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al actualizar la transaccion: "
                            + e.getMessage()
            );
        }
    }

    public void eliminarTransaccion(int idTransaccion) {
        String sql =
                "{ call sp_eliminar_transaccion(?) }";

        try (
                Connection conn = Database.obtenerConexion();
                CallableStatement cs = conn.prepareCall(sql)
        ) {
            cs.setInt(1, idTransaccion);
            cs.execute();

            System.out.println(
                    "Transaccion eliminada correctamente."
            );

        } catch (Exception e) {
            System.out.println(
                    "Error al eliminar la transaccion: "
                            + e.getMessage()
            );
        }
    }
}