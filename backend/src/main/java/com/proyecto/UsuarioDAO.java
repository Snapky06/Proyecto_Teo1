package com.proyecto;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

public class UsuarioDAO {

    public void insertarUsuario(String nombres, String apellidos, String correoElectronico, java.sql.Date fechaRegistro, java.math.BigDecimal salarioMensualBase, String creadoPor) {
        String sql = "{ ? = call sp_insertar_usuario(?, ?, ?, ?, ?, ?) }";
        
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setString(2, nombres);
            cs.setString(3, apellidos);
            cs.setString(4, correoElectronico);
            cs.setDate(5, fechaRegistro);
            cs.setBigDecimal(6, salarioMensualBase);
            cs.setString(7, creadoPor);

            cs.execute();
            System.out.println("Usuario registrado con ID: " + cs.getInt(1));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarUsuarios() {
        String sql = "{ call sp_listar_usuarios }";
        
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getInt("p_id_usuario") + " - " + 
                                   rs.getString("p_nombres") + " " + 
                                   rs.getString("p_apellidos") + " - " + 
                                   rs.getString("p_correo_electronico"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void consultarUsuario(int idUsuario) {
        String sql = "{ call sp_consultar_usuario(?) }";
        
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n--- DETALLES DEL USUARIO ---");
                    System.out.println("Nombre: " + rs.getString("p_nombres") + " " + rs.getString("p_apellidos"));
                    System.out.println("Correo: " + rs.getString("p_correo_electronico"));
                    System.out.println("Salario Base: L. " + rs.getBigDecimal("p_salario_mensual_base"));
                    System.out.println("Fecha de registro: " + rs.getDate("p_fecha_registro"));
                    System.out.println("Estado Activo: " + rs.getBoolean("p_estado"));
                    System.out.println("----------------------------");
                } else {
                    System.out.println("-> No se encontró ningún usuario con el ID: " + idUsuario);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el usuario: " + e.getMessage());
        }
    }

    public void actualizarUsuario(int idUsuario, String modificadoPor, String nombres, String apellidos, String correo, java.math.BigDecimal salario) {
        String sql = "{ call sp_actualizar_usuario(?, ?, ?, ?, ?, ?) }";
        
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            cs.setString(2, modificadoPor);
            cs.setString(3, nombres);
            cs.setString(4, apellidos);
            cs.setString(5, correo);
            cs.setBigDecimal(6, salario);

            cs.execute();
            System.out.println("-> ¡Usuario actualizado correctamente en la base de datos!");

        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    public void eliminarUsuario(int idUsuario, String modificadoPor) {
        String sql = "{ call sp_eliminar_usuario(?, ?) }";
        
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            cs.setString(2, modificadoPor);

            cs.execute();
            System.out.println("-> ¡Usuario eliminado (desactivado) exitosamente!");

        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}