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
}