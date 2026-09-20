package com.proyecto.cruds;

import com.proyecto.config.Database;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.math.BigDecimal;
import com.proyecto.BaseDAO;

public class UsuarioDAO extends BaseDAO {

    public void insertarUsuario(String nombres, String apellidos, String correo, Date fecha, BigDecimal salario, String creadoPor) {
        String sql = "EXECUTE PROCEDURE sp_insertar_usuario(?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Usuario registrado correctamente.", "Error al registrar", 
            nombres, apellidos, correo, fecha, salario, creadoPor);
    }

    public void actualizarUsuario(int idUsuario, String modificadoPor, String nombres, String apellidos, String correo, BigDecimal salario) {
        String sql = "EXECUTE PROCEDURE sp_actualizar_usuario(?, ?, ?, ?, ?, ?)";
        ejecutarProcedimiento(sql, "Usuario actualizado correctamente.", "Error al actualizar", 
            idUsuario, modificadoPor, nombres, apellidos, correo, salario);
    }

    public void eliminarUsuario(int idUsuario, String modificadoPor) {
        String sql = "EXECUTE PROCEDURE sp_eliminar_usuario(?, ?)";
        ejecutarProcedimiento(sql, "Usuario eliminado correctamente.", "Error al eliminar", 
            idUsuario, modificadoPor);
    }

    public void listarUsuarios() {
        String sql = "{ call sp_listar_usuarios }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("p_id_usuario"));
                System.out.println("Nombres: " + rs.getString("p_nombres"));
                System.out.println("Apellidos: " + rs.getString("p_apellidos"));
                System.out.println("Correo: " + rs.getString("p_correo_electronico"));
                System.out.println("----------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
    }

    public void consultarUsuario(int idUsuario) {
        String sql = "{ call sp_consultar_usuario(?) }";
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("--- DETALLES DEL USUARIO ---");
                    System.out.println("Nombres: " + rs.getString("p_nombres"));
                    System.out.println("Apellidos: " + rs.getString("p_apellidos"));
                    System.out.println("Correo: " + rs.getString("p_correo_electronico"));
                    System.out.println("Fecha registro: " + rs.getDate("p_fecha_registro"));
                    System.out.println("Salario base: " + rs.getBigDecimal("p_salario_mensual_base"));
                    System.out.println("Estado activo: " + rs.getBoolean("p_estado"));
                    System.out.println("----------------------------");
                } else {
                    System.out.println("No se encontro el usuario con ID: " + idUsuario);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el usuario: " + e.getMessage());
        }
    }

    public Integer loginPorNombre(String nombres) {
    String sql = "{ call sp_login_usuario(?) }";
    return ejecutarFuncionEntera(sql, "Error al intentar iniciar sesion", nombres);
}

}