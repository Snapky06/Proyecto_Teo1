package com.proyecto;

import com.proyecto.config.Database;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.LinkedHashMap;

public abstract class BaseDAO {

    protected void asignarParametros(PreparedStatement ps, Object... parametros) throws Exception {
        for (int i = 0; i < parametros.length; i++) {
            Object param = parametros[i];
            
            if (param == null) {
                ps.setNull(i + 1, java.sql.Types.VARCHAR);
            } else if (param instanceof String) {
                ps.setString(i + 1, (String) param);
            } else if (param instanceof Integer) {
                ps.setInt(i + 1, (Integer) param);
            } else if (param instanceof Short) {
                ps.setShort(i + 1, (Short) param);
            } else if (param instanceof BigDecimal) {
                ps.setBigDecimal(i + 1, (BigDecimal) param);
            } else if (param instanceof java.sql.Date) {
                ps.setDate(i + 1, (java.sql.Date) param);
            } else if (param instanceof Boolean) {
                ps.setBoolean(i + 1, (Boolean) param);
            } else {
                ps.setObject(i + 1, param);
            }
        }
    }

    protected void ejecutarProcedimiento(String sql, String mensajeExito, String mensajeError, Object... parametros) {
        try (Connection conn = Database.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            asignarParametros(ps, parametros);
            ps.execute();
            
            if (mensajeExito != null && !mensajeExito.isEmpty()) {
                System.out.println(mensajeExito);
            }
        } catch (Exception e) {
            System.out.println(mensajeError + ": " + e.getMessage());
        }
    }

    protected BigDecimal ejecutarFuncionDecimal(String sql, String mensajeError, Object... parametros) {
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, parametros);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                }
            }
        } catch (Exception e) {
            System.out.println(mensajeError + ": " + e.getMessage());
        }
        return null;
    }

    protected Integer ejecutarFuncionEntera(String sql, String mensajeError, Object... parametros) {
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, parametros);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println(mensajeError + ": " + e.getMessage());
        }
        return null;
    }

    protected Boolean ejecutarFuncionBooleana(String sql, String mensajeError, Object... parametros) {
        try (Connection conn = Database.obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            asignarParametros(cs, parametros);
            
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (Exception e) {
            System.out.println(mensajeError + ": " + e.getMessage());
        }
        return null;
    }

    protected Map<Integer, String> ejecutarFuncionDiccionario(String sql, String mensajeError, Object... parametros) {
    Map<Integer, String> resultado = new LinkedHashMap<>();
    
    try (Connection conn = Database.obtenerConexion();
         CallableStatement cs = conn.prepareCall(sql)) {
         
        asignarParametros(cs, parametros);
        
        try (ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                resultado.put(rs.getInt(1), rs.getString(2));
            }
        }
    } catch (Exception e) {
        System.out.println(mensajeError + ": " + e.getMessage());
    }
    
    return resultado;
}
}